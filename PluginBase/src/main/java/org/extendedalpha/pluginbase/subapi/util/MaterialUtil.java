package org.extendedalpha.pluginbase.subapi.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.extendedalpha.pluginbase.subapi.integration.VaultHandler;

/**
 * Util dealing with the loss of item id's.
 *
 * @author ExileDev
 */

public class MaterialUtil
{
	private MaterialUtil() { }

	/**
	 * Gets the {@link Material} from a given string using Bukkit, Vault, or
	 * internal Minecraft.
	 *
	 * @param string String to get the Material from
	 * @return The material, or null if not found
	 * @see Material#matchMaterial(String)
	 */
	public static final Material getMaterial(String string)
	{
		Material material = Material.matchMaterial(string);
		if (material != null)
			return material;

		// Resolve using Vault, if applicable
		if (Bukkit.getPluginManager().isPluginEnabled("Vault"))
		{
			try
			{
				material = VaultHandler.resolve(string);
				if (material != null)
					return material;
			} catch (Throwable ex) { }
		}

		try
		{
			// Attempt to grab it unsafely. The call will never return null,
			// but if nothing is found, it will return air.

			@SuppressWarnings("deprecation")
			Material internal = Bukkit.getUnsafe().getMaterialFromInternalName(string);
			if (internal != Material.AIR)
				return internal;
		} catch (Throwable ex) { }
		return null;
	}

	/**
	 * Gets the friendly name of a Material.
	 *
	 * @param mat Material
	 * @return Friendly name
	 */
	public static final String getMaterialName(Material mat)
	{
		return FormatUtil.getFriendlyName(mat);
	}

	/**
	 * Gets the friendly name of a Material.
	 *
	 * @param name Material name
	 * @return Friendly name, or "null" if not found
	 */
	public static final String getMaterialName(String name)
	{
		Material mat = getMaterial(name);
		if (mat == null)
			return "null";

		return getMaterialName(mat);
	}

	/**
	 * Converts a list of strings into a list of Materials.
	 *
	 * @param strings List to convert
	 * @return Converted list
	 */
	public static final List<Material> fromStrings(List<String> strings)
	{
		List<Material> ret = new ArrayList<>();

		for (String string : strings)
		{
			Material material = getMaterial(string);
			if (material != null)
				ret.add(material);
		}

		return ret;
	}
}