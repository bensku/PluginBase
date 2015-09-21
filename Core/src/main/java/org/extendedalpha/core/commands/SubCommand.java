/**
 * (c) 2015 dmulloy2
 */
package org.extendedalpha.core.commands;

/**
 * @author exiledev
 */

public abstract class SubCommand extends Command
{
	public SubCommand(Command parent)
	{
		super(parent.plugin);
		this.parent = parent;
	}

	protected final boolean argMatchesIdentifier(String arg)
	{
		if (arg.equalsIgnoreCase(name))
			return true;

		for (String alias : aliases)
		{
			if (arg.equalsIgnoreCase(alias))
				return true;
		}

		return false;
	}
}