package org.jqassistant.contrib.plugin.python.impl.scanner;

import com.buschmais.jqassistant.core.scanner.api.ScannerContext;
import com.buschmais.jqassistant.core.scanner.api.Scope;
import com.buschmais.jqassistant.plugin.common.api.scanner.AbstractDirectoryScannerPlugin;
import org.jqassistant.contrib.plugin.python.api.model.PythonPackage;
import org.jqassistant.contrib.plugin.python.api.scanner.PythonScope;
import org.jqassistant.contrib.plugin.python.impl.scanner.walker.StoreHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * Plugin that scans Python source directories.
 * 
 * @author Kevin M. Shrestha
 *
 */
public class PythonPackageScannerPlugin extends AbstractDirectoryScannerPlugin<PythonPackage> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PythonPackageScannerPlugin.class);
    private final String JQASSISTANT_PLUGIN_PYTHON_DIRNAME = "jqassistant.plugin.python.dirname";
    private String pythonDirName = "target";

    @Override
    protected Scope getRequiredScope() {
        return PythonScope.SRC;
    }

    @Override
    protected void configure() {
        super.configure();
        if (getProperties().containsKey(JQASSISTANT_PLUGIN_PYTHON_DIRNAME)) {
            pythonDirName = (String) getProperties().get(JQASSISTANT_PLUGIN_PYTHON_DIRNAME);
        }

        LOGGER.info("Python Source Parser plugin looks for files in directory '{}'", pythonDirName);
    }

    @Override
    protected PythonPackage getContainerDescriptor(File container, ScannerContext scannerContext) {
        return scannerContext.getStore().create(PythonPackage.class);
    }

    @Override
    protected void enterContainer(File container, PythonPackage pythonPackage, ScannerContext scannerContext) throws IOException {
        if (scannerContext.peekOrDefault(StoreHelper.class, null) == null) {
            scannerContext.push(StoreHelper.class, new StoreHelper(pythonPackage, scannerContext));
        }
    }

    @Override
    protected void leaveContainer(File container, PythonPackage containerDescriptor, ScannerContext scannerContext) throws IOException {
        scannerContext.pop(StoreHelper.class);
    }
}
