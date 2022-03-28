package io.github.mrplague.networking;

import io.github.mrplague.MrPlagueWarper;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Blocks;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

import java.util.List;

public class ModPacketsC2S {
    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(ModPackets.SEND_BLOCKPOS, ModPacketsC2S::onGetBlockPos);
        ServerPlayNetworking.registerGlobalReceiver(ModPackets.SEND_PLAYERPOS, ModPacketsC2S::onGetPlayerPos);
        ServerPlayNetworking.registerGlobalReceiver(ModPackets.WARP_TELEPORT, ModPacketsC2S::onWarpTeleport);
    }

    private static void onGetBlockPos(MinecraftServer minecraftServer, ServerPlayerEntity playerEntity, ServerPlayNetworkHandler serverPlayNetworkHandler, PacketByteBuf packetByteBuf, PacketSender packetSender) {
        BlockPos bufPos = packetByteBuf.readBlockPos();
        int bufInt = packetByteBuf.readInt();
        minecraftServer.execute(() -> {
            PacketByteBuf bufSolid = PacketByteBufs.create();
            bufSolid.writeBlockPos(bufPos);
            bufSolid.writeInt(1);
            PacketByteBuf bufFluid = PacketByteBufs.create();
            bufFluid.writeBlockPos(bufPos);
            bufFluid.writeInt(2);
            RegistryKey<World> worldRegistryKey;
            if (bufInt == 1) {
                worldRegistryKey = World.OVERWORLD;
            }
            else if (bufInt == 2) {
                worldRegistryKey = World.NETHER;
            }
            else {
                worldRegistryKey = World.END;
            }
            if (!(minecraftServer.getWorld(worldRegistryKey).getBlockState(bufPos).getBlock() == Blocks.AIR.getDefaultState().getBlock()) && !(minecraftServer.getWorld(worldRegistryKey).getBlockState(bufPos).getBlock() == Blocks.CAVE_AIR.getDefaultState().getBlock()) && !(minecraftServer.getWorld(worldRegistryKey).getBlockState(bufPos).getBlock() == Blocks.VOID_AIR.getDefaultState().getBlock()) && !(minecraftServer.getWorld(worldRegistryKey).getBlockState(bufPos).getBlock() == Blocks.WATER.getDefaultState().getBlock()) && !(minecraftServer.getWorld(worldRegistryKey).getBlockState(bufPos).getBlock() == Blocks.LAVA.getDefaultState().getBlock())) {
                ServerPlayNetworking.send(playerEntity, ModPackets.SEND_POS, bufSolid);
            }
            if (minecraftServer.getWorld(worldRegistryKey).getBlockState(bufPos).getBlock() == Blocks.WATER.getDefaultState().getBlock() || minecraftServer.getWorld(worldRegistryKey).getBlockState(bufPos).getBlock() == Blocks.LAVA.getDefaultState().getBlock()) {
                ServerPlayNetworking.send(playerEntity, ModPackets.SEND_POS, bufFluid);
            }
        });
    }

    private static void onGetPlayerPos(MinecraftServer minecraftServer, ServerPlayerEntity playerEntity, ServerPlayNetworkHandler serverPlayNetworkHandler, PacketByteBuf packetByteBuf, PacketSender packetSender) {
        int bufInt = packetByteBuf.readInt();
        minecraftServer.execute(() -> {
            RegistryKey<World> worldRegistryKey;
            if (bufInt == 1) {
                worldRegistryKey = World.OVERWORLD;
            }
            else if (bufInt == 2) {
                worldRegistryKey = World.NETHER;
            }
            else {
                worldRegistryKey = World.END;
            }
            List<ServerPlayerEntity> playerEntityList = minecraftServer.getWorld(worldRegistryKey).getPlayers();
            for (ServerPlayerEntity serverPlayerEntity : playerEntityList) {
                PacketByteBuf bufPlayer = PacketByteBufs.create();
                bufPlayer.writeBlockPos(serverPlayerEntity.getBlockPos());
                bufPlayer.writeInt(3);
                ServerPlayNetworking.send(playerEntity, ModPackets.SEND_POS, bufPlayer);
            }
        });
    }

    private static void onWarpTeleport(MinecraftServer minecraftServer, ServerPlayerEntity playerEntity, ServerPlayNetworkHandler serverPlayNetworkHandler, PacketByteBuf packetByteBuf, PacketSender packetSender) {
        int bufDim = packetByteBuf.readInt();
        BlockPos bufPos = packetByteBuf.readBlockPos();
        float bufYaw = packetByteBuf.readFloat();
        float bufPitch = packetByteBuf.readFloat();
        minecraftServer.execute(() -> {
            RegistryKey<World> worldRegistryKey;
            if (bufDim == 1) {
                worldRegistryKey = World.OVERWORLD;
            }
            else if (bufDim == 2) {
                worldRegistryKey = World.NETHER;
            }
            else {
                worldRegistryKey = World.END;
            }
            playerEntity.teleport(minecraftServer.getWorld(worldRegistryKey), bufPos.getX() + 0.5, bufPos.getY() + 0.5, bufPos.getZ() + 0.5, bufYaw, bufPitch);
            playerEntity.addStatusEffect(new StatusEffectInstance(MrPlagueWarper.IMMINENCE, 60));
            playerEntity.addStatusEffect(new StatusEffectInstance(MrPlagueWarper.OVERLOADING, 60));
        });
    }
}
