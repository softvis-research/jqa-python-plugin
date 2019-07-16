package org.jqassistant.contrib.plugin.python.api.model;

import com.buschmais.jqassistant.core.store.api.model.FullQualifiedNameDescriptor;
import com.buschmais.jqassistant.plugin.common.api.model.NamedDescriptor;

/**
 * Defines a common base descriptor for all members of
 * {@link Package}s.
 */
public interface PackageMember extends FullQualifiedNameDescriptor, NamedDescriptor {
}
