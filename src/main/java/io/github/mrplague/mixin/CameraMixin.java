package io.github.mrplague.mixin;

import io.github.mrplague.MrPlagueWarper;
import io.github.mrplague.MrPlagueWarperClient;
import net.minecraft.client.input.Input;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static io.github.mrplague.MrPlagueWarperClient.*;

@Mixin(Camera.class)
public abstract class CameraMixin {
    @Inject(method = "update", at = @At("HEAD"), cancellable = true)
    private void update(BlockView area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo info) {
        if (MrPlagueWarperClient.enabled && MrPlagueWarperClient.cameraEntity != null && MC.player != null) {
            MrPlagueWarperClient.cameraEntity.cameraRender();
            MC.player.input = new Input();
            if (MC.player.getX() >= MrPlagueWarperClient.cameraEntity.getX() + 120 || MC.player.getY() >= MrPlagueWarperClient.cameraEntity.getY() + 400 || MC.player.getZ() >= MrPlagueWarperClient.cameraEntity.getZ() + 120 || MC.player.getX() <= MrPlagueWarperClient.cameraEntity.getX() - 120 || MC.player.getY() <= MrPlagueWarperClient.cameraEntity.getY() - 400 || MC.player.getZ() <= MrPlagueWarperClient.cameraEntity.getZ() - 120) {
                MrPlagueWarperClient.toggle();
            }
        }
        else if (MrPlagueWarperClient.enabled && MrPlagueWarperClient.cameraEntity == null) {
            MrPlagueWarperClient.toggle();
        }
    }

    @Inject(method = "getSubmersionType", at = @At("HEAD"), cancellable = true)
    private void getSubmersionType(CallbackInfoReturnable<Boolean> ci) {
        if (MrPlagueWarperClient.enabled) {
            ci.setReturnValue(null);
        }
    }

    @Inject(method = "getFocusedEntity", at = @At("HEAD"), cancellable = true)
    private void onGetFocusedEntity(CallbackInfoReturnable<Entity> cir) {
        if (MrPlagueWarperClient.enabled && MC.player != null) {
            cir.setReturnValue(MC.player);
        }
    }
}
