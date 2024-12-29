package net.packages.seasonal_adventures.config;

import java.util.ArrayList;
import java.util.List;

public class Config {
    public boolean development;
    public boolean betaFeatures;
    public List<CharacterConfig> characterConfigs = new ArrayList<>();

    public Config(boolean development, boolean betaFeatures) {
        this.development = development;
        this.betaFeatures = betaFeatures;
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
