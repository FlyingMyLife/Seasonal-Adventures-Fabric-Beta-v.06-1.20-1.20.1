package dev.flyingmylife.seasonal_adventures.tag;

import dev.flyingmylife.seasonal_adventures.SA;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class BlockTags {
    public static class Blocks {
        private static TagKey<net.minecraft.block.Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, Identifier.of(SA.MOD_ID, name));
        }
    }
}
