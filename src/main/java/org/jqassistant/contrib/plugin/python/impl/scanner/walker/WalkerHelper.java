package org.jqassistant.contrib.plugin.python.impl.scanner.walker;

import com.buschmais.jqassistant.core.scanner.api.ScannerContext;
import com.buschmais.jqassistant.core.store.api.model.Descriptor;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser;
import org.jqassistant.contrib.plugin.python.api.model.ClassDescriptor;
import org.jqassistant.contrib.plugin.python.api.model.Method;
import org.jqassistant.contrib.plugin.python.api.model.Parameter;
import org.jqassistant.contrib.plugin.python.api.model.PythonSourceFile;
import org.jqassistant.contrib.plugin.python.impl.scanner.RuleIndex;
import org.jqassistant.contrib.plugin.python.impl.scanner.visitor.SearchHelper;
import org.jqassistant.contrib.plugin.python.impl.scanner.visitor.StoreHelper;

public class WalkerHelper {
    private ScannerContext scannerContext;
    private PythonSourceFile pythonSourceFile;
    private StoreHelper storeHelper;

    public WalkerHelper(ScannerContext scannerContext, PythonSourceFile pythonSourceFile, StoreHelper storeHelper) {
        this.scannerContext = scannerContext;
        this.pythonSourceFile = pythonSourceFile;
        this.storeHelper = storeHelper;
    }

    public ScannerContext getScannerContext() {
        return scannerContext;
    }

    public void createFile(PythonSourceFile pythonSourceFile, Python3Parser.File_inputContext ctx) {
        PythonSourceFile object = storeHelper.createAndCache(PythonSourceFile.class, ctx);
        object.setFileName(pythonSourceFile.getFileName());

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

    public void createParameters(PythonSourceFile pythonSourceFile, final Python3Parser.ParametersContext ctx) {
        Parameter object = storeHelper.createAndCache(Parameter.class, ctx);
        object.setName(SearchHelper.findNameToken(ctx));
        ImmutablePair<ParserRuleContext, Descriptor> parentPair = SearchHelper.findParentInCache(storeHelper, ctx, RuleIndex.METHOD);
        if (parentPair != null) {
            ParserRuleContext left = parentPair.getLeft();
            if (left.getRuleIndex() == RuleIndex.METHOD.getValue()) {
                Method right = (Method) parentPair.getRight();
                right.getParameters().add(object);
            }
        }
    }

    public void createFunction(PythonSourceFile pythonSourceFile, final Python3Parser.FuncdefContext ctx) {
        Method object = storeHelper.createAndCache(Method.class, ctx);
        object.setName(SearchHelper.findNameToken(ctx));
        ImmutablePair<ParserRuleContext, Descriptor> parentPair = SearchHelper.findParentInCache(storeHelper, ctx, RuleIndex.ANY);
        if (parentPair != null) {
            ParserRuleContext left = parentPair.getLeft();
            if (left.getRuleIndex() == RuleIndex.FILE.getValue()) {
                PythonSourceFile right = (PythonSourceFile) parentPair.getRight();
                right.getDefines().add(object);
            }
            if (left.getRuleIndex() == RuleIndex.CLASS.getValue()) {
                ClassDescriptor right = (ClassDescriptor) parentPair.getRight();
                right.getDeclaredMethods().add(object);
            }
        }
    }

    public void createImport(PythonSourceFile pythonSourceFile, Python3Parser.Import_stmtContext ctx) {
//        PythonSourceFile object = storeHelper.createAndCache(PythonSourceFile.class, ctx);
//        pythonSourceFile.getImports().add(object); //TODO: find correct import type
    }

    public void createClass(PythonSourceFile pythonSourceFile, Python3Parser.ClassdefContext ctx) {
        ClassDescriptor object = storeHelper.createAndCache(ClassDescriptor.class, ctx);
        object.setName(SearchHelper.findNameToken(ctx));
        object.setSourceFileName(pythonSourceFile.getFileName());
        pythonSourceFile.getContains().add(object);  //TODO: find correct parent
    }

}
