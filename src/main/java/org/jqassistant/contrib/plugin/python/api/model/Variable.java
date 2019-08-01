package org.jqassistant.contrib.plugin.python.api.model;

import com.buschmais.jqassistant.core.store.api.model.Descriptor;
import com.buschmais.jqassistant.plugin.common.api.model.NamedDescriptor;
import com.buschmais.jqassistant.plugin.common.api.model.ValueDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Property;
import com.buschmais.xo.neo4j.api.annotation.Relation;
import com.buschmais.xo.neo4j.api.annotation.Relation.Incoming;

import java.util.List;

/**
 * Describes a variable (i.e. static or instance variable)
 */
@Label(value = "Variable")
public interface Variable extends PythonSourceCode, NamedDescriptor, Signature, Descriptor {

    @Declares
    @Incoming
    Object getDeclaringObject();

    @Declares
    @Incoming
    Method getDeclaringMethod();

    @Incoming
    List<Writes> getWrittenBy();

    @Incoming
    List<Reads> getReadBy();

    @Relation("HAS")
    ValueDescriptor getValue();

    void setValue(ValueDescriptor valueDescriptor);
}
