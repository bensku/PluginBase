package org.extendedalpha.pluginbase.subapi.exception;

/**
 * An {@link Exception} that occurs when dealing with reflection.
 *
 * @author ExileDev
 */

public class ReflectionException extends RuntimeException
{
	private static final long serialVersionUID = -355857662220280587L;

	/**
	 * Constructs an empty ReflectionException.
	 */
	public ReflectionException()
	{
		super();
	}

	/**
	 * Constructs a ReflectionException with a given message.
	 *
	 * @param message Exception message
	 */
	public ReflectionException(String message)
	{
		super(message);
	}

	/**
	 * Constructs a ReflectionException with a given message and cause.
	 *
	 * @param message Exception message
	 * @param cause {@link Throwable} cause
	 */
	public ReflectionException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
