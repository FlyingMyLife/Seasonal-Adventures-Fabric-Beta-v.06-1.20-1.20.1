package net.packages.flying_machines.block.custom;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class LaptopBlock extends HorizontalFacingBlock {
    public static final BooleanProperty OPEN = BooleanProperty.of("open");
    public static final BooleanProperty OFF = BooleanProperty.of("off");
    private static final VoxelShape COLLISION_SHAPE = VoxelShapes.cuboid(0.5 - 7 / 16.0, 0.0, 0.5 - 7 / 16.0, 0.5 + 7 / 16.0, 4.0 / 16.0, 0.5 + 7 / 16.0);

    public LaptopBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(OPEN, false).with(OFF, true).with(FACING, Direction.NORTH));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(OPEN, OFF, FACING);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction playerFacing = ctx.getPlayerLookDirection().getOpposite();
        if (playerFacing.getAxis().isHorizontal()) {
            return this.getDefaultState().with(FACING, playerFacing);
        } else {
            return this.getDefaultState().with(FACING, Direction.NORTH);
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (player.isSneaking()) {
            if (state.get(OPEN)) {
                world.setBlockState(pos, state.with(OFF, !state.get(OFF)));
                return ActionResult.SUCCESS;
            }
        } else {
            boolean newOpenState = !state.get(OPEN);
            world.setBlockState(pos, state.with(OPEN, newOpenState).with(OFF, true));
            return ActionResult.SUCCESS;
        }
        return ActionResult.FAIL;
    }


    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    public static VoxelShape getCollisionShape() {
        return COLLISION_SHAPE;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return COLLISION_SHAPE;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.down()).isOpaque();
    }
}
