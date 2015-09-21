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

import lombok.Getter;
import org.extendedalpha.core.util.FormatUtil;
import org.extendedalpha.eapermissions.EAPermissions;
import org.extendedalpha.eapermissions.commands.EAPermissionsCommand;

/**
 * @author exiledev
 */

public abstract class WizardCommand extends EAPermissionsCommand
{
	protected @Getter String action = "";
	public WizardCommand(EAPermissions plugin)
	{
		super(plugin);
	}

	@Override
	public String getUsageTemplate(boolean displayHelp)
	{
		StringBuilder ret = new StringBuilder();
		ret.append(String.format("&b/%s &bwizard ", plugin.getCommandHandler().getCommandPrefix()));

		if (! action.isEmpty())
			ret.append(String.format("%s ", action));

		ret.append(name);

		for (String s : requiredArgs.subList(1, requiredArgs.size()))
			ret.append(String.format(" &3<%s>", s));

		for (String s : optionalArgs)
			ret.append(String.format(" &3[%s]", s));

		if (displayHelp)
			ret.append("&e" + description);

		return FormatUtil.format(ret.toString());
	}
}