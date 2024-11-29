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

    public static void initialize() {
        LOGGER.info("Initialization...");
        ImplementedParticles.registerParticles();
    }
}
