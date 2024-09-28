package net.packages.seasonal_adventures.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class AdvancedAutomatonBodyBlock extends Block {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final BooleanProperty UPPER = BooleanProperty.of("upper");
    private static final VoxelShape BOTTOM_COLLISION_SHAPE_NORTH_SOUTH = VoxelShapes.union(
            VoxelShapes.cuboid(0.5 - 4 / 16.0, -1 / 16.0, 0.5 - 2 / 16.0, 0.5 + 4 / 16.0, 10 / 16.0, 0.5 + 2 / 16.0),
            VoxelShapes.cuboid(0.5 - 8 / 16.0, 10 / 16.0, 0.5 - 2 / 16.0, 0.5 + 8 / 16.0, 1.0, 0.5 + 2 / 16.0)
    );
    private static final VoxelShape UPPER_COLLISION_SHAPE_NORTH_SOUTH = VoxelShapes.union(
            VoxelShapes.cuboid(0.5 - 8 / 16.0, -1 / 16.0, 0.5 - 2 / 16.0, 0.5 + 8 / 16.0, 6 / 16.0, 0.5 + 2 / 16.0),
            VoxelShapes.cuboid(0.5 - 4.5 / 16.0, 6 / 16.0, 0.5 - 4.5 / 16.0, 0.5 + 4.5 / 16.0, 14.5 / 16.0, 0.5 + 4.5 / 16.0)
    );
    private static final VoxelShape UPPER_COLLISION_SHAPE_EAST_WEST = VoxelShapes.union(
            VoxelShapes.cuboid(0.5 - 2 / 16.0, -1 / 16.0, 0.5 - 8 / 16.0, 0.5 + 2 / 16.0, 6 / 16.0, 0.5 + 8 / 16.0),
            VoxelShapes.cuboid(0.5 - 4.5 / 16.0, 6 / 16.0, 0.5 - 4.5 / 16.0, 0.5 + 4.5 / 16.0, 14.5 / 16.0, 0.5 + 4.5 / 16.0)
    );
    private static final VoxelShape BOTTOM_COLLISION_SHAPE_EAST_WEST = VoxelShapes.union(
            VoxelShapes.cuboid(0.5 - 2 / 16.0, -1 / 16.0, 0.5 - 4 / 16.0, 0.5 + 2 / 16.0, 10 / 16.0, 0.5 + 4 / 16.0),
            VoxelShapes.cuboid(0.5 - 2 / 16.0, 10 / 16.0, 0.5 - 8 / 16.0, 0.5 + 2 / 16.0, 1.0, 0.5 + 8 / 16.0)
    );

    public AdvancedAutomatonBodyBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(UPPER, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, UPPER);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        World world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();
        Direction facing = ctx.getPlayerLookDirection().getOpposite();

        if (pos.getY() < world.getHeight() - 1 && world.getBlockState(pos.up()).canReplace(ctx)) {
            if (!facing.getAxis().isVertical()) {
                return this.getDefaultState().with(FACING, facing).with(UPPER, false);
            } else {
                return this.getDefaultState().with(FACING, Direction.NORTH).with(UPPER, false);
            }
        }

        return null;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        world.setBlockState(pos.up(), this.getDefaultState().with(FACING, state.get(FACING)).with(UPPER, true), 3);
    }

    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        BlockPos otherPos = state.get(UPPER) ? pos.down() : pos.up();
        BlockState otherState = world.getBlockState(otherPos);

        if (otherState.isOf(this)) {
            world.setBlockState(otherPos, Blocks.AIR.getDefaultState(), 35);
            world.syncWorldEvent(2001, otherPos, Block.getRawIdFromState(otherState));
        }

        super.onBroken(world, pos, state);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (!state.get(UPPER)) {
            if (state.get(FACING).equals(Direction.NORTH) || state.get(FACING).equals(Direction.SOUTH)) {
                return BOTTOM_COLLISION_SHAPE_NORTH_SOUTH;
            } else {
                return BOTTOM_COLLISION_SHAPE_EAST_WEST;
            }
        } else {
            if (state.get(FACING).equals(Direction.NORTH) || state.get(FACING).equals(Direction.SOUTH)) {
                return UPPER_COLLISION_SHAPE_NORTH_SOUTH;
            } else {
                return UPPER_COLLISION_SHAPE_EAST_WEST;
            }
        }
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (!state.get(UPPER)) {
            if (state.get(FACING).equals(Direction.NORTH) || state.get(FACING).equals(Direction.SOUTH)) {
                return BOTTOM_COLLISION_SHAPE_NORTH_SOUTH;
            } else {
                return BOTTOM_COLLISION_SHAPE_EAST_WEST;
            }
        } else {
            if (state.get(FACING).equals(Direction.NORTH) || state.get(FACING).equals(Direction.SOUTH)) {
                return UPPER_COLLISION_SHAPE_NORTH_SOUTH;
            } else {
                return UPPER_COLLISION_SHAPE_EAST_WEST;
            }
        }
    }
}
