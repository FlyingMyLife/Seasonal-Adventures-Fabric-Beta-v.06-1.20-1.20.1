package net.packages.flying_machines.entity.client;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.packages.flying_machines.entity.custom.ATMEntity;
import net.packages.flying_machines.flying_machines;

public class ATMRenderer extends LivingEntityRenderer<ATMEntity, ATMModel<ATMEntity>> {
    public ATMRenderer(EntityRendererFactory.Context context) {
        super(context, new ATMModel<>(context.getPart(ModelLayers.ATM)), 0.75f);
    }
    private static final Identifier TEXTURE = new Identifier(flying_machines.MOD_ID, "textures/entity/atm.png");
    @Override
    public Identifier getTexture(ATMEntity entity) {
        return TEXTURE;
    }

}
