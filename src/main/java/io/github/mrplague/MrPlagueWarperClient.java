package io.github.mrplague;

import io.github.mrplague.networking.ModPacketsS2C;
import io.github.mrplague.particle.WarperAtom;
import io.github.mrplague.particle.WarperAtomReverse;
import io.github.mrplague.particle.WarperParticle;
import io.github.mrplague.util.CameraEntity;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.world.dimension.DimensionTypes;

public class MrPlagueWarperClient implements ClientModInitializer {
    public static final MinecraftClient MC = MinecraftClient.getInstance();
    public static CameraEntity cameraEntity;
    public static double x, y, z, warp1_x, warp1_y, warp1_z, warp2_x, warp2_y, warp2_z, warp3_x, warp3_y, warp3_z, warp4_x, warp4_y, warp4_z, warp5_x, warp5_y, warp5_z, warp6_x, warp6_y, warp6_z;
    public static float warp1_yaw, warp1_pitch, warp2_yaw, warp2_pitch, warp3_yaw, warp3_pitch, warp4_yaw, warp4_pitch, warp5_yaw, warp5_pitch, warp6_yaw, warp6_pitch;
    public static int viewed_dim, warp1_dim, warp2_dim, warp3_dim, warp4_dim, warp5_dim, warp6_dim;
    public static boolean enabled = false;

    public static void toggle() {
        if (enabled) {
            onDisable();
        } else {
            onEnable();
        }
        enabled = !enabled;
    }
    public static void disable() {
        if (enabled) {
            onDisable();
            enabled = false;
        }
    }

    private static void onEnable() {
        MC.chunkCullingEnabled = false;
        x = MC.player.getX();
        y = MC.player.getY();
        z = MC.player.getZ();
        if (MC.player.world.getRegistryKey().getValue().equals(DimensionTypes.OVERWORLD_ID)) {
            viewed_dim = 1;
            MC.player.sendMessage(Text.of("Viewing Overworld"), true);
        }
        else if (MC.player.world.getRegistryKey().getValue().equals(DimensionTypes.THE_NETHER_ID)) {
            viewed_dim = 2;
            MC.player.sendMessage(Text.of("Viewing Nether"), true);
        }
        else {
            viewed_dim = 3;
            MC.player.sendMessage(Text.of("Viewing End"), true);
        }
        if (cameraEntity == null) {
            cameraEntity = new CameraEntity();
            cameraEntity.spawn();
            MC.setCameraEntity(cameraEntity);
        }
        MC.worldRenderer.reload();
        cameraEntity.playSound(SoundEvents.ENTITY_ENDER_EYE_DEATH, 1, 1.25f);
    }

    private static void onDisable() {
        MC.chunkCullingEnabled = true;
        MC.player.input = new KeyboardInput(MC.options);
        MC.setCameraEntity(MC.player);
        if (cameraEntity != null) {
            cameraEntity.despawn();
            cameraEntity = null;
        }
        MC.worldRenderer.reload();
        MC.player.playSound(SoundEvents.ENTITY_ENDER_EYE_DEATH, 1, 1f);
    }

    public static void cycleForward() {
        if (enabled) {
            if (viewed_dim == 1) {
                viewed_dim = 2;
                MC.player.sendMessage(Text.of("Viewing Nether"), true);
            }
            else if (viewed_dim == 2) {
                viewed_dim = 3;
                MC.player.sendMessage(Text.of("Viewing End"), true);
            }
            else if (viewed_dim == 3) {
                viewed_dim = 1;
                MC.player.sendMessage(Text.of("Viewing Overworld"), true);
            }
            cameraEntity.playSound(SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, 1, 1.25f);
        }
    }

    public static void cycleBackward() {
        if (enabled) {
            if (viewed_dim == 1) {
                viewed_dim = 3;
                MC.player.sendMessage(Text.of("Viewing End"), true);
            }
            else if (viewed_dim == 2) {
                viewed_dim = 1;
                MC.player.sendMessage(Text.of("Viewing Overworld"), true);
            }
            else if (viewed_dim == 3) {
                viewed_dim = 2;
                MC.player.sendMessage(Text.of("Viewing Nether"), true);
            }
            cameraEntity.playSound(SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, 1, 1.25f);
        }
    }

    public static void cycleLimited() {
        if (enabled) {
            if (viewed_dim == 1) {
                viewed_dim = 2;
                MC.player.sendMessage(Text.of("Viewing Nether"), true);
            }
            else {
                viewed_dim = 1;
                MC.player.sendMessage(Text.of("Viewing Overworld"), true);
            }
            cameraEntity.playSound(SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, 1, 1.25f);
        }
    }

    @Override
    public void onInitializeClient() {
        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
            registry.register(MrPlagueWarper.identifier("particle/overworld_atom"));
            registry.register(MrPlagueWarper.identifier("particle/overworld_solid"));
            registry.register(MrPlagueWarper.identifier("particle/overworld_fluid"));
            registry.register(MrPlagueWarper.identifier("particle/overworld_player"));
            registry.register(MrPlagueWarper.identifier("particle/overworld_warp"));
            registry.register(MrPlagueWarper.identifier("particle/nether_atom"));
            registry.register(MrPlagueWarper.identifier("particle/nether_solid"));
            registry.register(MrPlagueWarper.identifier("particle/nether_fluid"));
            registry.register(MrPlagueWarper.identifier("particle/nether_player"));
            registry.register(MrPlagueWarper.identifier("particle/nether_warp"));
            registry.register(MrPlagueWarper.identifier("particle/end_atom"));
            registry.register(MrPlagueWarper.identifier("particle/end_solid"));
            registry.register(MrPlagueWarper.identifier("particle/end_fluid"));
            registry.register(MrPlagueWarper.identifier("particle/end_player"));
            registry.register(MrPlagueWarper.identifier("particle/end_warp"));
        }));

        ParticleFactoryRegistry.getInstance().register(MrPlagueWarper.OVERWORLD_ATOM, WarperAtom.Factory::new);
        ParticleFactoryRegistry.getInstance().register(MrPlagueWarper.OVERWORLD_ATOM_REVERSE, WarperAtomReverse.Factory::new);
        ParticleFactoryRegistry.getInstance().register(MrPlagueWarper.OVERWORLD_SOLID, WarperParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(MrPlagueWarper.OVERWORLD_FLUID, WarperParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(MrPlagueWarper.OVERWORLD_PLAYER, WarperParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(MrPlagueWarper.OVERWORLD_WARP, WarperParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(MrPlagueWarper.NETHER_ATOM, WarperAtom.Factory::new);
        ParticleFactoryRegistry.getInstance().register(MrPlagueWarper.NETHER_ATOM_REVERSE, WarperAtomReverse.Factory::new);
        ParticleFactoryRegistry.getInstance().register(MrPlagueWarper.NETHER_SOLID, WarperParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(MrPlagueWarper.NETHER_FLUID, WarperParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(MrPlagueWarper.NETHER_PLAYER, WarperParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(MrPlagueWarper.NETHER_WARP, WarperParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(MrPlagueWarper.END_ATOM, WarperAtom.Factory::new);
        ParticleFactoryRegistry.getInstance().register(MrPlagueWarper.END_ATOM_REVERSE, WarperAtomReverse.Factory::new);
        ParticleFactoryRegistry.getInstance().register(MrPlagueWarper.END_SOLID, WarperParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(MrPlagueWarper.END_FLUID, WarperParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(MrPlagueWarper.END_PLAYER, WarperParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(MrPlagueWarper.END_WARP, WarperParticle.Factory::new);

        ModPacketsS2C.register();
    }
}
