package net.packages.seasonal_adventures.recipe;

import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.resource.conditions.v1.ConditionJsonProvider;
import net.minecraft.util.Identifier;
import net.packages.seasonal_adventures.SeasonalAdventures;

public class BeefTartareRecipeCondition implements ConditionJsonProvider {

    private final boolean condition;

    public BeefTartareRecipeCondition(boolean condition) {
        this.condition = condition;
    }

    @Override
    public Identifier getConditionId() {
        return new Identifier(SeasonalAdventures.MOD_ID, "crafting_table/beef_tartare_condition");
    }

    @Override
    public void writeParameters(JsonObject object) {
       toJson().addProperty("condition", condition);
    }


    public static boolean shouldLoad(JsonObject jsonObject) {
        return SeasonalAdventures.isCandlelightInstalled;
    }
}
