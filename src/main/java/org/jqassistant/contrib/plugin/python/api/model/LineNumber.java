package org.jqassistant.contrib.plugin.python.api.model;

import com.buschmais.xo.neo4j.api.annotation.Property;

/**
 * Defines a descriptor containing line number information.
 */
public interface LineNumber {

    @Property("lineNumber")
    Integer getLineNumber();

    void setLineNumber(Integer lineNumber);

}
