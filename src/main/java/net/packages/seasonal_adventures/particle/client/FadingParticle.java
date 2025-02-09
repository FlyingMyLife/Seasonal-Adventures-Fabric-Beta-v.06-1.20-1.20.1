package net.packages.seasonal_adventures.particle.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.ParticleEffect;
import org.jetbrains.annotations.Nullable;

public class FadingParticle extends AnimatedParticle {
    @Environment(EnvType.CLIENT)
    FadingParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider) {
        super(world, x, y, z, spriteProvider, 0.0125F);
        this.velocityX = velocityX;
        this.velocityY = velocityY - 0.002;
        this.velocityZ = velocityZ;
        this.scale = 0.12f;
        this.maxAge = 120;
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

        if (age >= 100 && alpha >= 0) {
            alpha -= 0.005f;
        }
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<ParticleEffect> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public @Nullable Particle createParticle(ParticleEffect parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new FadingParticle(world, x, y, z, velocityX, velocityY, velocityZ, this.spriteProvider);
        }
    }
}
