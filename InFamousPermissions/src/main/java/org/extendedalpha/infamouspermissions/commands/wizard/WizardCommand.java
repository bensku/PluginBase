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

package org.extendedalpha.infamouspermissions.commands.wizard;

import lombok.Getter;
import org.extendedalpha.infamouspermissions.InFamousPermissions;
import org.extendedalpha.infamouspermissions.commands.InFamousPermissionsCommand;
import org.extendedalpha.pluginbase.subapi.util.FormatUtil;

/**
 * @author ExileDev
 */

public abstract class WizardCommand extends InFamousPermissionsCommand
{
	protected @Getter String action = "";
	public WizardCommand(InFamousPermissions plugin)
	{
		super(plugin);
	}

	@Override
	public String getUsageTemplate(boolean displayHelp)
	{
		StringBuilder ret = new StringBuilder();
		ret.append(String.format("&6/%s &6wizard ", plugin.getCommandHandler().getCommandPrefix()));

		if (! action.isEmpty())
			ret.append(String.format("%s ", action));

		ret.append(name);

		for (String s : requiredArgs.subList(1, requiredArgs.size()))
			ret.append(String.format(" &7<%s>", s));

		for (String s : optionalArgs)
			ret.append(String.format(" &7[%s]", s));

		if (displayHelp)
			ret.append("&6" + description);

		return FormatUtil.format(ret.toString());
	}
}