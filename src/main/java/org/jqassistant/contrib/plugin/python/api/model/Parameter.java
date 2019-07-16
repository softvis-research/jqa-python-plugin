package org.jqassistant.contrib.plugin.python.api.model;

import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Property;

/**
 * Describes a parameter of a method.
 */
@Label(value = "Parameter")
public interface Parameter extends PythonSourceCode {

    @Property("index")
    int getIndex();

    void setIndex(int index);

}
