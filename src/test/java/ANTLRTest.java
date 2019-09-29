import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.jqassistant.contrib.plugin.python.antlr4.Python3BaseListener;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Lexer;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.ClassdefContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.File_inputContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.ParametersContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.VarargslistContext;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


public class ANTLRTest {

    private String exampleFilePath;
    private File_inputContext file_inputContext;
    private Python3Parser parser;
    private boolean shouldPrint = true;

    @Before
    public void init() throws IOException {
//        exampleFilePath = "src/test/resources/example/http_server.py";
//        exampleFilePath = "src/test/resources/example/render.py";
//        exampleFilePath = "src/test/resources/example/simple.py";
        exampleFilePath = "../../youtube-dl/youtube_dl/downloader/http.py";

        if (shouldPrint) {
            System.out.println(ANSI_GREEN + "Source File: " + ANSI_RESET + exampleFilePath);
            System.out.println(ANSI_RED + "-----------------------------------------------------------------" + ANSI_RESET);
            columnPrint("INT", "(depth...) Rule Name", "Python 3 Source Code", 0);
            System.out.println(ANSI_RED + "-----------------------------------------------------------------" + ANSI_RESET);
        }

        InputStream inputStream = new FileInputStream(exampleFilePath);
        Python3Lexer lexer = new Python3Lexer(CharStreams.fromStream(inputStream));
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        parser = new Python3Parser(tokenStream);
    }

    @Test
    public void testParse() {
        file_inputContext = parser.file_input();

        iteratePrettyPrint(file_inputContext, 0);
    }

    @Test
    public void testWalker() {
        ParseTreeWalker.DEFAULT.walk(new Python3BaseListener() {
            @Override
            public void enterEval_input(Python3Parser.Eval_inputContext ctx) {
                columnPrint(Integer.toString(ctx.getRuleIndex()),
                        Python3Parser.ruleNames[ctx.getRuleIndex()] + " " + ctx.getText(),
                        ctx.getText(),
                        0);
            }
            @Override
            public void enterTfpdef(Python3Parser.TfpdefContext ctx) {
                columnPrint(Integer.toString(ctx.getRuleIndex()),
                        Python3Parser.ruleNames[ctx.getRuleIndex()] + " " + ctx.NAME().getText(),
                        ctx.getText(),
                        0);
            }
            @Override
            public void enterFuncdef(Python3Parser.FuncdefContext ctx) {
                columnPrint(Integer.toString(ctx.getRuleIndex()),
                        Python3Parser.ruleNames[ctx.getRuleIndex()] + " " + ctx.NAME().getText(),
                        ctx.getText(),
                        0);
            }

            @Override
            public void enterClassdef(final ClassdefContext ctx) {
                columnPrint(Integer.toString(ctx.getRuleIndex()),
                        Python3Parser.ruleNames[ctx.getRuleIndex()] + " " + ctx.NAME().getText(),
                        ctx.getText(),
                        0);
            }

            @Override
            public void enterVarargslist(final VarargslistContext ctx) {
                columnPrint(Integer.toString(ctx.getRuleIndex()),
                        Python3Parser.ruleNames[ctx.getRuleIndex()] + " " + ctx.getText(),
                        ctx.getText(),
                        0);
            }

            @Override
            public void enterParameters(final ParametersContext ctx) {
                columnPrint(Integer.toString(ctx.getRuleIndex()),
                        Python3Parser.ruleNames[ctx.getRuleIndex()] + " " + ctx.getText(),
                        ctx.getText(),
                        0);


            }
        }, parser.file_input());
    }

    private void iteratePrettyPrint(RuleContext context, int indentDepth) {
        boolean ignoringWrappers = true;

        boolean toBeIgnored = ignoringWrappers
                && context.getChildCount() == 1
                && context.getChild(0) instanceof ParserRuleContext;
        if (!toBeIgnored) {
            final int ruleIndex = context.getRuleIndex();
            String ruleName = Python3Parser.ruleNames[ruleIndex];
            String source = context.getText();

            columnPrint(Integer.toString(ruleIndex), ruleName, source, indentDepth);
        }
        for (int i = 0; i < context.getChildCount(); i++) {
            ParseTree element = context.getChild(i);
            if (element instanceof RuleContext) {
                iteratePrettyPrint((RuleContext)element, indentDepth + (toBeIgnored ? 0 : 1));
            }
        }
    }

    private void columnPrint(String ruleIndex, String ruleName, String source, int indentDepth) {
        String whitespace = " ";
        int indentSize = 2;

        if (shouldPrint) {
            System.out.println(ANSI_RED + "|" + ANSI_RESET
                    + new String(new char[Math.max(3 - ruleIndex.length(), 0)]).replace("\0", whitespace)
                    + ruleIndex
                    + ANSI_RED + "|" + ANSI_RESET
                    + new String(new char[indentDepth * indentSize]).replace("\0", whitespace)
                    + ANSI_GREEN + ruleName + ANSI_RESET
                    + new String(new char[Math.max((15 - indentDepth) * indentSize - ruleName.length(), 4)]).replace("\0", whitespace)
                    + ANSI_RED + "|" + ANSI_RESET
                    //                + new String(new char[indentDepth * indentSize]).replace("\0", whitespace)
                    + source
                    .replace("\n", (ANSI_CYAN + "\\n" + ANSI_RESET))
                    .replace("\r", (ANSI_CYAN + "\\r" + ANSI_RESET))
                    + ANSI_RED + "|" + ANSI_RESET
            );
        }
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
