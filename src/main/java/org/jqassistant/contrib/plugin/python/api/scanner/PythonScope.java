package org.jqassistant.contrib.plugin.python.api.scanner;

import com.buschmais.jqassistant.core.scanner.api.Scope;

/**
 * Defines the scopes for Python Source Parser Plugin.
 */
public enum PythonScope implements Scope {
    SRC;

    public static final String FILE_EXTENSION = ".py";

    @Override
    public String getPrefix() {
        return "python";
    }

    @Override
    public String getName() {
        return name();
    }
}
