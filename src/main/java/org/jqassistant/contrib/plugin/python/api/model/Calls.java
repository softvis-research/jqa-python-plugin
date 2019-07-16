package org.jqassistant.contrib.plugin.python.api.model;

import com.buschmais.jqassistant.core.store.api.model.Descriptor;
import com.buschmais.xo.neo4j.api.annotation.Relation;
import com.buschmais.xo.neo4j.api.annotation.Relation.Incoming;
import com.buschmais.xo.neo4j.api.annotation.Relation.Outgoing;

/**
 * Defines an CALLS relation between two methods.
 */
@Relation("CALLS")
public interface Calls extends Descriptor, LineNumber {

    @Outgoing
    Method getCallingMethod();

    @Incoming
    Method getCalledMethod();

}
