package net.flyingmylife.seasonal_adventures.datagen.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ItemTagProvider extends FabricTagProvider.ItemTagProvider {

    public ItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
//        getOrCreateTagBuilder(ItemTags.ABP_SUIT_ITEMS)
//                .add(SAItems.ABP_BOOTS)
//                .add(SAItems.ABP_LEGGINGS)
//                .add(SAItems.ABP_CHESTPLATE)
//                .add(SAItems.ABP_HELMET);
    }
}
