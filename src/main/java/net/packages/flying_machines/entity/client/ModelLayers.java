package net.packages.flying_machines.entity.client;

import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.packages.flying_machines.flying_machines;

public class ModelLayers {
    public static final EntityModelLayer DYLAN =
            new EntityModelLayer(new Identifier(flying_machines.MOD_ID, "dylan"), "main");
    public static final EntityModelLayer ATM =
            new EntityModelLayer(new Identifier(flying_machines.MOD_ID, "atm"), "main");
}
