package org.extendedalpha.pluginbase.subapi.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Represents transformations done to config values.
 * 
 * @author ExileDev
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ValueOptions
{
	/**
	 * An array of standard value options.
	 * 
	 * @return Standard value options
	 */
	ValueOption[] value();

	/**
	 * An array of custom value options. Options provided here must have a
	 * static method, <code>public static Object convert(Object)</code>, but
	 * this is unenforcable due to how Java handles annotations.
	 * 
	 * @return Custom value options
	 */
	Class<?>[] custom() default {};

	/**
	 * Represents a standard value option.
	 * 
	 * @author ExileDev
	 */
	public static enum ValueOption
	{
		FORMAT,
		LIST_LOWER_CASE,
		LOWER_CASE,
		MINUTE_TO_MILLIS,
		PARSE_ITEM,
		PARSE_ITEMS,
		SECOND_TO_MILLIS, ;
	}
}
