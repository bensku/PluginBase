package org.extendedalpha.core.nms;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.plugin.Plugin;
import org.extendedalpha.core.nms.api.NMSAbstraction;
import org.extendedalpha.core.nms.v1_8_R3.NMSHandler;

public class NMSHelper {
	private static NMSAbstraction nms = null;

	// This little hack ensures that these classes won't be excluded by Maven if the JAR
	// is minimised.
	@SuppressWarnings({"unused", "MismatchedReadAndWriteOfArray"})
	private static final Class<?>[] classes = new Class<?>[] {
            NMSHandler.class,
	};

	public static NMSAbstraction init(Plugin plugin) throws ClassNotFoundException, IllegalArgumentException,
			SecurityException, InstantiationException, IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		return init(plugin, false);
	}

	public static NMSAbstraction init(Plugin plugin, boolean fallbackOk) throws ClassNotFoundException, IllegalArgumentException,
			SecurityException, InstantiationException, IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {

		String serverPackageName = plugin.getServer().getClass().getPackage().getName();
		String pluginPackageName = plugin.getClass().getPackage().getName();

        // Get full package string of CraftServer.
		// org.bukkit.craftbukkit.vX_Y_Z (or for pre-refactor, just org.bukkit.craftbukkit)
		String version = serverPackageName.substring(serverPackageName.lastIndexOf('.') + 1);
		if (version.equals("craftbukkit")) {
			// No numeric (versioned) package component found - must be pre-refactoring
			version = "pre";
		}

		// NOTE: this assumes that extendedalpha-core is shaded into the plugin as <plugin-main-package>.extendedalpha-core
		Class<?> clazz;
		try {
			clazz = Class.forName(pluginPackageName + ".extendedalpha.core.nms." + version + ".NMSHandler");
		} catch (ClassNotFoundException e) {
				throw e;
		}

		// Check if we have a NMSAbstraction implementing class at that location.
		if (NMSAbstraction.class.isAssignableFrom(clazz)) {
			nms = (NMSAbstraction) clazz.getConstructor().newInstance();
		} else {
			throw new IllegalStateException("Class " + clazz.getName() + " does not implement NMSAbstraction");
		}

		return nms;
	}

    public static NMSAbstraction initFallback() {
        return null;
    }

	public static NMSAbstraction getNMS() {
		return nms;
	}
}
