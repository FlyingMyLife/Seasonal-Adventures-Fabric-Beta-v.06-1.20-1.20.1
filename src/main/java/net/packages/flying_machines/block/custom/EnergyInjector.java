package net.packages.flying_machines.block.custom;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.packages.flying_machines.block.entity.AnimatedBlockEntity;
import net.packages.flying_machines.block.entity.EnergyInjectorBlockEntity;
import org.jetbrains.annotations.Nullable;

public class EnergyInjector extends BlockWithEntity {
    public EnergyInjector(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new EnergyInjectorBlockEntity(pos, state);
    }
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }
}
