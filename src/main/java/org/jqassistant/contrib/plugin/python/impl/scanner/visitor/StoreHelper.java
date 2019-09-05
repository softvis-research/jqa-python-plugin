package org.jqassistant.contrib.plugin.python.impl.scanner.visitor;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

import com.buschmais.jqassistant.core.scanner.api.ScannerContext;
import com.buschmais.jqassistant.core.store.api.model.Descriptor;

import org.antlr.v4.runtime.ParserRuleContext;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.jqassistant.contrib.plugin.python.api.model.PythonSourceFile;

/**
 * The helper delegates creation and caching of store objects
 *
 * @author Kevin M. Shrestha
 */
public class StoreHelper {
    private ScannerContext scannerContext;
    private PythonSourceFile pythonSourceFile;
    private TreeMap<Integer, Set<ImmutablePair<ParserRuleContext, Descriptor>>> cache;

    public StoreHelper(ScannerContext scannerContext, PythonSourceFile pythonSourceFile) {
        this.scannerContext = scannerContext;
        this.pythonSourceFile = pythonSourceFile;
        cache = new TreeMap<>();
    }

    public <T extends Descriptor> T createAndCache(Class<T> fileClass, ParserRuleContext ctx) {
        T storeObject = scannerContext.getStore().create(fileClass);
        int ruleIndex = ctx.getRuleIndex();
//        if (!cache.containsKey(storeClassName)) {
//            TreeMap<String, Object> objectCache = new TreeMap<>();
//            cache.put(storeClassName, objectCache);
//        }
        if (!cache.containsKey(ruleIndex)) {
            cache.put(ruleIndex, new HashSet<>());
        }
        Set<ImmutablePair<ParserRuleContext, Descriptor>> objects = cache.get(ruleIndex);

        ImmutablePair<ParserRuleContext, Descriptor> objectWithContext =  new ImmutablePair<>(ctx, storeObject);
        objects.add(objectWithContext);

        return storeObject;
    }

    public Set<ImmutablePair<ParserRuleContext, Descriptor>> getCacheByRuleIndex(int ruleIndex) {
        return cache.get(ruleIndex);
    }

    public Set<Integer> getCacheKeySet() {
        return cache.keySet();
    }
}
