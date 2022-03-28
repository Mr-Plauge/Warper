
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

public class InterdimensionalSightToggle {
    public static void action(SerializableData.Instance data, Entity entity) {
        if (!(entity instanceof ServerPlayerEntity serverPlayerEntity)) return;
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBoolean(data.getBoolean("disable"));
        ServerPlayNetworking.send((ServerPlayerEntity)entity, ModPackets.TOGGLE_SIGHT, buf);
    }

    public static ActionFactory<Entity> getFactory() {
        return new ActionFactory<>(MrPlagueWarper.identifier("interdimensional_sight_toggle"),
                new SerializableData()
                .add("disable", SerializableDataTypes.BOOLEAN, false),
                InterdimensionalSightToggle::action
        );
    }
}