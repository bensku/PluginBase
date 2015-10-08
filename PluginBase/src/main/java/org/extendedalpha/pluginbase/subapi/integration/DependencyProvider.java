package org.extendedalpha.pluginbase.subapi.integration;

import java.util.logging.Level;

import org.extendedalpha.pluginbase.subapi.SubPlugin;
import org.extendedalpha.pluginbase.subapi.util.Util;

import org.apache.commons.lang.Validate;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;

import com.google.common.base.Preconditions;

/**
 * A dependency provider for optional {@link Plugin} dependencies.
 * <p>
 * In order to avoid a hard dependency, the following precautions should be taken: <br>
 * <ul>
 *   <li>Initialization of Objects of this Class should be wrapped in a try-catch block.</li>
 *   <li>Methods in this class should return Objects that will definitely exist at runtime.</li>
 *     <ul>
 *       <li>Like Bukkit objects, Strings, and Java primitives.</li>
 *     </ul>
 *   <li>Before calling methods from this class, {@link #isEnabled()} should be called.</li>
 * </ul>
 *
 * @author ExileDev
 */

public class DependencyProvider<T extends Plugin>
{
	protected String name;
	protected T dependency;
	protected boolean enabled;

	protected final SubPlugin handler;

	@SuppressWarnings("unchecked")
	public DependencyProvider(final SubPlugin handler, final String name)
	{
		Validate.notNull(handler, "handler cannot be null!");
		Validate.notNull(name, "name cannot be null!");

		this.handler = handler;
		this.name = name;

		if (dependency == null && ! enabled)
		{
			try
			{
				dependency = (T) handler.getServer().getPluginManager().getPlugin(name);
				if (dependency != null)
				{
					enabled = true;
					onEnable();
					handler.getLogHandler().log("{0} integration successful.", name);
				}
			}
			catch (Throwable ex)
			{
				handler.getLogHandler().log(Level.WARNING, Util.getUsefulStack(ex, "hooking into " + name));
			}
		}

		handler.getServer().getPluginManager().registerEvents(new Listener()
		{
			@EventHandler
			public void onPluginEnable(PluginEnableEvent event)
			{
				if (dependency == null && event.getPlugin().getName().equals(name))
				{
					try
					{
						dependency = (T) event.getPlugin();
						enabled = true;
						onEnable();
						handler.getLogHandler().log("{0} integration enabled.", name);
					}
					catch (Throwable ex)
					{
						handler.getLogHandler().log(Level.WARNING, Util.getUsefulStack(ex, "hooking into " + name));
					}
				}
			}

			@EventHandler
			public void onPluginDisable(PluginDisableEvent event)
			{
				if (dependency != null && event.getPlugin().getName().equals(name))
				{
					onDisable();
					enabled = false;
					dependency = null;
					handler.getLogHandler().log("{0} integration disabled.", name);
				}
			}

		}, handler);
	}

	/**
	 * Called when the dependency is found or enabled.
	 */
	public void onEnable() { }

	/**
	 * Called when the dependency is disabled.
	 */
	public void onDisable() { }

	/**
	 * Gets the dependency.
	 * 
	 * @return The dependency
	 * @throws NullPointerException if the dependency does not exist.
	 */
	public T getDependency()
	{
		return Preconditions.checkNotNull(dependency, name + " dependency does not exist.");
	}

	/**
	 * Gets this dependency's name.
	 * 
	 * @return The dependency's name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Whether or not this dependency is enabled.
	 * 
	 * @return True if enabled, false if not
	 */
	public boolean isEnabled()
	{
		return enabled && dependency != null;
	}
}
