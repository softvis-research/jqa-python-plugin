package org.jqassistant.contrib.plugin.python.api.model;

import com.buschmais.jqassistant.plugin.common.api.model.NamedDescriptor;
import com.buschmais.jqassistant.plugin.common.api.model.ValueDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Relation;
import com.buschmais.xo.neo4j.api.annotation.Relation.Incoming;
import com.buschmais.xo.neo4j.api.annotation.Relation.Outgoing;

import java.util.List;

/**
 * Describes a method.
 */
@Label(value = "Method")
public interface Method extends Python, Definable, NamedDescriptor, Signature {
    /**
     * Return all declared parameters of this method.
     *
     * @return The declared parameters.
     */
    @Relation("HAS")
    List<Parameter> getParameters();

    @Relation("HAS_DEFAULT")
    ValueDescriptor<?> getHasDefault();

    void setHasDefault(ValueDescriptor<?> hasDefault);

    /**
     * Return all declared throwables of this method.
     *
     * @return The declared throwables.
     */
    @Relation("RAISES")
    List<Exception> getDeclaredThrowables();

    /**
     * Return all read accesses to fields this method performs.
     *
     * @return All read accesses to fields this method performs.
     */
    @Outgoing
    @Reads
    List<Definable> getReads();

    /**
     * Return all write accesses to fields this method performs.
     *
     * @return All write accesses to fields this method performs.
     */
    @Outgoing
    @Writes
    List<Definable> getWrites();

    /**
     * Return all invocations of this method by other methods.
     *
     * @return The invocations of this method by other methods.
     */
    @Incoming
    @Calls
    List<PythonFile> getCalledBy();

    @Outgoing
    @Defines
    List<Variable> getVariables();


    /**
     * Return the first line number of the method.
     *
     * @return The first line number of the method.
     */
    Integer getFirstLineNumber();

    void setFirstLineNumber(Integer firstLineNumber);

    /**
     * Return the last line number of the method.
     *
     * @return The last line number of the method.
     */
    Integer getLastLineNumber();

    void setLastLineNumber(Integer lastLineNumber);

    /**
     * Return the number of source code lines containing code.
     *
     * @return The number of source code lines containing code.
     */
    int getEffectiveLineCount();

    void setEffectiveLineCount(int effectiveLineCount);
}
