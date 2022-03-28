package io.github.mrplague.mixin.blocks;

import io.github.mrplague.MrPlagueWarperClient;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractFurnaceBlock.class)
public class AbstractFurnaceBlockMixin {
    @Inject(method = "getRenderType", at = @At("HEAD"), cancellable = true)
    public void getRenderType(BlockState state, CallbackInfoReturnable<Boolean> ci) {
        if (MrPlagueWarperClient.enabled) {
            ci.setReturnValue(null);
        }
    }
}