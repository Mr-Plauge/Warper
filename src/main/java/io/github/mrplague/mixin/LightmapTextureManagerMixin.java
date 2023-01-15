package io.github.mrplague.mixin;

import io.github.mrplague.MrPlagueWarperClient;
import net.minecraft.client.render.LightmapTextureManager;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.client.option.GameOptions;

import static io.github.mrplague.MrPlagueWarperClient.MC;

@Mixin(value = LightmapTextureManager.class)
public class LightmapTextureManagerMixin {

    @Redirect(method = "update", at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(FF)F", ordinal = 2))
    private float max(float a, float b) {
        float gamma = MC.options.getGamma().getValue().floatValue();
        if (gamma < 0) {
            return gamma;
        }
        return Math.max(a, b);
    }
}
