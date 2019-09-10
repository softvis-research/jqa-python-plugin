package org.jqassistant.contrib.plugin.python.impl.scanner.walker;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser;
import org.jqassistant.contrib.plugin.python.impl.scanner.RuleIndex;

import java.util.Optional;
import java.util.Set;

public class SearchHelper {
    private ParseTree searchChildrenForStringText(Python3Parser.File_inputContext ctx, String searchString) {
        final int childCount = ctx.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ParseTree child = ctx.getChild(i);
            if (child.getText().equals(searchString)) {
                return child;
            }
        }
        return null;
    }

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

    public static Optional<ContextEntity> findParentInCache(StoreHelper storeHelper, ParserRuleContext ctx, RuleIndex parentRuleIndex) {
        if (parentRuleIndex != RuleIndex.ANY) {
            return findParentByRuleIndex(storeHelper, ctx, parentRuleIndex.getValue());
        }
        Set<Integer> cacheKeySet = storeHelper.getCacheKeySet("");
        for (Integer integer : cacheKeySet) {
            Optional<ContextEntity> parent = findParentByRuleIndex(storeHelper, ctx, integer);
            if (parent.isPresent()) {
                return parent;
            }
        }

        return Optional.empty();
    }

    public static Optional<ContextEntity> findParentByRuleIndex(StoreHelper storeHelper, ParserRuleContext ctx, int parentRuleIndex) {
        ContextEntityCache cache = storeHelper.getCacheByRuleIndex("", parentRuleIndex);
        for (ContextEntity ce: cache) {
            Optional<ContextEntity> parent = recursiveFindParent(ctx, ce);
            if (parent.isPresent()) {
                return parent;
            }
        }
        return Optional.empty();
    }

    private static Optional<ContextEntity> recursiveFindParent(ParserRuleContext ctx, ContextEntity ce) {

        ParserRuleContext parent = ctx.getParent();
        if (parent == null) {
            return Optional.empty();
        }

        ParserRuleContext searchCtx = ce.getContext();
        if (parent == searchCtx) {
            return Optional.of(ce);
        }

        return recursiveFindParent(parent, ce);
    }
}
