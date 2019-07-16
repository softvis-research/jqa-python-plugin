package org.jqassistant.contrib.plugin.python.api.model;

import com.buschmais.jqassistant.plugin.common.api.model.FileDescriptor;
import com.buschmais.xo.api.annotation.ResultOf;
import com.buschmais.xo.api.annotation.ResultOf.Parameter;
import com.buschmais.xo.neo4j.api.annotation.Cypher;
import com.buschmais.xo.neo4j.api.annotation.Relation;

import java.util.List;

public interface PythonSourceFile extends PythonSourceCode, FileDescriptor {

    @Relation("CONTAINS")
    List<Type> getTypes();

    @ResultOf
    @Cypher("MATCH (file:SourceCode:File) WHERE id(file)={this} MERGE (file)-[:CONTAINS]->(type:SourceCode:Type{fqn:{fqn}}) RETURN type")
    Type resolveType(@Parameter("fqn") String fqn);
}
