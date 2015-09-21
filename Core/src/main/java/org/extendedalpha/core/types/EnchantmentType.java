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

package org.extendedalpha.core.types;

import java.util.Map;
import java.util.Map.Entry;

import lombok.Getter;

import org.bukkit.enchantments.Enchantment;

/**
 * Represents various enchantments with more friendly names.
 *
 * @author exiledev
 */

@Getter
public enum EnchantmentType
{
	ARROW_DAMAGE("power", "arrowdmg"),
	ARROW_FIRE("flame", "fire"),
	ARROW_INFINITE("infinity", "inf"),
	ARROW_KNOCKBACK("punch", "arrowkb"),
	DAMAGE_ALL("sharpness", "sharp"),
	DAMAGE_ARTHROPODS("bane"),
	DAMAGE_UNDEAD("smite"),
	DEPTH_STRIDER("depthstrider"),
	DIG_SPEED("efficiency", "eff"),
	DURABILITY("durability", "dura"),
	FIRE_ASPECT("fireaspect"),
	KNOCKBACK("knockback"),
	LOOT_BONUS_BLOCKS("fortune"),
	LOOT_BONUS_MOBS("looting"),
	LUCK("luck"),
	LOOT("loot"),
	OXYGEN("respiration", "breathing"),
	PROTECTION_ENVIRONMENTAL("prot", "protection"),
	PROTECTION_EXPLOSIONS("blast"),
	PROTECTION_FALL("feather"),
	PROTECTION_FIRE("fireprot"),
	PROTECTION_PROJECTILE("proj"),
	SILK_TOUCH("silk"),
	THORNS("thorns"),
	WATER_WORKER("aqua", "affinity");

	private String name;
	private String[] aliases;

	private EnchantmentType(String name, String... aliases)
	{
		this.name = name;
		this.aliases = aliases;
	}

	/**
	 * Returns a friendlier name of a given {@link Enchantment}.
	 *
	 * @param enchant {@link Enchantment}
	 * @return Friendlier name.
	 */
	public static String toName(Enchantment enchant)
	{
		EnchantmentType type = getByName(enchant.getName());
		if (type != null)
			return type.getName();

		return enchant.getName().toLowerCase();
	}

	/**
	 * Attempts to get an {@link Enchantment} from a given string.
	 *
	 * @param enchant Enchantment name
	 * @return The enchantment, or null if none exists.
	 */
	public static Enchantment toEnchantment(String enchant)
	{
		enchant = enchant.replaceAll(" ", "_");
		enchant = enchant.toUpperCase();

		EnchantmentType type = getByName(enchant);
		if (type != null)
			return Enchantment.getByName(type.name());

		return Enchantment.getByName(enchant);
	}

	/**
	 * Returns a <code>String</code> representation of a {@link Map} of
	 * enchantments.
	 *
	 * @param enchantments Enchantment map
	 */
	public static String toString(Map<Enchantment, Integer> enchantments)
	{
		StringJoiner joiner = new StringJoiner(", ");
		for (Entry<Enchantment, Integer> entry : enchantments.entrySet())
		{
			joiner.append(EnchantmentType.toName(entry.getKey()) + ":" + entry.getValue());
		}

		return joiner.toString();
	}

	// ---- Utility Methods

	private static EnchantmentType getByName(String name)
	{
		for (EnchantmentType type : values())
		{
			if (type.getName().equalsIgnoreCase(name)
					|| type.name().equalsIgnoreCase(name)
					|| containsIgnoreCase(name, type.getAliases()))
				return type;
		}

		return null;
	}

	private static boolean containsIgnoreCase(String lookup, String[] array)
	{
		for (String string : array)
		{
			if (string.equalsIgnoreCase(lookup))
				return true;
		}

		return false;
	}
}
