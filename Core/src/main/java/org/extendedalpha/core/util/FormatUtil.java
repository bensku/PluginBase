/*
 * Copyright (C) 2014-2015  ExtendedAlpha
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.extendedalpha.core.util;

import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.regex.Pattern;

import org.extendedalpha.core.types.StringJoiner;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;

/**
 * Util used for general formatting.
 */

public class FormatUtil
{
	private FormatUtil() { }

	/**
	 * Formats a given string with its objects.
	 *
	 * @param format Base string
	 * @param objects Objects to format in
	 * @return Formatted string
	 * @see {@link MessageFormat#format(String, Object...)}
	 */
	public static String format(String format, Object... objects)
	{
		Validate.notNull(format, "format cannot be null!");

		try
		{
			format = MessageFormat.format(format, objects);
		} catch (Throwable ex) { }

		return replaceColors(format);
	}

	private static final String[] rainbowColors = new String[]
	{
			"c", "6", "e", "a", "b", "d", "5"
	};

	/**
	 * Replaces color codes in a given string. Includes rainbow.
	 *
	 * @param message Message to replace color codes in
	 * @return Formatted chat message
	 */
	public static String replaceColors(String message)
	{
		Validate.notNull(message, "message cannot be null!");
		message = message.replaceAll("(&([zZ]))", "&z");
		if (message.contains("&z"))
		{
			StringBuilder ret = new StringBuilder();
			String[] ss = message.split("&z");
			ret.append(ss[0]);
			ss[0] = null;

			for (String s : ss)
			{
				if (s != null)
				{
					int index = 0;
					while (index < s.length() && s.charAt(index) != '&')
					{
						ret.append("&" + rainbowColors[index % rainbowColors.length]);
						ret.append(s.charAt(index));
						index++;
					}

					if (index < s.length())
					{
						ret.append(s.substring(index));
					}
				}
			}

			message = ret.toString();
		}

		// Format the colors
		return ChatColor.translateAlternateColorCodes('&', message);
	}

	/**
	 * Returns a user-friendly representation of a given Object. This is mostly
	 * used for {@link Enum} constants.
	 * <p>
	 * If the object or any of its superclasses (minus Object) do not implement
	 * a toString() method, the object's simple name will be returned.
	 *
	 * @param obj Object to get the user-friendly representation of
	 * @return A user-friendly representation of the given Object.
	 */
	public static String getFriendlyName(Object obj)
	{
		Validate.notNull(obj, "obj cannot be null!");

		try
		{
			// Clever little method to check if the method isn't declared by a class other than Object.
			Method method = obj.getClass().getMethod("toString");
			if (method.getDeclaringClass().getSuperclass() == null)
				return obj.getClass().getSimpleName();
		} catch (Throwable ex) { }
		return getFriendlyName(obj.toString());
	}

	/**
	 * Returns a user-friendly version of a given String.
	 *
	 * @param string String to get the user-friendly version of
	 * @return A user-friendly version of the given String.
	 */
	public static String getFriendlyName(String string)
	{
		Validate.notNull(string, "string cannot be null!");

		return WordUtils.capitalize(string.toLowerCase().replaceAll("_", " "));
	}

	private static Pattern VOWELS = null;

	/**
	 * Returns the proper article of a given string
	 *
	 * @param string String to get the article for
	 * @return The proper article of a given string
	 */
	public static String getArticle(String string)
	{
		Validate.notNull(string, "string cannot be null!");

		if (VOWELS == null)
			VOWELS = Pattern.compile("[aeiou]");

		string = string.toLowerCase();
		return VOWELS.matcher(string).matches() ? "an" : "a";
	}

	/**
	 * Returns the proper plural of a given string
	 *
	 * @param string String to get the plural for
	 * @return The proper plural of a given string
	 */
	public static String getPlural(String string, int amount)
	{
		Validate.notNull(string, "string cannot be null!");

		amount = Math.abs(amount);
		if (amount != 1)
		{
			char end = string.charAt(string.length() - 1);
			if (end != 's')
				return Character.isUpperCase(end) ? string + "S" : string + "s";
		}

		return string;
	}

	/**
	 * Capitalizes the first letter of a given string.
	 * 
	 * @param string String to capitalize
	 * @return The resulting String
	 */
	public static String capitalizeFirst(String string)
	{
		Validate.notEmpty(string, "string cannot be null");
		return Character.toUpperCase(string.charAt(0)) + string.substring(1);
	}

	/**
	 * Joins together multiple given strings with the given glue using the
	 * {@link StringJoiner} class.
	 *
	 * @param delimiter String to join the args together with
	 * @param args Strings to join together
	 * @return Multiple strings joined together with the given glue.
	 * @see {@link StringJoiner}
	 */
	public static String join(String delimiter, String... args)
	{
		Validate.notNull(delimiter, "glue cannot be null");
		Validate.noNullElements(args, "args cannot have null elements!");

		return new StringJoiner(delimiter).appendAll(args).toString();
	}

	/**
	 * Joins together multiple given strings with a single space using the
	 * {@link StringJoiner} class.
	 *
	 * @param args Strings to join together
	 * @return Multiple strings joined together with a space
	 * @see {@link StringJoiner}
	 */
	public static String join(String... args)
	{
		Validate.noNullElements(args, "args cannot have null elements!");

		return StringJoiner.SPACE.newString().appendAll(args).toString();
	}
}
