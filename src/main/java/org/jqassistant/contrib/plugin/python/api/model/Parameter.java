package org.jqassistant.contrib.plugin.python.api.model;

import com.buschmais.jqassistant.plugin.common.api.model.NamedDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Property;

/**
 * Describes a parameter of a method.
 */
@Label(value = "Parameter")
public interface Parameter extends Python, NamedDescriptor {
    public static final int ruleIndex = 99999;

    @Property("index")
    int getIndex();

    void setIndex(int index);
}
