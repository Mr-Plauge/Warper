package io.github.mrplague.mixin;

import io.github.mrplague.MrPlagueWarperClient;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static io.github.mrplague.MrPlagueWarperClient.MC;

@Mixin(Entity.class)
public class EntityMixin {
    @Inject(method = "changeLookDirection", at = @At("HEAD"), cancellable = true)
    private void onChangeLookDirection(double x, double y, CallbackInfo ci) {
        if (MrPlagueWarperClient.enabled && MrPlagueWarperClient.cameraEntity != null && this.equals(MC.player) && MC.player != null) {
            MrPlagueWarperClient.cameraEntity.changeLookDirection(x, y);
            ci.cancel();
        }
    }
}
