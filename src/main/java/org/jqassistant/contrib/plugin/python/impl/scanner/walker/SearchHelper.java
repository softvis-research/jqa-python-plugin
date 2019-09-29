package org.jqassistant.contrib.plugin.python.impl.scanner.walker;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;
import org.jqassistant.contrib.plugin.python.antlr4.Python3Parser;
import org.jqassistant.contrib.plugin.python.impl.scanner.RuleIndex;
import org.jqassistant.contrib.plugin.python.impl.scanner.walker.cache.Cache;
import org.jqassistant.contrib.plugin.python.impl.scanner.walker.cache.CacheManager;
import org.jqassistant.contrib.plugin.python.impl.scanner.walker.cache.ContextEntity;
import org.jqassistant.contrib.plugin.python.impl.scanner.walker.cache.ContextEntityCache;
import org.jqassistant.contrib.plugin.python.impl.scanner.walker.cache.RuleIndexCache;

import java.util.Optional;
import java.util.Set;

class SearchHelper {
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

    static String findToken(ParseTree ctx, final int typeIndex) {
        final int childCount = ctx.getChildCount();
        for (int i = 0; i < childCount; i++) {
            Optional<TerminalNodeImpl> node = getTerminalNode(ctx, i);
            if (node.isPresent()) {
                if (checkForNodeType(node, typeIndex)) {
                    StringBuilder token = new StringBuilder();
//                    int prependingDotsIndex = 1;
//                    while (checkForNodeType(getTerminalNode(ctx, i - prependingDotsIndex), Python3Parser.DOT)) {
//                        token.append(".");
//                        prependingDotsIndex++;
//                    }
                    token.append(node.get().getText());
                    return token.toString();
                }
            } else {
                return findToken(ctx.getChild(i), typeIndex);
            }
        }
        String backupName = ctx.getChild(1).getText();
        return backupName.isEmpty() ? backupName : "null"; //if symbol 40 is not found
    }

    private static Optional<TerminalNodeImpl> getTerminalNode(final ParseTree ctx, final int i) {
        ParseTree object = ctx.getChild(i);
        if (object instanceof TerminalNodeImpl) {
            return Optional.of((TerminalNodeImpl) object);
        }
        return Optional.empty();
    }

    private static boolean checkForNodeType(final Optional<TerminalNodeImpl> child, final int typeIndex) {
        return child.filter(terminalNode -> terminalNode.getSymbol().getType() == typeIndex).isPresent();
    }

    static Optional<ContextEntity> findParentInAllCache(CacheManager cacheManager, ParserRuleContext ctx,
            RuleIndex parentRuleIndex) {
        Cache cache = cacheManager.getCache();
        for (RuleIndexCache currentCache : cache.values()) {
            Optional<ContextEntity> parent = findParentInThisCache(currentCache, ctx, parentRuleIndex);
            if (parent.isPresent()) {
                return parent;
            }
        }

        return Optional.empty();
    }

    static Optional<ContextEntity> findParentInThisCache(RuleIndexCache currentCache, ParserRuleContext ctx,
            RuleIndex parentRuleIndex) {
        if (currentCache == null) {
            return Optional.empty();
        }
        if (parentRuleIndex != RuleIndex.ANY) {
            return findParentByRuleIndex(currentCache, ctx, parentRuleIndex.getValue());
        }
        Set<Integer> cacheKeySet = currentCache.keySet();
        for (Integer integer : cacheKeySet) {
            Optional<ContextEntity> parent = findParentByRuleIndex(currentCache, ctx, integer);
            if (parent.isPresent()) {
                return parent;
            }
        }

        return Optional.empty();
    }

    private static Optional<ContextEntity> findParentByRuleIndex(RuleIndexCache currentCache, ParserRuleContext ctx,
            int parentRuleIndex) {
        ContextEntityCache cache = currentCache.get(parentRuleIndex);
        if (cache != null) {
            for (ContextEntity ce: cache) {
                Optional<ContextEntity> parent = recursiveFindParent(ctx, ce);
                if (parent.isPresent()) {
                    return parent;
                }
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
