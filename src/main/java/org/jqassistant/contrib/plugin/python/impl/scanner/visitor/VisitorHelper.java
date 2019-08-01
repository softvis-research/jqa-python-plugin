package org.jqassistant.contrib.plugin.python.impl.scanner.visitor;

import com.buschmais.jqassistant.core.scanner.api.ScannerContext;
import org.jqassistant.contrib.plugin.python.api.model.Variable;
import org.jqassistant.contrib.plugin.python.api.model.PythonSourceFile;
import org.jqassistant.contrib.plugin.python.api.model.ObjectDescriptor;

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
    Variable getVariable(String signature, ObjectDescriptor parent) {
        Variable variable = null;
        for (Object member : parent.getDeclaredVariables()) {
            if (member instanceof Variable) {
                Variable existingVariable = (Variable) member;
                if (existingVariable.getSignature().equals(signature)) {
                    variable = existingVariable;
                }
            }
        }
        if (variable != null) {
            return variable;
        }
        variable = scannerContext.getStore().create(Variable.class);
        variable.setName(signature.substring(signature.indexOf(" ") + 1));
        variable.setSignature(signature);
        parent.getDeclaredVariables().add(variable);
        return variable;
    }
}
