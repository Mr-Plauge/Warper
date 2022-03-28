package io.github.mrplague;

import io.github.mrplague.networking.ModPacketsC2S;
import io.github.mrplague.registry.action.MrPlagueWarperEntityActions;
import io.github.mrplague.status_effect.ImminenceStatusEffect;
import io.github.mrplague.status_effect.OverloadingStatusEffect;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class MrPlagueWarper implements ModInitializer {
	public static final String MODID = "mrplaguewarper";

	public static final SoundEvent WARPER_ARRIVE = new SoundEvent(identifier("warper.arrive"));
	public static final SoundEvent WARPER_CLEAVE = new SoundEvent(identifier("warper.cleave"));
	public static final SoundEvent WARPER_CRACKLE = new SoundEvent(identifier("warper.crackle"));
	public static final SoundEvent WARPER_HURT = new SoundEvent(identifier("warper.hurt"));
	public static final SoundEvent WARPER_IDLE = new SoundEvent(identifier("warper.idle"));
	public static final SoundEvent WARPER_KILLED = new SoundEvent(identifier("warper.killed"));
	public static final SoundEvent WARPER_RELOCATE = new SoundEvent(identifier("warper.relocate"));
	public static final SoundEvent WARPER_REORIENT = new SoundEvent(identifier("warper.reorient"));
	public static final SoundEvent WARPER_RETREAT = new SoundEvent(identifier("warper.retreat"));
	public static final SoundEvent WARPER_TRANSMIT = new SoundEvent(identifier("warper.transmit"));
	public static final SoundEvent WARPER_WARP = new SoundEvent(identifier("warper.warp"));

	public static final DefaultParticleType OVERWORLD_ATOM = FabricParticleTypes.simple();
	public static final DefaultParticleType OVERWORLD_ATOM_REVERSE = FabricParticleTypes.simple();
	public static final DefaultParticleType OVERWORLD_SOLID = FabricParticleTypes.simple();
	public static final DefaultParticleType OVERWORLD_FLUID = FabricParticleTypes.simple();
	public static final DefaultParticleType OVERWORLD_PLAYER = FabricParticleTypes.simple();
	public static final DefaultParticleType OVERWORLD_WARP = FabricParticleTypes.simple();
	public static final DefaultParticleType NETHER_ATOM = FabricParticleTypes.simple();
	public static final DefaultParticleType NETHER_ATOM_REVERSE = FabricParticleTypes.simple();
	public static final DefaultParticleType NETHER_SOLID = FabricParticleTypes.simple();
	public static final DefaultParticleType NETHER_FLUID = FabricParticleTypes.simple();
	public static final DefaultParticleType NETHER_PLAYER = FabricParticleTypes.simple();
	public static final DefaultParticleType NETHER_WARP = FabricParticleTypes.simple();
	public static final DefaultParticleType END_ATOM = FabricParticleTypes.simple();
	public static final DefaultParticleType END_ATOM_REVERSE = FabricParticleTypes.simple();
	public static final DefaultParticleType END_SOLID = FabricParticleTypes.simple();
	public static final DefaultParticleType END_FLUID = FabricParticleTypes.simple();
	public static final DefaultParticleType END_PLAYER = FabricParticleTypes.simple();
	public static final DefaultParticleType END_WARP = FabricParticleTypes.simple();

	public static final StatusEffect IMMINENCE = new ImminenceStatusEffect();
	public static final StatusEffect OVERLOADING = new OverloadingStatusEffect();

	public static Identifier identifier(String path) {
		return new Identifier(MODID, path);
	}

	@Override
	public void onInitialize() {
		MrPlagueWarperEntityActions.register();

		Registry.register(Registry.SOUND_EVENT, WARPER_ARRIVE.getId(), WARPER_ARRIVE);
		Registry.register(Registry.SOUND_EVENT, WARPER_CLEAVE.getId(), WARPER_CLEAVE);
		Registry.register(Registry.SOUND_EVENT, WARPER_CRACKLE.getId(), WARPER_CRACKLE);
		Registry.register(Registry.SOUND_EVENT, WARPER_HURT.getId(), WARPER_HURT);
		Registry.register(Registry.SOUND_EVENT, WARPER_IDLE.getId(), WARPER_IDLE);
		Registry.register(Registry.SOUND_EVENT, WARPER_KILLED.getId(), WARPER_KILLED);
		Registry.register(Registry.SOUND_EVENT, WARPER_RELOCATE.getId(), WARPER_RELOCATE);
		Registry.register(Registry.SOUND_EVENT, WARPER_REORIENT.getId(), WARPER_REORIENT);
		Registry.register(Registry.SOUND_EVENT, WARPER_RETREAT.getId(), WARPER_RETREAT);
		Registry.register(Registry.SOUND_EVENT, WARPER_TRANSMIT.getId(), WARPER_TRANSMIT);
		Registry.register(Registry.SOUND_EVENT, WARPER_WARP.getId(), WARPER_WARP);

		Registry.register(Registry.PARTICLE_TYPE, identifier("overworld_atom"), OVERWORLD_ATOM);
		Registry.register(Registry.PARTICLE_TYPE, identifier("overworld_atom_reverse"), OVERWORLD_ATOM_REVERSE);
		Registry.register(Registry.PARTICLE_TYPE, identifier("overworld_solid"), OVERWORLD_SOLID);
		Registry.register(Registry.PARTICLE_TYPE, identifier("overworld_fluid"), OVERWORLD_FLUID);
		Registry.register(Registry.PARTICLE_TYPE, identifier("overworld_player"), OVERWORLD_PLAYER);
		Registry.register(Registry.PARTICLE_TYPE, identifier("overworld_warp"), OVERWORLD_WARP);
		Registry.register(Registry.PARTICLE_TYPE, identifier("nether_atom"), NETHER_ATOM);
		Registry.register(Registry.PARTICLE_TYPE, identifier("nether_atom_reverse"), NETHER_ATOM_REVERSE);
		Registry.register(Registry.PARTICLE_TYPE, identifier("nether_solid"), NETHER_SOLID);
		Registry.register(Registry.PARTICLE_TYPE, identifier("nether_fluid"), NETHER_FLUID);
		Registry.register(Registry.PARTICLE_TYPE, identifier("nether_player"), NETHER_PLAYER);
		Registry.register(Registry.PARTICLE_TYPE, identifier("nether_warp"), NETHER_WARP);
		Registry.register(Registry.PARTICLE_TYPE, identifier("end_atom"), END_ATOM);
		Registry.register(Registry.PARTICLE_TYPE, identifier("end_atom_reverse"), END_ATOM_REVERSE);
		Registry.register(Registry.PARTICLE_TYPE, identifier("end_solid"), END_SOLID);
		Registry.register(Registry.PARTICLE_TYPE, identifier("end_fluid"), END_FLUID);
		Registry.register(Registry.PARTICLE_TYPE, identifier("end_player"), END_PLAYER);
		Registry.register(Registry.PARTICLE_TYPE, identifier("end_warp"), END_WARP);

		Registry.register(Registry.STATUS_EFFECT, identifier("imminence"), IMMINENCE);
		Registry.register(Registry.STATUS_EFFECT, identifier("overloading"), OVERLOADING);

		ModPacketsC2S.register();
	}
}
