/**
 * 
 */
package org.extendedalpha.skyblock.util;

import org.bukkit.Bukkit;

import org.extendedalpha.skyblock.Settings;

/**
 * General purpose logging with access to a debug level
 * @author ExileDev
 *
 */
public class Logger {
    /**
     * General purpose logger to reduce console spam
     * @param level
     * @param info
     */
    public static void logger(int level, String info) {
	if (level <= Settings.debug) {
	    Bukkit.getLogger().info("DEBUG ["+level+"]:"+info);
	}
    }

}
