package org.jqassistant.contrib.plugin.python.api.model;

import com.buschmais.jqassistant.plugin.common.api.model.NamedDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;

/**
 * Describes an Exception.
 */
@Label(value = "Exception")
public interface Exception extends Python, NamedDescriptor {

}
