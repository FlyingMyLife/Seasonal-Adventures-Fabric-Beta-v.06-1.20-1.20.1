package dev.flyingmylife.seasonal_adventures.sound;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import dev.flyingmylife.seasonal_adventures.SA;

public class SASounds {
    public static final SoundEvent DYLAN_HURT_SOUND = registerSoundEvent("dylan_hurt_sound");
    public static final SoundEvent PICK_PIN_SOUND = registerSoundEvent("pick_pin");
    public static final SoundEvent LOCKPICK_UNLOCK_SOUND = registerSoundEvent("lockpick_unlock");

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = Identifier.of(SA.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }
}
