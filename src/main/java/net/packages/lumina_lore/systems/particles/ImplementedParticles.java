package net.packages.lumina_lore.systems.particles;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.packages.seasonal_adventures.SeasonalAdventures;

public class ImplementedParticles {
    public static final DefaultParticleType YELLOW_HIGHRES_PARTICLE = FabricParticleTypes.simple();
    public static void registerParticles(){
        registerParticle("yellow_highres_particle", ImplementedParticles.YELLOW_HIGHRES_PARTICLE);
    }
    private static void registerParticle(String path, DefaultParticleType particleType) {
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(SeasonalAdventures.MOD_ID, path), particleType);
    }
}
