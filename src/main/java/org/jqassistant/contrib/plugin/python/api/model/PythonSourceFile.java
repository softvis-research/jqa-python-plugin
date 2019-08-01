package org.jqassistant.contrib.plugin.python.api.model;

import com.buschmais.jqassistant.plugin.common.api.model.FileDescriptor;
import com.buschmais.xo.api.annotation.ResultOf;
import com.buschmais.xo.api.annotation.ResultOf.Parameter;
import com.buschmais.xo.neo4j.api.annotation.Cypher;
import com.buschmais.xo.neo4j.api.annotation.Relation;

import java.util.List;

public interface PythonSourceFile extends PythonSourceCode, FileDescriptor {

    @Relation("CONTAINS")
    List<ObjectDescriptor> getTypes();

    @ResultOf
    @Cypher("MATCH (file:PythonSourceCode:File) WHERE id(file)={this} MERGE (file)-[:CONTAINS]->(type:PythonSourceCode:Type{fqn:{fqn}}) RETURN type")
    ObjectDescriptor resolveType(@Parameter("fqn") String fqn);
}
