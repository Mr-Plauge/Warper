package io.github.mrplague.mixin;

import io.github.mrplague.MrPlagueWarper;
import io.github.mrplague.MrPlagueWarperClient;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particle.ParticleEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ParticleManager.class)
public abstract class ParticleManagerMixin {
    @Inject(method = "addParticle", at = @At("HEAD"), cancellable = true)
    private void addParticle(ParticleEffect parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ, CallbackInfoReturnable<Boolean> ci) {
        if (MrPlagueWarperClient.enabled && (parameters != MrPlagueWarper.OVERWORLD_SOLID && parameters != MrPlagueWarper.OVERWORLD_FLUID && parameters != MrPlagueWarper.OVERWORLD_PLAYER && parameters != MrPlagueWarper.OVERWORLD_WARP && parameters != MrPlagueWarper.NETHER_SOLID && parameters != MrPlagueWarper.NETHER_FLUID && parameters != MrPlagueWarper.NETHER_PLAYER && parameters != MrPlagueWarper.NETHER_WARP && parameters != MrPlagueWarper.END_SOLID && parameters != MrPlagueWarper.END_FLUID && parameters != MrPlagueWarper.END_PLAYER && parameters != MrPlagueWarper.END_WARP)) {
            ci.setReturnValue(null);
        }
    }
}