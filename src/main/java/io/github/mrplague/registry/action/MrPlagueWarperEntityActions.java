package io.github.mrplague.registry.action;

import io.github.apace100.origins.power.factory.action.ActionFactory;
import io.github.apace100.origins.registry.ModRegistries;
import io.github.mrplague.action.entity.*;
import net.minecraft.entity.*;
import net.minecraft.util.registry.Registry;

public class MrPlagueWarperEntityActions {
	public static void register() {
		register(IndexedSound.getFactory());
		register(InterdimensionalSightCycle.getFactory());
		register(InterdimensionalSightToggle.getFactory());
		register(WarpPoint.getFactory());
	}

	private static void register(ActionFactory<Entity> actionFactory) {
		Registry.register(ModRegistries.ENTITY_ACTION, actionFactory.getSerializerId(), actionFactory);
	}
}