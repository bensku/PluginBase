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

package org.extendedalpha.eapermissions.handlers;

import java.util.logging.Level;

import lombok.AllArgsConstructor;

import org.bukkit.plugin.java.JavaPlugin;
import org.extendedalpha.core.util.FormatUtil;

/**
 * Handles logging and formatting through the plugin's logger.
 *
 * @author exiledev
 */

@AllArgsConstructor
public class LogHandler
{
	private final JavaPlugin plugin;

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
	 * Logs a debug message to console with a given level level if <code>debug</code> is set to
	 * <code>true</code> in the config.yml.
	 *
	 * @param level Logging {@link Level}.
	 * @param msg Message to log.
	 * @param objects Objects to format in.
	 */
	public final void debug(Level level, String msg, Object... objects)
	{
		if (plugin.getConfig().getBoolean("debug", false))
		{
			log(level, "[Debug] " + msg, objects);
		}
	}

	/**
	 * Logs a debug message to console with the INFO level if <code>debug</code> is set to
	 * <code>true</code> in the config.yml.
	 *
	 * @param msg Message to log.
	 * @param objects Objects to format in.
	 */
	public final void debug(String msg, Object... objects)
	{
		debug(Level.INFO, msg, objects);
	}
}