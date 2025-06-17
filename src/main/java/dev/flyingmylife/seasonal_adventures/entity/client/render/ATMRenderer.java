package dev.flyingmylife.seasonal_adventures.entity.client.render;

import dev.flyingmylife.seasonal_adventures.SA;
import dev.flyingmylife.seasonal_adventures.entity.client.model.ATMModel;
import dev.flyingmylife.seasonal_adventures.entity.client.layer.SARenderLayers;
import dev.flyingmylife.seasonal_adventures.entity.client.render.render_state.ATMRenderState;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.util.Identifier;
import dev.flyingmylife.seasonal_adventures.entity.custom.ATMEntity;

public class ATMRenderer extends LivingEntityRenderer<ATMEntity, ATMRenderState, ATMModel<ATMEntity>> {

    private static final Identifier TEXTURE = Identifier.of(SA.MOD_ID, "textures/entity/atm.png");

    public ATMRenderer(EntityRendererFactory.Context context) {
        super(context, new ATMModel<>(context.getPart(SARenderLayers.ATM_LAYER)), 0.5f);
    }

    @Override
    public ATMRenderState createRenderState() {
        return new ATMRenderState();
    }
    @Override
    public Identifier getTexture(ATMRenderState state) {
        return TEXTURE;
    }
}
