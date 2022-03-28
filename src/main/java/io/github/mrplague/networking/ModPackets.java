package io.github.mrplague.networking;

import io.github.mrplague.MrPlagueWarper;
import net.minecraft.util.Identifier;

public class ModPackets {
    public static final Identifier SEND_BLOCKPOS = MrPlagueWarper.identifier("send_blockpos");
    public static final Identifier SEND_PLAYERPOS = MrPlagueWarper.identifier("send_playerpos");
    public static final Identifier SEND_POS = MrPlagueWarper.identifier("send_pos");
    public static final Identifier WARP_POINT = MrPlagueWarper.identifier("warp_point");
    public static final Identifier WARP_TELEPORT = MrPlagueWarper.identifier("warp_teleport");
    public static final Identifier INDEXED_SOUND = MrPlagueWarper.identifier("indexed_sound");
    public static final Identifier TOGGLE_SIGHT = MrPlagueWarper.identifier("toggle_sight");
    public static final Identifier CYCLE_SIGHT = MrPlagueWarper.identifier("cycle_sight");
}