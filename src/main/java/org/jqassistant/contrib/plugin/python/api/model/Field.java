package org.jqassistant.contrib.plugin.python.api.model;

import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Property;
import com.buschmais.xo.neo4j.api.annotation.Relation;

import java.util.List;

/**
 * Describes a field (i.e. static or instance variable) of a class.
 */
@Label(value = "Field")
public interface Field extends Member {

    /**
     * @param volatileField
     *            the volatileField to set
     */
    void setVolatile(Boolean volatileField);

    List<Writes> getWrittenBy();

    List<Reads> getReadBy();

    @Relation("HAS")
    PrimitiveValue getValue();

    void setValue(PrimitiveValue valueDescriptor);
}
