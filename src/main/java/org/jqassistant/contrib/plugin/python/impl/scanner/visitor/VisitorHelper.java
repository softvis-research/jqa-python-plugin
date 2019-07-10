package org.jqassistant.contrib.plugin.python.impl.scanner.visitor;

import com.buschmais.jqassistant.core.scanner.api.ScannerContext;
import org.jqassistant.contrib.plugin.python.api.model.PythonSourceFileDescriptor;

/**
 * The helper delegates creation and caching of types to the type resolver,
 * holds a reference of the type solver and its facade, and provides the
 * visitors with descriptors.
 *
 * @author Kevin M. Shrestha
 */
public class VisitorHelper {
    private ScannerContext scannerContext;
    private PythonSourceFileDescriptor pythonSourceFileDescriptor;

    public VisitorHelper(ScannerContext scannerContext, PythonSourceFileDescriptor pythonSourceFileDescriptor) {
        this.scannerContext = scannerContext;
        this.pythonSourceFileDescriptor = pythonSourceFileDescriptor;
    }
}
