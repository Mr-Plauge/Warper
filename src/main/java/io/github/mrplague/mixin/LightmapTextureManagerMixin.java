package io.github.mrplague.mixin;

import io.github.mrplague.MrPlagueWarperClient;
import net.minecraft.client.render.LightmapTextureManager;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.client.options.GameOptions;

@Mixin(value = LightmapTextureManager.class)
public class LightmapTextureManagerMixin {

    @Redirect(at = @At(value = "FIELD", target = "Lnet/minecraft/client/options/GameOptions;gamma*:D", opcode = Opcodes.GETFIELD), method = "update(F)V")
    private double getFieldValue(GameOptions options) {
        if (MrPlagueWarperClient.enabled) {
            return 20;
        } else {
            return options.gamma;
        }
    }
}
