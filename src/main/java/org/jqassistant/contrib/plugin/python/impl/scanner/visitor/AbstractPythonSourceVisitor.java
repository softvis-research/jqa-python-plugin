package org.jqassistant.contrib.plugin.python.impl.scanner.visitor;

import com.buschmais.jqassistant.core.store.api.model.Descriptor;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.jqassistant.contrib.plugin.python.api.model.Field;
import org.jqassistant.contrib.plugin.python.api.model.Type;

import java.util.Iterator;

public abstract class AbstractPythonSourceVisitor<T extends Descriptor> extends AbstractParseTreeVisitor<T> {
    protected VisitorHelper visitorHelper;
    protected Descriptor descriptor;

    public AbstractPythonSourceVisitor(VisitorHelper visitorHelper) {
        this.visitorHelper = visitorHelper;
    }

}
