package org.extendedalpha.pluginbase.subapi.io;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;

/**
 * @author ExileDev
 */

public class Closer implements Closeable
{
	private final List<AutoCloseable> list = new ArrayList<>();

	/**
	 * Registers a closeable object.
	 *
	 * @param closeable Object to register
	 * @return The object
	 */
	public final <C extends AutoCloseable> C register(C closeable)
	{
		Validate.notNull(closeable, "closeable cannot be null!");

		list.add(closeable);
		return closeable;
	}

	@Override
	public final void close()
	{
		if (list.isEmpty())
			return;

		for (AutoCloseable closeable : list)
		{
			closeQuietly(closeable);
		}

		list.clear();
	}

	/**
	 * Quietly closes a closeable object, ignoring all exceptions.
	 * 
	 * @param closeable Object to close
	 */
	public static void closeQuietly(AutoCloseable closeable)
	{
		try
		{
			if (closeable != null)
				closeable.close();
		} catch (Throwable ex) { }
	}
}
