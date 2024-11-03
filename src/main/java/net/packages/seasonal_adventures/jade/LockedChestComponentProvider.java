package net.packages.seasonal_adventures.jade;
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

    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        Text level = Text.literal("UNKNOWN").formatted(Formatting.DARK_RED, Formatting.ITALIC);
        if (blockAccessor.getServerData().getInt("lockLevel") == 0) level = Text.translatable("tooltip.seasonal_adventures.jade.locked_chest.level.beginner").formatted(Formatting.ITALIC, Formatting.GRAY);
        if (blockAccessor.getServerData().getInt("lockLevel") == 1) level = Text.translatable("tooltip.seasonal_adventures.jade.locked_chest.level.knowledgeable").formatted(Formatting.ITALIC, Formatting.YELLOW);
        if (blockAccessor.getServerData().getInt("lockLevel") == 2) level = Text.translatable("tooltip.seasonal_adventures.jade.locked_chest.level.experienced").formatted(Formatting.ITALIC, Formatting.AQUA);
        if (blockAccessor.getServerData().getInt("lockLevel") == 3) level = Text.translatable("tooltip.seasonal_adventures.jade.locked_chest.level.skilled").formatted(Formatting.ITALIC, Formatting.LIGHT_PURPLE);
        if (blockAccessor.getServerData().getInt("lockLevel") == 4) level = Text.translatable("tooltip.seasonal_adventures.jade.locked_chest.level.expert").formatted(Formatting.ITALIC, Formatting.DARK_PURPLE);
        iTooltip.add(Text.empty());
        iTooltip.add(Text.translatable("tooltip.seasonal_adventures.jade.locked_chest.level").formatted(Formatting.BOLD, Formatting.WHITE));
        iTooltip.append(level);
        iTooltip.add(Text.empty());
    }

    @Override
    public void appendServerData(NbtCompound nbtCompound, BlockAccessor blockAccessor) {
        if (blockAccessor.getBlock().equals(Blocks.LOCKED_CHEST_LVL_COPPER)) nbtCompound.putInt("lockLevel", 0);
        if (blockAccessor.getBlock().equals(Blocks.LOCKED_CHEST_LVL_IRON)) nbtCompound.putInt("lockLevel", 1);
        if (blockAccessor.getBlock().equals(Blocks.LOCKED_CHEST_LVL_GOLD)) nbtCompound.putInt("lockLevel", 2);
        if (blockAccessor.getBlock().equals(Blocks.LOCKED_CHEST_LVL_DIAMOND)) nbtCompound.putInt("lockLevel", 3);
        if (blockAccessor.getBlock().equals(Blocks.LOCKED_CHEST_LVL_NETHERITE)) nbtCompound.putInt("lockLevel", 4);
    }

    @Override
    public Identifier getUid() {
        return new Identifier(SeasonalAdventures.MOD_ID, "locked_chest_component_provider");
    }
}