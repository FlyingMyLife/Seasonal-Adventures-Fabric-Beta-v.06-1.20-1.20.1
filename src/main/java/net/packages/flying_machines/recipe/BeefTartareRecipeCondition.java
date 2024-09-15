package net.packages.flying_machines.recipe;

import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.resource.conditions.v1.ConditionJsonProvider;
import net.minecraft.util.Identifier;
import net.packages.flying_machines.flying_machines; // Replace with your main class package

public class BeefTartareRecipeCondition implements ConditionJsonProvider {

    private final boolean condition;

    public BeefTartareRecipeCondition(boolean condition) {
        this.condition = condition;
    }

    @Override
    public Identifier getConditionId() {
        return new Identifier(flying_machines.MOD_ID, "custom_condition");
    }

    @Override
    public void writeParameters(JsonObject object) {
       toJson().addProperty("condition", condition);
    }


    public static boolean shouldLoad(JsonObject jsonObject) {
        return flying_machines.isCandlelightInstalled;
    }
}
