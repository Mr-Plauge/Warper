package io.github.mrplague.mixin;

import io.github.mrplague.MrPlagueWarperClient;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    @Inject(method = "isBlockBreakingRestricted", at = @At("HEAD"), cancellable = true)
    public void onIsBlockBreakingRestricted(CallbackInfoReturnable<Boolean> cir) {
        if (MrPlagueWarperClient.enabled) {
            cir.setReturnValue(true);
        }
    }
}