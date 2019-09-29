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
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Lexer;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.File_inputContext;
import org.jqassistant.contrib.plugin.python.api.model.PythonFile;
import org.jqassistant.contrib.plugin.python.api.model.PythonPackage;
import org.jqassistant.contrib.plugin.python.api.scanner.PythonScope;
import org.jqassistant.contrib.plugin.python.impl.scanner.walker.PythonSourceWalker;
import org.jqassistant.contrib.plugin.python.impl.scanner.walker.WalkerHelper;
import org.jqassistant.contrib.plugin.python.impl.scanner.walker.cache.CacheManager;
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
public class PythonFileScannerPlugin extends AbstractScannerPlugin<FileResource, PythonFile> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PythonFileScannerPlugin.class);

    @Override
    public boolean accepts(FileResource item, String path, Scope scope) {
        LOGGER.debug("item, path, scope: " + item + ",\t" + path + ", \t" + scope);
        return path.toLowerCase().endsWith(PythonScope.FILE_EXTENSION);
    }

    @Override
    public PythonFile scan(FileResource item, String path, Scope scope, Scanner scanner) throws IOException {
        final ScannerContext scannerContext = scanner.getContext();
        if (scannerContext.peekOrDefault(CacheManager.class, null) == null) {
            final FileDescriptor fileDescriptor = scannerContext.getCurrentDescriptor();
            final PythonPackage pythonPackage = scannerContext.getStore().addDescriptorType(fileDescriptor, PythonPackage.class);
            scannerContext.push(CacheManager.class, new CacheManager(pythonPackage, scannerContext, path));
        }
        final CacheManager cacheManager = scannerContext.peek(CacheManager.class);
        cacheManager.setCurrentPath(cacheManager.getRootPath() + path);

        final WalkerHelper walkerHelper = new WalkerHelper(scannerContext, cacheManager);

        try (final InputStream inputStream = item.createStream()) {

            final Python3Lexer lexer = new Python3Lexer(CharStreams.fromStream(inputStream));
            final CommonTokenStream tokenStream = new CommonTokenStream(lexer);
            final Python3Parser parser = new Python3Parser(tokenStream);

            File_inputContext tree = parser.file_input();

            ParseTreeWalker.DEFAULT.walk(new PythonSourceWalker(walkerHelper), tree);
        } catch (PythonSourceException pse) {
            LOGGER.warn(pse.getClass().getSimpleName() + " " + pse.getMessage() + " in " + path);
        }

        return cacheManager.getPythonFile();
    }
}
