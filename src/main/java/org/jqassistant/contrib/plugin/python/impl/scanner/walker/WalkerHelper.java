package org.jqassistant.contrib.plugin.python.impl.scanner.walker;

import com.buschmais.jqassistant.core.scanner.api.ScannerContext;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser;
import org.jqassistant.contrib.plugin.python.api.model.Class;
import org.jqassistant.contrib.plugin.python.api.model.Method;
import org.jqassistant.contrib.plugin.python.api.model.Parameter;
import org.jqassistant.contrib.plugin.python.api.model.PythonFile;
import org.jqassistant.contrib.plugin.python.impl.scanner.RuleIndex;

public class WalkerHelper {
    private ScannerContext scannerContext;
    private StoreHelper storeHelper;

    public WalkerHelper(ScannerContext scannerContext, StoreHelper storeHelper) {
        this.scannerContext = scannerContext;
        this.storeHelper = storeHelper;
    }

    public ScannerContext getScannerContext() {
        return scannerContext;
    }

    public void createFile(Python3Parser.File_inputContext ctx) {
        PythonFile object = storeHelper.createAndCache(PythonFile.class, ctx);
        object.setFileName(ctx.getText());

        ParseTree import_stmt = searchChildrenForStringText(ctx, "import_stmt");
    }

    private ParseTree searchChildrenForStringText(Python3Parser.File_inputContext ctx, String searchString) {
        final int childCount = ctx.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ParseTree child = ctx.getChild(i);
            if (child.getText().equals(searchString)) {
                return child;
            }
        }
        return null;
    }

    public void createParameters(final Python3Parser.ParametersContext ctx) {
        Parameter object = storeHelper.createAndCache(Parameter.class, ctx);
        object.setName(SearchHelper.findNameToken(ctx));
        ContextEntity parentCe = SearchHelper.findParentInCache(storeHelper, ctx, RuleIndex.METHOD);
        if (parentCe != null) {
            ParserRuleContext c = parentCe.getContext();
            if (c.getRuleIndex() == RuleIndex.METHOD.getValue()) {
                Method e = (Method) parentCe.getEntity();
                e.getParameters().add(object);
            }
        } else {
            System.out.println("parent not found");
        }
    }

    public void createFunction(final Python3Parser.FuncdefContext ctx) {
        Method object = storeHelper.createAndCache(Method.class, ctx);
        object.setName(SearchHelper.findNameToken(ctx));
        ContextEntity parentCe = SearchHelper.findParentInCache(storeHelper, ctx, RuleIndex.ANY);
        if (parentCe != null) {
            ParserRuleContext c = parentCe.getContext();
            if (c.getRuleIndex() == RuleIndex.FILE.getValue()) {
                PythonFile e = (PythonFile) parentCe.getEntity();
                e.getDefinedMethods().add(object);
            }
            if (c.getRuleIndex() == RuleIndex.CLASS.getValue()) {
                Class e = (Class) parentCe.getEntity();
                e.getDefinedMethods().add(object);
            }
        } else {
            System.out.println("parent not found");
        }
    }

    public void createImport(Python3Parser.Import_stmtContext ctx) {
//        PythonSourceFile object = storeHelper.createAndCache(PythonSourceFile.class, ctx);
//        pythonSourceFile.getImports().add(object); //TODO: find correct import type
    }

    public void createClass(Python3Parser.ClassdefContext ctx) {
        Class object = storeHelper.createAndCache(Class.class, ctx);
        object.setName(SearchHelper.findNameToken(ctx));

        ContextEntity parentCe = SearchHelper.findParentInCache(storeHelper, ctx, RuleIndex.ANY);
        if (parentCe != null) {
            ParserRuleContext c = parentCe.getContext();
            if (c.getRuleIndex() == RuleIndex.FILE.getValue()) {
                PythonFile e = (PythonFile) parentCe.getEntity();
                object.setSourceFileName(e.getFileName());
                e.getContainedClasses().add(object);
            }
        } else {
            System.out.println("parent not found");
        }
    }

}
