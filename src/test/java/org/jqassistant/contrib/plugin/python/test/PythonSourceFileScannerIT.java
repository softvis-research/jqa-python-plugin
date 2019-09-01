package org.jqassistant.contrib.plugin.python.test;

import com.buschmais.jqassistant.plugin.common.test.AbstractPluginIT;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Lexer;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.File_inputContext;
import org.jqassistant.contrib.plugin.python.api.model.PythonSourceDirectory;
import org.jqassistant.contrib.plugin.python.api.model.PythonSourceFile;
import org.jqassistant.contrib.plugin.python.api.scanner.PythonScope;
import org.jqassistant.contrib.plugin.python.impl.scanner.visitor.VisitorHelper;
import org.jqassistant.contrib.plugin.python.impl.scanner.walker.PythonSourceWalker;
import org.jqassistant.contrib.plugin.python.impl.scanner.walker.WalkerHelper;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

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
        File file = new File(FILE_PATH);
        store.beginTransaction();
        PythonSourceFile pythonSourceFile = getScanner().scan(file, FILE_PATH, PythonScope.SRC);
        store.commitTransaction();
    }

}
