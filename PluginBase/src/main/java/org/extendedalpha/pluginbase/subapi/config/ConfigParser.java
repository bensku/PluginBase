package org.extendedalpha.pluginbase.subapi.config;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.bukkit.Material;
import org.extendedalpha.pluginbase.subapi.SubPlugin;
import org.extendedalpha.pluginbase.subapi.util.*;

import org.bukkit.configuration.file.FileConfiguration;

/**
 * Parses a configuration class.
 *
 * @author Jaryn-R
 */

public final class ConfigParser
{
	/**
	 * Parses an instance-based configuration.
	 *
	 * @param plugin Plugin instance
	 * @param object Object to parse
	 */
	public static void parse(SubPlugin plugin, Object object)
	{
		parse(plugin, object.getClass(), object);
	}

	/**
	 * Parses a static-based configuration.
	 *
	 * @param plugin Plugin instance
	 * @param clazz Configuration class
	 */
	public static void parse(SubPlugin plugin, Class<?> clazz)
	{
		parse(plugin, clazz, null);
	}

	@SuppressWarnings("unchecked")
	private static void parse(SubPlugin plugin, Class<?> clazz, Object object)
	{
		FileConfiguration config = plugin.getConfig();

		for (Field field : clazz.getDeclaredFields())
		{
			if (! field.isAccessible())
				field.setAccessible(true);

			Object def = null;

			try
			{
				def = field.get(object);
			}
			catch (Throwable ex)
			{
				plugin.getLogHandler().log(Level.WARNING, Util.getUsefulStack(ex, "accessing field {0}", field));
			}

			Key key = field.getAnnotation(Key.class);
			if (key != null)
			{
				String path = key.value();

				try
				{
					Object value = config.get(path);
					if (value != null)
					{
						ValueOptions options = field.getAnnotation(ValueOptions.class);
						if (options != null)
						{
							for (ValueOptions.ValueOption option : options.value())
							{
								switch (option)
								{
									case FORMAT:
										value = FormatUtil.format(value.toString());
										break;
									case LIST_LOWER_CASE:
										List<String> list = new ArrayList<>();
										for (String line : (List<String>) value)
											list.add(line.toLowerCase());
										value = list;
										break;
									case LOWER_CASE:
										value = value.toString().toLowerCase();
										break;
									case MINUTE_TO_MILLIS:
										value = TimeUnit.MINUTES.toMillis(NumberUtil.toLong(value));
										break;
									// Item parsing handles null values on its own
									case PARSE_ITEM:
										value = ItemUtil.readItem(value.toString(), plugin);
										break;
									case PARSE_ITEMS:
										value = ItemUtil.readItems((List<String>) value, plugin);
										break;
									// Check for nulls with materials
									case PARSE_MATERIAL:
										value = MaterialUtil.getMaterial(value.toString());
										if (value == null && ! options.allowNull())
										{
											plugin.getLogHandler().log(Level.WARNING, "Failed to read material \"{0}\" from {1}. Defaulting to {2}", value, path, def);
											value = def;
										}

										break;
									case PARSE_MATERIALS:
										List<Material> materials = new ArrayList<>();
										for (String string : (List<String>) value)
										{
											Material material = MaterialUtil.getMaterial(string);
											if (material == null && ! options.allowNull())
											{
												plugin.getLogHandler().log(Level.WARNING, "Failed to read material \"{0}\" from {1}", string, path);
											}
											else
											{
												materials.add(material);
											}
										}

										value = materials;
										break;
									case SECOND_TO_MILLIS:
										value = TimeUnit.SECONDS.toMillis(NumberUtil.toLong(value));
										break;
									default:
										throw new IllegalArgumentException("Unsupported option: " + option);
								}
							}

							for (Class<?> custom : options.custom())
							{
								Method convert = custom.getMethod("convert", Object.class);
								if (convert.isAccessible())
								{
									value = convert.invoke(null, value);
								}
							}
						}

						field.set(object, value);
					}
				}
				catch (ClassCastException ex)
				{

				}
				catch (Throwable ex)
				{
					plugin.getLogHandler().log(Level.SEVERE, Util.getUsefulStack(ex, "loading value from {0}", path));
				}
			}
		}
	}
}
