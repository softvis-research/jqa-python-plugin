package org.jqassistant.contrib.plugin.python.impl.scanner.visitor;

import org.antlr.v4.runtime.tree.ParseTree;
import org.jqassistant.contrib.plugin.python.api.model.Field;
import org.jqassistant.contrib.plugin.python.api.model.Type;

/**
 * This visitor handles parsed fields and enum values and creates corresponding descriptors.
 */
public class FieldVisitor extends AbstractPythonSourceVisitor<Field> {

    public FieldVisitor(VisitorHelper visitorHelper) {
        super(visitorHelper);
    }

    @Override
    public Field visit(final ParseTree tree) {
        String signature = "";
        Type parent = null;
        String text = tree.getText();

        System.out.println(visitorHelper.getField(signature, parent));
//        return visitorHelper.getField(signature, parent);
        return tree.accept(this);
    }
}
