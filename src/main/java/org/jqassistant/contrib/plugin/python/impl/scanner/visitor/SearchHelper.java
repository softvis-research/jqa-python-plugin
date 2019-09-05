package org.jqassistant.contrib.plugin.python.impl.scanner.visitor;

import java.util.Map;
import java.util.Set;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser;
import org.jqassistant.contrib.plugin.python.impl.scanner.RuleIndex;

import com.buschmais.jqassistant.core.store.api.model.Descriptor;

public class SearchHelper {


    public static String findNameToken(ParseTree ctx) {
        final int childCount = ctx.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ParseTree object = ctx.getChild(i);
            if (object instanceof TerminalNodeImpl) {
                TerminalNodeImpl child = (TerminalNodeImpl) object;
                if (child.getSymbol().getType() == Python3Parser.NAME) {
                    return child.getText();
                }
            } else {
                return findNameToken(object);
            }
        }
        String backupName = ctx.getChild(1).getText();
        return backupName.isEmpty() ? backupName : "null"; //if symbol 40 is not found
    }

    public static ImmutablePair<ParserRuleContext, Descriptor> findParentInCache(StoreHelper storeHelper, ParserRuleContext ctx, RuleIndex parentRuleIndex) {
        if (parentRuleIndex != RuleIndex.ANY) {
            return findParentByRuleIndex(storeHelper, ctx, parentRuleIndex.getValue());
        }
        Set<Integer> cacheKeySet = storeHelper.getCacheKeySet();
        for (Integer integer : cacheKeySet) {
            ImmutablePair<ParserRuleContext, Descriptor> parent = findParentByRuleIndex(storeHelper, ctx, integer);
            if (parent != null) {
                return parent;
            }
        }

        return null;
    }

    public static ImmutablePair<ParserRuleContext, Descriptor> findParentByRuleIndex(StoreHelper storeHelper, ParserRuleContext ctx, int parentRuleIndex) {
        Set<ImmutablePair<ParserRuleContext, Descriptor>> allObjects = storeHelper.getCacheByRuleIndex(parentRuleIndex);
        for (ImmutablePair<ParserRuleContext, Descriptor> pair: allObjects) {
            ImmutablePair<ParserRuleContext, Descriptor> parent = recursiveFindParent(ctx, pair);
            if (parent != null) {
                return parent;
            }
        }
        return null;
    }

    private static ImmutablePair<ParserRuleContext, Descriptor> recursiveFindParent(
            ParserRuleContext ctx,
            ImmutablePair<ParserRuleContext, Descriptor> pair) {

        ParserRuleContext parent = ctx.getParent();
        if (parent == null) {
            return null;
        }

        ParserRuleContext searchCtx = pair.getLeft();
        if (parent == searchCtx) {
            return pair;
        }

        return recursiveFindParent(parent, pair);
    }
}
