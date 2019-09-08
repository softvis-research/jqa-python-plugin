package org.jqassistant.contrib.plugin.python.impl.scanner;

import com.buschmais.jqassistant.core.scanner.api.ScannerContext;
import com.buschmais.jqassistant.core.scanner.api.Scope;
import com.buschmais.jqassistant.plugin.common.api.scanner.AbstractDirectoryScannerPlugin;
import org.jqassistant.contrib.plugin.python.api.model.PythonPackage;
import org.jqassistant.contrib.plugin.python.api.scanner.PythonScope;
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
public class PackageScannerPlugin extends AbstractDirectoryScannerPlugin<PythonPackage> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PackageScannerPlugin.class);
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
    protected void enterContainer(File container, PythonPackage containerDescriptor, ScannerContext scannerContext) throws IOException {
//        if (scannerContext.peekOrDefault(PythonTypeResolver.class, null) == null) {
//            scannerContext.push(PythonTypeResolver.class, new PythonTypeResolver(scannerContext));
//        }
    }

    @Override
    protected void leaveContainer(File container, PythonPackage containerDescriptor, ScannerContext scannerContext) throws IOException {
//        scannerContext.pop(PythonTypeResolver.class);
//        scannerContext.pop(PythonTypeSolver.class);
    }
}
