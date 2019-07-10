package org.jqassistant.contrib.plugin.python.api.scanner;

import com.buschmais.jqassistant.core.scanner.api.Scope;

/**
 * Defines the scopes for Java SourceParser Plugin.
 */
public enum PythonScope implements Scope {
    SRC;

    @Override
    public String getPrefix() {
        return "python";
    }

    @Override
    public String getName() {
        return name();
    }
}
