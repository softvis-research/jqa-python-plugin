package org.jqassistant.contrib.plugin.python.impl.scanner.walker;

import com.buschmais.jqassistant.core.scanner.api.ScannerContext;
import com.buschmais.jqassistant.plugin.common.api.model.FileDescriptor;
import lombok.AllArgsConstructor;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.Expr_stmtContext;
import org.jqassistant.contrib.plugin.python.api.model.Method;
import org.jqassistant.contrib.plugin.python.api.model.Parameter;
import org.jqassistant.contrib.plugin.python.api.model.PythonClass;
import org.jqassistant.contrib.plugin.python.api.model.PythonException;
import org.jqassistant.contrib.plugin.python.api.model.PythonFile;
import org.jqassistant.contrib.plugin.python.api.model.PythonPackage;
import org.jqassistant.contrib.plugin.python.api.model.Variable;
import org.jqassistant.contrib.plugin.python.api.scanner.PythonScope;
import org.jqassistant.contrib.plugin.python.impl.scanner.RuleIndex;
import org.jqassistant.contrib.plugin.python.impl.scanner.walker.cache.CacheManager;
import org.jqassistant.contrib.plugin.python.impl.scanner.walker.cache.ContextEntity;

import java.util.Optional;

@AllArgsConstructor
public class WalkerHelper {
    private ScannerContext scannerContext;
    private CacheManager cacheManager;

    public void createFile(Python3Parser.File_inputContext ctx) {
        FileDescriptor fileDescriptor = scannerContext.getCurrentDescriptor();
        String nameToken = fileDescriptor.getFileName();
        String nameNoSlash = nameToken.replace("/", "");

        PythonFile pythonFile = cacheManager.getOrphanFileFromCacheOrCreate(nameNoSlash, ctx, fileDescriptor);

        pythonFile.setFullQualifiedName(cacheManager.getPythonPackage().getFileName() + nameToken);
        pythonFile.setName(nameNoSlash);
        pythonFile.setFileName(nameNoSlash);
        cacheManager.getPythonPackage().getContains().add(pythonFile);

        Optional<ContextEntity> parentInCache = SearchHelper.findParentInAllCache(cacheManager, ctx, RuleIndex.PACKAGE);
        if (parentInCache.isPresent()) {
            Optional<PythonPackage> opt = parentInCache.get().getPythonPackage();
            opt.ifPresent(parent -> parent.getContains().add(pythonFile));
        }

        cacheManager.setPythonFile(pythonFile);
    }

    public void createImport(Python3Parser.Import_stmtContext ctx) {
        String nameToken = SearchHelper.findToken(ctx, Python3Parser.NAME, 0);
        String subString = nameToken;
        int depth = 0;
        while (ctx.getText().split(subString)[0].endsWith(".")) {
            subString = "." + subString;
            depth++;
        }
        PythonFile object = cacheManager.getRuleOrOrphanFromCacheOrCreate(nameToken, PythonFile.class, null, null, RuleIndex.FILE);
        while (nameToken.startsWith(".")) {
            nameToken = nameToken.substring(1);
        }
        object.setName(nameToken);
        object.setFileName(nameToken + PythonScope.FILE_EXTENSION);

        Optional<ContextEntity> parentInCache = SearchHelper.findParentInThisCache(cacheManager.getCurrentCache(), ctx, RuleIndex.ANY);
        if (parentInCache.isPresent()) {
            Optional<PythonFile> opt = parentInCache.get().getPythonFile();
//            opt.ifPresent(parent -> object.getDependents().add(parent));
            opt.ifPresent(parent -> parent.getDependencies().add(object));
        } else {
            System.out.println("parent not found");
        }
    }

    public void createParameters(final Python3Parser.TfpdefContext ctx) {
        String name = SearchHelper.findToken(ctx, Python3Parser.NAME, 0);
        Parameter object = cacheManager.createAndCache(name, Parameter.class, ctx, null);
        object.setName(name);
        Optional<ContextEntity> parentInCache = SearchHelper.findParentInThisCache(cacheManager.getCurrentCache(), ctx, RuleIndex.METHOD);
        if (parentInCache.isPresent()) {
            Optional<Method> opt = parentInCache.get().getMethod();
            opt.ifPresent(parent -> parent.getParameters().add(object));
        } else {
            System.out.println("parent not found");
        }
    }

    public void createFunction(final Python3Parser.FuncdefContext ctx) {
        String name = SearchHelper.findToken(ctx, Python3Parser.NAME, 0);
        Method object = cacheManager.createAndCache(name, Method.class, ctx, null);
        object.setName(name);
        Optional<ContextEntity> parentInCache = SearchHelper.findParentInThisCache(cacheManager.getCurrentCache(), ctx, RuleIndex.ANY);
        if (parentInCache.isPresent()) {
            Optional<PythonFile> opt = parentInCache.get().getPythonFile();
            opt.ifPresent(parent -> parent.getDefinedMethods().add(object));

            Optional<PythonClass> opt2 = parentInCache.get().getPythonClass();
            opt2.ifPresent(parent -> parent.getDefinedMethods().add(object));
        } else {
            System.out.println("parent not found");
        }
    }

    public void createClass(Python3Parser.ClassdefContext ctx) {
        String name = SearchHelper.findToken(ctx, Python3Parser.NAME, 0);
        String superClassName = SearchHelper.findToken(ctx, Python3Parser.NAME, 1);

        PythonClass object = cacheManager.getRuleOrOrphanFromCacheOrCreate(name, PythonClass.class, ctx, null, RuleIndex.CLASS);
        object.setName(name);

        if (superClassName != null && !superClassName.equals("object")) {
            if (superClassName.equals("Exception")) {
                object.setSuperClass(cacheManager.getRuleOrOrphanFromCacheOrCreate(superClassName, PythonException.class, null, null, RuleIndex.EXCEPTION));
            } else {
                object.setSuperClass(cacheManager.getRuleOrOrphanFromCacheOrCreate(superClassName, PythonClass.class, null, null, RuleIndex.CLASS));
            }
        }

        Optional<ContextEntity> parentInCache = SearchHelper.findParentInThisCache(cacheManager.getCurrentCache(), ctx, RuleIndex.ANY);
        if (parentInCache.isPresent()) {
            Optional<PythonFile> opt = parentInCache.get().getPythonFile();
//            opt.ifPresent(object::setParentObject);
            opt.ifPresent(parent -> parent.getContainedClasses().add(object));
            opt.ifPresent(parent -> object.setSourceFileName(parent.getFileName()));
        } else {
            System.out.println("parent not found");
        }
    }

    public void createException(Python3Parser.Except_clauseContext ctx) {
        String name = SearchHelper.findToken(ctx, Python3Parser.NAME, 0);
        PythonException object = cacheManager.getRuleOrOrphanFromCacheOrCreate(name, PythonException.class, ctx, null, RuleIndex.EXCEPTION);
        object.setName(name);

        Optional<ContextEntity> parentInCache = SearchHelper.findParentInThisCache(cacheManager.getCurrentCache(), ctx, RuleIndex.METHOD);
        if (parentInCache.isPresent()) {
            Optional<Method> opt = parentInCache.get().getMethod();
            opt.ifPresent(parent -> parent.getDeclaredThrowables().add(object));
        } else {
            System.out.println("parent not found");
        }
    }

    public void createExprStatement(final Expr_stmtContext ctx) {
        String name = SearchHelper.findToken(ctx, Python3Parser.NAME, 0);
        if (!name.isEmpty() && !name.equals("null")) {
            Variable object = cacheManager.getRuleOrOrphanFromCacheOrCreate(name, Variable.class, ctx, null, RuleIndex.EXPR_STATEMENT);
            object.setName(name);
        }
    }
}
