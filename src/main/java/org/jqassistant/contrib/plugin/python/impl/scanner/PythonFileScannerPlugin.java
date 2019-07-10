package org.jqassistant.contrib.plugin.python.impl.scanner;

import com.buschmais.jqassistant.core.scanner.api.Scanner;
import com.buschmais.jqassistant.core.scanner.api.ScannerContext;
import com.buschmais.jqassistant.core.scanner.api.ScannerPlugin.Requires;
import com.buschmais.jqassistant.core.scanner.api.Scope;
import com.buschmais.jqassistant.plugin.common.api.model.FileDescriptor;
import com.buschmais.jqassistant.plugin.common.api.scanner.AbstractScannerPlugin;
import com.buschmais.jqassistant.plugin.common.api.scanner.filesystem.FileResource;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.jqassistant.contrib.plugin.python.Python3Lexer;
import org.jqassistant.contrib.plugin.python.Python3Parser;
import org.jqassistant.contrib.plugin.python.Python3Parser.File_inputContext;
import org.jqassistant.contrib.plugin.python.api.model.PythonSourceFileDescriptor;
import org.jqassistant.contrib.plugin.python.api.scanner.PythonScope;
import org.jqassistant.contrib.plugin.python.impl.scanner.visitor.VisitorHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Plugin that scans Python source files.
 *
 * @author Kevin M. Shrestha
 */
@Requires(FileDescriptor.class)
public class PythonFileScannerPlugin extends AbstractScannerPlugin<FileResource, PythonSourceFileDescriptor> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PythonFileScannerPlugin.class);

    @Override
    public boolean accepts(FileResource item, String path, Scope scope) throws IOException {
        return PythonScope.SRC.equals(scope) && path.toLowerCase().endsWith(".py");
    }

    @Override
    public PythonSourceFileDescriptor scan(FileResource item, String path, Scope scope, Scanner scanner) throws IOException {
        ScannerContext scannerContext = scanner.getContext();
        FileDescriptor fileDescriptor = scannerContext.getCurrentDescriptor();
        PythonSourceFileDescriptor pythonSourceFileDescriptor = scannerContext.getStore().addDescriptorType(fileDescriptor, PythonSourceFileDescriptor.class);

        InputStream inputStream = PythonFileScannerPlugin.class.getResourceAsStream("/examples/http_server.py");
        Lexer lexer = new Python3Lexer(CharStreams.fromStream(inputStream));
        TokenStream tokenStream = new CommonTokenStream(lexer);
        Python3Parser parser = new Python3Parser(tokenStream);
        final File_inputContext file_inputContext = parser.file_input();
        ParseTree tree = file_inputContext;
        System.out.println(tree.getText());

        VisitorHelper visitorHelper = new VisitorHelper(scannerContext, pythonSourceFileDescriptor);

        return pythonSourceFileDescriptor;
    }
}
