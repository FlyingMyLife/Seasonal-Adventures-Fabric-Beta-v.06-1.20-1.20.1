package net.packages.seasonal_adventures.gui.screen;

import eu.midnightdust.lib.config.MidnightConfig;
import net.packages.seasonal_adventures.config.object.ConfigObject;

import java.util.Arrays;
import java.util.List;

public class ConfigScreen extends MidnightConfig {
    public static final String config = "config";

    @Entry(category = config) public static boolean betaFeatures = false;

    @Entry(category = config) public static ConfigObject.ConfigButtonPosition configButtonPos = ConfigObject.ConfigButtonPosition.DEFAULT;

    @Entry(category = config) public static boolean fancyLocationLoading = true;

    @Entry(category = config) public static boolean countUnspecifiedItemsAsNeutral = false;

    @Comment(category = config) public static Comment booleanDescription;

    @Comment(category = config, centered = true) public static Comment dylanPresentsDescription;

    @Entry(category = config) public static List<String> positiveDylanPresents = Arrays.asList(
            "seasonal_adventures:beef_tartare",
            "candlelight:beef_tartare",
            "minecraft:cooked_mutton",
            "minecraft:cooked_beef",
            "minecraft:cooked_porkchop",
            "minecraft:diamond",
            "minecraft:netherite_ingot",
            "minecraft:cookie",
            "minecraft:cooked_chicken",
            "minecraft:cooked_cod",
            "minecraft:cooked_salmon",
            "minecraft:cake"
    );

    @Entry(category = config) public static List<String> neutralDylanPresents = Arrays.asList(
            "seasonal_adventures:lockpick",
            "minecraft:book",
            "minecraft:compass",
            "minecraft:clock",
            "minecraft:spyglass",
            "minecraft:paper",
            "minecraft:lightning_rod",
            "minecraft:redstone",
            "minecraft:blaze_powder",
            "minecraft:tnt"
    );

    @Entry(category = config) public static List<String> negativeDylanPresents = List.of();
}
