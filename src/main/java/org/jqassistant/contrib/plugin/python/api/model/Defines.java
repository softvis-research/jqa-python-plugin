package org.jqassistant.contrib.plugin.python.api.model;

import com.buschmais.xo.neo4j.api.annotation.Relation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines the defines relation used for variables, methods and inner classes.
 */
@Relation("DEFINES")
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Defines {
}
