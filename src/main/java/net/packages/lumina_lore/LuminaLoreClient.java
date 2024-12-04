package net.packages.lumina_lore;


import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.packages.lumina_lore.systems.particles.ImplementedParticles;
import net.packages.lumina_lore.systems.particles.client.SkintParticle;

public class LuminaLoreClient {
    public static void initializeClient(){
        LuminaLore.LOGGER.info("Initialization client...");
        ParticleFactoryRegistry.getInstance().register(ImplementedParticles.SKINT_PARTICLE, SkintParticle.Factory::new);
    }
}
