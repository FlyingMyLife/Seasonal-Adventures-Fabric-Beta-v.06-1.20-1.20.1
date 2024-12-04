package net.packages.seasonal_adventures.jade;
import net.minecraft.block.Block;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.packages.seasonal_adventures.SeasonalAdventures;
import net.packages.seasonal_adventures.block.Blocks;
import snownee.jade.api.*;
import snownee.jade.api.config.IPluginConfig;

import java.util.HashMap;
import java.util.Map;


public enum DylanEntityComponentProvider implements
        IEntityComponentProvider, IServerDataProvider<EntityAccessor> {
    INSTANCE;


    @Override
    public void appendTooltip(ITooltip iTooltip, EntityAccessor entityAccessor, IPluginConfig iPluginConfig) {
        iTooltip.add(Text.translatable("tooltip.seasonal_adventures.jade.automaton"));
    }

    @Override
    public void appendServerData(NbtCompound nbtCompound, EntityAccessor entityAccessor) {

    }

    @Override
    public Identifier getUid() {
        return new Identifier(SeasonalAdventures.MOD_ID, "dylan_entity_component_provider");
    }
}