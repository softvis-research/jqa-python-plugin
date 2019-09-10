package org.jqassistant.contrib.plugin.python.impl.scanner.walker;

import com.buschmais.jqassistant.core.scanner.api.ScannerContext;
import com.buschmais.jqassistant.plugin.common.api.model.FileDescriptor;
import lombok.AllArgsConstructor;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser;
import org.jqassistant.contrib.plugin.python.api.model.Method;
import org.jqassistant.contrib.plugin.python.api.model.Parameter;
import org.jqassistant.contrib.plugin.python.api.model.PythonClass;
import org.jqassistant.contrib.plugin.python.api.model.PythonFile;
import org.jqassistant.contrib.plugin.python.api.model.PythonPackage;
import org.jqassistant.contrib.plugin.python.impl.scanner.RuleIndex;

import java.util.Optional;

@AllArgsConstructor
public class WalkerHelper {
    private ScannerContext scannerContext;
    private StoreHelper storeHelper;

    public void createFile(Python3Parser.File_inputContext ctx) {
        FileDescriptor fileDescriptor = scannerContext.getCurrentDescriptor();
        PythonFile pythonFile = storeHelper.createAndCache(PythonFile.class, ctx, fileDescriptor);

        storeHelper.cacheFile(pythonFile, ctx);
        pythonFile.setFullQualifiedName(storeHelper.getPythonPackage().getFileName());
        pythonFile.setName(fileDescriptor.getFileName());
        storeHelper.getPythonPackage().getContains().add(pythonFile);

        Optional<ContextEntity> parentInCache = SearchHelper.findParentInCache(storeHelper, ctx, RuleIndex.PACKAGE);
        if (parentInCache.isPresent()) {
            Optional<PythonPackage> opt = parentInCache.get().getPythonPackage();
            opt.ifPresent(parent -> parent.getContains().add(pythonFile));
        }

        storeHelper.setPythonFile(pythonFile);
    }

    public void createParameters(final Python3Parser.ParametersContext ctx) {
        Parameter object = storeHelper.createAndCache(Parameter.class, ctx, null);
        object.setName(SearchHelper.findNameToken(ctx));
        Optional<ContextEntity> parentInCache = SearchHelper.findParentInCache(storeHelper, ctx, RuleIndex.METHOD);
        if (parentInCache.isPresent()) {
            Optional<Method> opt = parentInCache.get().getMethod();
            opt.ifPresent(parent -> parent.getParameters().add(object));
        } else {
            System.out.println("parent not found");
        }
    }

    public void createFunction(final Python3Parser.FuncdefContext ctx) {
        Method object = storeHelper.createAndCache(Method.class, ctx, null);
        object.setName(SearchHelper.findNameToken(ctx));
        Optional<ContextEntity> parentInCache = SearchHelper.findParentInCache(storeHelper, ctx, RuleIndex.ANY);
        if (parentInCache.isPresent()) {
            Optional<PythonFile> opt = parentInCache.get().getPythonFile();
            opt.ifPresent(parent -> parent.getDefinedMethods().add(object));

            Optional<PythonClass> opt2 = parentInCache.get().getPythonClass();
            opt2.ifPresent(parent -> parent.getDefinedMethods().add(object));
        } else {
            System.out.println("parent not found");
        }
    }

    public void createImport(Python3Parser.Import_stmtContext ctx) {
//        PythonSourceFile object = storeHelper.createAndCache(PythonSourceFile.class, ctx, null);
//        pythonSourceFile.getImports().add(object); //TODO: find correct import type
    }

    public void createClass(Python3Parser.ClassdefContext ctx) {
        PythonClass object = storeHelper.createAndCache(PythonClass.class, ctx, null);
        object.setName(SearchHelper.findNameToken(ctx));

        Optional<ContextEntity> parentInCache = SearchHelper.findParentInCache(storeHelper, ctx, RuleIndex.ANY);
        if (parentInCache.isPresent()) {
            Optional<PythonFile> opt = parentInCache.get().getPythonFile();
            opt.ifPresent(parent -> parent.getContainedClasses().add(object));
            opt.ifPresent(parent -> object.setSourceFileName(parent.getFileName()));
        } else {
            System.out.println("parent not found");
        }
    }

}
