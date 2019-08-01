package org.jqassistant.contrib.plugin.python.api.model;

import com.buschmais.jqassistant.core.store.api.model.FullQualifiedNameDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Relation.Incoming;

import java.util.List;
import java.util.Set;

import static com.buschmais.xo.neo4j.api.annotation.Relation.Outgoing;

/**
 * Describes an object.
 */
@Label(value = "Object", usingIndexedPropertyOf = FullQualifiedNameDescriptor.class)
public interface ObjectDescriptor extends PackageMember {

    @Incoming
    @Declares
    ObjectDescriptor getDeclaringObject();

    /**
     * Return the declared inner classes.
     *
     * @return The declared inner classes.
     */
    @Outgoing
    @Declares
    Set<ObjectDescriptor> getDeclaredInnerClasses();

    /**
     * Return the declared methods.
     *
     * @return The declared methods.
     */
    @Outgoing
    @Declares
    List<Method> getDeclaredMethods();

    /**
     * Return the declared variables.
     *
     * @return The declared variables.
     */
    @Outgoing
    @Declares
    List<Variable> getDeclaredVariables();


    @Outgoing
    List<Imports> getDependencies();

    @Incoming
    List<Imports> getDependents();

}
