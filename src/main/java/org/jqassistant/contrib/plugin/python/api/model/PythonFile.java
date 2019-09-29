package org.jqassistant.contrib.plugin.python.api.model;

import com.buschmais.jqassistant.core.store.api.model.FullQualifiedNameDescriptor;
import com.buschmais.jqassistant.plugin.common.api.model.FileDescriptor;
import com.buschmais.jqassistant.plugin.common.api.model.NamedDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Relation;
import com.buschmais.xo.neo4j.api.annotation.Relation.Outgoing;

import java.util.List;

@Label("File")
public interface PythonFile extends Python, Definable, FileDescriptor, NamedDescriptor, FullQualifiedNameDescriptor {
//    @Incoming
//    @Relation("IMPORTED BY")
//    List<PythonFile> getDependents();

    @Outgoing
    @Relation("IMPORTS")
    List<PythonFile> getDependencies();
}
