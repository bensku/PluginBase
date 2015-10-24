package org.extendedalpha.pluginbase.subapi.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

import org.extendedalpha.pluginbase.subapi.SubPlugin;
import org.extendedalpha.pluginbase.subapi.types.CustomSkullType;
import org.extendedalpha.pluginbase.subapi.types.EnchantmentType;
import org.extendedalpha.pluginbase.subapi.types.StringJoiner;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

/**
 * Util that deals with Items.
 *
 * @author ExileDev
 */

public class ItemUtil
{
	private ItemUtil() { }

	/**
	 * Parses an ItemStack from configuration. This provides limited meta
	 * support. This should be surrounded in a try-catch block to deal with
	 * unparsable items.
	 * <p>
	 * The basic format is "<code>[Type]:[Data], [Amount],
	 *  [Enchantment:Level...], [Meta]</code>"
	 *
	 * @param string String to parse
	 * @return ItemStack from given string, unless parsing fails
	 * @throws NullPointerException if the material is null
	 * @throws IllegalArgumentException if the amount is less than 1
	 * @throws IndexOutOfBoundsException if the string is in an improper format
	 */
	public static ItemStack readItem(String string)
	{
		if (string.startsWith("potion:"))
			return readPotion(string);

		Material material = null;
		int amount = -1;
		short data = 0;

		// Meta
		final String meta = string;
		Map<Enchantment, Integer> enchantments = new LinkedHashMap<>();

		string = string.replaceAll("\\s", "");
		if (string.contains(","))
		{
			String str = string.substring(0, string.indexOf(","));
			if (str.contains(":"))
			{
				String[] split = str.split(":");
				material = MaterialUtil.getMaterial(split[0]);
				if (material == null)
					throw new NullPointerException("Null material \"" + split[0] + "\"");

				data = NumberUtil.toShort(split[1]);
			}
			else
			{
				material = MaterialUtil.getMaterial(str);
				if (material == null)
					throw new NullPointerException("Null material \"" + str + "\"");
			}

			str = string.substring(string.indexOf(",") + 1);
			if (str.contains(","))
			{
				amount = NumberUtil.toInt(str.substring(0, str.indexOf(",")));
				if (amount <= 0)
					throw new IllegalArgumentException("Illegal amount: " + str.substring(0, str.indexOf(",")));

				str = str.substring(str.indexOf(",") + 1);
				if (! str.isEmpty())
				{
					String[] split = str.split(",");
					for (String ench : split)
					{
						if (ench.contains(":"))
						{
							Enchantment enchant = EnchantmentType.toEnchantment(ench.substring(0, ench.indexOf(":")));
							int level = NumberUtil.toInt(ench.substring(ench.indexOf(":") + 1));

							if (enchant != null && level > 0)
							{
								enchantments.put(enchant, level);
							}
						}
					}
				}
			}
			else
			{
				amount = NumberUtil.toInt(str);
				if (amount <= 0)
					throw new IllegalArgumentException("Illegal amount: " + str);
			}
		}
		else
		{
			// They must've just specified a material or id
			material = MaterialUtil.getMaterial(string);
			if (material == null)
				throw new NullPointerException("Null material \"" + string + "\"");
			amount = 1;
		}

		ItemStack ret = new ItemStack(material, amount, data);
		ret.addUnsafeEnchantments(enchantments);

		// Parse meta
		parseItemMeta(ret, meta);
		return ret;
	}

	/**
	 * Safely reads an item, logging any exceptions.
	 * 
	 * @param string String to parse
	 * @param plugin Plugin instance
	 * @return ItemStack, or null if parsing failed
	 * @see #readItem(String)
	 */
	public static ItemStack readItem(String string, SubPlugin plugin)
	{
		try
		{
			return ItemUtil.readItem(string);
		}
		catch (Throwable ex)
		{
			plugin.getLogHandler().log(Level.WARNING, Util.getUsefulStack(ex, "parsing item \"" + string + "\""));
			return null;
		}
	}

	/**
	 * Safely reads a list of items, logging any exceptions.
	 * 
	 * @param strings Strings to parse
	 * @param plugin Plugin instance
	 * @return List of ItemStacks
	 * @see #readItem(String)
	 */
	public static final List<ItemStack> readItems(List<String> strings, SubPlugin plugin)
	{
		List<ItemStack> ret = new ArrayList<>();
		for (String string : strings)
		{
			ItemStack item = readItem(string, plugin);
			if (item != null)
				ret.add(item);
		}

		return ret;
	}

	/**
	 * Parses a potion from configuration.
	 *
	 * @param string String to read
	 * @return ItemStack from string, or null if parsing fails
	 */
	public static ItemStack readPotion(String string)
	{
		// Meta
		final String meta = string;
		ItemStack ret = null;

		// Normalize string
		string = string.replaceAll("\\s", "");
		string = string.substring(string.indexOf(":") + 1);

		String[] split = string.split(",");
		if (split.length == 3)
		{
			// Get the type
			PotionType type = org.extendedalpha.pluginbase.subapi.types.PotionType.toType(split[0]);
			if (type != null)
			{
				// Get the amount
				int amount = NumberUtil.toInt(split[1]);
				if (amount != -1)
				{
					// Get the level
					int level = NumberUtil.toInt(split[2]);
					if (level != -1)
					{
						// Build potion / stack
						Potion potion = new Potion(1);
						potion.setType(type);
						potion.setLevel(level);
						potion.setSplash(false);
						ret = potion.toItemStack(amount);
					}
				}
			}
		}
		else if (split.length == 4)
		{
			// Get the type
			PotionType type = org.extendedalpha.pluginbase.subapi.types.PotionType.toType(split[0]);
			if (type != null)
			{
				// Get the amount
				int amount = NumberUtil.toInt(split[1]);
				if (amount != -1)
				{
					// Get the level
					int level = NumberUtil.toInt(split[2]);
					if (level != -1)
					{
						// Is splash
						boolean splash = Util.toBoolean(split[3]);

						// Build potion / stack
						Potion potion = new Potion(1);
						potion.setType(type);
						potion.setLevel(level);
						potion.setSplash(splash);
						ret = potion.toItemStack(amount);
					}
				}
			}
		}

		if (ret == null)
			return null;

		// Parse meta
		parseItemMeta(ret, meta);
		return ret;
	}

	/**
	 * Parses ItemMeta from a given string, then applies it to a given item.
	 * @param item Item to apply meta to
	 * @param string String to parse meta from
	 */
	public static void parseItemMeta(ItemStack item, String string)
	{
		try
		{
			ItemMeta meta = item.getItemMeta();

			// Name
			String nameKey = "name:";
			if (string.contains(nameKey))
			{
				String name = string.substring(string.indexOf(nameKey) + nameKey.length());
				int commaIndex = name.indexOf(",");
				if (commaIndex != -1)
					name = name.substring(0, commaIndex);

				meta.setDisplayName(FormatUtil.format(name.replace('_', ' ')));
			}

			// Lore
			String loreKey = "lore:";
			if (string.contains(loreKey))
			{
				String str = string.substring(string.indexOf(loreKey) + loreKey.length());
				int commaIndex = str.indexOf(",");
				if (commaIndex != -1)
					str.substring(0, commaIndex);
				str = str.replace('_', ' ');

				List<String> lore = new ArrayList<>();
				for (String split : str.split("\\|"))
					lore.add(FormatUtil.format(split));
				meta.setLore(lore);
			}

			// Leather armor
			if (meta instanceof LeatherArmorMeta)
			{
				String colorKey = "color:";
				if (string.contains(colorKey))
				{
					String str = string.substring(string.indexOf(colorKey) + colorKey.length());
					int commaIndex = str.indexOf(",");
					if (commaIndex != -1)
						str.substring(0, commaIndex);

					DyeColor dyeColor = DyeColor.valueOf(str.toUpperCase());
					((LeatherArmorMeta) meta).setColor(dyeColor.getColor());
				}
			}

			// Skulls
			if (meta instanceof SkullMeta)
			{
				String ownerKey = "owner:";
				String typeKey = "type:";

				if (string.contains(ownerKey))
				{
					String owner = string.substring(string.indexOf(ownerKey) + ownerKey.length());

					// Attempt to use CustomSkullType
					CustomSkullType type = CustomSkullType.get(owner);
					if (type != null)
						((SkullMeta) meta).setOwner(type.getOwner());
					else
						((SkullMeta) meta).setOwner(owner);
				}
				else if (string.contains(typeKey))
				{
					String type = string.substring(string.indexOf(typeKey) + typeKey.length());

					// Attempt to use CustomSkullType
					CustomSkullType customType = CustomSkullType.get(type);
					if (type != null)
						((SkullMeta) meta).setOwner(customType.getOwner());
					else
						((SkullMeta) meta).setOwner(type);
				}
			}

			item.setItemMeta(meta);
		} catch (Throwable ex) { }
	}

	/**
	 * Serializes a given ItemStack in the same format as
	 * {@link ItemUtil#readItem(String)}.
	 *
	 * @param stack Stack to serialize
	 * @return Serialized string
	 */
	public static String serialize(ItemStack stack)
	{
		StringBuilder ret = new StringBuilder();
		ret.append(stack.getType());
		if (stack.getDurability() > 0)
			ret.append(":" + stack.getDurability());
		ret.append(", " + stack.getAmount());

		if (! stack.getEnchantments().isEmpty())
		{
			StringJoiner joiner = new StringJoiner(", ");
			for (Entry<Enchantment, Integer> ench : stack.getEnchantments().entrySet())
				joiner.append(EnchantmentType.toName(ench.getKey()) + ":" + ench.getValue());
			ret.append(", " + joiner.toString());
		}

		ItemMeta meta = stack.getItemMeta();
		if (meta.hasDisplayName())
		{
			ret.append(", name:" + meta.getDisplayName()
					.replace(ChatColor.COLOR_CHAR, '&')
					.replace(' ', '_'));
		}

		if (meta.hasLore())
		{
			StringJoiner lore = new StringJoiner("|");
			for (String line : meta.getLore())
			{
				lore.append(line
						.replace(ChatColor.COLOR_CHAR, '&')
						.replace(' ', '_'));
			}

			ret.append(", lore:" + lore.toString());
		}

		return ret.toString();
	}

	/**
	 * Returns an ItemStack's enchantments in string form.
	 *
	 * @param stack ItemStack to get enchantments
	 * @return The item's enchantments in string form
	 */
	public static String getEnchantments(ItemStack stack)
	{
		StringBuilder ret = new StringBuilder();
		if (! stack.getEnchantments().isEmpty())
		{
			ret.append("(");
			for (Entry<Enchantment, Integer> enchantment : stack.getEnchantments().entrySet())
				ret.append(EnchantmentType.toName(enchantment.getKey()) + ": " + enchantment.getValue() + ", ");
			ret.delete(ret.lastIndexOf(","), ret.lastIndexOf(" "));
			ret.append(")");
		}

		return ret.toString();
	}
}
