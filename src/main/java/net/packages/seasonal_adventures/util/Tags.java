package net.packages.seasonal_adventures.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.packages.seasonal_adventures.SeasonalAdventures;

public class Tags {
    public static class Blocks {


        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, new Identifier(SeasonalAdventures.MOD_ID, name));
        }
    }
    public static class Items {
        public static final TagKey<Item> ABP_SUIT_ITEMS = createTag("abp_suit_items");

        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, new Identifier(SeasonalAdventures.MOD_ID, name));
        }
    }
}
