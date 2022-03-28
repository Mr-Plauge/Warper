package io.github.mrplague.mixin;

import io.github.mrplague.MrPlagueWarperClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static io.github.mrplague.MrPlagueWarperClient.MC;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    @Inject(method = "getCameraPlayer", at = @At("HEAD"), cancellable = true)
    private void onGetCameraPlayer(CallbackInfoReturnable<PlayerEntity> cir) {
        if (MrPlagueWarperClient.enabled && MC.player != null) {
            cir.setReturnValue(MC.player);
        }
    }
}
