package org.extendedalpha.pluginbase.subapi.types;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.extendedalpha.pluginbase.subapi.util.FormatUtil;

import org.apache.commons.lang.Validate;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Represents various potion types with friendlier names.
 *
 * @author ExileDev
 */

@Getter
@AllArgsConstructor
public enum PotionType
{
	FIRE_RESISTANCE("fireres"),
	INSTANT_DAMAGE("damage"),
	INSTANT_HEAL("heal"),
	INVISIBILITY("invis"),
	NIGHT_VISION("nvg"),
	POISON("poison"),
	REGEN("regen"),
	SLOWNESS("slow"),
	SPEED("speed"),
	STRENGTH("strength"),
	WATER("water"),
	WATER_BREATHING("waterbreath"),
	WEAKNESS("weak");

	private final String name;

	/**
	 * Returns a friendlier name of a given {@link PotionEffectType}.
	 *
	 * @param effect Potion effect
	 * @return Friendlier name.
	 */
	public static String toName(PotionEffectType effect)
	{
		Validate.notNull(effect, "effect cannot be null!");
		for (PotionType e : PotionType.values())
		{
			if (e.toString().equals(effect.getName()))
				return e.name;
		}

		return FormatUtil.format(effect.getName());
	}


	public static org.bukkit.potion.PotionType toType(String string)
	{
		Validate.notNull(string, "string cannot be null!");
		for (PotionType type : PotionType.values())
		{
			if (type.name.equalsIgnoreCase(string))
				return org.bukkit.potion.PotionType.valueOf(type.toString());
		}

		return null;
	}

	/**
	 * Returns a <code>String</code> representation of a {@link Collection} of
	 * PotionEffects
	 *
	 * @param effects Collection of potion effects.
	 */
	public static String toString(Collection<PotionEffect> effects)
	{
		Validate.notNull(effects, "effects cannot be null!");

		StringJoiner joiner = new StringJoiner(", ");
		for (PotionEffect effect : effects)
		{
			joiner.append(PotionType.toName(effect.getType()));
		}

		return joiner.toString();
	}
}