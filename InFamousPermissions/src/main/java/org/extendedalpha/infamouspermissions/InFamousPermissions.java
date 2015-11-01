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

package org.extendedalpha.infamouspermissions;

import lombok.Getter;
import org.extendedalpha.infamouspermissions.commands.CmdCleanUp;
import org.extendedalpha.infamouspermissions.commands.CmdCreateGroup;
import org.extendedalpha.infamouspermissions.commands.CmdHelp;
import org.extendedalpha.infamouspermissions.commands.CmdNick;
import org.extendedalpha.infamouspermissions.commands.CmdPrefix;
import org.extendedalpha.infamouspermissions.commands.CmdPrefixReset;
import org.extendedalpha.infamouspermissions.commands.CmdRealName;
import org.extendedalpha.infamouspermissions.commands.CmdReload;
import org.extendedalpha.infamouspermissions.commands.CmdSave;
import org.extendedalpha.infamouspermissions.commands.CmdSuffix;
import org.extendedalpha.infamouspermissions.commands.CmdSuffixReset;
import org.extendedalpha.infamouspermissions.commands.CmdVersion;
import org.extendedalpha.infamouspermissions.commands.group.CmdGroup;
import org.extendedalpha.infamouspermissions.commands.group.CmdListGroups;
import org.extendedalpha.infamouspermissions.commands.user.CmdUser;
import org.extendedalpha.infamouspermissions.commands.wizard.CmdWizard;
import org.extendedalpha.infamouspermissions.conversion.ConversionHandler;
import org.extendedalpha.infamouspermissions.handlers.AntiItemHandler;
import org.extendedalpha.infamouspermissions.handlers.ChatHandler;
import org.extendedalpha.infamouspermissions.handlers.CommandHandler;
import org.extendedalpha.infamouspermissions.handlers.DataHandler;
import org.extendedalpha.infamouspermissions.handlers.LogHandler;
import org.extendedalpha.infamouspermissions.handlers.MirrorHandler;
import org.extendedalpha.infamouspermissions.handlers.PermissionHandler;
import org.extendedalpha.infamouspermissions.handlers.WizardHandler;
import org.extendedalpha.infamouspermissions.listeners.ChatListener;
import org.extendedalpha.infamouspermissions.listeners.PlayerListener;
import org.extendedalpha.infamouspermissions.listeners.ServerListener;
import org.extendedalpha.infamouspermissions.listeners.WorldListener;
import org.extendedalpha.infamouspermissions.vault.VaultHandler;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.extendedalpha.pluginbase.subapi.types.Reloadable;
import org.extendedalpha.pluginbase.subapi.util.FormatUtil;

/**
 * @author ExileDev
 */

@Getter
public class InFamousPermissions extends JavaPlugin implements Reloadable
{
	private PermissionHandler permissionHandler;
	private AntiItemHandler antiItemHandler;
	private CommandHandler commandHandler;
	private MirrorHandler mirrorHandler;
	private WizardHandler wizardHandler;
	private ChatHandler chatHandler;
	private DataHandler dataHandler;
	private LogHandler logHandler;

	private boolean disabling;
	private boolean updated;

	private String prefix = FormatUtil.format("&7[&6InFamousPerms&7]&6 ");

	@Override
	public void onLoad()
	{
		// Vault Integration
		PluginManager pm = getServer().getPluginManager();
		if (pm.getPlugin("Vault") != null)
		{
			try
			{
				VaultHandler.setupIntegration(this);
			} catch (Throwable ex) { }
		}
	}

	@Override
	public void onEnable()
	{
		long start = System.currentTimeMillis();

		disabling = false;

		// Register log handler
		logHandler = new LogHandler(this);

		// If this is the first time we've run,
		// attempt to convert from other systems
		if (! getDataFolder().exists())
			ConversionHandler.convert(this);

		// Configuration
		saveDefaultConfig();
		reloadConfig();

		// Register other handlers
		antiItemHandler = new AntiItemHandler(this);
		commandHandler = new CommandHandler(this);
		mirrorHandler = new MirrorHandler(this);
		wizardHandler = new WizardHandler(this);
		dataHandler = new DataHandler(this);
		chatHandler = new ChatHandler(this);

		permissionHandler = new PermissionHandler(this);
		permissionHandler.load();

		// Register prefixed commands
		commandHandler.setCommandPrefix("infamousperms");
		commandHandler.registerPrefixedCommand(new CmdCleanUp(this));
		commandHandler.registerPrefixedCommand(new CmdCreateGroup(this));
		commandHandler.registerPrefixedCommand(new CmdGroup(this));
		commandHandler.registerPrefixedCommand(new CmdHelp(this));
		commandHandler.registerPrefixedCommand(new CmdListGroups(this));
		commandHandler.registerPrefixedCommand(new CmdReload(this));
		commandHandler.registerPrefixedCommand(new CmdSave(this));
		commandHandler.registerPrefixedCommand(new CmdUser(this));
		commandHandler.registerPrefixedCommand(new CmdVersion(this));
		commandHandler.registerPrefixedCommand(new CmdWizard(this));

		// Register non-prefixed commands
		commandHandler.registerCommand(new CmdNick(this));
		commandHandler.registerCommand(new CmdPrefix(this));
		commandHandler.registerCommand(new CmdPrefixReset(this));
		commandHandler.registerCommand(new CmdRealName(this));
		commandHandler.registerCommand(new CmdSuffix(this));
		commandHandler.registerCommand(new CmdSuffixReset(this));

		// Register listeners
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new ChatListener(this), this);
		pm.registerEvents(new PlayerListener(this), this);
		pm.registerEvents(new ServerListener(this), this);
		pm.registerEvents(new WorldListener(this), this);

		// Initial update
		new BukkitRunnable()
		{
			@Override
			public void run()
			{
				permissionHandler.update();
				logHandler.log("Groups and users updated!");
				updated = true;
			}
		}.runTaskLater(this, 20L);

		logHandler.log("{0} has been enabled. Took {1} ms.", getDescription().getFullName(), System.currentTimeMillis() - start);
	}

	@Override
	public void onDisable()
	{
		long start = System.currentTimeMillis();

		disabling = true;
		updated = false;

		getServer().getServicesManager().unregisterAll(this);
		getServer().getScheduler().cancelTasks(this);

		dataHandler.save();

		logHandler.log("{0} has been disabled. Took {1} ms.", getDescription().getFullName(), System.currentTimeMillis() - start);
	}

	@Override
	public void reload()
	{
		reloadConfig();

		chatHandler.reload();
		dataHandler.reload();
		mirrorHandler.reload();
		permissionHandler.reload();
	}
}