package org.jqassistant.contrib.plugin.python.api.model;

import com.buschmais.jqassistant.core.store.api.model.Descriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Relation.Incoming;

import java.util.List;
import java.util.Set;

import static com.buschmais.xo.neo4j.api.annotation.Relation.Outgoing;

/**
 * Describes the properties that Files, Classes and Methods have in common.
 */
@Label(value = "Definable")
public interface Definable extends Python, Descriptor {
    @Incoming
    @Contains
    Object getParentObject();

    void setParentObject(Object parentObject);

    /**
     * Return the declared inner classes.
     *
     * @return The declared inner classes.
     */
    @Outgoing
    @Contains
    Set<PythonClass> getContainedClasses();

    /**
     * Return the declared methods.
     *
     * @return The declared methods.
     */
    @Outgoing
    @Defines
    List<Method> getDefinedMethods();

    /**
     * Return the declared methods.
     *
     * @return The declared methods.
     */
    @Outgoing
    @Calls
    List<Method> getCalledMethods();

    /**
     * Return the declared variables.
     *
     * @return The declared variables.
     */
    @Outgoing
    @Defines
    List<Variable> getDeclaredVariables();
}
