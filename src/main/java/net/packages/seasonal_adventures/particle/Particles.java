package net.packages.seasonal_adventures.particle;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.client.particle.AnimatedParticle;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.packages.seasonal_adventures.SeasonalAdventures;


public class Particles {
    public static final ParticleType SKINTH_PARTICLE = FabricParticleTypes.simple();
    public static final ParticleType SKINTH_OF_DREAMS_PARTICLE = FabricParticleTypes.simple();

    public static void registerParticles(){
        registerParticle("skinth_particle", Particles.SKINTH_PARTICLE);
        registerParticle("skinth_of_dreams_particle", Particles.SKINTH_OF_DREAMS_PARTICLE);
    }
    private static void registerParticle(String path, ParticleType<SimpleParticleType> particleType) {
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(SeasonalAdventures.MOD_ID, path), particleType);
    }
}
