package org.jqassistant.contrib.plugin.python.api.model;

import com.buschmais.jqassistant.core.store.api.model.Descriptor;
import com.buschmais.xo.neo4j.api.annotation.Relation;
import com.buschmais.xo.neo4j.api.annotation.Relation.Incoming;
import com.buschmais.xo.neo4j.api.annotation.Relation.Outgoing;

/**
 * Defines a WRITES relation between a method and a field.
 */
@Relation("WRITES")
public interface Writes extends Descriptor, LineNumber {

    @Outgoing
    Method getMethod();

    @Incoming
    Variable getField();

}
