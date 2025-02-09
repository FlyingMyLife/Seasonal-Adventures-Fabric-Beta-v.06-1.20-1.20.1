package net.packages.seasonal_adventures.block;

import net.packages.seasonal_adventures.block.custom.*;
import net.packages.seasonal_adventures.SeasonalAdventures;
import net.minecraft.block.Block;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Blocks {

    //Locked Chests
    public static final Block LOCKED_CHEST_LVL_COPPER = registerBlock("locked_chest_lvl_copper",
            new LockedChestBlock(Block.Settings.copy(net.minecraft.block.Blocks.CHEST).strength(-1f).nonOpaque().sounds(BlockSoundGroup.WOOD), 0));
    public static final Block GUIDING_SKINTH = registerBlock("guiding_skinth",
            new GuidingSkinthBlock(Block.Settings.copy(net.minecraft.block.Blocks.CHEST).strength(-1f).nonOpaque().sounds(BlockSoundGroup.AMETHYST_BLOCK)));
    public static final Block LOCKED_CHEST_LVL_IRON = registerBlock("locked_chest_lvl_iron",
            new LockedChestBlock(Block.Settings.copy(net.minecraft.block.Blocks.CHEST).strength(-1f).nonOpaque().sounds(BlockSoundGroup.WOOD), 1));
    public static final Block LOCKED_CHEST_LVL_GOLD = registerBlock("locked_chest_lvl_gold",
            new LockedChestBlock(Block.Settings.copy(net.minecraft.block.Blocks.CHEST).strength(-1f).nonOpaque().sounds(BlockSoundGroup.WOOD), 2));
    public static final Block LOCKED_CHEST_LVL_DIAMOND = registerBlock("locked_chest_lvl_diamond",
            new LockedChestBlock(Block.Settings.copy(net.minecraft.block.Blocks.CHEST).strength(-1f).nonOpaque().sounds(BlockSoundGroup.WOOD), 3));
    public static final Block LOCKED_CHEST_LVL_NETHERITE = registerBlock("locked_chest_lvl_netherite",
            new LockedChestBlock(Block.Settings.copy(net.minecraft.block.Blocks.CHEST).strength(-1f).nonOpaque().sounds(BlockSoundGroup.WOOD), 4));


    public static final Block TITANIUM_BLOCK = registerBlock("titanium_block",
            new Block(Block.Settings.copy(net.minecraft.block.Blocks.NETHERITE_BLOCK).sounds(BlockSoundGroup.NETHERITE)));
    public static final Block GRADE_5_TITANIUM_BLOCK = registerBlock("grade_5_titanium_block",
            new Block(Block.Settings.copy(net.minecraft.block.Blocks.NETHERITE_BLOCK).sounds(BlockSoundGroup.COPPER)));
    public static final Block ALUMINIUM_ORE = registerBlock("aluminium_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(2, 5), Block.Settings.copy(net.minecraft.block.Blocks.STONE).strength(2f)));
    public static final Block TITANIUM_ORE = registerBlock("titanium_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(2, 5), Block.Settings.copy(net.minecraft.block.Blocks.STONE).strength(2f)));
    public static final Block DEEPSLATE_TITANIUM_ORE = registerBlock("deepslate_titanium_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(2, 5), Block.Settings.copy(net.minecraft.block.Blocks.DEEPSLATE).strength(4f)));
    public static final Block LITHIUM_ORE = registerBlock("lithium_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(2, 5), Block.Settings.copy(net.minecraft.block.Blocks.STONE).strength(2f)));
    public static final Block DEEPSLATE_LITHIUM_ORE = registerBlock("deepslate_lithium_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(2, 5), Block.Settings.copy(net.minecraft.block.Blocks.DEEPSLATE).strength(4f)));
    public static final Block DEEPSLATE_ALUMINIUM_ORE = registerBlock("deepslate_aluminium_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(2, 5), Block.Settings.copy(net.minecraft.block.Blocks.DEEPSLATE).strength(4f)));
    public static final Block LAPTOP = registerBlock("laptop",
            new LaptopBlock(Block.Settings.copy(net.minecraft.block.Blocks.CRAFTING_TABLE).strength(0.2f).nonOpaque().sounds(BlockSoundGroup.STONE)));
    public static final Block ADVANCED_AUTOMATON_BODY = registerBlock("advanced_automaton_body",
            new AdvancedAutomatonBodyBlock(Block.Settings.copy(net.minecraft.block.Blocks.CRAFTING_TABLE).strength(0.2f).nonOpaque().sounds(BlockSoundGroup.STONE)));

    public static final List<Block> SIMPLE_BLOCKS = Arrays.asList(
            DEEPSLATE_ALUMINIUM_ORE,
            DEEPSLATE_TITANIUM_ORE,
            DEEPSLATE_LITHIUM_ORE,
            ALUMINIUM_ORE,
            TITANIUM_ORE,
            LITHIUM_ORE,
            TITANIUM_BLOCK,
            GRADE_5_TITANIUM_BLOCK
    );

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(SeasonalAdventures.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
            return Registry.register(Registries.ITEM, Identifier.of(SeasonalAdventures.MOD_ID, name),
                    new BlockItem(block, new Item.Settings()) {
            });
    }


    public static void registerModBlocks() {
        SeasonalAdventures.LOGGER.info("Registering Blocks for " + SeasonalAdventures.MOD_ID);
    }

}
