package net.packages.seasonal_adventures.config.object;

import java.util.Arrays;
import java.util.List;

public class ConfigObject {
    public boolean betaFeatures;
    public ConfigButtonPosition configButtonPos;
    public boolean atmBreakable;
    public boolean fancyLocationLoading;
    public boolean countUnspecifiedItemsAsNeutral;

    public List<String> positiveDylanPresents = Arrays.asList(
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

    public List<String> neutralDylanPresents = Arrays.asList(
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

    public List<String> negativeDylanPresents = List.of();

    public ConfigObject(boolean betaFeatures, boolean atmBreakable, boolean fancyLocationLoading, boolean countUnspecifiedItemsAsNeutral) {
        this.betaFeatures = betaFeatures;
        this.atmBreakable = atmBreakable;
        this.fancyLocationLoading = fancyLocationLoading;
        this.countUnspecifiedItemsAsNeutral = countUnspecifiedItemsAsNeutral;
        this.configButtonPos = configButtonPos.DEFAULT;
    }

    public enum ConfigButtonPosition {
        HIDDEN,
        DEFAULT,
        LEFT_MULTIPLAYER,
    }
}
