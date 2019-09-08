package org.jqassistant.contrib.plugin.python.api.model;

import com.buschmais.jqassistant.core.store.api.model.FullQualifiedNameDescriptor;
import com.buschmais.jqassistant.plugin.common.api.model.NamedDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Relation;

@Label(value = "Class", usingIndexedPropertyOf = FullQualifiedNameDescriptor.class)
public interface Class extends Python, NamedDescriptor, Definable {

    /**
     * Return the super class.
     *
     * @return The super class.
     */
    @Relation("INHERITS")
    Class getSuperClass();

    /**
     * Set the super class.
     *
     * @param superClass
     *            The super class.
     */
    void setSuperClass(Class superClass);

    /**
     * Return the name of the source file.
     *
     * @return The name of the source file.
     */
    String getSourceFileName();

    /**
     * Set the name of the source file.
     *
     * @param sourceFileName
     *            The name of the source file.
     */
    void setSourceFileName(String sourceFileName);
}
