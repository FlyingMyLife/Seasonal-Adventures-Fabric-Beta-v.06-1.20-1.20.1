package net.packages.seasonal_adventures.entity.client;

import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.packages.seasonal_adventures.SeasonalAdventures;

public class ModelLayers {
    public static final EntityModelLayer DYLAN =
            new EntityModelLayer(new Identifier(SeasonalAdventures.MOD_ID, "dylan"), "main");
    public static final EntityModelLayer ATM =
            new EntityModelLayer(new Identifier(SeasonalAdventures.MOD_ID, "atm"), "main");
}
