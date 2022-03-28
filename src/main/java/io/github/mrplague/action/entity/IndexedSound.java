
package io.github.mrplague.action.entity;


import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import io.github.mrplague.MrPlagueWarper;
import io.github.mrplague.networking.ModPackets;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public class IndexedSound {
    public static void action(SerializableData.Instance data, Entity entity) {
        if (!(entity instanceof ServerPlayerEntity serverPlayerEntity)) return;
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(data.getInt("index"));
        ServerPlayNetworking.send((ServerPlayerEntity)entity, ModPackets.INDEXED_SOUND, buf);
    }

    public static ActionFactory<Entity> getFactory() {
        return new ActionFactory<>(MrPlagueWarper.identifier("indexed_sound"),
                new SerializableData()
                .add("index", SerializableDataTypes.INT, 1),
                IndexedSound::action
        );
    }
}