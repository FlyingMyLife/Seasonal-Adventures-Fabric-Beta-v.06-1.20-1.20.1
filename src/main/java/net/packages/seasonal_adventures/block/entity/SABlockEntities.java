package net.packages.seasonal_adventures.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.packages.seasonal_adventures.SeasonalAdventures;
import net.packages.seasonal_adventures.block.SABlocks;
import net.packages.seasonal_adventures.block.entity.client.GuidingSkinthBlockRenderer;
import net.packages.seasonal_adventures.block.entity.client.LockedChestLvLCopperBlockRenderer;
import net.packages.seasonal_adventures.block.entity.client.LockedChestLvLIronBlockRenderer;
import net.packages.seasonal_adventures.block.entity.lockedChests.LockedChestLvLCopperBlockEntity;
import net.packages.seasonal_adventures.block.entity.lockedChests.LockedChestLvLIronBlockEntity;


public class SABlockEntities {
    public static BlockEntityType<LockedChestLvLCopperBlockEntity> LOCKED_CHEST_LVL_COPPER_BLOCK_ENTITY;
    public static BlockEntityType<LockedChestLvLIronBlockEntity> LOCKED_CHEST_LVL_IRON_BLOCK_ENTITY;
    public static BlockEntityType<GuidingSkinthBlockEntity> GUIDING_SKINTH_BLOCK_ENTITY;

    public static void registerEntities() {
        LOCKED_CHEST_LVL_COPPER_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(SeasonalAdventures.MOD_ID,"locked_block_lvl_copper_block_entity"),
                FabricBlockEntityTypeBuilder.create(LockedChestLvLCopperBlockEntity::new, SABlocks.LOCKED_CHEST_LVL_COPPER).build());
        LOCKED_CHEST_LVL_IRON_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(SeasonalAdventures.MOD_ID,"locked_block_lvl_iron_block_entity"),
                FabricBlockEntityTypeBuilder.create(LockedChestLvLIronBlockEntity::new, SABlocks.LOCKED_CHEST_LVL_IRON).build());
        GUIDING_SKINTH_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(SeasonalAdventures.MOD_ID,"guiding_skinth_block_entity"),
                FabricBlockEntityTypeBuilder.create(GuidingSkinthBlockEntity::new, SABlocks.GUIDING_SKINTH).build());
    }

    public static void registerEntityRenderFactories() {
        BlockEntityRendererFactories.register(SABlockEntities.LOCKED_CHEST_LVL_COPPER_BLOCK_ENTITY, LockedChestLvLCopperBlockRenderer::new);
        BlockEntityRendererFactories.register(SABlockEntities.LOCKED_CHEST_LVL_IRON_BLOCK_ENTITY, LockedChestLvLIronBlockRenderer::new);
        BlockEntityRendererFactories.register(SABlockEntities.GUIDING_SKINTH_BLOCK_ENTITY, GuidingSkinthBlockRenderer::new);
    }
}
