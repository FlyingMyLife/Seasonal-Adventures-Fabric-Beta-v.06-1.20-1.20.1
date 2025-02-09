package net.packages.seasonal_adventures.entity.client;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.util.Identifier;
import net.packages.seasonal_adventures.SeasonalAdventures;
import net.packages.seasonal_adventures.entity.custom.ATMEntity;
import java.util.Optional;

public class ATMModel<T extends ATMEntity> extends EntityModel<LivingEntityRenderState> {
    public static final EntityModelLayer ATM =
            new EntityModelLayer(Identifier.of(SeasonalAdventures.MOD_ID, "atm"), "main");
    public final ModelPart atm;

    protected ATMModel(ModelPart atm) {
        super(atm);
        this.atm = atm;
    }


    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData atm = modelPartData.addChild("atm", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, -14.0F, -7.0F, 16.0F, 14.0F, 15.0F, new Dilation(0.0F))
                .uv(0, 29).cuboid(-8.0F, -33.0F, -7.0F, 16.0F, 5.0F, 15.0F, new Dilation(0.0F))
                .uv(28, 48).cuboid(5.0F, -28.0F, -3.0F, 3.0F, 14.0F, 11.0F, new Dilation(0.0F))
                .uv(0, 48).cuboid(-8.0F, -28.0F, -3.0F, 3.0F, 14.0F, 11.0F, new Dilation(0.0F))
                .uv(59, 26).cuboid(-5.0F, -28.0F, 5.0F, 10.0F, 14.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        ModelPartData atmpart_r1 = atm.addChild("atmpart_r1", ModelPartBuilder.create().uv(56, 48).cuboid(0.0F, -15.0F, 7.0F, 10.0F, 15.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-5.0F, -15.0F, -9.0F, -0.2182F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }

    @Override
    public Optional<ModelPart> getPart(String name) {
        return Optional.ofNullable(atm);
    }
}