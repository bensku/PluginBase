package org.extendedalpha.pluginbase;

public class EAValidate {
	public static void isTrue(boolean cond, String message) {
		if (!cond) {
			throw new EAUtilsException(message);
		}
	}

	public static void isFalse(boolean cond, String message) {
		if (cond) {
			throw new EAUtilsException(message);
		}
	}

	public static void notNull(Object o, String message) {
		if (o == null) {
			throw new EAUtilsException(message);
		}
	}
}
