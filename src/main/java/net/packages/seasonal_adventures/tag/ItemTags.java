package net.packages.seasonal_adventures.tag;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.packages.seasonal_adventures.SeasonalAdventures;

public class ItemTags {
    public static final TagKey<Item> ABP_SUIT_ITEMS = createTag("abp_suit_items");
    public static final TagKey<Item> V_ITEMS = createTag("v_items");

    private static TagKey<Item> createTag(String name) {
        return TagKey.of(RegistryKeys.ITEM, new Identifier(SeasonalAdventures.MOD_ID, name));
    }
}
