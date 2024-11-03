package net.packages.seasonal_adventures.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.book.RecipeCategory;
import net.packages.seasonal_adventures.block.Blocks;
import net.packages.seasonal_adventures.item.Items;

import java.util.List;
import java.util.function.Consumer;

public class RecipeProvider extends FabricRecipeProvider {
    public static List<ItemConvertible> TITANIUM_SMELTABLES = List.of(
            Blocks.TITANIUM_ORE,
            Blocks.DEEPSLATE_TITANIUM_ORE,
            Items.RAW_TITANIUM
    );
    public static List<ItemConvertible> ALUMINIUM_SMELTABLES = List.of(
            Blocks.ALUMINIUM_ORE,
            Blocks.DEEPSLATE_ALUMINIUM_ORE,
            Items.RAW_ALUMINIUM
    );
    public static List<ItemConvertible> LITHIUM_SMELTABLES = List.of(
            Blocks.LITHIUM_ORE,
            Blocks.DEEPSLATE_LITHIUM_ORE,
            Items.RAW_LITHIUM
    );
    public RecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        offerSmelting(exporter, TITANIUM_SMELTABLES, RecipeCategory.MISC, Items.TITANIUM_INGOT,
                0.7f, 200, "titanium_ingot");
        offerBlasting(exporter, TITANIUM_SMELTABLES, RecipeCategory.MISC, Items.TITANIUM_INGOT,
                0.7f, 100, "titanium_ingot");
        offerSmelting(exporter, ALUMINIUM_SMELTABLES, RecipeCategory.MISC, Items.ALUMINIUM_INGOT,
                0.7f, 200, "aluminium_ingot");
        offerBlasting(exporter, ALUMINIUM_SMELTABLES, RecipeCategory.MISC, Items.ALUMINIUM_INGOT,
                0.7f, 100, "aluminium_ingot");
        offerSmelting(exporter, LITHIUM_SMELTABLES, RecipeCategory.MISC, Items.LITHIUM_INGOT,
                0.7f, 200, "lithium_ingot");
        offerBlasting(exporter, LITHIUM_SMELTABLES, RecipeCategory.MISC, Items.LITHIUM_INGOT,
                0.7f, 100, "lithium_ingot");

        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, Items.ALUMINIUM_NUGGET, RecipeCategory.MISC, Items.ALUMINIUM_INGOT);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, Items.GRADE_5_TITANIUM_INGOT, RecipeCategory.DECORATIONS, Blocks.GRADE_5_TITANIUM_BLOCK);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, Items.TITANIUM_INGOT, RecipeCategory.DECORATIONS, Blocks.TITANIUM_BLOCK);
    }
}
