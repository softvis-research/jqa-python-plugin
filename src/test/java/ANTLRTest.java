import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.jqassistant.contrib.plugin.python.Python3Lexer;
import org.jqassistant.contrib.plugin.python.Python3Parser;
import org.jqassistant.contrib.plugin.python.Python3Parser.File_inputContext;
import org.jqassistant.contrib.plugin.python.impl.scanner.PythonFileScannerPlugin;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class ANTLRTest {
    @Test
    public void testParse() throws IOException {
        String examplePath = "/examples/http_server.py";
//        String examplePath = "/examples/render.py";
//        String examplePath = "/examples/simple.py";

        InputStream inputStream = PythonFileScannerPlugin.class.getResourceAsStream(examplePath);
        Python3Lexer lexer = new Python3Lexer(CharStreams.fromStream(inputStream));
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        Python3Parser parser = new Python3Parser(tokenStream);
        File_inputContext file_inputContext = parser.file_input();

        columnPrint("INT", "(depth...) Rule Name", "Python 3 Source Code", 0);
        System.out.println(ANSI_RED + "-----------------------------------------------------------------" + ANSI_RESET);
        indentPrint(file_inputContext, 0);
    }

    private void indentPrint(RuleContext context, int indentDepth) {
        boolean ignoringWrappers = true;

        boolean toBeIgnored = ignoringWrappers
                && context.getChildCount() == 1
                && context.getChild(0) instanceof ParserRuleContext;
        if (!toBeIgnored) {
            final int ruleIndex = context.getRuleIndex();
            String ruleName = Python3Parser.ruleNames[ruleIndex];
            String source = context.getText()
                    .replace("\n", (ANSI_CYAN + "\\n" + ANSI_RESET))
                    .replace("\r", (ANSI_CYAN + "\\r" + ANSI_RESET));

            columnPrint(Integer.toString(ruleIndex), ruleName, source, indentDepth);
        }
        for (int i = 0; i < context.getChildCount(); i++) {
            ParseTree element = context.getChild(i);
            if (element instanceof RuleContext) {
                indentPrint((RuleContext)element, indentDepth + (toBeIgnored ? 0 : 1));
            }
        }
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
