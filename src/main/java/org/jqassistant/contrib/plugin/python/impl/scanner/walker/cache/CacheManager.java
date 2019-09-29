package org.jqassistant.contrib.plugin.python.impl.scanner.walker.cache;

import com.buschmais.jqassistant.core.scanner.api.ScannerContext;
import com.buschmais.jqassistant.core.store.api.model.Descriptor;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.ParserRuleContext;
import org.apache.commons.lang.StringUtils;
import org.jqassistant.contrib.plugin.python.api.model.PythonFile;
import org.jqassistant.contrib.plugin.python.api.model.PythonPackage;
import org.jqassistant.contrib.plugin.python.impl.scanner.RuleIndex;

/**
 * The helper delegates creation and caching of store objects
 *
 * @author Kevin M. Shrestha
 */
public class CacheManager {
    @Getter
    private String rootPath;
    @Getter
    @Setter
    private String currentPath;
    @Getter
    private PythonPackage pythonPackage;
    @Getter
    @Setter
    private PythonFile pythonFile;
    private ScannerContext scannerContext;
    @Getter
    private Cache cache = new Cache();

    public CacheManager(final PythonPackage pythonPackage, final ScannerContext scannerContext, final String rootPath) {
        pythonPackage.setFullQualifiedName(rootPath);
        this.pythonPackage = pythonPackage;
        this.scannerContext = scannerContext;
        this.rootPath = rootPath;

        RuleIndexCache ruleIndexCache = new RuleIndexCache();
        ContextEntityCache contextEntities = new ContextEntityCache();
        ContextEntity packageCE = new ContextEntity(pythonPackage.getName(), null, pythonPackage);
        contextEntities.add(packageCE);
        ruleIndexCache.put(RuleIndex.PACKAGE.getValue(), contextEntities);

        cache.put(rootPath, ruleIndexCache);
    }

    private void giveOrphanAHome(final String name, final ContextEntity ce, final ParserRuleContext ctx) {
        cacheObject(name, ce.getEntity(), ctx, currentPath);
    }

    public <T extends Descriptor> T getRuleOrOrphanFromCacheOrCreate(String name, Class<T> fileClass, ParserRuleContext ctx, Descriptor addDescriptorType, RuleIndex ruleIndex) {
        for (RuleIndexCache currentCache : cache.values()) {
            if (currentCache.containsKey(ruleIndex.getValue())) {
                for (ContextEntity ce : currentCache.get(ruleIndex.getValue())) {
                    if (ce.getName().contains(name)) {
                        if (ce.isTypeOfRuleIndex(ruleIndex)) {
                            T descriptor = tryCast(ce, fileClass);
                            if (fileClass.isInstance(descriptor)) {
                                return descriptor;
                            }
                        }
                    }
                }
            }
            if (currentCache.containsKey(RuleIndex.ORPHAN.getValue())) {
                for (ContextEntity ce : currentCache.get(RuleIndex.ORPHAN.getValue())) {
                    if (ce.getName().contains(name)) {
                        if (ctx != null) {
                            giveOrphanAHome(name, ce, ctx);
                            currentCache.remove(ce);
                        }
                        T descriptor = tryCast(ce, fileClass);
                        if (fileClass.isInstance(descriptor)) {
                            return descriptor;
                        }
                    }
                }
            }
        }

        return createAndCache(name, fileClass, ctx, addDescriptorType);
    }

    private <T extends Descriptor> T tryCast(final ContextEntity ce, Class<T> fileClass) {
        T testClass = null;
        try {
            testClass = (T) ce.getEntity();
            return testClass;
        } catch (ClassCastException e) {
            //skip
            //            System.out.println(e);
        }
        return null;
    }

    @Deprecated
    public PythonFile getFileOrOrphanFromCacheOrCreate(String name, ParserRuleContext ctx, Descriptor addDescriptorType) {
        for (RuleIndexCache currentCache : cache.values()) {
            if (currentCache.containsKey(RuleIndex.FILE.getValue())) {
                for (ContextEntity ce : currentCache.get(RuleIndex.FILE.getValue())) {
                    if (ce.getName().contains(name)) {
                        return (PythonFile) ce.getEntity();
                    }
                }
            }
            if (currentCache.containsKey(RuleIndex.ORPHAN.getValue())) {
                for (ContextEntity ce : currentCache.get(RuleIndex.ORPHAN.getValue())) {
                    if (ce.getName().contains(name)) {
                        if (ctx != null) {
                            giveOrphanAHome(name, ce, ctx);
                            currentCache.remove(ce);
                        }
                        return (PythonFile) ce.getEntity();
                    }
                }
            }
        }

        return createAndCache(name, PythonFile.class, ctx, addDescriptorType);
    }

    @Deprecated
    public PythonFile getOrphanFileFromCacheOrCreate(String name, ParserRuleContext ctx, Descriptor addDescriptorType) {
        for (RuleIndexCache currentCache : cache.values()) {
            if (currentCache.containsKey(RuleIndex.ORPHAN.getValue())) {
                for (ContextEntity ce : currentCache.get(RuleIndex.ORPHAN.getValue())) {
                    if (ce.getName().contains(name)) {
                        if (ctx != null) {
                            giveOrphanAHome(name, ce, ctx);
                            currentCache.remove(ce);
                        }
                        return (PythonFile) ce.getEntity();
                    }
                }
            }
        }

        return createAndCache(name, PythonFile.class, ctx, addDescriptorType);
    }

    public <T extends Descriptor> T createAndCache(String name, Class<T> fileClass, ParserRuleContext ctx,
            Descriptor addDescriptorType) {
        T storeObject = create(fileClass, addDescriptorType);
        return cacheObject(name, storeObject, ctx, currentPath);
    }

    private <T extends Descriptor> T create(Class<T> fileClass, Descriptor addDescriptorType) {
        T storeObject;
        if (addDescriptorType == null) {
            storeObject = scannerContext.getStore().create(fileClass);
        } else {
            storeObject = scannerContext.getStore().addDescriptorType(addDescriptorType, fileClass);
        }

        return storeObject;
    }

    private <T extends Descriptor> T cacheObject(String name, T storeObject, ParserRuleContext ctx, String fqn) {
        int ruleIndex;
        if (ctx == null) {
            ruleIndex = RuleIndex.ORPHAN.getValue();
            fqn = "ORPHAN";
        } else {
            ruleIndex = ctx.getRuleIndex();
        }
        if (StringUtils.isEmpty(fqn)) {
            fqn = currentPath;
        }
        if (StringUtils.isEmpty(name)) {
            name = currentPath;
        }
        if (!cache.containsKey(fqn)) {
            cache.put(fqn, new RuleIndexCache());
        }
        name = name.replace(".py", "");

        RuleIndexCache ruleIndexCache = cache.get(fqn);
        if (!ruleIndexCache.containsKey(ruleIndex)) {
            ruleIndexCache.put(ruleIndex, new ContextEntityCache());
        }
        ContextEntityCache contextEntityCache = ruleIndexCache.get(ruleIndex);

        contextEntityCache.add(new ContextEntity(name, ctx, storeObject));

        return storeObject;
    }

    public RuleIndexCache getCurrentCache() {
        return cache.get(currentPath);
    }
}

