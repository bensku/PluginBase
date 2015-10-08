
package org.extendedalpha.pluginbase.subapi;

import org.extendedalpha.pluginbase.subapi.types.LazyLocation;
import org.extendedalpha.pluginbase.subapi.types.SimpleVector;

import org.bukkit.configuration.serialization.ConfigurationSerialization;

/**
 * SubAPI utility class
 * 
 * @author ExileDev
 */

public class SubAPI
{
	private static boolean registered = false;
	public static final void checkRegistrations()
	{
		if (! registered)
		{
			ConfigurationSerialization.registerClass(LazyLocation.class);
			ConfigurationSerialization.registerClass(SimpleVector.class);
			registered = true;
		}
	}
}
