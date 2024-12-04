package net.packages.lumina_lore.systems.particles.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

import java.util.Random;

public class SkintParticle extends AnimatedParticle {
    @Environment(EnvType.CLIENT)
    SkintParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider) {
        super(world, x, y, z, spriteProvider, 0.0125F);
        Random random = new Random();
        this.velocityX = velocityX;
        this.velocityY = velocityY - 0.002;
        this.velocityZ = velocityZ;
        this.scale *= random.nextFloat(0.25f,0.35f);
        this.maxAge = 200;
        this.setTargetColor(15916745);
        this.setSpriteForAge(spriteProvider);
        this.alpha = 1.0F;
    }

    public void move(double dx, double dy, double dz) {
        this.setBoundingBox(this.getBoundingBox().offset(dx, dy, dz));
        this.repositionFromBoundingBox();
    }

    @Override
    public void tick() {
        super.tick();

    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            return new SkintParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider);
        }
    }
}
