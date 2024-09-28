package net.packages.seasonal_adventures.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.packages.seasonal_adventures.SeasonalAdventures;
import net.packages.seasonal_adventures.block.Blocks;


public class ModBlockEntities {
    public static BlockEntityType<AnimatedBlockEntity> ANIMATED_BLOCK_ENTITY;
    public static BlockEntityType<EnergyInjectorBlockEntity>ENERGY_INJECTOR_BLOCK_ENTITY;

    public static void registerBlockEntities() {
        ANIMATED_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(SeasonalAdventures.MOD_ID,"animated_block_entity"),
              FabricBlockEntityTypeBuilder.create(AnimatedBlockEntity::new, Blocks.ANIMATED_BLOCK).build());
        ENERGY_INJECTOR_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(SeasonalAdventures.MOD_ID,"energy_injector_block_entity"),
                FabricBlockEntityTypeBuilder.create(EnergyInjectorBlockEntity::new, Blocks.ENERGY_INJECTOR_BLOCK).build());
    }

}
