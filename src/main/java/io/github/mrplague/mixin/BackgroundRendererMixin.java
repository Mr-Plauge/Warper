package io.github.mrplague.mixin;

import io.github.mrplague.MrPlagueWarperClient;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.world.ClientWorld;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BackgroundRenderer.class)
public class BackgroundRendererMixin {
    @Shadow
    private static float red;

    @Shadow
    private static float blue;

    @Shadow
    private static float green;


    @Inject(method = "render", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/BackgroundRenderer;blue:F", opcode = Opcodes.PUTSTATIC, ordinal = 0, shift = At.Shift.AFTER), slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/util/CubicSampler;sampleColor(Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/CubicSampler$RgbFetcher;)Lnet/minecraft/util/math/Vec3d;")))
    private static void setFogColorToSkyColor(Camera camera, float partialTicks, ClientWorld world, int i, float f, CallbackInfo info) {
        if (MrPlagueWarperClient.enabled) {
            if (MrPlagueWarperClient.viewed_dim == 1)
            {
                BackgroundRendererMixin.red = 0f;
                BackgroundRendererMixin.green = 0.05f;
                BackgroundRendererMixin.blue = 0.01f;
            }
            else if (MrPlagueWarperClient.viewed_dim == 2)
            {
                BackgroundRendererMixin.red = 0.05f;
                BackgroundRendererMixin.green = 0f;
                BackgroundRendererMixin.blue = 0.01f;
            }
            else
            {
                BackgroundRendererMixin.red = 0.03f;
                BackgroundRendererMixin.green = 0f;
                BackgroundRendererMixin.blue = 0.03f;
            }
        }
    }
}
