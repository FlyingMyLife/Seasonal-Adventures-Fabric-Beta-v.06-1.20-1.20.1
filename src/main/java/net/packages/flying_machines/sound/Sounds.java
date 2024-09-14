package net.packages.flying_machines.sound;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.packages.flying_machines.flying_machines;

public class Sounds {
    public static final SoundEvent DYLAN_HURT_SOUND = registerSoundEvent("dylan_hurt_sound");
    public static final SoundEvent PICK_PIN_SOUND = registerSoundEvent("pick_pin");

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = new Identifier(flying_machines.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSounds() {
        flying_machines.LOGGER.info("Registering Sounds for " + flying_machines.MOD_ID);
    }
}
