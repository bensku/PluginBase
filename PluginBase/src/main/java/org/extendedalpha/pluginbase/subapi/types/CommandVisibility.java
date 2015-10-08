package org.extendedalpha.pluginbase.subapi.types;

/**
 * Represents various visibilities for commands
 *
 * @author ExileDev
 */

public enum CommandVisibility
{
	/**
	 * Visible to everyone
	 */
	ALL,

	/**
	 * Visible to players with a permission
	 */
	PERMISSION,

	/**
	 * Visible to operators
	 */
	OPS,

	/**
	 * Invisible
	 */
	NONE;
}
