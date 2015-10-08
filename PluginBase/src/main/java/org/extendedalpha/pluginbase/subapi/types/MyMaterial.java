package org.extendedalpha.pluginbase.subapi.types;

import java.util.Objects;

import lombok.Data;
import org.extendedalpha.pluginbase.subapi.util.FormatUtil;
import org.extendedalpha.pluginbase.subapi.util.MaterialUtil;
import org.extendedalpha.pluginbase.subapi.util.NumberUtil;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

/**
 * A serializable Material and data combination.
 *
 * @author ExileDev
 */

@Data
public class MyMaterial
{
	private final boolean ignoreData;
	private final Material material;
	private final short data;

	public MyMaterial(Material material, short data, boolean ignoreData)
	{
		this.ignoreData = ignoreData;
		this.material = material;
		this.data = data;
	}

	public MyMaterial(Material material, short data)
	{
		this(material, data, false);
	}

	@SuppressWarnings("deprecation") // MaterialData#getData()
	public MyMaterial(Material material, MaterialData data)
	{
		this(material, data.getData(), false);
	}

	public MyMaterial(Material material)
	{
		this(material, (short) 0, true);
	}

	// --- ItemStacks

	/**
	 * Whether or not a given {@link ItemStack} matches this MyMaterial.
	 *
	 * @param item ItemStack to check
	 * @return True if they match, false if not
	 */
	public final boolean matches(ItemStack item)
	{
		return item.getType() == material && (ignoreData ? true : item.getDurability() == data);
	}

	/**
	 * Whether or not this MyMaterial matches the given Material and data.
	 * 
	 * @param material Material to check
	 * @param data Data to check
	 * @return True if they matche, false if not
	 */
	public boolean matches(Material material, short data)
	{
		return this.material == material && (ignoreData || this.data == data);
	}

	/**
	 * Creates a new {@link ItemStack} based around this MyMaterial.
	 *
	 * @param amount Amount, defaults to 1
	 * @return The new {@link ItemStack}
	 */
	public final ItemStack newItemStack(int amount)
	{
		if (amount <= 0)
			amount = 1;

		return new ItemStack(material, amount, ignoreData ? 0 : data);
	}

	// ---- Getters

	/**
	 * Gets the friendly name of the underlying material.
	 *
	 * @return Friendly name of the underlying Material
	 */
	public final String getName()
	{
		return FormatUtil.getFriendlyName(material);
	}

	/**
	 * Serializes this MyMaterial. This is essentially the opposite of
	 * {@link #fromString(String)}
	 * <p>
	 * Format: {@code <Material>[:Data]}
	 *
	 * @return This MyMaterial, serialized
	 */
	public final String serialize()
	{
		return material.name() + (! ignoreData ? ":" + data : "");
	}

	// ---- Generic Methods

	@Override
	public String toString()
	{
		if (ignoreData)
			return material.toString();

		return "MyMaterial[material=" + material + ", data=" + data + "]";
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof MyMaterial)
		{
			MyMaterial that = (MyMaterial) obj;
			return this.material == that.material && (ignoreData || this.data == that.data);
		}

		return false;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(material, (ignoreData ? 0 : data));
	}

	/**
	 * Retrieves the equivalent MyMaterial from a given ItemStack using its type
	 * and durability.
	 * 
	 * @param item ItemStack
	 * @return The equivalent MyMaterial
	 */
	public static MyMaterial fromItem(ItemStack item)
	{
		if (item.getDurability() == 0)
			return new MyMaterial(item.getType(), item.getDurability());
		else
			return new MyMaterial(item.getType());
	}

	/**
	 * Attempts to parse a MyMaterial from a given string.
	 * <p>
	 * Format: <code>Material:Data</code>
	 *
	 * @param string String to get the MyMaterial from
	 * @return Resulting MyMaterial, or null if parsing failed
	 */
	public static MyMaterial fromString(String string)
	{
		string = string.replaceAll(" ", "");

		try
		{
			if (string.contains(":"))
			{
				String[] split = string.split(":");
				Material material = MaterialUtil.getMaterial(split[0]);
				if (material != null)
				{
					short data = NumberUtil.toShort(split[1]);
					boolean ignoreData = data == -1;
					if (data <= 0)
						data = 0;

					return new MyMaterial(material, data, ignoreData);
				}
			}

			Material material = MaterialUtil.getMaterial(string);
			if (material != null)
				return new MyMaterial(material);
		} catch (Throwable ex) { }
		return null;
	}
}
