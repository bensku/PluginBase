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

package org.extendedalpha.eapermissions.commands.wizard;

import java.util.ArrayList;
import java.util.List;

import org.extendedalpha.eapermissions.EAPermissions;
import org.extendedalpha.eapermissions.commands.EAPermissionsCommand;

/**
 * @author exiledev
 */

public class CmdWizard extends EAPermissionsCommand
{
	private List<WizardCommand> subCommands;

	public CmdWizard(EAPermissions plugin)
	{
		super(plugin);
		this.name = "wizard";
		this.requiredArgs.add("action");
		this.requiredArgs.add("args");
		this.optionalArgs.add("world");
		this.description = "Modify permissions with a wizard";
		this.hasSubCommands = true;
		this.mustBePlayer = true;
		this.usesPrefix = true;

		this.registerSubCommands();
	}

	private final void registerSubCommands()
	{
		this.subCommands = new ArrayList<WizardCommand>();

		subCommands.add(new CmdCreateGroup(plugin));
	}

	@Override
	public void perform()
	{
		String action = "";
		String name = "";
		List<String> argsList = new ArrayList<>();

		if (args.length == 2)
		{
			action = "";
			name = args[1];
			for (int i = 2; i < args.length; i++)
				argsList.add(args[i]);
		}
		else
		{
			action = args[1];
			name = args[2];
			for (int i = 3; i < args.length; i++)
				argsList.add(args[i]);
		}

		for (WizardCommand command : subCommands)
		{
			if (command.getAction().equalsIgnoreCase(action))
			{
				if (name.equalsIgnoreCase(command.getName()) || command.getAliases().contains(name.toLowerCase()))
				{
					command.execute(sender, argsList.toArray(new String[0]));
					return;
				}
			}
		}

		err("Invalid arguments! Try &c/eaperms help&4!");
	}

	@Override
	public List<WizardCommand> getSubCommands()
	{
		return subCommands;
	}
}