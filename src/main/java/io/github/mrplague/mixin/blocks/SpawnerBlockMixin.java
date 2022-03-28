package io.github.mrplague.mixin.blocks;

import io.github.mrplague.MrPlagueWarperClient;
import net.minecraft.block.BlockState;
import net.minecraft.block.SpawnerBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SpawnerBlock.class)
public class SpawnerBlockMixin {
    @Inject(method = "getRenderType", at = @At("HEAD"), cancellable = true)
    public void getRenderType(BlockState state, CallbackInfoReturnable<Boolean> ci) {
        if (MrPlagueWarperClient.enabled) {
            ci.setReturnValue(null);
        }
    }
}