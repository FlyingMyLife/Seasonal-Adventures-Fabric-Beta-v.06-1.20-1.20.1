package net.flyingmylife.seasonal_adventures.block.entity;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.flyingmylife.seasonal_adventures.SA;
import net.flyingmylife.seasonal_adventures.block.SABlocks;
import net.flyingmylife.seasonal_adventures.block.entity.client.GuidingSkinthBlockRenderer;
import net.flyingmylife.seasonal_adventures.block.entity.client.CopperLCBlockRenderer;
import net.flyingmylife.seasonal_adventures.block.entity.client.IronLCBlockRenderer;
import net.flyingmylife.seasonal_adventures.block.entity.lockedChests.CopperLCBlockEntity;
import net.flyingmylife.seasonal_adventures.block.entity.lockedChests.IronLCBlockEntity;


public class SABlockEntities {
    public static BlockEntityType<CopperLCBlockEntity> LOCKED_CHEST_LVL_COPPER_BLOCK_ENTITY;
    public static BlockEntityType<IronLCBlockEntity> LOCKED_CHEST_LVL_IRON_BLOCK_ENTITY;
    public static BlockEntityType<GuidingSkinthBlockEntity> GUIDING_SKINTH_BLOCK_ENTITY;

    public static void registerEntities() {
        LOCKED_CHEST_LVL_COPPER_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(SA.MOD_ID,"locked_chest_lvl_copper_block_entity"),
                BlockEntityType.Builder.create(CopperLCBlockEntity::new, SABlocks.LOCKED_CHEST_LVL_COPPER).build());
        LOCKED_CHEST_LVL_IRON_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(SA.MOD_ID,"locked_chest_lvl_iron_block_entity"),
                BlockEntityType.Builder.create(IronLCBlockEntity::new, SABlocks.LOCKED_CHEST_LVL_IRON).build());
        GUIDING_SKINTH_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(SA.MOD_ID,"guiding_skinth_block_entity"),
                BlockEntityType.Builder.create(GuidingSkinthBlockEntity::new, SABlocks.GUIDING_SKINTH).build());
    }

    public static void registerEntityRenderFactories() {
        BlockEntityRendererFactories.register(SABlockEntities.LOCKED_CHEST_LVL_COPPER_BLOCK_ENTITY, CopperLCBlockRenderer::new);
        BlockEntityRendererFactories.register(SABlockEntities.LOCKED_CHEST_LVL_IRON_BLOCK_ENTITY, IronLCBlockRenderer::new);
        BlockEntityRendererFactories.register(SABlockEntities.GUIDING_SKINTH_BLOCK_ENTITY, GuidingSkinthBlockRenderer::new);
    }
}
