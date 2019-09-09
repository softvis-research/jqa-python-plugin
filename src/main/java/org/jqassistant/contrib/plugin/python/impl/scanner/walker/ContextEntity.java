package org.jqassistant.contrib.plugin.python.impl.scanner.walker;

import java.util.Optional;

import com.buschmais.jqassistant.core.store.api.model.Descriptor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.antlr.v4.runtime.ParserRuleContext;
import org.jqassistant.contrib.plugin.python.api.model.Method;
import org.jqassistant.contrib.plugin.python.api.model.PythonFile;
import org.jqassistant.contrib.plugin.python.api.model.PythonPackage;
import org.jqassistant.contrib.plugin.python.impl.scanner.RuleIndex;

@AllArgsConstructor
public class ContextEntity {
    @Getter
    final ParserRuleContext context;
    @Getter
    final Descriptor entity;

    public int getRuleIndex() {
        return context.getRuleIndex();
    }

//    public void allocateChild(final Descriptor child, final ClassdefContext ctx) {
//        if (isPythonFile(context)) {
//            PythonFile e = (PythonFile) entity;
//            child.setSourceFileName(e.getFileName());
//            e.getContainedClasses().add(object);
//        }
//    }
//
//    public boolean isPythonFile(final ParserRuleContext ctx) {
//        return ctx.getRuleIndex() == RuleIndex.FILE.getValue();
//    }

    public boolean hasContext() {
        return !context.isEmpty();
    }

    public boolean isPythonPackage() {
        return this.context.getRuleIndex() == RuleIndex.PACKAGE.getValue();
    }

    public Optional<PythonPackage> getPythonPackage() {
        return isPythonPackage() ? Optional.of((PythonPackage) getEntity()) : Optional.empty();
    }

    public boolean isPythonFile() {
        return this.context.getRuleIndex() == RuleIndex.FILE.getValue();
    }

    public Optional<PythonFile> getPythonFile() {
        return isPythonFile() ? Optional.of((PythonFile) getEntity()) : Optional.empty();
    }

    public boolean isMethod() {
        return this.context.getRuleIndex() == RuleIndex.METHOD.getValue();
    }

    public Optional<Method> getMethod() {
        return isMethod() ? Optional.of((Method) getEntity()) : Optional.empty();
    }
}
