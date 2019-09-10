package org.jqassistant.contrib.plugin.python.impl.scanner.walker;

import lombok.AllArgsConstructor;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.jqassistant.contrib.plugin.python.antlr4.Python3BaseListener;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.And_exprContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.And_testContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.AnnassignContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.ArglistContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.ArgumentContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.Arith_exprContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.Assert_stmtContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.Async_funcdefContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.Async_stmtContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.AtomContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.Atom_exprContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.AugassignContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.Break_stmtContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.ClassdefContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.Comp_forContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.Comp_ifContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.Comp_iterContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.Comp_opContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.ComparisonContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.Compound_stmtContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.Continue_stmtContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.DecoratedContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.DecoratorContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.DecoratorsContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.Del_stmtContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.DictorsetmakerContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.Dotted_as_nameContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.Dotted_as_namesContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.Dotted_nameContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.Encoding_declContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.Eval_inputContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.Except_clauseContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.ExprContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.Expr_stmtContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.ExprlistContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.FactorContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.File_inputContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.Flow_stmtContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.For_stmtContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.FuncdefContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.Global_stmtContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.If_stmtContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.Import_as_nameContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.Import_as_namesContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.Import_fromContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.Import_nameContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.Import_stmtContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.LambdefContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.Lambdef_nocondContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.Nonlocal_stmtContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.Not_testContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.Or_testContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.ParametersContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.Pass_stmtContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.PowerContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.Raise_stmtContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.Return_stmtContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.Shift_exprContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.Simple_stmtContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.Single_inputContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.SliceopContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.Small_stmtContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.Star_exprContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.StmtContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.SubscriptContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.SubscriptlistContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.SuiteContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.TermContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.TestContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.Test_nocondContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.TestlistContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.Testlist_compContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.Testlist_star_exprContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.TfpdefContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.TrailerContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.Try_stmtContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.TypedargslistContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.VarargslistContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.VfpdefContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.While_stmtContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.With_itemContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.With_stmtContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.Xor_exprContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.Yield_argContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.Yield_exprContext;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser.Yield_stmtContext;

@AllArgsConstructor
public class PythonSourceWalker extends Python3BaseListener {
    private WalkerHelper walkerHelper;

    @Override
    public void enterSingle_input(final Single_inputContext ctx) {
        super.enterSingle_input(ctx);
    }

    @Override
    public void exitSingle_input(final Single_inputContext ctx) {
        super.exitSingle_input(ctx);
    }

    @Override
    public void enterFile_input(final File_inputContext ctx) {
        walkerHelper.createFile(ctx);
    }

    @Override
    public void exitFile_input(final File_inputContext ctx) {
        super.exitFile_input(ctx);
    }

    @Override
    public void enterEval_input(final Eval_inputContext ctx) {
        super.enterEval_input(ctx);
    }

    @Override
    public void exitEval_input(final Eval_inputContext ctx) {
        super.exitEval_input(ctx);
    }

    @Override
    public void enterDecorator(final DecoratorContext ctx) {
        super.enterDecorator(ctx);
    }

    @Override
    public void exitDecorator(final DecoratorContext ctx) {
        super.exitDecorator(ctx);
    }

    @Override
    public void enterDecorators(final DecoratorsContext ctx) {
        super.enterDecorators(ctx);
    }

    @Override
    public void exitDecorators(final DecoratorsContext ctx) {
        super.exitDecorators(ctx);
    }

    @Override
    public void enterDecorated(final DecoratedContext ctx) {
        super.enterDecorated(ctx);
    }

    @Override
    public void exitDecorated(final DecoratedContext ctx) {
        super.exitDecorated(ctx);
    }

    @Override
    public void enterAsync_funcdef(final Async_funcdefContext ctx) {
        super.enterAsync_funcdef(ctx);
    }

    @Override
    public void exitAsync_funcdef(final Async_funcdefContext ctx) {
        super.exitAsync_funcdef(ctx);
    }

    @Override
    public void enterFuncdef(final FuncdefContext ctx) {
        walkerHelper.createFunction(ctx);
    }

    @Override
    public void exitFuncdef(final FuncdefContext ctx) {
        super.exitFuncdef(ctx);
    }

    @Override
    public void enterParameters(final ParametersContext ctx) {
        walkerHelper.createParameters(ctx);
    }

    @Override
    public void exitParameters(final ParametersContext ctx) {
        super.exitParameters(ctx);
    }

    @Override
    public void enterTypedargslist(final TypedargslistContext ctx) {
        super.enterTypedargslist(ctx);
    }

    @Override
    public void exitTypedargslist(final TypedargslistContext ctx) {
        super.exitTypedargslist(ctx);
    }

    @Override
    public void enterTfpdef(final TfpdefContext ctx) {
        super.enterTfpdef(ctx);
    }

    @Override
    public void exitTfpdef(final TfpdefContext ctx) {
        super.exitTfpdef(ctx);
    }

    @Override
    public void enterVarargslist(final VarargslistContext ctx) {
        super.enterVarargslist(ctx);
    }

    @Override
    public void exitVarargslist(final VarargslistContext ctx) {
        super.exitVarargslist(ctx);
    }

    @Override
    public void enterVfpdef(final VfpdefContext ctx) {
        super.enterVfpdef(ctx);
    }

    @Override
    public void exitVfpdef(final VfpdefContext ctx) {
        super.exitVfpdef(ctx);
    }

    @Override
    public void enterStmt(final StmtContext ctx) {
        super.enterStmt(ctx);
    }

    @Override
    public void exitStmt(final StmtContext ctx) {
        super.exitStmt(ctx);
    }

    @Override
    public void enterSimple_stmt(final Simple_stmtContext ctx) {
        super.enterSimple_stmt(ctx);
    }

    @Override
    public void exitSimple_stmt(final Simple_stmtContext ctx) {
        super.exitSimple_stmt(ctx);
    }

    @Override
    public void enterSmall_stmt(final Small_stmtContext ctx) {
        super.enterSmall_stmt(ctx);
    }

    @Override
    public void exitSmall_stmt(final Small_stmtContext ctx) {
        super.exitSmall_stmt(ctx);
    }

    @Override
    public void enterExpr_stmt(final Expr_stmtContext ctx) {
        super.enterExpr_stmt(ctx);
    }

    @Override
    public void exitExpr_stmt(final Expr_stmtContext ctx) {
        super.exitExpr_stmt(ctx);
    }

    @Override
    public void enterAnnassign(final AnnassignContext ctx) {
        super.enterAnnassign(ctx);
    }

    @Override
    public void exitAnnassign(final AnnassignContext ctx) {
        super.exitAnnassign(ctx);
    }

    @Override
    public void enterTestlist_star_expr(final Testlist_star_exprContext ctx) {
        super.enterTestlist_star_expr(ctx);
    }

    @Override
    public void exitTestlist_star_expr(final Testlist_star_exprContext ctx) {
        super.exitTestlist_star_expr(ctx);
    }

    @Override
    public void enterAugassign(final AugassignContext ctx) {
        super.enterAugassign(ctx);
    }

    @Override
    public void exitAugassign(final AugassignContext ctx) {
        super.exitAugassign(ctx);
    }

    @Override
    public void enterDel_stmt(final Del_stmtContext ctx) {
        super.enterDel_stmt(ctx);
    }

    @Override
    public void exitDel_stmt(final Del_stmtContext ctx) {
        super.exitDel_stmt(ctx);
    }

    @Override
    public void enterPass_stmt(final Pass_stmtContext ctx) {
        super.enterPass_stmt(ctx);
    }

    @Override
    public void exitPass_stmt(final Pass_stmtContext ctx) {
        super.exitPass_stmt(ctx);
    }

    @Override
    public void enterFlow_stmt(final Flow_stmtContext ctx) {
        super.enterFlow_stmt(ctx);
    }

    @Override
    public void exitFlow_stmt(final Flow_stmtContext ctx) {
        super.exitFlow_stmt(ctx);
    }

    @Override
    public void enterBreak_stmt(final Break_stmtContext ctx) {
        super.enterBreak_stmt(ctx);
    }

    @Override
    public void exitBreak_stmt(final Break_stmtContext ctx) {
        super.exitBreak_stmt(ctx);
    }

    @Override
    public void enterContinue_stmt(final Continue_stmtContext ctx) {
        super.enterContinue_stmt(ctx);
    }

    @Override
    public void exitContinue_stmt(final Continue_stmtContext ctx) {
        super.exitContinue_stmt(ctx);
    }

    @Override
    public void enterReturn_stmt(final Return_stmtContext ctx) {
        super.enterReturn_stmt(ctx);
    }

    @Override
    public void exitReturn_stmt(final Return_stmtContext ctx) {
        super.exitReturn_stmt(ctx);
    }

    @Override
    public void enterYield_stmt(final Yield_stmtContext ctx) {
        super.enterYield_stmt(ctx);
    }

    @Override
    public void exitYield_stmt(final Yield_stmtContext ctx) {
        super.exitYield_stmt(ctx);
    }

    @Override
    public void enterRaise_stmt(final Raise_stmtContext ctx) {
        super.enterRaise_stmt(ctx);
    }

    @Override
    public void exitRaise_stmt(final Raise_stmtContext ctx) {
        super.exitRaise_stmt(ctx);
    }

    @Override
    public void enterImport_stmt(final Import_stmtContext ctx) {
        walkerHelper.createImport(ctx);
    }

    @Override
    public void exitImport_stmt(final Import_stmtContext ctx) {
        super.exitImport_stmt(ctx);
    }

    @Override
    public void enterImport_name(final Import_nameContext ctx) {
        super.enterImport_name(ctx);
    }

    @Override
    public void exitImport_name(final Import_nameContext ctx) {
        super.exitImport_name(ctx);
    }

    @Override
    public void enterImport_from(final Import_fromContext ctx) {
        super.enterImport_from(ctx);
    }

    @Override
    public void exitImport_from(final Import_fromContext ctx) {
        super.exitImport_from(ctx);
    }

    @Override
    public void enterImport_as_name(final Import_as_nameContext ctx) {
        super.enterImport_as_name(ctx);
    }

    @Override
    public void exitImport_as_name(final Import_as_nameContext ctx) {
        super.exitImport_as_name(ctx);
    }

    @Override
    public void enterDotted_as_name(final Dotted_as_nameContext ctx) {
        super.enterDotted_as_name(ctx);
    }

    @Override
    public void exitDotted_as_name(final Dotted_as_nameContext ctx) {
        super.exitDotted_as_name(ctx);
    }

    @Override
    public void enterImport_as_names(final Import_as_namesContext ctx) {
        super.enterImport_as_names(ctx);
    }

    @Override
    public void exitImport_as_names(final Import_as_namesContext ctx) {
        super.exitImport_as_names(ctx);
    }

    @Override
    public void enterDotted_as_names(final Dotted_as_namesContext ctx) {
        super.enterDotted_as_names(ctx);
    }

    @Override
    public void exitDotted_as_names(final Dotted_as_namesContext ctx) {
        super.exitDotted_as_names(ctx);
    }

    @Override
    public void enterDotted_name(final Dotted_nameContext ctx) {
        super.enterDotted_name(ctx);
    }

    @Override
    public void exitDotted_name(final Dotted_nameContext ctx) {
        super.exitDotted_name(ctx);
    }

    @Override
    public void enterGlobal_stmt(final Global_stmtContext ctx) {
        super.enterGlobal_stmt(ctx);
    }

    @Override
    public void exitGlobal_stmt(final Global_stmtContext ctx) {
        super.exitGlobal_stmt(ctx);
    }

    @Override
    public void enterNonlocal_stmt(final Nonlocal_stmtContext ctx) {
        super.enterNonlocal_stmt(ctx);
    }

    @Override
    public void exitNonlocal_stmt(final Nonlocal_stmtContext ctx) {
        super.exitNonlocal_stmt(ctx);
    }

    @Override
    public void enterAssert_stmt(final Assert_stmtContext ctx) {
        super.enterAssert_stmt(ctx);
    }

    @Override
    public void exitAssert_stmt(final Assert_stmtContext ctx) {
        super.exitAssert_stmt(ctx);
    }

    @Override
    public void enterCompound_stmt(final Compound_stmtContext ctx) {
        super.enterCompound_stmt(ctx);
    }

    @Override
    public void exitCompound_stmt(final Compound_stmtContext ctx) {
        super.exitCompound_stmt(ctx);
    }

    @Override
    public void enterAsync_stmt(final Async_stmtContext ctx) {
        super.enterAsync_stmt(ctx);
    }

    @Override
    public void exitAsync_stmt(final Async_stmtContext ctx) {
        super.exitAsync_stmt(ctx);
    }

    @Override
    public void enterIf_stmt(final If_stmtContext ctx) {
        super.enterIf_stmt(ctx);
    }

    @Override
    public void exitIf_stmt(final If_stmtContext ctx) {
        super.exitIf_stmt(ctx);
    }

    @Override
    public void enterWhile_stmt(final While_stmtContext ctx) {
        super.enterWhile_stmt(ctx);
    }

    @Override
    public void exitWhile_stmt(final While_stmtContext ctx) {
        super.exitWhile_stmt(ctx);
    }

    @Override
    public void enterFor_stmt(final For_stmtContext ctx) {
        super.enterFor_stmt(ctx);
    }

    @Override
    public void exitFor_stmt(final For_stmtContext ctx) {
        super.exitFor_stmt(ctx);
    }

    @Override
    public void enterTry_stmt(final Try_stmtContext ctx) {
        super.enterTry_stmt(ctx);
    }

    @Override
    public void exitTry_stmt(final Try_stmtContext ctx) {
        super.exitTry_stmt(ctx);
    }

    @Override
    public void enterWith_stmt(final With_stmtContext ctx) {
        super.enterWith_stmt(ctx);
    }

    @Override
    public void exitWith_stmt(final With_stmtContext ctx) {
        super.exitWith_stmt(ctx);
    }

    @Override
    public void enterWith_item(final With_itemContext ctx) {
        super.enterWith_item(ctx);
    }

    @Override
    public void exitWith_item(final With_itemContext ctx) {
        super.exitWith_item(ctx);
    }

    @Override
    public void enterExcept_clause(final Except_clauseContext ctx) {
        super.enterExcept_clause(ctx);
    }

    @Override
    public void exitExcept_clause(final Except_clauseContext ctx) {
        super.exitExcept_clause(ctx);
    }

    @Override
    public void enterSuite(final SuiteContext ctx) {
        super.enterSuite(ctx);
    }

    @Override
    public void exitSuite(final SuiteContext ctx) {
        super.exitSuite(ctx);
    }

    @Override
    public void enterTest(final TestContext ctx) {
        super.enterTest(ctx);
    }

    @Override
    public void exitTest(final TestContext ctx) {
        super.exitTest(ctx);
    }

    @Override
    public void enterTest_nocond(final Test_nocondContext ctx) {
        super.enterTest_nocond(ctx);
    }

    @Override
    public void exitTest_nocond(final Test_nocondContext ctx) {
        super.exitTest_nocond(ctx);
    }

    @Override
    public void enterLambdef(final LambdefContext ctx) {
        super.enterLambdef(ctx);
    }

    @Override
    public void exitLambdef(final LambdefContext ctx) {
        super.exitLambdef(ctx);
    }

    @Override
    public void enterLambdef_nocond(final Lambdef_nocondContext ctx) {
        super.enterLambdef_nocond(ctx);
    }

    @Override
    public void exitLambdef_nocond(final Lambdef_nocondContext ctx) {
        super.exitLambdef_nocond(ctx);
    }

    @Override
    public void enterOr_test(final Or_testContext ctx) {
        super.enterOr_test(ctx);
    }

    @Override
    public void exitOr_test(final Or_testContext ctx) {
        super.exitOr_test(ctx);
    }

    @Override
    public void enterAnd_test(final And_testContext ctx) {
        super.enterAnd_test(ctx);
    }

    @Override
    public void exitAnd_test(final And_testContext ctx) {
        super.exitAnd_test(ctx);
    }

    @Override
    public void enterNot_test(final Not_testContext ctx) {
        super.enterNot_test(ctx);
    }

    @Override
    public void exitNot_test(final Not_testContext ctx) {
        super.exitNot_test(ctx);
    }

    @Override
    public void enterComparison(final ComparisonContext ctx) {
        super.enterComparison(ctx);
    }

    @Override
    public void exitComparison(final ComparisonContext ctx) {
        super.exitComparison(ctx);
    }

    @Override
    public void enterComp_op(final Comp_opContext ctx) {
        super.enterComp_op(ctx);
    }

    @Override
    public void exitComp_op(final Comp_opContext ctx) {
        super.exitComp_op(ctx);
    }

    @Override
    public void enterStar_expr(final Star_exprContext ctx) {
        super.enterStar_expr(ctx);
    }

    @Override
    public void exitStar_expr(final Star_exprContext ctx) {
        super.exitStar_expr(ctx);
    }

    @Override
    public void enterExpr(final ExprContext ctx) {
        super.enterExpr(ctx);
    }

    @Override
    public void exitExpr(final ExprContext ctx) {
        super.exitExpr(ctx);
    }

    @Override
    public void enterXor_expr(final Xor_exprContext ctx) {
        super.enterXor_expr(ctx);
    }

    @Override
    public void exitXor_expr(final Xor_exprContext ctx) {
        super.exitXor_expr(ctx);
    }

    @Override
    public void enterAnd_expr(final And_exprContext ctx) {
        super.enterAnd_expr(ctx);
    }

    @Override
    public void exitAnd_expr(final And_exprContext ctx) {
        super.exitAnd_expr(ctx);
    }

    @Override
    public void enterShift_expr(final Shift_exprContext ctx) {
        super.enterShift_expr(ctx);
    }

    @Override
    public void exitShift_expr(final Shift_exprContext ctx) {
        super.exitShift_expr(ctx);
    }

    @Override
    public void enterArith_expr(final Arith_exprContext ctx) {
        super.enterArith_expr(ctx);
    }

    @Override
    public void exitArith_expr(final Arith_exprContext ctx) {
        super.exitArith_expr(ctx);
    }

    @Override
    public void enterTerm(final TermContext ctx) {
        super.enterTerm(ctx);
    }

    @Override
    public void exitTerm(final TermContext ctx) {
        super.exitTerm(ctx);
    }

    @Override
    public void enterFactor(final FactorContext ctx) {
        super.enterFactor(ctx);
    }

    @Override
    public void exitFactor(final FactorContext ctx) {
        super.exitFactor(ctx);
    }

    @Override
    public void enterPower(final PowerContext ctx) {
        super.enterPower(ctx);
    }

    @Override
    public void exitPower(final PowerContext ctx) {
        super.exitPower(ctx);
    }

    @Override
    public void enterAtom_expr(final Atom_exprContext ctx) {
        super.enterAtom_expr(ctx);
    }

    @Override
    public void exitAtom_expr(final Atom_exprContext ctx) {
        super.exitAtom_expr(ctx);
    }

    @Override
    public void enterAtom(final AtomContext ctx) {
        super.enterAtom(ctx);
    }

    @Override
    public void exitAtom(final AtomContext ctx) {
        super.exitAtom(ctx);
    }

    @Override
    public void enterTestlist_comp(final Testlist_compContext ctx) {
        super.enterTestlist_comp(ctx);
    }

    @Override
    public void exitTestlist_comp(final Testlist_compContext ctx) {
        super.exitTestlist_comp(ctx);
    }

    @Override
    public void enterTrailer(final TrailerContext ctx) {
        super.enterTrailer(ctx);
    }

    @Override
    public void exitTrailer(final TrailerContext ctx) {
        super.exitTrailer(ctx);
    }

    @Override
    public void enterSubscriptlist(final SubscriptlistContext ctx) {
        super.enterSubscriptlist(ctx);
    }

    @Override
    public void exitSubscriptlist(final SubscriptlistContext ctx) {
        super.exitSubscriptlist(ctx);
    }

    @Override
    public void enterSubscript(final SubscriptContext ctx) {
        super.enterSubscript(ctx);
    }

    @Override
    public void exitSubscript(final SubscriptContext ctx) {
        super.exitSubscript(ctx);
    }

    @Override
    public void enterSliceop(final SliceopContext ctx) {
        super.enterSliceop(ctx);
    }

    @Override
    public void exitSliceop(final SliceopContext ctx) {
        super.exitSliceop(ctx);
    }

    @Override
    public void enterExprlist(final ExprlistContext ctx) {
        super.enterExprlist(ctx);
    }

    @Override
    public void exitExprlist(final ExprlistContext ctx) {
        super.exitExprlist(ctx);
    }

    @Override
    public void enterTestlist(final TestlistContext ctx) {
        super.enterTestlist(ctx);
    }

    @Override
    public void exitTestlist(final TestlistContext ctx) {
        super.exitTestlist(ctx);
    }

    @Override
    public void enterDictorsetmaker(final DictorsetmakerContext ctx) {
        super.enterDictorsetmaker(ctx);
    }

    @Override
    public void exitDictorsetmaker(final DictorsetmakerContext ctx) {
        super.exitDictorsetmaker(ctx);
    }

    @Override
    public void enterClassdef(final ClassdefContext ctx) {
        walkerHelper.createClass(ctx);
    }

    @Override
    public void exitClassdef(final ClassdefContext ctx) {
        super.exitClassdef(ctx);
    }

    @Override
    public void enterArglist(final ArglistContext ctx) {
        super.enterArglist(ctx);
    }

    @Override
    public void exitArglist(final ArglistContext ctx) {
        super.exitArglist(ctx);
    }

    @Override
    public void enterArgument(final ArgumentContext ctx) {
        super.enterArgument(ctx);
    }

    @Override
    public void exitArgument(final ArgumentContext ctx) {
        super.exitArgument(ctx);
    }

    @Override
    public void enterComp_iter(final Comp_iterContext ctx) {
        super.enterComp_iter(ctx);
    }

    @Override
    public void exitComp_iter(final Comp_iterContext ctx) {
        super.exitComp_iter(ctx);
    }

    @Override
    public void enterComp_for(final Comp_forContext ctx) {
        super.enterComp_for(ctx);
    }

    @Override
    public void exitComp_for(final Comp_forContext ctx) {
        super.exitComp_for(ctx);
    }

    @Override
    public void enterComp_if(final Comp_ifContext ctx) {
        super.enterComp_if(ctx);
    }

    @Override
    public void exitComp_if(final Comp_ifContext ctx) {
        super.exitComp_if(ctx);
    }

    @Override
    public void enterEncoding_decl(final Encoding_declContext ctx) {
        super.enterEncoding_decl(ctx);
    }

    @Override
    public void exitEncoding_decl(final Encoding_declContext ctx) {
        super.exitEncoding_decl(ctx);
    }

    @Override
    public void enterYield_expr(final Yield_exprContext ctx) {
        super.enterYield_expr(ctx);
    }

    @Override
    public void exitYield_expr(final Yield_exprContext ctx) {
        super.exitYield_expr(ctx);
    }

    @Override
    public void enterYield_arg(final Yield_argContext ctx) {
        super.enterYield_arg(ctx);
    }

    @Override
    public void exitYield_arg(final Yield_argContext ctx) {
        super.exitYield_arg(ctx);
    }

    @Override
    public void enterEveryRule(final ParserRuleContext ctx) {
        super.enterEveryRule(ctx);
    }

    @Override
    public void exitEveryRule(final ParserRuleContext ctx) {
        super.exitEveryRule(ctx);
    }

    @Override
    public void visitTerminal(final TerminalNode node) {
        super.visitTerminal(node);
    }

    @Override
    public void visitErrorNode(final ErrorNode node) {
        super.visitErrorNode(node);
    }
}
