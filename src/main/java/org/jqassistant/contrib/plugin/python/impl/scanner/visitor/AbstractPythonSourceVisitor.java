package org.jqassistant.contrib.plugin.python.impl.scanner.visitor;

import com.buschmais.jqassistant.core.store.api.model.Descriptor;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

public abstract class AbstractPythonSourceVisitor<T extends Descriptor> extends AbstractParseTreeVisitor<T> {
    protected StoreHelper storeHelper;
    protected Descriptor descriptor;

    public AbstractPythonSourceVisitor(StoreHelper storeHelper) {
        this.storeHelper = storeHelper;
    }

}
