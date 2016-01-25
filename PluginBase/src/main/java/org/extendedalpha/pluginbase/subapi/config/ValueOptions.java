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
	 * Whether or not to allow null values. Defaults to false.
	 *
	 * @return True or false
	 */
	boolean allowNull() default false;

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
	 */
	public static enum ValueOption
	{
		FORMAT,
		LIST_LOWER_CASE,
		LOWER_CASE,
		MINUTE_TO_MILLIS,
		MINUTE_TO_TICKS,
		PARSE_ITEM,
		PARSE_ITEMS,
		PARSE_MATERIAL,
		PARSE_MATERIALS,
		SECOND_TO_MILLIS,
		SECOND_TO_TICKS,
		;
	}
}