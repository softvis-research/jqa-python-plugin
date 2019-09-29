package org.jqassistant.contrib.plugin.python.impl.scanner.walker.cache;

import com.buschmais.jqassistant.core.store.api.model.Descriptor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.ParserRuleContext;
import org.jqassistant.contrib.plugin.python.api.model.Method;
import org.jqassistant.contrib.plugin.python.api.model.Parameter;
import org.jqassistant.contrib.plugin.python.api.model.PythonClass;
import org.jqassistant.contrib.plugin.python.api.model.PythonFile;
import org.jqassistant.contrib.plugin.python.api.model.PythonPackage;
import org.jqassistant.contrib.plugin.python.impl.scanner.RuleIndex;

import java.util.Optional;

@AllArgsConstructor
@Getter
@Setter
public class ContextEntity {
    final String name;
    ParserRuleContext context;
    final Descriptor entity;

    public int getRuleIndex() {
        return context.getRuleIndex();
    }

    public boolean hasContext() {
        return !context.isEmpty();
    }

    //    public void allocateChild(final Descriptor child, final ClassdefContext ctx) {
//        if (isPythonFile(context)) {
//            PythonFile e = (PythonFile) entity;
//            child.setSourceFileName(e.getFileName());
//            e.getContainedClasses().add(object);
//        }
//    }

    public boolean isTypeOfRuleIndex(int ruleIndex) {
        return this.context.getRuleIndex() == ruleIndex;
    }

    public boolean isPythonPackage() {
        return this.context.getRuleIndex() == RuleIndex.PACKAGE.getValue();
    }
    public boolean isPythonFile() {
        return this.context.getRuleIndex() == RuleIndex.FILE.getValue();
    }
    public boolean isMethod() {
        return this.context.getRuleIndex() == RuleIndex.METHOD.getValue();
    }
    public boolean isParameter() {
        return this.context.getRuleIndex() == RuleIndex.PARAMETER.getValue();
    }
    public boolean isStatement() {
        return this.context.getRuleIndex() == RuleIndex.STATEMENT.getValue();
    }
    public boolean isImportFrom() {
        return this.context.getRuleIndex() == RuleIndex.IMPORT_FROM.getValue();
    }
    public boolean isImportAsName() {
        return this.context.getRuleIndex() == RuleIndex.IMPORT_AS_NAME.getValue();
    }
    public boolean isPythonClass() {
        return this.context.getRuleIndex() == RuleIndex.CLASS.getValue();
    }


    public Optional<PythonPackage> getPythonPackage() {
        return isPythonPackage() ? Optional.of((PythonPackage) getEntity()) : Optional.empty();
    }
    public Optional<PythonFile> getPythonFile() {
        return isPythonFile() ? Optional.of((PythonFile) getEntity()) : Optional.empty();
    }
    public Optional<Method> getMethod() {
        return isMethod() ? Optional.of((Method) getEntity()) : Optional.empty();
    }
    public Optional<Parameter> getParameter() {
        return isParameter() ? Optional.of((Parameter) getEntity()) : Optional.empty();
    }
//    public Optional<Statement> getStatement() {
//        return isStatement() ? Optional.of((Statement) getEntity()) : Optional.empty();
//    }
//    public Optional<Import> getImportFrom() {
//        return isImportFrom() ? Optional.of((Import) getEntity()) : Optional.empty();
//    }
//    public Optional<Import> getImportAsName() {
//        return isImportAsName() ? Optional.of((Import) getEntity()) : Optional.empty();
//    }
    public Optional<PythonClass> getPythonClass() {
        return isPythonClass() ? Optional.of((PythonClass) getEntity()) : Optional.empty();
    }
}
