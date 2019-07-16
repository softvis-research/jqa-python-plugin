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
import org.jqassistant.contrib.plugin.python.antlr4.Python3Lexer;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.File_inputContext;
import org.jqassistant.contrib.plugin.python.api.model.PythonSourceFile;
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
public class PythonSourceFileScannerPlugin extends AbstractScannerPlugin<FileResource, PythonSourceFile> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PythonSourceFileScannerPlugin.class);

    @Override
    public boolean accepts(FileResource item, String path, Scope scope) {
        return PythonScope.SRC.equals(scope) && path.toLowerCase().endsWith(".py");
    }

    @Override
    public PythonSourceFile scan(FileResource item, String path, Scope scope, Scanner scanner) throws IOException {
        final ScannerContext scannerContext = scanner.getContext();
        final FileDescriptor fileDescriptor = scannerContext.getCurrentDescriptor();
        final PythonSourceFile pythonSourceFile = scannerContext.getStore().addDescriptorType(fileDescriptor, PythonSourceFile.class);
        final VisitorHelper visitorHelper = new VisitorHelper(scannerContext, pythonSourceFile);

        try (final InputStream inputStream = item.createStream()) {
            pythonSourceFile.setFileName(item.getFile().getName());


            final Python3Lexer lexer = new Python3Lexer(CharStreams.fromStream(inputStream));
            final CommonTokenStream tokenStream = new CommonTokenStream(lexer);
            final Python3Parser parser = new Python3Parser(tokenStream);

            File_inputContext file_inputContext = parser.file_input();
        } catch (PythonSourceException pse) {
            LOGGER.warn(pse.getClass().getSimpleName() + " " + pse.getMessage() + " in " + pythonSourceFile.getFileName());
        }

//        visitorHelper.storeDependencies();
        return pythonSourceFile;
    }
}
