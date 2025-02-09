package net.packages.seasonal_adventures.entity;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.packages.seasonal_adventures.SeasonalAdventures;
import net.packages.seasonal_adventures.entity.client.ATMRenderer;
import net.packages.seasonal_adventures.entity.custom.ATMEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static net.packages.seasonal_adventures.SeasonalAdventures.MOD_ID;

public class SAEntities {
    public static final RegistryKey<EntityType<?>> ATM_ENTITY_TYPE = RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(MOD_ID, "atm"));

    public static final EntityType<ATMEntity> ATM = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(SeasonalAdventures.MOD_ID, "atm"),
            EntityType.Builder.create(ATMEntity::new, SpawnGroup.MISC)
                    .dimensions(1f, 2.0625f)
                    .makeFireImmune()
                    .build(ATM_ENTITY_TYPE)
    );

    public static void registerEntityAttributes() {
        FabricDefaultAttributeRegistry.register(SAEntities.ATM, ATMEntity.createATMAttributes());
    }

    public static void registerEntityRenderers() {
        EntityRendererRegistry.register(SAEntities.ATM, ATMRenderer::new);
    }
}
