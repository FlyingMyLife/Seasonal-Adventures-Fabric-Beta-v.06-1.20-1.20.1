package dev.flyingmylife.seasonal_adventures.datagen.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import dev.flyingmylife.seasonal_adventures.block.SABlocks;
import dev.flyingmylife.seasonal_adventures.item.SAItems;

import java.util.List;
import java.util.concurrent.CompletableFuture;
public class RecipeProvider extends FabricRecipeProvider {

    public RecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }


    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {
        RecipeGenerator generator = new RecipeGenerator(wrapperLookup, recipeExporter) {
            @Override
            public void generate() {
                List<ItemConvertible> titanium = List.of(
                        SABlocks.TITANIUM_ORE,
                        SABlocks.DEEPSLATE_TITANIUM_ORE,
                        SAItems.RAW_TITANIUM
                );

                List<ItemConvertible> aluminium = List.of(
                        SABlocks.ALUMINIUM_ORE,
                        SABlocks.DEEPSLATE_ALUMINIUM_ORE,
                        SAItems.RAW_ALUMINUM
                );

                List<ItemConvertible> lithium = List.of(
                        SABlocks.LITHIUM_ORE,
                        SABlocks.DEEPSLATE_LITHIUM_ORE,
                        SAItems.RAW_LITHIUM
                );
                offerSmelting(titanium, RecipeCategory.MISC, SAItems.TITANIUM_INGOT,
                        0.7f, 200, "titanium_ingot");
                offerBlasting(titanium, RecipeCategory.MISC, SAItems.TITANIUM_INGOT,
                        0.7f, 100, "titanium_ingot");
                offerSmelting(aluminium, RecipeCategory.MISC, SAItems.ALUMINUM_INGOT,
                        0.7f, 200, "aluminium_ingot");
                offerBlasting(aluminium, RecipeCategory.MISC, SAItems.ALUMINUM_INGOT,
                        0.7f, 100, "aluminium_ingot");
                offerSmelting(lithium, RecipeCategory.MISC, SAItems.LITHIUM_INGOT,
                        0.7f, 200, "lithium_ingot");
                offerBlasting(lithium, RecipeCategory.MISC, SAItems.LITHIUM_INGOT,
                        0.7f, 100, "lithium_ingot");
                offerReversibleCompactingRecipes(RecipeCategory.BUILDING_BLOCKS, SAItems.REFINED_TITANIUM_INGOT, RecipeCategory.DECORATIONS, SABlocks.REFINED_TITANIUM_BLOCK);
                offerReversibleCompactingRecipes(RecipeCategory.BUILDING_BLOCKS, SAItems.TITANIUM_INGOT, RecipeCategory.DECORATIONS, SABlocks.TITANIUM_BLOCK);
            }
        };
        return generator;
    }

    @Override
    public String getName() {
        return "recipe_provider";
    }
}
