package org.jqassistant.contrib.plugin.python.api.model;

import com.buschmais.jqassistant.plugin.common.api.model.ValueDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Property;

/**
 * Represents a primitive value.
 */
@Label("Value")
public interface Value extends Python, ValueDescriptor<Object> {

    @Property("value")
    @Override
    Object getValue();

    @Override
    void setValue(Object value);
}
