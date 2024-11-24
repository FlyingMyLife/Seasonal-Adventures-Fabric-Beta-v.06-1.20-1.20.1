package net.packages.lumina_lore;

import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.packages.lumina_lore.systems.particles.ImplementedParticles;
import net.packages.seasonal_adventures.SeasonalAdventures;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LuminaLore {
    public static final Logger LOGGER = LoggerFactory.getLogger("Lumina Lore");
    private static void registerParticle(String path, DefaultParticleType particleType) {
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(SeasonalAdventures.MOD_ID, path), particleType);
    }
    public static void initialize() {
        LOGGER.info("Initialization...");
        registerParticle("yellow_highres_particle", ImplementedParticles.YELLOW_HIGHRES_PARTICLE);
    }
}
