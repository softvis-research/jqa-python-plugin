package org.jqassistant.contrib.plugin.python.impl.scanner.visitor;

import com.buschmais.jqassistant.core.store.api.model.Descriptor;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;
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
import org.jqassistant.contrib.plugin.python.antlr4.Python3Visitor;

public class PythonSourceVisitor<T extends Descriptor> implements Python3Visitor<T> {
    protected VisitorHelper visitorHelper;
    protected Descriptor descriptor;

    public PythonSourceVisitor(VisitorHelper visitorHelper) {
        this.visitorHelper = visitorHelper;
    }

    @Override 
    public T visitSingle_input(final Single_inputContext ctx) {
        return null;
    }

    @Override 
    public T visitFile_input(final File_inputContext ctx) {
        return null;
    }

    @Override 
    public T visitEval_input(final Eval_inputContext ctx) {
        return null;
    }

    @Override 
    public T visitDecorator(final DecoratorContext ctx) {
        return null;
    }

    @Override 
    public T visitDecorators(final DecoratorsContext ctx) {
        return null;
    }

    @Override 
    public T visitDecorated(final DecoratedContext ctx) {
        return null;
    }

    @Override 
    public T visitAsync_funcdef(final Async_funcdefContext ctx) {
        return null;
    }

    @Override 
    public T visitFuncdef(final FuncdefContext ctx) {
        return null;
    }

    @Override 
    public T visitParameters(final ParametersContext ctx) {
        return null;
    }

    @Override 
    public T visitTypedargslist(final TypedargslistContext ctx) {
        return null;
    }

    @Override 
    public T visitTfpdef(final TfpdefContext ctx) {
        return null;
    }

    @Override 
    public T visitVarargslist(final VarargslistContext ctx) {
        return null;
    }

    @Override 
    public T visitVfpdef(final VfpdefContext ctx) {
        return null;
    }

    @Override 
    public T visitStmt(final StmtContext ctx) {
        return null;
    }

    @Override 
    public T visitSimple_stmt(final Simple_stmtContext ctx) {
        return null;
    }

    @Override 
    public T visitSmall_stmt(final Small_stmtContext ctx) {
        return null;
    }

    @Override 
    public T visitExpr_stmt(final Expr_stmtContext ctx) {
        return null;
    }

    @Override 
    public T visitAnnassign(final AnnassignContext ctx) {
        return null;
    }

    @Override 
    public T visitTestlist_star_expr(final Testlist_star_exprContext ctx) {
        return null;
    }

    @Override 
    public T visitAugassign(final AugassignContext ctx) {
        return null;
    }

    @Override 
    public T visitDel_stmt(final Del_stmtContext ctx) {
        return null;
    }

    @Override 
    public T visitPass_stmt(final Pass_stmtContext ctx) {
        return null;
    }

    @Override 
    public T visitFlow_stmt(final Flow_stmtContext ctx) {
        return null;
    }

    @Override 
    public T visitBreak_stmt(final Break_stmtContext ctx) {
        return null;
    }

    @Override 
    public T visitContinue_stmt(final Continue_stmtContext ctx) {
        return null;
    }

    @Override 
    public T visitReturn_stmt(final Return_stmtContext ctx) {
        return null;
    }

    @Override 
    public T visitYield_stmt(final Yield_stmtContext ctx) {
        return null;
    }

    @Override 
    public T visitRaise_stmt(final Raise_stmtContext ctx) {
        return null;
    }

    @Override 
    public T visitImport_stmt(final Import_stmtContext ctx) {
        return null;
    }

    @Override 
    public T visitImport_name(final Import_nameContext ctx) {
        return null;
    }

    @Override 
    public T visitImport_from(final Import_fromContext ctx) {
        return null;
    }

    @Override 
    public T visitImport_as_name(final Import_as_nameContext ctx) {
        return null;
    }

    @Override 
    public T visitDotted_as_name(final Dotted_as_nameContext ctx) {
        return null;
    }

    @Override 
    public T visitImport_as_names(final Import_as_namesContext ctx) {
        return null;
    }

    @Override 
    public T visitDotted_as_names(final Dotted_as_namesContext ctx) {
        return null;
    }

    @Override 
    public T visitDotted_name(final Dotted_nameContext ctx) {
        return null;
    }

    @Override 
    public T visitGlobal_stmt(final Global_stmtContext ctx) {
        return null;
    }

    @Override 
    public T visitNonlocal_stmt(final Nonlocal_stmtContext ctx) {
        return null;
    }

    @Override 
    public T visitAssert_stmt(final Assert_stmtContext ctx) {
        return null;
    }

    @Override 
    public T visitCompound_stmt(final Compound_stmtContext ctx) {
        return null;
    }

    @Override 
    public T visitAsync_stmt(final Async_stmtContext ctx) {
        return null;
    }

    @Override 
    public T visitIf_stmt(final If_stmtContext ctx) {
        return null;
    }

    @Override 
    public T visitWhile_stmt(final While_stmtContext ctx) {
        return null;
    }

    @Override 
    public T visitFor_stmt(final For_stmtContext ctx) {
        return null;
    }

    @Override 
    public T visitTry_stmt(final Try_stmtContext ctx) {
        return null;
    }

    @Override 
    public T visitWith_stmt(final With_stmtContext ctx) {
        return null;
    }

    @Override 
    public T visitWith_item(final With_itemContext ctx) {
        return null;
    }

    @Override 
    public T visitExcept_clause(final Except_clauseContext ctx) {
        return null;
    }

    @Override 
    public T visitSuite(final SuiteContext ctx) {
        return null;
    }

    @Override 
    public T visitTest(final TestContext ctx) {
        return null;
    }

    @Override 
    public T visitTest_nocond(final Test_nocondContext ctx) {
        return null;
    }

    @Override 
    public T visitLambdef(final LambdefContext ctx) {
        return null;
    }

    @Override 
    public T visitLambdef_nocond(final Lambdef_nocondContext ctx) {
        return null;
    }

    @Override 
    public T visitOr_test(final Or_testContext ctx) {
        return null;
    }

    @Override 
    public T visitAnd_test(final And_testContext ctx) {
        return null;
    }

    @Override 
    public T visitNot_test(final Not_testContext ctx) {
        return null;
    }

    @Override 
    public T visitComparison(final ComparisonContext ctx) {
        return null;
    }

    @Override 
    public T visitComp_op(final Comp_opContext ctx) {
        return null;
    }

    @Override 
    public T visitStar_expr(final Star_exprContext ctx) {
        return null;
    }

    @Override 
    public T visitExpr(final ExprContext ctx) {
        return null;
    }

    @Override 
    public T visitXor_expr(final Xor_exprContext ctx) {
        return null;
    }

    @Override 
    public T visitAnd_expr(final And_exprContext ctx) {
        return null;
    }

    @Override 
    public T visitShift_expr(final Shift_exprContext ctx) {
        return null;
    }

    @Override 
    public T visitArith_expr(final Arith_exprContext ctx) {
        return null;
    }

    @Override 
    public T visitTerm(final TermContext ctx) {
        return null;
    }

    @Override 
    public T visitFactor(final FactorContext ctx) {
        return null;
    }

    @Override 
    public T visitPower(final PowerContext ctx) {
        return null;
    }

    @Override 
    public T visitAtom_expr(final Atom_exprContext ctx) {
        return null;
    }

    @Override 
    public T visitAtom(final AtomContext ctx) {
        return null;
    }

    @Override 
    public T visitTestlist_comp(final Testlist_compContext ctx) {
        return null;
    }

    @Override 
    public T visitTrailer(final TrailerContext ctx) {
        return null;
    }

    @Override 
    public T visitSubscriptlist(final SubscriptlistContext ctx) {
        return null;
    }

    @Override 
    public T visitSubscript(final SubscriptContext ctx) {
        return null;
    }

    @Override 
    public T visitSliceop(final SliceopContext ctx) {
        return null;
    }

    @Override 
    public T visitExprlist(final ExprlistContext ctx) {
        return null;
    }

    @Override 
    public T visitTestlist(final TestlistContext ctx) {
        return null;
    }

    @Override 
    public T visitDictorsetmaker(final DictorsetmakerContext ctx) {
        return null;
    }

    @Override 
    public T visitClassdef(final ClassdefContext ctx) {
        return null;
    }

    @Override 
    public T visitArglist(final ArglistContext ctx) {
        return null;
    }

    @Override 
    public T visitArgument(final ArgumentContext ctx) {
        return null;
    }

    @Override 
    public T visitComp_iter(final Comp_iterContext ctx) {
        return null;
    }

    @Override 
    public T visitComp_for(final Comp_forContext ctx) {
        return null;
    }

    @Override 
    public T visitComp_if(final Comp_ifContext ctx) {
        return null;
    }

    @Override 
    public T visitEncoding_decl(final Encoding_declContext ctx) {
        return null;
    }

    @Override 
    public T visitYield_expr(final Yield_exprContext ctx) {
        return null;
    }

    @Override 
    public T visitYield_arg(final Yield_argContext ctx) {
        return null;
    }

    @Override 
    public T visit(final ParseTree tree) {
        return null;
    }

    @Override 
    public T visitChildren(final RuleNode node) {
        return null;
    }

    @Override 
    public T visitTerminal(final TerminalNode node) {
        return null;
    }

    @Override 
    public T visitErrorNode(final ErrorNode node) {
        return null;
    }
}
