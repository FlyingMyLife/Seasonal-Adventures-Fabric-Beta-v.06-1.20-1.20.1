package net.packages.seasonal_adventures.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.packages.seasonal_adventures.item.Items;
import net.packages.seasonal_adventures.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ItemTagProvider extends FabricTagProvider.ItemTagProvider {

    public ItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(ItemTags.ABP_SUIT_ITEMS)
                .add(Items.ABP_BOOTS)
                .add(Items.ABP_LEGGINGS)
                .add(Items.ABP_CHESTPLATE)
                .add(Items.ABP_HELMET);

        getOrCreateTagBuilder(ItemTags.V_ITEMS)
                .add(Items.V1)
                .add(Items.V5)
                .add(Items.V10)
                .add(Items.V50)
                .add(Items.V100)
                .add(Items.V500)
                .add(Items.V1000)
                .add(Items.V10000);
    }
}
