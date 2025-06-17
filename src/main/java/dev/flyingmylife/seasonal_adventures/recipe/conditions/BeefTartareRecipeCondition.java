package dev.flyingmylife.seasonal_adventures.recipe.conditions;

import com.mojang.serialization.*;
import dev.flyingmylife.seasonal_adventures.SA;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;
import net.minecraft.util.Identifier;

public class BeefTartareRecipeCondition implements ResourceConditionType<ResourceCondition> {

    @Override
    public Identifier id() {
        return Identifier.of(SA.MOD_ID, "crafting_table/beef_tartare_condition");
    }

    @Override
    public MapCodec<ResourceCondition> codec() {
        return null;
    }

}
