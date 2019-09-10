package org.jqassistant.contrib.plugin.python.test;

import com.buschmais.jqassistant.plugin.common.test.AbstractPluginIT;
import org.jqassistant.contrib.plugin.python.api.model.PythonFile;
import org.jqassistant.contrib.plugin.python.api.scanner.PythonScope;
import org.junit.Test;

/**
 * Contains test to verify correct scanning of a python source file.
 * 
 * @author Kevin M. Shrestha
 *
 */
public class PythonFileScannerIT extends AbstractPluginIT {

    @Test
    public void testScanPythonFile() {
//        final String FILE_PATH = "src/test/resources/example/http_server.py";
        final String FILE_PATH = "src/test/resources/example/render.py";
        java.io.File file = new java.io.File(FILE_PATH);
        store.beginTransaction();
        PythonFile python = getScanner().scan(file, FILE_PATH, PythonScope.SRC);
        store.commitTransaction();
    }

}
