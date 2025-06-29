package net.flyingmylife.seasonal_adventures.item.custom;

import com.mojang.serialization.Codec;
import net.flyingmylife.seasonal_adventures.SA;
import net.flyingmylife.seasonal_adventures.item.SAItems;
import net.minecraft.component.ComponentType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.UUID;

public class KeyItem extends Item {
    public KeyItem(Settings settings) {
        super(settings);
    }

    public static final ComponentType<String> KEY_ID_COMPONENT_TYPE = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(SA.MOD_ID, "key_id"),
            ComponentType.<String>builder().codec(Codec.STRING).build()
    );

    public static ItemStack createKey(UUID uuid) {
        ItemStack key = new ItemStack(SAItems.KEY);
        key.set(KEY_ID_COMPONENT_TYPE, uuid.toString());
        return key;
    }
}
