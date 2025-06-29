package net.flyingmylife.seasonal_adventures.util.game;

import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;

public interface AnimatedPlayer {

    ModifierLayer<IAnimation> seasonalAdventuresGetModAnimation();
}