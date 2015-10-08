package org.extendedalpha.pluginbase.subapi.handlers;

import java.util.logging.Level;

import lombok.AllArgsConstructor;
import org.extendedalpha.pluginbase.subapi.SubPlugin;
import org.extendedalpha.pluginbase.subapi.util.FormatUtil;

/**
 * Handles logging and formatting through the plugin's logger.
 *
 * @author ExileDev
 */

@AllArgsConstructor
public class LogHandler
{
	private final SubPlugin plugin;

	/**
	 * Logs a formatted message to console with a given level.
	 *
	 * @param level Logging {@link Level}.
	 * @param msg Message to log.
	 * @param objects Objects to format in.
	 */
	public final void log(Level level, String msg, Object... objects)
	{
		plugin.getLogger().log(level, FormatUtil.format(msg, objects));
	}

	/**
	 * Logs a formatted message to console with INFO level.
	 *
	 * @param msg Message to log.
	 * @param objects Objects to format in.
	 */
	public final void log(String msg, Object... objects)
	{
		log(Level.INFO, msg, objects);
	}

	/**
	 * Logs a debug message to console with a given level if <code>debug</code>
	 * is set to <code>true</code> in the config.yml.
	 *
	 * @param level Logging {@link Level}.
	 * @param msg Message to log.
	 * @param objects Objects to format in.
	 */
	public final void debug(Level level, String msg, Object... objects)
	{
		if (Boolean.getBoolean("subapi.debug") || plugin.getConfig().getBoolean("debug", false))
			log(level, "[Debug] " + msg, objects);
	}

	/**
	 * Logs a debug message to console with the INFO level if <code>debug</code>
	 * is set to <code>true</code> in the config.yml.
	 *
	 * @param msg Message to log.
	 * @param objects Objects to format in.
	 */
	public final void debug(String msg, Object... objects)
	{
		debug(Level.INFO, msg, objects);
	}

	/**
	 * Logs a debug message to the console if the system property
	 * <code>subapi.debug</code> is true.
	 * 
	 * @param msg Message to log.
	 * @param objects Objects to format in.
	 */
	public static void globalDebug(String msg, Object... objects)
	{
		if (Boolean.getBoolean("subapi.debug"))
			System.out.println("[Debug] " + FormatUtil.format(msg, objects));
	}
}