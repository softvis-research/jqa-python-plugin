package org.jqassistant.contrib.plugin.python.impl.scanner.visitor;

import com.buschmais.jqassistant.core.store.api.model.Descriptor;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

public abstract class AbstractPythonSourceVisitor<T extends Descriptor> extends AbstractParseTreeVisitor<T> {
    protected VisitorHelper visitorHelper;
    protected Descriptor descriptor;

    public AbstractPythonSourceVisitor(VisitorHelper visitorHelper) {
        this.visitorHelper = visitorHelper;
    }

}
