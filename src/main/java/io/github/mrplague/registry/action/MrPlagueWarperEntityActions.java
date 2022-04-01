package io.github.mrplague.registry.action;

import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.mrplague.action.entity.*;
import net.minecraft.entity.*;
import net.minecraft.util.registry.Registry;

public class MrPlagueWarperEntityActions {
	public static void register() {
		register(IndexedSound.getFactory());
		register(InterdimensionalSightCycle.getFactory());
		register(InterdimensionalSightToggle.getFactory());
		register(RenderBlocks.getFactory());
		register(WarpPoint.getFactory());
	}

	private static void register(ActionFactory<Entity> actionFactory) {
		Registry.register(ApoliRegistries.ENTITY_ACTION, actionFactory.getSerializerId(), actionFactory);
	}
}