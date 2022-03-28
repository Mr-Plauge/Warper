package io.github.mrplague.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

@Environment(EnvType.CLIENT)
public class WarperAtom extends SpriteBillboardParticle {
    private final double startX;
    private final double startY;
    private final double startZ;

    protected WarperAtom(ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
        super(clientWorld, d, e, f);
        this.velocityX = g;
        this.velocityY = h;
        this.velocityZ = i;
        this.x = d;
        this.y = e;
        this.z = f;
        this.startX = this.x;
        this.startY = this.y;
        this.startZ = this.z;
        this.scale = 0.1F * (this.random.nextFloat() * 0.2F + 0.5F);
        float j = this.random.nextFloat() * 0.6F + 0.4F;
        this.maxAge = (int)(Math.random() * 10.0D) + 40;
    }

    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_LIT;
    }

    public void move(double dx, double dy, double dz) {
        this.setBoundingBox(this.getBoundingBox().offset(dx, dy, dz));
        this.repositionFromBoundingBox();
    }

    public float getSize(float tickDelta) {
        float f = ((float)this.age + tickDelta) / (float)this.maxAge;
        f = 1.0F - f;
        f *= f;
        f = 1.0F - f;
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
            float g = f;
            f = -f + f * f * 2.0F;
            f = 1.0F - f;
            this.x = this.startX + this.velocityX * (double)f;
            this.y = this.startY + this.velocityY * (double)f + (double)(1.0F - g);
            this.z = this.startZ + this.velocityZ * (double)f;
        }
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            WarperAtom warperAtom = new WarperAtom(clientWorld, d, e, f, g, h, i);
            warperAtom.setSprite(this.spriteProvider);
            return warperAtom;
        }
    }
}