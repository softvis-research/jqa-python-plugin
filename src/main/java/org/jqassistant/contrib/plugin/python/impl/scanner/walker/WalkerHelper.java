package org.jqassistant.contrib.plugin.python.impl.scanner.walker;

import com.buschmais.jqassistant.core.scanner.api.ScannerContext;
import org.jqassistant.contrib.plugin.python.api.model.PythonSourceFile;

public class WalkerHelper {
    private ScannerContext scannerContext;
    private PythonSourceFile pythonSourceFile;

    public WalkerHelper(ScannerContext scannerContext, PythonSourceFile pythonSourceFile) {
        this.scannerContext = scannerContext;
        this.pythonSourceFile = pythonSourceFile;
    }

    public void createFile() {
        pythonSourceFile = scannerContext.getStore().create(PythonSourceFile.class);
    }

    public ScannerContext getScannerContext() {
        return scannerContext;
    }
}
