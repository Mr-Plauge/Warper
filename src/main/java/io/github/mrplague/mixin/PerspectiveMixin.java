
package io.github.mrplague.mixin;

import io.github.mrplague.MrPlagueWarperClient;
import net.minecraft.client.option.Perspective;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Perspective.class)
public class PerspectiveMixin {
    @Inject(method = "isFirstPerson", at = @At("HEAD"), cancellable = true)
    private void isFirstPerson(CallbackInfoReturnable<Boolean> ci) {
        if (MrPlagueWarperClient.enabled) {
            ci.setReturnValue(true);
        }
    }

    @Inject(method = "isFrontView", at = @At("HEAD"), cancellable = true)
    private void isFrontView(CallbackInfoReturnable<Boolean> ci) {
        if (MrPlagueWarperClient.enabled) {
            ci.setReturnValue(false);
        }
    }
}