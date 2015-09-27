package org.extendedalpha.core;

import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.extendedalpha.core.types.LazyLocation;
import org.extendedalpha.core.types.SimpleVector;

public class PluginLoaderAPI {

    private static boolean registered = false;
    public static final void checkRegistrations()
    {
        if (! registered)
        {
            ConfigurationSerialization.registerClass(LazyLocation.class);
            ConfigurationSerialization.registerClass(SimpleVector.class);
            registered = true;
        }
    }
}
