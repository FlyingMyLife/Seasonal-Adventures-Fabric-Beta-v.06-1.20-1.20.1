package net.packages.seasonal_adventures.api.jade.provider.block;
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


public enum LockedChestComponentProvider implements
        IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    INSTANCE;
    private static final Block [] levels = {Blocks.LOCKED_CHEST_LVL_COPPER, Blocks.LOCKED_CHEST_LVL_IRON,
            Blocks.LOCKED_CHEST_LVL_GOLD, Blocks.LOCKED_CHEST_LVL_DIAMOND, Blocks.LOCKED_CHEST_LVL_NETHERITE};
    private static final Text [] LevelText = {Text.translatable("tooltip.seasonal_adventures.jade.locked_chest.level.beginner").formatted(Formatting.ITALIC, Formatting.GRAY), Text.translatable("tooltip.seasonal_adventures.jade.locked_chest.level.knowledgeable").formatted(Formatting.ITALIC, Formatting.YELLOW), Text.translatable("tooltip.seasonal_adventures.jade.locked_chest.level.experienced").formatted(Formatting.ITALIC, Formatting.AQUA), Text.translatable("tooltip.seasonal_adventures.jade.locked_chest.level.skilled").formatted(Formatting.ITALIC, Formatting.LIGHT_PURPLE), Text.translatable("tooltip.seasonal_adventures.jade.locked_chest.level.expert").formatted(Formatting.ITALIC, Formatting.DARK_PURPLE)};
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
        int level = 0;
        for (int i = 0; i < levels.length - 1; i++) {
            if (blockAccessor.getBlock().equals(levels[i])) {
                level = i;
            }
        }
        nbtCompound.putInt("lockLevel", level);
    }
    @Override
    public Identifier getUid() {
        return new Identifier(SeasonalAdventures.MOD_ID, "locked_chest_component_provider");
    }

}