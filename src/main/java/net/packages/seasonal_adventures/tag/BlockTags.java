package net.packages.seasonal_adventures.tag;

import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.packages.seasonal_adventures.SeasonalAdventures;

public class BlockTags {
    public static class Blocks {
        private static TagKey<net.minecraft.block.Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, Identifier.of(SeasonalAdventures.MOD_ID, name));
        }
    }
}
