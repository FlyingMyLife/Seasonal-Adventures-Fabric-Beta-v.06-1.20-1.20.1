package net.flyingmylife.seasonal_adventures.block.custom;

import com.mojang.serialization.MapCodec;
import net.flyingmylife.seasonal_adventures.SA;
import net.flyingmylife.seasonal_adventures.block.SABlocks;
import net.flyingmylife.seasonal_adventures.block.entity.DuckerControlSystemBlockEntity;
import net.flyingmylife.seasonal_adventures.gui.handler.DuckerScreenHandler;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class DuckerControlSystemBlock extends BlockWithEntity {
    public static final MapCodec<DuckerControlSystemBlock> CODEC = createCodec(DuckerControlSystemBlock::new);
    public static final EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;
    public static final BooleanProperty PARENT = BooleanProperty.of("parent");
    private static final VoxelShape VOXEL_SHAPE = Stream.of(
            Block.createCuboidShape(0, 0, 0, 32, 16, 16),
            Block.createCuboidShape(0, 16, 0, 11, 32, 16),
            Block.createCuboidShape(11, 16, 10, 32, 32, 16),
            Block.createCuboidShape(-12, 0, -0.25, 0, 17, 13.25),
            Block.createCuboidShape(15, 23, 6, 27, 29, 10)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();

    public DuckerControlSystemBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        builder.add(PARENT);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient) {
            if (world.getBlockEntity(pos) instanceof DuckerControlSystemBlockEntity blockEntity) {
                NamedScreenHandlerFactory factory = blockEntity.createScreenHandlerFactory((ServerWorld) world, pos);
                if (factory != null) {
                    player.openHandledScreen(factory);
                }
            } else if (SA.DEV_ENVIRONMENT) {
                SA.LOGGER.warn("Block entity of block:{}, not found", this);
            }
        }

        return ActionResult.SUCCESS;
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SABlocks.Utilities.rotateShape(Direction.NORTH, state.get(FACING), VOXEL_SHAPE);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new DuckerControlSystemBlockEntity(pos, state, pos);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
