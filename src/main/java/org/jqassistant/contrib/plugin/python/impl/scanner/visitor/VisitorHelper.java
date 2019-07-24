package org.jqassistant.contrib.plugin.python.impl.scanner.visitor;

import com.buschmais.jqassistant.core.scanner.api.ScannerContext;
import org.jqassistant.contrib.plugin.python.api.model.Field;
import org.jqassistant.contrib.plugin.python.api.model.PythonSourceFile;
import org.jqassistant.contrib.plugin.python.api.model.Type;

import java.util.Iterator;

/**
 * The helper delegates creation and caching of types to the type resolver,
 * holds a reference of the type solver and its facade, and provides the
 * visitors with descriptors.
 *
 * @author Kevin M. Shrestha
 */
public class VisitorHelper {
    private ScannerContext scannerContext;
    private PythonSourceFile pythonSourceFile;

    public VisitorHelper(ScannerContext scannerContext, PythonSourceFile pythonSourceFile) {
        this.scannerContext = scannerContext;
        this.pythonSourceFile = pythonSourceFile;
    }

    /**
     * Return the field descriptor for the given field signature and type
     * descriptor.
     *
     * @param signature
     *            The field signature.
     * @param parent
     *            The parent type descriptor.
     * @return The field descriptor.
     */
    Field getField(String signature, Type parent) {
        Field Field = null;
        for (Object member : parent.getDeclaredFields()) {
            if (member instanceof Field) {
                Field existingField = (Field) member;
                if (existingField.getSignature().equals(signature)) {
                    Field = existingField;
                }
            }
        }
        if (Field != null) {
            return Field;
        }
        Field = scannerContext.getStore().create(Field.class);
        Field.setName(signature.substring(signature.indexOf(" ") + 1));
        Field.setSignature(signature);
        parent.getDeclaredFields().add(Field);
        return Field;
    }
}
