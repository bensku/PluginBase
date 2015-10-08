package org.extendedalpha.pluginbase.subapi.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Represents a clickable action.
 * 
 * @author ExileDev
 */

@Getter
@AllArgsConstructor
final public class ClickEvent
{
	/**
	 * The type of action to preform on click
	 */
	private final Action action;

	/**
	 * Depends on action
	 *
	 * @see Action
	 */
	private final String value;

	public enum Action
	{
		/**
		 * Open a url at the path given by {@code getValue()}
		 */
		OPEN_URL,

		/**
		 * Open a file at the path given by {@code getValue()}
		 */
		OPEN_FILE,

		/**
		 * Run the command given by {@code getValue()}
		 */
		RUN_COMMAND,

		/**
		 * Inserts the string given by {@code getValue()} into the player's text
		 * box
		 */
		SUGGEST_COMMAND
	}

	@Override
	public String toString()
	{
		return String.format("ClickEvent{action=%s, value=%s}", action, value);
	}
}
