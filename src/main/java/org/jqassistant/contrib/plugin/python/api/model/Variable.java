package org.jqassistant.contrib.plugin.python.api.model;

import com.buschmais.jqassistant.plugin.common.api.model.NamedDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Relation.Incoming;

/**
 * Describes a field (i.e. static or instance variable).
 */
@Label(value = "Variable")
public interface Variable extends PythonSourceCode,
//        SignatureDescriptor,
        NamedDescriptor
//        TypedDescriptor
{

    @Declares
    @Incoming
    Method getMethod();
}
