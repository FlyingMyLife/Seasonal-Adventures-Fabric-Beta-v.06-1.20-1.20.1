package net.flyingmylife.seasonal_adventures.entity.client.render;

import net.flyingmylife.seasonal_adventures.SA;
import net.flyingmylife.seasonal_adventures.entity.client.model.ATMModel;
import net.flyingmylife.seasonal_adventures.entity.client.layer.SARenderLayers;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.util.Identifier;
import net.flyingmylife.seasonal_adventures.entity.custom.ATMEntity;

public class ATMRenderer extends LivingEntityRenderer<ATMEntity, ATMModel<ATMEntity>> {

    private static final Identifier TEXTURE = Identifier.of(SA.MOD_ID, "textures/entity/atm.png");

    public ATMRenderer(EntityRendererFactory.Context context) {
        super(context, new ATMModel<>(context.getPart(SARenderLayers.ATM_LAYER)), 0.5f);
    }

    @Override
    public Identifier getTexture(ATMEntity entity) {
        return TEXTURE;
    }
}
