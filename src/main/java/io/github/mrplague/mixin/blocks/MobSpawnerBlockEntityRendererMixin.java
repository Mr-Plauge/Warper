package io.github.mrplague.mixin.blocks;

import io.github.mrplague.MrPlagueWarperClient;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.MobSpawnerBlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobSpawnerBlockEntityRenderer.class)
public class MobSpawnerBlockEntityRendererMixin {
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public void render(MobSpawnerBlockEntity mobSpawnerBlockEntity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j, CallbackInfo info) {
        if (MrPlagueWarperClient.enabled) {
            info.cancel();
        }
    }
}
