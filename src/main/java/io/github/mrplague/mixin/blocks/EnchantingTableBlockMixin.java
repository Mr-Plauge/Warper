package io.github.mrplague.mixin.blocks;

import io.github.mrplague.MrPlagueWarperClient;
import net.minecraft.block.BlockState;
import net.minecraft.block.EnchantingTableBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantingTableBlock.class)
public class EnchantingTableBlockMixin {
    @Inject(method = "getRenderType", at = @At("HEAD"), cancellable = true)
    public void getRenderType(BlockState state, CallbackInfoReturnable<Boolean> ci) {
        if (MrPlagueWarperClient.enabled) {
            ci.setReturnValue(null);
        }
    }
}
