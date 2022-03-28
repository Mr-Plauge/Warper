package io.github.mrplague.status_effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class OverloadingStatusEffect extends StatusEffect {
    public OverloadingStatusEffect() {
        super(StatusEffectCategory.HARMFUL, 0xFFFA8E);
    }
}
