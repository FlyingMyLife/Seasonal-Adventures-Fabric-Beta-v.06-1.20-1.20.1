package net.packages.seasonal_adventures.config.object;

import java.util.ArrayList;
import java.util.List;

public class ConfigObject {
    public boolean development;
    public boolean betaFeatures;
    public boolean atm_breakable;
    public List<CharacterConfig> characterConfigs = new ArrayList<>();

    public ConfigObject(boolean development, boolean betaFeatures, boolean atm_breakable) {
        this.development = development;
        this.betaFeatures = betaFeatures;
        this.atm_breakable = atm_breakable;
    }

    public static class CharacterConfig {
        public String name;
        public PresentConfig presentConfig = new PresentConfig();

        public CharacterConfig(String name) {
            this.name = name;
        }
    }

    public static class PresentConfig {
        public boolean countUnspecifiedItemsAsNeutral;

        // If var countUnspecifiedItemsAsNeutral is true, all unspecified items will be counted as neutral items,
        // otherwise they will be counted as negative items.

        public List<String> positive = new ArrayList<>();
        public List<String> neutral = new ArrayList<>();
        public List<String> negative = new ArrayList<>();

    }
}
