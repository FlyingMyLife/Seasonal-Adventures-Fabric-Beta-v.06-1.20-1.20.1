package net.packages.seasonal_adventures.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.packages.seasonal_adventures.item.SAItems;
import net.packages.seasonal_adventures.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ItemTagProvider extends FabricTagProvider.ItemTagProvider {

    public ItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(ItemTags.ABP_SUIT_ITEMS)
                .add(SAItems.ABP_BOOTS)
                .add(SAItems.ABP_LEGGINGS)
                .add(SAItems.ABP_CHESTPLATE)
                .add(SAItems.ABP_HELMET);

        getOrCreateTagBuilder(ItemTags.V_ITEMS)
                .add(SAItems.V1)
                .add(SAItems.V5)
                .add(SAItems.V10)
                .add(SAItems.V50)
                .add(SAItems.V100)
                .add(SAItems.V500)
                .add(SAItems.V1000)
                .add(SAItems.V10000);
    }
}
