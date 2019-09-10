package org.jqassistant.contrib.plugin.python.impl.scanner.walker;

import com.buschmais.jqassistant.core.scanner.api.ScannerContext;
import com.buschmais.jqassistant.core.store.api.model.Descriptor;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.ParserRuleContext;
import org.jqassistant.contrib.plugin.python.api.model.PythonFile;
import org.jqassistant.contrib.plugin.python.api.model.PythonPackage;
import org.jqassistant.contrib.plugin.python.impl.scanner.RuleIndex;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

/**
 * The helper delegates creation and caching of store objects
 *
 * @author Kevin M. Shrestha
 */
public class StoreHelper {
    private String fqn;
    @Getter
    private PythonPackage pythonPackage;
    @Getter
    @Setter
    private PythonFile pythonFile;
    private ScannerContext scannerContext;
    private Cache cache = new Cache();

    public StoreHelper(final PythonPackage pythonPackage,
            final ScannerContext scannerContext) {
        this.pythonPackage = pythonPackage;
        this.scannerContext = scannerContext;

        RuleCache ruleCache = new RuleCache();
        ContextEntityCache contextEntities = new ContextEntityCache();
        ContextEntity packageCE = new ContextEntity(null, pythonPackage);
        contextEntities.add(packageCE);
        ruleCache.put(RuleIndex.PACKAGE.getValue(), contextEntities);

        this.pythonPackage.setFullQualifiedName(this.pythonPackage.getFileName());
        this.fqn = this.pythonPackage.getFileName();
        cache.put(fqn, ruleCache);
    }

    public void cacheFile(PythonFile pythonFile, ParserRuleContext ctx) {
        int ruleIndex = ctx.getRuleIndex();

        RuleCache ruleCache = cache.get(fqn);
        if (!ruleCache.containsKey(ruleIndex)) {
            ruleCache.put(ruleIndex, new ContextEntityCache());
        }

        ContextEntityCache contextEntityCache = ruleCache.get(ruleIndex);

        contextEntityCache.add(new ContextEntity(ctx, pythonFile));
    }

    public <T extends Descriptor> T createAndCache(Class<T> fileClass, ParserRuleContext ctx, Descriptor additionalDescriptorType) {
        T storeObject;
        if (additionalDescriptorType == null) {
            storeObject = scannerContext.getStore().create(fileClass);
        } else {
            storeObject = scannerContext.getStore().addDescriptorType(additionalDescriptorType, fileClass);
        }

        int ruleIndex = ctx.getRuleIndex();

        RuleCache ruleCache = cache.get(fqn);
        if (!ruleCache.containsKey(ruleIndex)) {
            ruleCache.put(ruleIndex, new ContextEntityCache());
        }
        ContextEntityCache contextEntityCache = ruleCache.get(ruleIndex);

        contextEntityCache.add(new ContextEntity(ctx, storeObject));

        return storeObject;
    }

    public ContextEntityCache getCacheByRuleIndex(String fqn, int ruleIndex) {
        if (fqn.isEmpty()) {
            fqn = this.fqn;
        }
        RuleCache ruleCache = cache.get(fqn);
        return ruleCache.get(ruleIndex);
    }

    public Set<Integer> getCacheKeySet(String fqn) {
        if (fqn.isEmpty()) {
            fqn = this.fqn;
        }
        return cache.get(fqn).keySet();
    }
}

class Cache extends HashMap<String, RuleCache> {}

class RuleCache extends TreeMap<Integer, ContextEntityCache> {}

class ContextEntityCache extends HashSet<ContextEntity> {}

