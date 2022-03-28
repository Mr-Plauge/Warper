package io.github.mrplague.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

@Environment(EnvType.CLIENT)
public class WarperAtomReverse extends WarperAtom {
    WarperAtomReverse(ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
        super(clientWorld, d, e, f, g, h, i);
        this.scale = (float)((double)this.scale * 1.5D);
        this.maxAge = (int)(Math.random() * 2.0D) + 60;
    }

    public float getSize(float tickDelta) {
        float f = 1.0F - ((float)this.age + tickDelta) / ((float)this.maxAge * 1.5F);
        return this.scale * f;
    }

    public int getBrightness(float tint) {
        return 15728880;
    }

    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        if (this.age++ >= this.maxAge) {
            this.markDead();
        } else {
            float f = (float)this.age / (float)this.maxAge;
            this.x += this.velocityX * (double)f;
            this.y += this.velocityY * (double)f;
            this.z += this.velocityZ * (double)f;
        }
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            WarperAtomReverse warperAtomReverse = new WarperAtomReverse(clientWorld, d, e, f, g, h, i);
            warperAtomReverse.setSprite(this.spriteProvider);
            return warperAtomReverse;
        }
    }
}