
package io.github.mrplague.action.entity;


import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.mrplague.MrPlagueWarper;
import io.github.mrplague.networking.ModPackets;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;

public class RenderBlocks {
    public static void action(SerializableData.Instance data, Entity entity) {
        if (!(entity instanceof ServerPlayerEntity serverPlayerEntity)) return;
        ServerPlayNetworking.send((ServerPlayerEntity)entity, ModPackets.RENDER_BLOCKS, PacketByteBufs.empty());
    }

    public static ActionFactory<Entity> getFactory() {
        return new ActionFactory<>(MrPlagueWarper.identifier("render_blocks"),
                new SerializableData(),
                RenderBlocks::action
        );
    }
}