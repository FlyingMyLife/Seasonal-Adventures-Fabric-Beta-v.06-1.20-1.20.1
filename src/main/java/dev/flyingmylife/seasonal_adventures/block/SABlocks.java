package dev.flyingmylife.seasonal_adventures.block;

import dev.flyingmylife.seasonal_adventures.SA;
import dev.flyingmylife.seasonal_adventures.block.custom.*;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public class SABlocks {
    public static Set<Block> CUSTOM_MODELED_BLOCKS = new HashSet<>();

    public static final Block LOCKED_CHEST_LVL_COPPER;
    public static final Block LOCKED_CHEST_LVL_IRON;
    public static final Block LOCKED_CHEST_LVL_GOLD;
    public static final Block LOCKED_CHEST_LVL_DIAMOND;
    public static final Block LOCKED_CHEST_LVL_NETHERITE;

    public static final Block GUIDING_SKINTH;
    public static final Block DUCKER_SYSTEM;

    public static final Block REFINED_TITANIUM_BLOCK;
    public static final Block TITANIUM_BLOCK;

    public static final Block ALUMINIUM_ORE;
    public static final Block TITANIUM_ORE;
    public static final Block LITHIUM_ORE;
    public static final Block DEEPSLATE_TITANIUM_ORE;
    public static final Block DEEPSLATE_LITHIUM_ORE;
    public static final Block DEEPSLATE_ALUMINIUM_ORE;

    public static final Block LAPTOP;
    public static final Block ADVANCED_AUTOMATON_BODY;

    public static void registerBlocks() {
        SA.LOGGER.info("Registering blocks for Seasonal Adventures");
    }

    private static Block register(String name, Function<AbstractBlock.Settings, Block> blockFactory, AbstractBlock.Settings settings, boolean shouldRegisterItem) {
        RegistryKey<Block> blockKey = keyOfBlock(name);
        Block block = blockFactory.apply(settings.registryKey(blockKey));
        if (shouldRegisterItem) {
            RegistryKey<Item> itemKey = keyOfItem(name);
            BlockItem blockItem = new BlockItem(block, new Item.Settings().registryKey(itemKey));
            Registry.register(Registries.ITEM, itemKey, blockItem);
        }
        return Registry.register(Registries.BLOCK, blockKey, block);
    }

    private static Block registerModeledBlock(String name, Function<AbstractBlock.Settings, Block> blockFactory, AbstractBlock.Settings settings, boolean shouldRegisterItem) {
        Block block = register(name, blockFactory, settings, shouldRegisterItem);
        CUSTOM_MODELED_BLOCKS.add(block);
        return block;
    }

    private static Block register(String name, Function<AbstractBlock.Settings, Block> blockFactory, AbstractBlock.Settings settings) {
        return register(name, blockFactory, settings, true);
    }

    private static Block registerModeledBlock(String name, Function<AbstractBlock.Settings, Block> blockFactory, AbstractBlock.Settings settings) {
        return registerModeledBlock(name, blockFactory, settings, true);
    }

    private static RegistryKey<Block> keyOfBlock(String name) {
        return RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(SA.MOD_ID, name));
    }

    private static RegistryKey<Item> keyOfItem(String name) {
        return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(SA.MOD_ID, name));
    }

    static {
        LOCKED_CHEST_LVL_COPPER = registerModeledBlock("locked_chest_lvl_copper", LockedChestBlock::new, Block.Settings.copy(Blocks.CHEST).strength(-1f).nonOpaque().sounds(BlockSoundGroup.WOOD));
        LOCKED_CHEST_LVL_IRON = registerModeledBlock("locked_chest_lvl_iron", LockedChestBlock::new, Block.Settings.copy(Blocks.CHEST).strength(-1f).nonOpaque().sounds(BlockSoundGroup.WOOD));
        LOCKED_CHEST_LVL_GOLD = registerModeledBlock("locked_chest_lvl_gold", LockedChestBlock::new, Block.Settings.copy(Blocks.CHEST).strength(-1f).nonOpaque().sounds(BlockSoundGroup.WOOD));
        LOCKED_CHEST_LVL_DIAMOND = registerModeledBlock("locked_chest_lvl_diamond", LockedChestBlock::new, Block.Settings.copy(Blocks.CHEST).strength(-1f).nonOpaque().sounds(BlockSoundGroup.WOOD));
        LOCKED_CHEST_LVL_NETHERITE = registerModeledBlock("locked_chest_lvl_netherite", LockedChestBlock::new, Block.Settings.copy(Blocks.CHEST).strength(-1f).nonOpaque().sounds(BlockSoundGroup.WOOD));

        GUIDING_SKINTH = registerModeledBlock("guiding_skinth", GuidingSkinthBlock::new, Block.Settings.copy(Blocks.CHEST).strength(-1f).nonOpaque().sounds(BlockSoundGroup.AMETHYST_BLOCK));
        DUCKER_SYSTEM = registerModeledBlock("ducker_system", DuckerSystemBlock::new, Block.Settings.copy(Blocks.NETHERITE_BLOCK).sounds(BlockSoundGroup.NETHERITE));

        REFINED_TITANIUM_BLOCK = register("refined_titanium_block", Block::new, Block.Settings.copy(Blocks.NETHERITE_BLOCK).sounds(BlockSoundGroup.COPPER));
        TITANIUM_BLOCK = register("titanium_block", Block::new, Block.Settings.copy(Blocks.NETHERITE_BLOCK).sounds(BlockSoundGroup.NETHERITE));

        ALUMINIUM_ORE = register("aluminium_ore", settings -> new ExperienceDroppingBlock(UniformIntProvider.create(2, 5), settings), Block.Settings.copy(Blocks.STONE).strength(2f));
        TITANIUM_ORE = register("titanium_ore", settings -> new ExperienceDroppingBlock(UniformIntProvider.create(2, 5), settings), Block.Settings.copy(Blocks.STONE).strength(2f));
        LITHIUM_ORE = register("lithium_ore", settings -> new ExperienceDroppingBlock(UniformIntProvider.create(2, 5), settings), Block.Settings.copy(Blocks.STONE).strength(2f));

        DEEPSLATE_TITANIUM_ORE = register("deepslate_titanium_ore", settings -> new ExperienceDroppingBlock(UniformIntProvider.create(2, 5), settings), Block.Settings.copy(Blocks.DEEPSLATE).strength(4f));
        DEEPSLATE_LITHIUM_ORE = register("deepslate_lithium_ore", settings -> new ExperienceDroppingBlock(UniformIntProvider.create(2, 5), settings), Block.Settings.copy(Blocks.STONE).strength(4f));
        DEEPSLATE_ALUMINIUM_ORE = register("deepslate_aluminium_ore", settings -> new ExperienceDroppingBlock(UniformIntProvider.create(2, 5), settings), Block.Settings.copy(Blocks.STONE).strength(4f));

        LAPTOP = registerModeledBlock("laptop", LaptopBlock::new, Block.Settings.copy(Blocks.CRAFTING_TABLE).strength(0.2f).nonOpaque().sounds(BlockSoundGroup.STONE));
        ADVANCED_AUTOMATON_BODY = registerModeledBlock("advanced_automaton_body", AdvancedAutomatonBodyBlock::new, Block.Settings.copy(Blocks.CRAFTING_TABLE).strength(0.2f).nonOpaque().sounds(BlockSoundGroup.STONE));
    }

    public static class Utilities {
        public static VoxelShape rotateShape(Direction from, Direction to, VoxelShape shape) {
            VoxelShape[] buffer = new VoxelShape[] { shape, VoxelShapes.empty() };

            int times = (to.getHorizontalQuarterTurns() - from.getHorizontalQuarterTurns() + 4) % 4;

            for (int i = 0; i < times; i++) {
                buffer[0].forEachBox((minX, minY, minZ, maxX, maxY, maxZ) -> {
                    buffer[1] = VoxelShapes.union(buffer[1], VoxelShapes.cuboid(
                            1 - maxZ, minY, minX,
                            1 - minZ, maxY, maxX
                    ));
                });
                buffer[0] = buffer[1];
                buffer[1] = VoxelShapes.empty();
            }

            return buffer[0];
        }

    }
}
