package io.github.mrplague.util;

import com.mojang.authlib.GameProfile;
import io.github.mrplague.MrPlagueWarper;
import io.github.mrplague.MrPlagueWarperClient;
import io.github.mrplague.networking.ModPackets;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EntityPose;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionType;

import java.util.UUID;

import static io.github.mrplague.MrPlagueWarperClient.*;

public class CameraEntity extends ClientPlayerEntity {
    double multX;
    double multZ;

    private static final ClientPlayNetworkHandler NETWORK_HANDLER = new ClientPlayNetworkHandler(MC, MC.currentScreen, MC.getNetworkHandler().getConnection(), new GameProfile(UUID.randomUUID(), "FreeCamera"), MC.createTelemetrySender()) {
        @Override
        public void sendPacket(Packet<?> packet) {
        }
    };

    public CameraEntity() {
        super(MC, MC.world, NETWORK_HANDLER, MC.player.getStatHandler(), MC.player.getRecipeBook(), false, false);

        copyPositionAndRotation(MC.player);
        this.getAbilities().allowModifyWorld = false;
        this.noClip = true;
        this.input = new KeyboardInput(MC.options);
    }

    public void spawn() {
        if (clientWorld != null) {
            clientWorld.addEntity(getId(), this);
        }
    }

    public void despawn() {
        if (clientWorld != null && clientWorld.getEntityById(getId()) != null) {
            clientWorld.removeEntity(getId(), RemovalReason.DISCARDED);
        }
    }

    public final double getMultX() {
        return multX;
    }

    public final double getMultZ() {
        return multZ;
    }

    public void cameraRender() {
        if (MrPlagueWarperClient.viewed_dim == 2)
        {
            multX = (client.player.world.getRegistryKey().getValue().equals(DimensionType.THE_NETHER_ID) ? 0 : ((MrPlagueWarperClient.x) / 8 - MrPlagueWarperClient.x));
            multZ = (client.player.world.getRegistryKey().getValue().equals(DimensionType.THE_NETHER_ID) ? 0 : ((MrPlagueWarperClient.z) / 8 - MrPlagueWarperClient.z));
        }
        else
        {
            multX = (client.player.world.getRegistryKey().getValue().equals(DimensionType.THE_NETHER_ID) ? ((MrPlagueWarperClient.x * 8) - MrPlagueWarperClient.x) : 0);
            multZ = (client.player.world.getRegistryKey().getValue().equals(DimensionType.THE_NETHER_ID) ? ((MrPlagueWarperClient.z * 8) - MrPlagueWarperClient.z) : 0);
        }
        for (int x = -4; x < 5; x++) {
            for (int y = -4; y < 5; y++) {
                for (int z = -4; z < 5; z++) {
                    PacketByteBuf buf = PacketByteBufs.create();
                    buf.writeBlockPos(new BlockPos(this.getX() + x + multX, this.getY() + y, this.getZ() + z + multZ));
                    buf.writeInt(MrPlagueWarperClient.viewed_dim);
                    ClientPlayNetworking.send(ModPackets.SEND_BLOCKPOS, buf);
                }
            }
        }
        PacketByteBuf bufDim = PacketByteBufs.create();
        bufDim.writeInt(MrPlagueWarperClient.viewed_dim);
        ClientPlayNetworking.send(ModPackets.SEND_PLAYERPOS, bufDim);
        if (MrPlagueWarperClient.viewed_dim == 1 && MrPlagueWarperClient.enabled && MrPlagueWarperClient.cameraEntity != null) {
            if (warp1_dim == 1) {
                MC.particleManager.addParticle(MrPlagueWarper.OVERWORLD_WARP, warp1_x - (int)multX + 0.5, warp1_y + 0.5, warp1_z - (int)multZ + 0.5, 0, 0, 0);
            }
            if (warp2_dim == 1) {
                MC.particleManager.addParticle(MrPlagueWarper.OVERWORLD_WARP, warp2_x - (int)multX + 0.5, warp2_y + 0.5, warp2_z - (int)multZ + 0.5, 0, 0, 0);
            }
            if (warp3_dim == 1) {
                MC.particleManager.addParticle(MrPlagueWarper.OVERWORLD_WARP, warp3_x - (int)multX + 0.5, warp3_y + 0.5, warp3_z - (int)multZ + 0.5, 0, 0, 0);
            }
            if (warp4_dim == 1) {
                MC.particleManager.addParticle(MrPlagueWarper.OVERWORLD_WARP, warp4_x - (int)multX + 0.5, warp4_y + 0.5, warp4_z - (int)multZ + 0.5, 0, 0, 0);
            }
            if (warp5_dim == 1) {
                MC.particleManager.addParticle(MrPlagueWarper.OVERWORLD_WARP, warp5_x - (int)multX + 0.5, warp5_y + 0.5, warp5_z - (int)multZ + 0.5, 0, 0, 0);
            }
            if (warp6_dim == 1) {
                MC.particleManager.addParticle(MrPlagueWarper.OVERWORLD_WARP, warp6_x - (int)multX + 0.5, warp6_y + 0.5, warp6_z - (int)multZ + 0.5, 0, 0, 0);
            }
        }
        if (MrPlagueWarperClient.viewed_dim == 2 && MrPlagueWarperClient.enabled && MrPlagueWarperClient.cameraEntity != null) {
            if (warp1_dim == 2) {
                MC.particleManager.addParticle(MrPlagueWarper.NETHER_WARP, warp1_x - (int)multX + 0.5, warp1_y + 0.5, warp1_z - (int)multZ + 0.5, 0, 0, 0);
            }
            if (warp2_dim == 2) {
                MC.particleManager.addParticle(MrPlagueWarper.NETHER_WARP, warp2_x - (int)multX + 0.5, warp2_y + 0.5, warp2_z - (int)multZ + 0.5, 0, 0, 0);
            }
            if (warp3_dim == 2) {
                MC.particleManager.addParticle(MrPlagueWarper.NETHER_WARP, warp3_x - (int)multX + 0.5, warp3_y + 0.5, warp3_z - (int)multZ + 0.5, 0, 0, 0);
            }
            if (warp4_dim == 2) {
                MC.particleManager.addParticle(MrPlagueWarper.NETHER_WARP, warp4_x - (int)multX + 0.5, warp4_y + 0.5, warp4_z - (int)multZ + 0.5, 0, 0, 0);
            }
            if (warp5_dim == 2) {
                MC.particleManager.addParticle(MrPlagueWarper.NETHER_WARP, warp5_x - (int)multX + 0.5, warp5_y + 0.5, warp5_z - (int)multZ + 0.5, 0, 0, 0);
            }
            if (warp6_dim == 2) {
                MC.particleManager.addParticle(MrPlagueWarper.NETHER_WARP, warp6_x - (int)multX + 0.5, warp6_y + 0.5, warp6_z - (int)multZ + 0.5, 0, 0, 0);
            }
        }
        if (MrPlagueWarperClient.viewed_dim == 3 && MrPlagueWarperClient.enabled && MrPlagueWarperClient.cameraEntity != null) {
            if (warp1_dim == 3) {
                MC.particleManager.addParticle(MrPlagueWarper.END_WARP, warp1_x - (int)multX + 0.5, warp1_y + 0.5, warp1_z - (int)multZ + 0.5, 0, 0, 0);
            }
            if (warp2_dim == 3) {
                MC.particleManager.addParticle(MrPlagueWarper.END_WARP, warp2_x - (int)multX + 0.5, warp2_y + 0.5, warp2_z - (int)multZ + 0.5, 0, 0, 0);
            }
            if (warp3_dim == 3) {
                MC.particleManager.addParticle(MrPlagueWarper.END_WARP, warp3_x - (int)multX + 0.5, warp3_y + 0.5, warp3_z - (int)multZ + 0.5, 0, 0, 0);
            }
            if (warp4_dim == 3) {
                MC.particleManager.addParticle(MrPlagueWarper.END_WARP, warp4_x - (int)multX + 0.5, warp4_y + 0.5, warp4_z - (int)multZ + 0.5, 0, 0, 0);
            }
            if (warp5_dim == 3) {
                MC.particleManager.addParticle(MrPlagueWarper.END_WARP, warp5_x - (int)multX + 0.5, warp5_y + 0.5, warp5_z - (int)multZ + 0.5, 0, 0, 0);
            }
            if (warp6_dim == 3) {
                MC.particleManager.addParticle(MrPlagueWarper.END_WARP, warp6_x - (int)multX + 0.5, warp6_y + 0.5, warp6_z - (int)multZ + 0.5, 0, 0, 0);
            }
        }
    }

    @Override
    public void tickMovement() {
        this.getAbilities().flying = true;
        this.getAbilities().setFlySpeed((float)0.05);
        super.tickMovement();
    }

    @Override
    public void setPose(EntityPose pose) {
    }

    @Override
    public boolean isSpectator() {
        return true;
    }
}