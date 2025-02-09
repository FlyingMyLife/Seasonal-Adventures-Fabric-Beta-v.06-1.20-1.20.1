package net.packages.seasonal_adventures.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.packages.seasonal_adventures.block.SABlocks;
import net.packages.seasonal_adventures.item.SAItems;

import java.util.List;
import java.util.concurrent.CompletableFuture;
public class RecipeProvider extends FabricRecipeProvider {
    public static List<ItemConvertible> TITANIUM_SMELTABLES = List.of(
            SABlocks.TITANIUM_ORE,
            SABlocks.DEEPSLATE_TITANIUM_ORE,
            SAItems.RAW_TITANIUM
    );
    public static List<ItemConvertible> ALUMINIUM_SMELTABLES = List.of(
            SABlocks.ALUMINIUM_ORE,
            SABlocks.DEEPSLATE_ALUMINIUM_ORE,
            SAItems.RAW_ALUMINUM
    );
    public static List<ItemConvertible> LITHIUM_SMELTABLES = List.of(
            SABlocks.LITHIUM_ORE,
            SABlocks.DEEPSLATE_LITHIUM_ORE,
            SAItems.RAW_LITHIUM
    );

    public RecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }


    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {
        RecipeGenerator generator = new RecipeGenerator(wrapperLookup, recipeExporter) {
            @Override
            public void generate() {
                offerSmelting(TITANIUM_SMELTABLES, RecipeCategory.MISC, SAItems.TITANIUM_INGOT,
                        0.7f, 200, "titanium_ingot");
                offerBlasting(TITANIUM_SMELTABLES, RecipeCategory.MISC, SAItems.TITANIUM_INGOT,
                        0.7f, 100, "titanium_ingot");
                offerSmelting(ALUMINIUM_SMELTABLES, RecipeCategory.MISC, SAItems.ALUMINUM_INGOT,
                        0.7f, 200, "aluminium_ingot");
                offerBlasting(ALUMINIUM_SMELTABLES, RecipeCategory.MISC, SAItems.ALUMINUM_INGOT,
                        0.7f, 100, "aluminium_ingot");
                offerSmelting(LITHIUM_SMELTABLES, RecipeCategory.MISC, SAItems.LITHIUM_INGOT,
                        0.7f, 200, "lithium_ingot");
                offerBlasting(LITHIUM_SMELTABLES, RecipeCategory.MISC, SAItems.LITHIUM_INGOT,
                        0.7f, 100, "lithium_ingot");
                offerReversibleCompactingRecipes(RecipeCategory.BUILDING_BLOCKS, SAItems.GRADE_5_TITANIUM_INGOT, RecipeCategory.DECORATIONS, SABlocks.GRADE_5_TITANIUM_BLOCK);
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
