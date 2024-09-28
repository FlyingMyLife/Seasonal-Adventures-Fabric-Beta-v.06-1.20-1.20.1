package net.packages.seasonal_adventures.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.packages.seasonal_adventures.SeasonalAdventures;
import net.packages.seasonal_adventures.entity.custom.ATMEntity;
import net.packages.seasonal_adventures.entity.custom.DylanEntity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class Entities {
    public static final EntityType<DylanEntity> DYLAN = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(SeasonalAdventures.MOD_ID, "dylan"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, DylanEntity::new).spawnableFarFromPlayer()
                    .dimensions(EntityDimensions.fixed(0.6f, 1.8f)).fireImmune().build());

    public static final EntityType<ATMEntity> ATM = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(SeasonalAdventures.MOD_ID, "atm"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, ATMEntity::new)
                    .dimensions(EntityDimensions.fixed(1f, 2.0625f)).fireImmune().build());

}
