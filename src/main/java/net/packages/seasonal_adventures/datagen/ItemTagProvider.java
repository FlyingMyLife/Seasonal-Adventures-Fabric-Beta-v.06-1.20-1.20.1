package net.packages.seasonal_adventures.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.packages.seasonal_adventures.item.Items;
import net.packages.seasonal_adventures.util.Tags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ItemTagProvider extends FabricTagProvider.ItemTagProvider {

    public ItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(Tags.Items.ABP_SUIT_ITEMS)
                .add(Items.ABP_BOOTS)
                .add(Items.ABP_LEGGINGS)
                .add(Items.ABP_CHESTPLATE)
                .add(Items.ABP_HELMET);
    }
}
