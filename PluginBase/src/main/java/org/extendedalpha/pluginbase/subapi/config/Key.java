package org.extendedalpha.pluginbase.subapi.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Represents a key in a standard YAML configuration.
 * 
 * @author ExileDev
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Key
{
	/**
	 * This key's path, in standard YAML format
	 * 
	 * @return This key's path
	 */
	String value();
}
