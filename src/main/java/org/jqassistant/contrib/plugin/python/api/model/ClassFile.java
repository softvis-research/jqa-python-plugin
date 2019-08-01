package org.jqassistant.contrib.plugin.python.api.model;

import com.buschmais.jqassistant.plugin.common.api.model.FileDescriptor;
import com.buschmais.jqassistant.plugin.common.api.model.MD5Descriptor;
import com.buschmais.jqassistant.plugin.common.api.model.ValidDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Relation;

public interface ClassFile extends ObjectDescriptor, FileDescriptor, MD5Descriptor, ValidDescriptor {

    /**
     * Return the super class.
     *
     * @return The super class.
     */
    @Relation("INHERITS")
    ObjectDescriptor getSuperClass();

    /**
     * Set the super class.
     *
     * @param superClass
     *            The super class.
     */
    void setSuperClass(ObjectDescriptor superClass);

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
