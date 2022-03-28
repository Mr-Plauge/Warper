package io.github.mrplague.mixin;

import io.github.mrplague.MrPlagueWarperClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {

    @Inject(at = @At("HEAD"), method = "bobView", cancellable = true)
    private void bobView(MatrixStack matrices, float f, CallbackInfo info) {
        if (MrPlagueWarperClient.enabled) {
            info.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "renderHand", cancellable = true)
    private void renderHand(MatrixStack matrices, Camera camera, float tickDelta, CallbackInfo info) {
        if (MrPlagueWarperClient.enabled) {
            info.cancel();
        }
    }

}