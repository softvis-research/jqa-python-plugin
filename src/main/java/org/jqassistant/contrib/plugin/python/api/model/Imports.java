package org.jqassistant.contrib.plugin.python.api.model;

import com.buschmais.jqassistant.core.store.api.model.Descriptor;
import com.buschmais.xo.neo4j.api.annotation.Relation;
import com.buschmais.xo.neo4j.api.annotation.Relation.Incoming;
import com.buschmais.xo.neo4j.api.annotation.Relation.Outgoing;

@Relation("IMPORTS")
public interface Imports extends Descriptor {

    @Incoming
    Definable getDependency();

    @Outgoing
    Definable getDependent();

    Integer getWeight();

    void setWeight(Integer weight);

}
