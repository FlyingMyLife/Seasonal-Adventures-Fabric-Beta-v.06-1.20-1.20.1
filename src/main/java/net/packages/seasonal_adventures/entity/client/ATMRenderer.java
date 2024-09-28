package net.packages.seasonal_adventures.entity.client;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.util.Identifier;
import net.packages.seasonal_adventures.SeasonalAdventures;
import net.packages.seasonal_adventures.entity.custom.ATMEntity;

public class ATMRenderer extends LivingEntityRenderer<ATMEntity, ATMModel<ATMEntity>> {
    public ATMRenderer(EntityRendererFactory.Context context) {
        super(context, new ATMModel<>(context.getPart(ModelLayers.ATM)), 0.75f);
    }
    private static final Identifier TEXTURE = new Identifier(SeasonalAdventures.MOD_ID, "textures/entity/atm.png");
    @Override
    public Identifier getTexture(ATMEntity entity) {
        return TEXTURE;
    }

}
