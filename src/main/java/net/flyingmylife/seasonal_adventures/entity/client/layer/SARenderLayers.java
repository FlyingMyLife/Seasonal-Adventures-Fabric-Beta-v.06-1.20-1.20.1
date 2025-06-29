package net.flyingmylife.seasonal_adventures.entity.client.layer;

import net.flyingmylife.seasonal_adventures.SA;
import net.flyingmylife.seasonal_adventures.entity.client.model.ATMModel;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class SARenderLayers {
    public static final EntityModelLayer ATM_LAYER = new EntityModelLayer(
            Identifier.of(SA.MOD_ID, "atm"), "main"
    );

    public static void registerLayers() {
        EntityModelLayerRegistry.registerModelLayer(ATM_LAYER, ATMModel::getTexturedModelData);
    }

}
