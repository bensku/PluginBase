package org.extendedalpha.pluginbase.subapi.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Represents an action performed when hovering.
 *
 * @author ExileDev
 */

@Getter
@AllArgsConstructor
public final class HoverEvent
{
	private final Action action;
	private final BaseComponent[] value;

	public HoverEvent(Action action, String value)
	{
		this(action, TextComponent.fromLegacyText(value));
	}

	public enum Action
	{
		SHOW_TEXT,
		SHOW_ACHIEVEMENT,
		SHOW_ITEM
	}

	@Override
	public String toString()
	{
		return String.format("HoverEvent{action=%s, value=%s}", action, value);
	}
}
