package org.jqassistant.contrib.plugin.python.test;

import com.buschmais.jqassistant.plugin.common.test.AbstractPluginIT;
import org.jqassistant.contrib.plugin.python.api.model.PythonSourceDirectory;
import org.jqassistant.contrib.plugin.python.api.scanner.PythonScope;
import org.junit.Test;

import java.io.File;

/**
 * Contains test to verify correct scanning of python source directories.
 * 
 * @author Kevin M. Shrestha
 *
 */
public class PythonSourceDirectoryScannerIT extends AbstractPluginIT {

    @Test
    public void testScanPythonDirectory() {
        final String TEST_DIRECTORY_PATH = "src/test/resources";
        final String FILE_DIRECTORY_PATH = "src/test/resources/example/";
        File directory = new File(FILE_DIRECTORY_PATH);
        store.beginTransaction();
        PythonSourceDirectory pythonSourceDirectory = getScanner().scan(directory, TEST_DIRECTORY_PATH, PythonScope.SRC);
        store.commitTransaction();
    }

}
