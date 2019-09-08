package org.jqassistant.contrib.plugin.python.api.model;

import com.buschmais.jqassistant.plugin.common.api.model.NamedDescriptor;
import com.buschmais.jqassistant.plugin.common.api.model.ValueDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Relation;
import com.buschmais.xo.neo4j.api.annotation.Relation.Incoming;

import java.util.List;

/**
 * Describes a variable (i.e. static or instance variable)
 */
@Label(value = "Variable")
public interface Variable extends Python, NamedDescriptor, Signature {

    @Defines
    @Incoming
    Object getDeclaringObject();

    @Defines
    @Incoming
    Method getDeclaringMethod();

    @Incoming
    @Writes
    List<Definable> getWrittenBy();

    @Incoming
    @Reads
    List<Definable> getReadBy();

    @Relation("HAS")
    ValueDescriptor getValue();

    void setValue(ValueDescriptor valueDescriptor);
}
