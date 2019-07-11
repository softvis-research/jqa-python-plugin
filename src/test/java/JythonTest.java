import org.junit.Before;
import org.junit.Test;
import org.python.core.PyCode;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class JythonTest {
    private String exampleFilePath;

    @Before
    public void init() throws IOException {
        System.out.println(ANSI_GREEN + "Source File: " + ANSI_RESET + exampleFilePath);
        System.out.println(ANSI_RED + "-----------------------------------------------------------------" + ANSI_RESET);
        columnPrint("INT", "(depth...) Rule Name", "Python 3 Source Code", 0);
        System.out.println(ANSI_RED + "-----------------------------------------------------------------" + ANSI_RESET);

        exampleFilePath = "src/main/resources/examples/http_server.py";
//        exampleFilePath = "src/main/resources/examples/render.py";
//        exampleFilePath = "src/main/resources/examples/simple.py";
    }
    @Test
    public void testParse() throws IOException {
        Reader reader = new FileReader(exampleFilePath);

        PythonInterpreter interpreter = new PythonInterpreter();

        interpreter.exec("import ast");
        PyObject o = interpreter.eval("ast.dump(ast.parse('" + exampleFilePath + "', 'filename', 'eval'))" + "\n");
        System.out.print(o.toString());

        System.out.println(ANSI_RED + "\n---------------------------------------------------------------" + ANSI_RESET);
        final PyCode compile = interpreter.compile(reader);
        System.out.println(compile);
    }

    private void columnPrint(String ruleIndex, String ruleName, String source, int indentDepth) {
        String whitespace = " ";
        int indentSize = 2;

        System.out.println(ANSI_RED + "|" + ANSI_RESET
                + whitespace.repeat(Math.max(3 - ruleIndex.length(), 0))
                + ruleIndex
                + ANSI_RED + "|" + ANSI_RESET
                + whitespace.repeat(indentDepth * indentSize)
                + ANSI_GREEN + ruleName + ANSI_RESET
                + whitespace.repeat(Math.max((15 - indentDepth) * indentSize - ruleName.length(), 4))
                + ANSI_RED + "|" + ANSI_RESET
//                + whitespace.repeat(indentDepth * indentSize)
                + source
                + ANSI_RED + "|" + ANSI_RESET
        );
    }

    static final String ANSI_RESET  = "\u001B[0m";
    static final String ANSI_BLACK  = "\u001B[30m";
    static final String ANSI_RED    = "\u001B[31m";
    static final String ANSI_GREEN  = "\u001B[32m";
    static final String ANSI_YELLOW = "\u001B[33m";
    static final String ANSI_BLUE   = "\u001B[34m";
    static final String ANSI_PURPLE = "\u001B[35m";
    static final String ANSI_CYAN   = "\u001B[36m";
    static final String ANSI_WHITE  = "\u001B[37m";
}
