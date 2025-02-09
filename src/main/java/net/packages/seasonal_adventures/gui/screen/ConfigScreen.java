package net.packages.seasonal_adventures.gui.screen;

import eu.midnightdust.lib.config.MidnightConfig;
import net.packages.seasonal_adventures.config.ClientConfig;

import java.util.Arrays;
import java.util.List;

public class ConfigScreen extends MidnightConfig {
    public static final String config = "config";

    @Entry(category = config) public static ClientConfig.ClientConfigButtonPosition configButtonPos = ClientConfig.ClientConfigButtonPosition.DEFAULT;

    @Entry(category = config) public static boolean fancyLocationLoading = true;


}
