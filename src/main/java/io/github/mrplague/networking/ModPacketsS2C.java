package io.github.mrplague.networking;

import io.github.mrplague.MrPlagueWarper;
import io.github.mrplague.MrPlagueWarperClient;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionType;

public class ModPacketsS2C {
    @Environment(EnvType.CLIENT)
    public static void register() {
        ClientPlayConnectionEvents.INIT.register(((clientPlayNetworkHandler, minecraftClient) -> {
            ClientPlayNetworking.registerReceiver(ModPackets.SEND_POS, ModPacketsS2C::onReceivePos);
            ClientPlayNetworking.registerReceiver(ModPackets.WARP_POINT, ModPacketsS2C::onWarpPoint);
            ClientPlayNetworking.registerReceiver(ModPackets.TOGGLE_SIGHT, ModPacketsS2C::onToggleSight);
            ClientPlayNetworking.registerReceiver(ModPackets.CYCLE_SIGHT, ModPacketsS2C::onCycleSight);
            ClientPlayNetworking.registerReceiver(ModPackets.INDEXED_SOUND, ModPacketsS2C::onIndexedSound);
        }));
    }

    private static void onReceivePos(MinecraftClient minecraftClient, ClientPlayNetworkHandler clientPlayNetworkHandler, PacketByteBuf packetByteBuf, PacketSender packetSender) {
        BlockPos bufPos = packetByteBuf.readBlockPos();
        int bufInt = packetByteBuf.readInt();
        minecraftClient.execute(() -> {
            if (MrPlagueWarperClient.cameraEntity != null) {
                ParticleEffect particleEffect;
                if (bufInt == 1) {
                    if (MrPlagueWarperClient.viewed_dim == 1) {
                        particleEffect = MrPlagueWarper.OVERWORLD_SOLID;
                    }
                    else if (MrPlagueWarperClient.viewed_dim == 2) {
                        particleEffect = MrPlagueWarper.NETHER_SOLID;
                    }
                    else {
                        particleEffect = MrPlagueWarper.END_SOLID;
                    }
                }
                else if (bufInt == 2) {
                    if (MrPlagueWarperClient.viewed_dim == 1) {
                        particleEffect = MrPlagueWarper.OVERWORLD_FLUID;
                    }
                    else if (MrPlagueWarperClient.viewed_dim == 2) {
                        particleEffect = MrPlagueWarper.NETHER_FLUID;
                    }
                    else {
                        particleEffect = MrPlagueWarper.END_FLUID;
                    }
                }
                else {
                    if (MrPlagueWarperClient.viewed_dim == 1) {
                        particleEffect = MrPlagueWarper.OVERWORLD_PLAYER;
                    }
                    else if (MrPlagueWarperClient.viewed_dim == 2) {
                        particleEffect = MrPlagueWarper.NETHER_PLAYER;
                    }
                    else {
                        particleEffect = MrPlagueWarper.END_PLAYER;
                    }
                }
                minecraftClient.particleManager.addParticle(particleEffect, bufPos.getX() + 0.5 - (int)MrPlagueWarperClient.cameraEntity.getMultX(), bufPos.getY() + 0.5, bufPos.getZ() + 0.5 - (int)MrPlagueWarperClient.cameraEntity.getMultZ(),0, 0, 0);
            }
        });
    }

    private static void onIndexedSound(MinecraftClient minecraftClient, ClientPlayNetworkHandler clientPlayNetworkHandler, PacketByteBuf packetByteBuf, PacketSender packetSender) {
        int bufInt = packetByteBuf.readInt();
        minecraftClient.execute(() -> {
            if (bufInt == 1) {
                minecraftClient.player.sendMessage(Text.of("Warp network enabled"), true);
                if (MrPlagueWarperClient.cameraEntity != null) {
                    MrPlagueWarperClient.cameraEntity.playSound(SoundEvents.BLOCK_END_PORTAL_FRAME_FILL, 1, 1.2f);
                }
                else {
                    minecraftClient.player.playSound(SoundEvents.BLOCK_END_PORTAL_FRAME_FILL, 1, 1.2f);
                }
            }
            else if (bufInt == 2) {
                minecraftClient.player.sendMessage(Text.of("Warp network disabled"), true);
                if (MrPlagueWarperClient.cameraEntity != null) {
                    MrPlagueWarperClient.cameraEntity.playSound(SoundEvents.BLOCK_END_PORTAL_FRAME_FILL, 1, 1f);
                }
                else {
                    minecraftClient.player.playSound(SoundEvents.BLOCK_END_PORTAL_FRAME_FILL, 1, 1f);
                }
            }
            else {
                if (MrPlagueWarperClient.cameraEntity != null) {
                    MrPlagueWarperClient.cameraEntity.playSound(MrPlagueWarper.WARPER_TRANSMIT, 1, 1f);
                }
                else {
                    minecraftClient.player.playSound(MrPlagueWarper.WARPER_RELOCATE, 1, 1f);
                }
            }
        });
    }

    private static void onToggleSight(MinecraftClient minecraftClient, ClientPlayNetworkHandler clientPlayNetworkHandler, PacketByteBuf packetByteBuf, PacketSender packetSender) {
        boolean bufBoolean = packetByteBuf.readBoolean();
        minecraftClient.execute(() -> {
            if (bufBoolean == true) {
                MrPlagueWarperClient.disable();
            }
            else {
                MrPlagueWarperClient.toggle();
            }
        });
    }

    private static void onCycleSight(MinecraftClient minecraftClient, ClientPlayNetworkHandler clientPlayNetworkHandler, PacketByteBuf packetByteBuf, PacketSender packetSender) {
        boolean bufReversed = packetByteBuf.readBoolean();
        boolean bufLimited = packetByteBuf.readBoolean();
        minecraftClient.execute(() -> {
            if (bufLimited == false) {
                if (bufReversed == false) {
                    MrPlagueWarperClient.cycleForward();
                }
                else {
                    MrPlagueWarperClient.cycleBackward();
                }
            }
            else {
                MrPlagueWarperClient.cycleLimited();
            }
        });
    }

    private static void onWarpPoint(MinecraftClient minecraftClient, ClientPlayNetworkHandler clientPlayNetworkHandler, PacketByteBuf packetByteBuf, PacketSender packetSender) {
        int bufInt = packetByteBuf.readInt();
        minecraftClient.execute(() -> {
            PacketByteBuf bufIndex = PacketByteBufs.create();
            double multX;
            double multZ;
            if (MrPlagueWarperClient.cameraEntity != null) {
                if (bufInt == 1) {
                    MrPlagueWarperClient.warp1_x = MrPlagueWarperClient.cameraEntity.getBlockPos().getX() + (int)MrPlagueWarperClient.cameraEntity.getMultX();
                    MrPlagueWarperClient.warp1_y = MrPlagueWarperClient.cameraEntity.getBlockPos().getY();
                    MrPlagueWarperClient.warp1_z = MrPlagueWarperClient.cameraEntity.getBlockPos().getZ() + (int)MrPlagueWarperClient.cameraEntity.getMultZ();
                    MrPlagueWarperClient.warp1_yaw = MrPlagueWarperClient.cameraEntity.getYaw(0);
                    MrPlagueWarperClient.warp1_pitch = MrPlagueWarperClient.cameraEntity.getPitch(0);
                    if (MrPlagueWarperClient.viewed_dim == 1) {
                        MrPlagueWarperClient.warp1_dim = 1;
                    }
                    else if (MrPlagueWarperClient.viewed_dim == 2) {
                        MrPlagueWarperClient.warp1_dim = 2;
                    }
                    else {
                        MrPlagueWarperClient.warp1_dim = 3;
                    }
                    minecraftClient.player.sendMessage(Text.of("Warp point 1 set"), true);
                }
                else if (bufInt == 2) {
                    MrPlagueWarperClient.warp2_x = MrPlagueWarperClient.cameraEntity.getBlockPos().getX() + (int)MrPlagueWarperClient.cameraEntity.getMultX();
                    MrPlagueWarperClient.warp2_y = MrPlagueWarperClient.cameraEntity.getBlockPos().getY();
                    MrPlagueWarperClient.warp2_z = MrPlagueWarperClient.cameraEntity.getBlockPos().getZ() + (int)MrPlagueWarperClient.cameraEntity.getMultZ();
                    MrPlagueWarperClient.warp2_yaw = MrPlagueWarperClient.cameraEntity.getYaw(0);
                    MrPlagueWarperClient.warp2_pitch = MrPlagueWarperClient.cameraEntity.getPitch(0);
                    if (MrPlagueWarperClient.viewed_dim == 1) {
                        MrPlagueWarperClient.warp2_dim = 1;
                    }
                    else if (MrPlagueWarperClient.viewed_dim == 2) {
                        MrPlagueWarperClient.warp2_dim = 2;
                    }
                    else {
                        MrPlagueWarperClient.warp2_dim = 3;
                    }
                    minecraftClient.player.sendMessage(Text.of("Warp point 2 set"), true);
                }
                else if (bufInt == 3) {
                    MrPlagueWarperClient.warp3_x = MrPlagueWarperClient.cameraEntity.getBlockPos().getX() + (int)MrPlagueWarperClient.cameraEntity.getMultX();
                    MrPlagueWarperClient.warp3_y = MrPlagueWarperClient.cameraEntity.getBlockPos().getY();
                    MrPlagueWarperClient.warp3_z = MrPlagueWarperClient.cameraEntity.getBlockPos().getZ() + (int)MrPlagueWarperClient.cameraEntity.getMultZ();
                    MrPlagueWarperClient.warp3_yaw = MrPlagueWarperClient.cameraEntity.getYaw(0);
                    MrPlagueWarperClient.warp3_pitch = MrPlagueWarperClient.cameraEntity.getPitch(0);
                    if (MrPlagueWarperClient.viewed_dim == 1) {
                        MrPlagueWarperClient.warp3_dim = 1;
                    }
                    else if (MrPlagueWarperClient.viewed_dim == 2) {
                        MrPlagueWarperClient.warp3_dim = 2;
                    }
                    else {
                        MrPlagueWarperClient.warp3_dim = 3;
                    }
                    minecraftClient.player.sendMessage(Text.of("Warp point 3 set"), true);
                }
                else if (bufInt == 4) {
                    MrPlagueWarperClient.warp4_x = MrPlagueWarperClient.cameraEntity.getBlockPos().getX() + (int)MrPlagueWarperClient.cameraEntity.getMultX();
                    MrPlagueWarperClient.warp4_y = MrPlagueWarperClient.cameraEntity.getBlockPos().getY();
                    MrPlagueWarperClient.warp4_z = MrPlagueWarperClient.cameraEntity.getBlockPos().getZ() + (int)MrPlagueWarperClient.cameraEntity.getMultZ();
                    MrPlagueWarperClient.warp4_yaw = MrPlagueWarperClient.cameraEntity.getYaw(0);
                    MrPlagueWarperClient.warp4_pitch = MrPlagueWarperClient.cameraEntity.getPitch(0);
                    if (MrPlagueWarperClient.viewed_dim == 1) {
                        MrPlagueWarperClient.warp4_dim = 1;
                    }
                    else if (MrPlagueWarperClient.viewed_dim == 2) {
                        MrPlagueWarperClient.warp4_dim = 2;
                    }
                    else {
                        MrPlagueWarperClient.warp4_dim = 3;
                    }
                    minecraftClient.player.sendMessage(Text.of("Warp point 4 set"), true);
                }
                else if (bufInt == 5) {
                    MrPlagueWarperClient.warp5_x = MrPlagueWarperClient.cameraEntity.getBlockPos().getX() + (int)MrPlagueWarperClient.cameraEntity.getMultX();
                    MrPlagueWarperClient.warp5_y = MrPlagueWarperClient.cameraEntity.getBlockPos().getY();
                    MrPlagueWarperClient.warp5_z = MrPlagueWarperClient.cameraEntity.getBlockPos().getZ() + (int)MrPlagueWarperClient.cameraEntity.getMultZ();
                    MrPlagueWarperClient.warp5_yaw = MrPlagueWarperClient.cameraEntity.getYaw(0);
                    MrPlagueWarperClient.warp5_pitch = MrPlagueWarperClient.cameraEntity.getPitch(0);
                    if (MrPlagueWarperClient.viewed_dim == 1) {
                        MrPlagueWarperClient.warp5_dim = 1;
                    }
                    else if (MrPlagueWarperClient.viewed_dim == 2) {
                        MrPlagueWarperClient.warp5_dim = 2;
                    }
                    else {
                        MrPlagueWarperClient.warp5_dim = 3;
                    }
                    minecraftClient.player.sendMessage(Text.of("Warp point 5 set"), true);
                }
                else if (bufInt == 6) {
                    MrPlagueWarperClient.warp6_x = MrPlagueWarperClient.cameraEntity.getBlockPos().getX() + (int)MrPlagueWarperClient.cameraEntity.getMultX();
                    MrPlagueWarperClient.warp6_y = MrPlagueWarperClient.cameraEntity.getBlockPos().getY();
                    MrPlagueWarperClient.warp6_z = MrPlagueWarperClient.cameraEntity.getBlockPos().getZ() + (int)MrPlagueWarperClient.cameraEntity.getMultZ();
                    MrPlagueWarperClient.warp6_yaw = MrPlagueWarperClient.cameraEntity.getYaw(0);
                    MrPlagueWarperClient.warp6_pitch = MrPlagueWarperClient.cameraEntity.getPitch(0);
                    if (MrPlagueWarperClient.viewed_dim == 1) {
                        MrPlagueWarperClient.warp6_dim = 1;
                    }
                    else if (MrPlagueWarperClient.viewed_dim == 2) {
                        MrPlagueWarperClient.warp6_dim = 2;
                    }
                    else {
                        MrPlagueWarperClient.warp6_dim = 3;
                    }
                    minecraftClient.player.sendMessage(Text.of("Warp point 6 set"), true);
                }
                MrPlagueWarperClient.cameraEntity.playSound(SoundEvents.BLOCK_BEACON_POWER_SELECT, 1, 1.25f);
            }
            else {
                if (bufInt == 1 && MrPlagueWarperClient.warp1_dim != 0) {
                    if (MrPlagueWarperClient.warp1_dim == 2) {
                        multX = (minecraftClient.player.world.getRegistryKey().getValue().equals(DimensionType.THE_NETHER_ID)) ? MrPlagueWarperClient.warp1_x : MrPlagueWarperClient.warp1_x * 8;
                        multZ = (minecraftClient.player.world.getRegistryKey().getValue().equals(DimensionType.THE_NETHER_ID)) ? MrPlagueWarperClient.warp1_z : MrPlagueWarperClient.warp1_z * 8;
                    }
                    else {
                        multX = (minecraftClient.player.world.getRegistryKey().getValue().equals(DimensionType.THE_NETHER_ID) ? MrPlagueWarperClient.warp1_x / 8 : MrPlagueWarperClient.warp1_x);
                        multZ = (minecraftClient.player.world.getRegistryKey().getValue().equals(DimensionType.THE_NETHER_ID) ? MrPlagueWarperClient.warp1_z / 8 : MrPlagueWarperClient.warp1_z);
                    }
                    if (!(minecraftClient.player.getX() >= multX + 900 || minecraftClient.player.getZ() >= multZ + 900 || minecraftClient.player.getX() <= multX - 900 || minecraftClient.player.getZ() <= multZ - 900)) {
                        bufIndex.writeInt(MrPlagueWarperClient.warp1_dim);
                        bufIndex.writeBlockPos(new BlockPos(MrPlagueWarperClient.warp1_x, MrPlagueWarperClient.warp1_y, MrPlagueWarperClient.warp1_z));
                        bufIndex.writeFloat(MrPlagueWarperClient.warp1_yaw);
                        bufIndex.writeFloat(MrPlagueWarperClient.warp1_pitch);
                    }
                    else {
                        bufIndex = null;
                    }
                }
                else if (bufInt == 2 && MrPlagueWarperClient.warp2_dim != 0) {
                    if (MrPlagueWarperClient.warp2_dim == 2) {
                        multX = (minecraftClient.player.world.getRegistryKey().getValue().equals(DimensionType.THE_NETHER_ID)) ? MrPlagueWarperClient.warp2_x : MrPlagueWarperClient.warp2_x * 8;
                        multZ = (minecraftClient.player.world.getRegistryKey().getValue().equals(DimensionType.THE_NETHER_ID)) ? MrPlagueWarperClient.warp2_z : MrPlagueWarperClient.warp2_z * 8;
                    }
                    else {
                        multX = (minecraftClient.player.world.getRegistryKey().getValue().equals(DimensionType.THE_NETHER_ID) ? MrPlagueWarperClient.warp2_x / 8 : MrPlagueWarperClient.warp2_x);
                        multZ = (minecraftClient.player.world.getRegistryKey().getValue().equals(DimensionType.THE_NETHER_ID) ? MrPlagueWarperClient.warp2_z / 8 : MrPlagueWarperClient.warp2_z);
                    }
                    if (!(minecraftClient.player.getX() >= multX + 900 || minecraftClient.player.getZ() >= multZ + 900 || minecraftClient.player.getX() <= multX - 900 || minecraftClient.player.getZ() <= multZ - 900)) {
                        bufIndex.writeInt(MrPlagueWarperClient.warp2_dim);
                        bufIndex.writeBlockPos(new BlockPos(MrPlagueWarperClient.warp2_x, MrPlagueWarperClient.warp2_y, MrPlagueWarperClient.warp2_z));
                        bufIndex.writeFloat(MrPlagueWarperClient.warp2_yaw);
                        bufIndex.writeFloat(MrPlagueWarperClient.warp2_pitch);
                    }
                    else {
                        bufIndex = null;
                    }
                }
                else if (bufInt == 3 && MrPlagueWarperClient.warp3_dim != 0) {
                    if (MrPlagueWarperClient.warp3_dim == 2) {
                        multX = (minecraftClient.player.world.getRegistryKey().getValue().equals(DimensionType.THE_NETHER_ID)) ? MrPlagueWarperClient.warp3_x : MrPlagueWarperClient.warp3_x * 8;
                        multZ = (minecraftClient.player.world.getRegistryKey().getValue().equals(DimensionType.THE_NETHER_ID)) ? MrPlagueWarperClient.warp3_z : MrPlagueWarperClient.warp3_z * 8;
                    }
                    else {
                        multX = (minecraftClient.player.world.getRegistryKey().getValue().equals(DimensionType.THE_NETHER_ID) ? MrPlagueWarperClient.warp3_x / 8 : MrPlagueWarperClient.warp3_x);
                        multZ = (minecraftClient.player.world.getRegistryKey().getValue().equals(DimensionType.THE_NETHER_ID) ? MrPlagueWarperClient.warp3_z / 8 : MrPlagueWarperClient.warp3_z);
                    }
                    if (!(minecraftClient.player.getX() >= multX + 900 || minecraftClient.player.getZ() >= multZ + 900 || minecraftClient.player.getX() <= multX - 900 || minecraftClient.player.getZ() <= multZ - 900)) {
                        bufIndex.writeInt(MrPlagueWarperClient.warp3_dim);
                        bufIndex.writeBlockPos(new BlockPos(MrPlagueWarperClient.warp3_x, MrPlagueWarperClient.warp3_y, MrPlagueWarperClient.warp3_z));
                        bufIndex.writeFloat(MrPlagueWarperClient.warp3_yaw);
                        bufIndex.writeFloat(MrPlagueWarperClient.warp3_pitch);
                    }
                    else {
                        bufIndex = null;
                    }
                }
                else if (bufInt == 4 && MrPlagueWarperClient.warp4_dim != 0) {
                    if (MrPlagueWarperClient.warp4_dim == 2) {
                        multX = (minecraftClient.player.world.getRegistryKey().getValue().equals(DimensionType.THE_NETHER_ID)) ? MrPlagueWarperClient.warp4_x : MrPlagueWarperClient.warp4_x * 8;
                        multZ = (minecraftClient.player.world.getRegistryKey().getValue().equals(DimensionType.THE_NETHER_ID)) ? MrPlagueWarperClient.warp4_z : MrPlagueWarperClient.warp4_z * 8;
                    }
                    else {
                        multX = (minecraftClient.player.world.getRegistryKey().getValue().equals(DimensionType.THE_NETHER_ID) ? MrPlagueWarperClient.warp4_x / 8 : MrPlagueWarperClient.warp4_x);
                        multZ = (minecraftClient.player.world.getRegistryKey().getValue().equals(DimensionType.THE_NETHER_ID) ? MrPlagueWarperClient.warp4_z / 8 : MrPlagueWarperClient.warp4_z);
                    }
                    if (!(minecraftClient.player.getX() >= multX + 900 || minecraftClient.player.getZ() >= multZ + 900 || minecraftClient.player.getX() <= multX - 900 || minecraftClient.player.getZ() <= multZ - 900)) {
                        bufIndex.writeInt(MrPlagueWarperClient.warp4_dim);
                        bufIndex.writeBlockPos(new BlockPos(MrPlagueWarperClient.warp4_x, MrPlagueWarperClient.warp4_y, MrPlagueWarperClient.warp4_z));
                        bufIndex.writeFloat(MrPlagueWarperClient.warp4_yaw);
                        bufIndex.writeFloat(MrPlagueWarperClient.warp4_pitch);
                    }
                    else {
                        bufIndex = null;
                    }
                }
                else if (bufInt == 5 && MrPlagueWarperClient.warp5_dim != 0) {
                    if (MrPlagueWarperClient.warp5_dim == 2) {
                        multX = (minecraftClient.player.world.getRegistryKey().getValue().equals(DimensionType.THE_NETHER_ID)) ? MrPlagueWarperClient.warp5_x : MrPlagueWarperClient.warp5_x * 8;
                        multZ = (minecraftClient.player.world.getRegistryKey().getValue().equals(DimensionType.THE_NETHER_ID)) ? MrPlagueWarperClient.warp5_z : MrPlagueWarperClient.warp5_z * 8;
                    }
                    else {
                        multX = (minecraftClient.player.world.getRegistryKey().getValue().equals(DimensionType.THE_NETHER_ID) ? MrPlagueWarperClient.warp5_x / 8 : MrPlagueWarperClient.warp5_x);
                        multZ = (minecraftClient.player.world.getRegistryKey().getValue().equals(DimensionType.THE_NETHER_ID) ? MrPlagueWarperClient.warp5_z / 8 : MrPlagueWarperClient.warp5_z);
                    }
                    if (!(minecraftClient.player.getX() >= multX + 900 || minecraftClient.player.getZ() >= multZ + 900 || minecraftClient.player.getX() <= multX - 900 || minecraftClient.player.getZ() <= multZ - 900)) {
                        bufIndex.writeInt(MrPlagueWarperClient.warp5_dim);
                        bufIndex.writeBlockPos(new BlockPos(MrPlagueWarperClient.warp5_x, MrPlagueWarperClient.warp5_y, MrPlagueWarperClient.warp5_z));
                        bufIndex.writeFloat(MrPlagueWarperClient.warp5_yaw);
                        bufIndex.writeFloat(MrPlagueWarperClient.warp5_pitch);
                    }
                    else {
                        bufIndex = null;
                    }
                }
                else if (bufInt == 6 && MrPlagueWarperClient.warp6_dim != 0) {
                    if (MrPlagueWarperClient.warp6_dim == 2) {
                        multX = (minecraftClient.player.world.getRegistryKey().getValue().equals(DimensionType.THE_NETHER_ID)) ? MrPlagueWarperClient.warp6_x : MrPlagueWarperClient.warp6_x * 8;
                        multZ = (minecraftClient.player.world.getRegistryKey().getValue().equals(DimensionType.THE_NETHER_ID)) ? MrPlagueWarperClient.warp6_z : MrPlagueWarperClient.warp6_z * 8;
                    }
                    else {
                        multX = (minecraftClient.player.world.getRegistryKey().getValue().equals(DimensionType.THE_NETHER_ID) ? MrPlagueWarperClient.warp6_x / 8 : MrPlagueWarperClient.warp6_x);
                        multZ = (minecraftClient.player.world.getRegistryKey().getValue().equals(DimensionType.THE_NETHER_ID) ? MrPlagueWarperClient.warp6_z / 8 : MrPlagueWarperClient.warp6_z);
                    }
                    if (!(minecraftClient.player.getX() >= multX + 900 || minecraftClient.player.getZ() >= multZ + 900 || minecraftClient.player.getX() <= multX - 900 || minecraftClient.player.getZ() <= multZ - 900)) {
                        bufIndex.writeInt(MrPlagueWarperClient.warp6_dim);
                        bufIndex.writeBlockPos(new BlockPos(MrPlagueWarperClient.warp6_x, MrPlagueWarperClient.warp6_y, MrPlagueWarperClient.warp6_z));
                        bufIndex.writeFloat(MrPlagueWarperClient.warp6_yaw);
                        bufIndex.writeFloat(MrPlagueWarperClient.warp6_pitch);
                    }
                    else {
                        bufIndex = null;
                    }
                }
                else {
                    bufIndex = null;
                }
                if (bufIndex != null) {
                    ClientPlayNetworking.send(ModPackets.WARP_TELEPORT, bufIndex);
                }
                else {
                    minecraftClient.player.sendMessage(Text.of("Warp point is out of range or unset"), true);
                    minecraftClient.player.playSound(MrPlagueWarper.WARPER_REORIENT, 1, 1);
                }
            }
        });
    }
}
