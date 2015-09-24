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

package org.extendedalpha.eapermissions;

import lombok.Getter;
import org.extendedalpha.core.types.Reloadable;
import org.extendedalpha.core.util.FormatUtil;
import org.extendedalpha.eapermissions.commands.CmdCleanUp;
import org.extendedalpha.eapermissions.commands.CmdCreateGroup;
import org.extendedalpha.eapermissions.commands.CmdHelp;
import org.extendedalpha.eapermissions.commands.CmdNick;
import org.extendedalpha.eapermissions.commands.CmdPrefix;
import org.extendedalpha.eapermissions.commands.CmdPrefixReset;
import org.extendedalpha.eapermissions.commands.CmdRealName;
import org.extendedalpha.eapermissions.commands.CmdReload;
import org.extendedalpha.eapermissions.commands.CmdSave;
import org.extendedalpha.eapermissions.commands.CmdSuffix;
import org.extendedalpha.eapermissions.commands.CmdSuffixReset;
import org.extendedalpha.eapermissions.commands.CmdVersion;
import org.extendedalpha.eapermissions.commands.group.CmdGroup;
import org.extendedalpha.eapermissions.commands.group.CmdListGroups;
import org.extendedalpha.eapermissions.commands.user.CmdUser;
import org.extendedalpha.eapermissions.commands.wizard.CmdWizard;
import org.extendedalpha.eapermissions.conversion.ConversionHandler;
import org.extendedalpha.eapermissions.handlers.AntiItemHandler;
import org.extendedalpha.eapermissions.handlers.ChatHandler;
import org.extendedalpha.eapermissions.handlers.CommandHandler;
import org.extendedalpha.eapermissions.handlers.DataHandler;
import org.extendedalpha.eapermissions.handlers.LogHandler;
import org.extendedalpha.eapermissions.handlers.MirrorHandler;
import org.extendedalpha.eapermissions.handlers.PermissionHandler;
import org.extendedalpha.eapermissions.handlers.WizardHandler;
import org.extendedalpha.eapermissions.listeners.ChatListener;
import org.extendedalpha.eapermissions.listeners.PlayerListener;
import org.extendedalpha.eapermissions.listeners.ServerListener;
import org.extendedalpha.eapermissions.listeners.WorldListener;
import org.extendedalpha.eapermissions.vault.VaultHandler;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author exiledev
 */

@Getter
public class EAPermissions extends JavaPlugin implements Reloadable
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

	private String prefix = FormatUtil.format("&3[&eEAPerms&3]&e ");

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
		commandHandler.setCommandPrefix("eaperms");
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