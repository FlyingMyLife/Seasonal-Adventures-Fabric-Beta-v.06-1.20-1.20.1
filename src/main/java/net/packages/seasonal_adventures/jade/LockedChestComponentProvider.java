package net.packages.seasonal_adventures.jade;
import net.minecraft.block.Block;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.packages.seasonal_adventures.SeasonalAdventures;
import net.packages.seasonal_adventures.block.Blocks;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public enum LockedChestComponentProvider implements
        IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    INSTANCE;
    private static final Map<Block, Integer> BlockToLevel = new HashMap<>();
    private static final Text[] LevelText = {Text.translatable("tooltip.seasonal_adventures.jade.locked_chest.level.beginner").formatted(Formatting.ITALIC, Formatting.GRAY), Text.translatable("tooltip.seasonal_adventures.jade.locked_chest.level.knowledgeable").formatted(Formatting.ITALIC, Formatting.YELLOW), Text.translatable("tooltip.seasonal_adventures.jade.locked_chest.level.experienced").formatted(Formatting.ITALIC, Formatting.AQUA), Text.translatable("tooltip.seasonal_adventures.jade.locked_chest.level.skilled").formatted(Formatting.ITALIC, Formatting.LIGHT_PURPLE), Text.translatable("tooltip.seasonal_adventures.jade.locked_chest.level.expert").formatted(Formatting.ITALIC, Formatting.DARK_PURPLE)};
    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        int lockLevel = blockAccessor.getServerData().getInt("lockLevel");
        Text level = LevelText[lockLevel];
        iTooltip.add(Text.empty());
        iTooltip.add(Text.translatable("tooltip.seasonal_adventures.jade.locked_chest.level").formatted(Formatting.BOLD, Formatting.WHITE));
        iTooltip.append(level);
        iTooltip.add(Text.empty());
    }

    @Override
    public void appendServerData(NbtCompound nbtCompound, BlockAccessor blockAccessor) {
        initializeMap();
        Integer level = BlockToLevel.get(blockAccessor.getBlock());
        assert level != null;
        nbtCompound.putInt("lockLevel", level);
    }
    @Override
    public Identifier getUid() {
        return new Identifier(SeasonalAdventures.MOD_ID, "locked_chest_component_provider");
    }

    private void initializeMap(){
        BlockToLevel.put(Blocks.LOCKED_CHEST_LVL_COPPER, 0);
        BlockToLevel.put(Blocks.LOCKED_CHEST_LVL_IRON, 1);
        BlockToLevel.put(Blocks.LOCKED_CHEST_LVL_GOLD, 2);
        BlockToLevel.put(Blocks.LOCKED_CHEST_LVL_DIAMOND, 3);
        BlockToLevel.put(Blocks.LOCKED_CHEST_LVL_NETHERITE, 4);
    }
}