package org.jqassistant.contrib.plugin.python.impl.scanner.walker;

import com.buschmais.jqassistant.core.store.api.model.Descriptor;
import org.antlr.v4.runtime.ParserRuleContext;
import org.apache.commons.lang3.tuple.MutablePair;

public class ContextEntity extends MutablePair<ParserRuleContext, Descriptor> {
    <T extends Descriptor> ContextEntity(final ParserRuleContext ctx, final T storeObject) {
        super(ctx, storeObject);
    }

    public ParserRuleContext getContext() {
        return super.getLeft();
    }

    public Descriptor getEntity() {
        return super.getRight();
    }
}
