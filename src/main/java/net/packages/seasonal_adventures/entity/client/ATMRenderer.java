package net.packages.seasonal_adventures.entity.client;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.util.Identifier;
import net.packages.seasonal_adventures.SeasonalAdventures;
import net.packages.seasonal_adventures.entity.custom.ATMEntity;

public class ATMRenderer extends LivingEntityRenderer<ATMEntity, LivingEntityRenderState, ATMModel<ATMEntity>> {

    private static final Identifier TEXTURE = Identifier.of(SeasonalAdventures.MOD_ID, "textures/entity/atm.png");

    public ATMRenderer(EntityRendererFactory.Context context) {
        super(context, new ATMModel<ATMEntity>(context.getPart(ATMModel.ATM)) , 0.75f);
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }
    @Override
    public Identifier getTexture(LivingEntityRenderState state) {
        return TEXTURE;
    }
}
