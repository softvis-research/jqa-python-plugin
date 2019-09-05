package org.jqassistant.contrib.plugin.python.test;

import com.buschmais.jqassistant.core.store.api.Store;
import com.buschmais.jqassistant.plugin.common.test.AbstractPluginIT;

import org.jqassistant.contrib.plugin.python.api.model.PythonSourceFile;
import org.jqassistant.contrib.plugin.python.api.scanner.PythonScope;
import org.junit.Test;

import java.io.File;

/**
 * Contains test to verify correct scanning of a python source file.
 * 
 * @author Kevin M. Shrestha
 *
 */
public class PythonSourceFileScannerIT extends AbstractPluginIT {

    @Test
    public void testScanPythonFile() {
        final String FILE_PATH = "src/test/resources/example/http_server.py";
//        final String FILE_PATH = "src/test/resources/example/render.py";
        File file = new File(FILE_PATH);
        store.beginTransaction();
        boolean debugLoop = true;
//        while (debugLoop) {
            PythonSourceFile pythonSourceFile = getScanner().scan(file, FILE_PATH, PythonScope.SRC);
//        }
        store.commitTransaction();
    }

}
