package org.jqassistant.contrib.plugin.python.impl.scanner.visitor;

import org.antlr.v4.runtime.tree.ParseTree;
import org.jqassistant.contrib.plugin.python.api.model.Variable;
import org.jqassistant.contrib.plugin.python.api.model.ObjectDescriptor;

/**
 * This visitor handles variables and creates corresponding descriptors.
 */
public class VariableVisitor extends AbstractPythonSourceVisitor<Variable> {

    public VariableVisitor(StoreHelper storeHelper) {
        super(storeHelper);
    }

    @Override
    public Variable visit(final ParseTree tree) {
        String signature = "";
        ObjectDescriptor parentObject = null;

        ParseTree parent = tree.getParent();
        String text = tree.getText();


//        System.out.println(storeHelper.getVariable(signature, parentObject));
//        return visitorHelper.getField(signature, parent);
        return tree.accept(this);
    }
}
