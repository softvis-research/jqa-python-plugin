package org.jqassistant.contrib.plugin.python.api.model;

import com.buschmais.jqassistant.core.store.api.model.FullQualifiedNameDescriptor;
import com.buschmais.jqassistant.plugin.common.api.model.DirectoryDescriptor;
import com.buschmais.jqassistant.plugin.common.api.model.FileContainerDescriptor;
import com.buschmais.jqassistant.plugin.common.api.model.NamedDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;

/**
 * Describes a Python package.
 */
@Label(value = "Package", usingIndexedPropertyOf = FullQualifiedNameDescriptor.class)
public interface PythonPackage extends Python, FullQualifiedNameDescriptor, DirectoryDescriptor, FileContainerDescriptor,
        NamedDescriptor {
}
