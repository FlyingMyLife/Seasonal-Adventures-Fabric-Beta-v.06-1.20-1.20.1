package net.packages.seasonal_adventures.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import java.util.List;

public class LaptopBlock extends HorizontalFacingBlock {
    public static final BooleanProperty OPEN = BooleanProperty.of("open");
    public static final BooleanProperty OFF = BooleanProperty.of("off");
    private static final VoxelShape COLLISION_SHAPE = VoxelShapes.cuboid(0.5 - 7 / 16.0, 0.0, 0.5 - 7 / 16.0, 0.5 + 7 / 16.0, 4.0 / 16.0, 0.5 + 7 / 16.0);

    public LaptopBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(OPEN, false).with(OFF, true).with(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
        return null;
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("tooltip.seasonal_adventures.without_functional.detailed").formatted(Formatting.GRAY));
        } else {
            tooltip.add(Text.translatable("tooltip.seasonal_adventures.without_functional.hint").formatted(Formatting.RED));
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(OPEN, OFF, FACING);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
                /*
        if (player instanceof AnimatedPlayer){
            var animationContainer = ((AnimatedPlayer) (player)).seasonalAdventuresGetModAnimation();

            KeyframeAnimation anim = PlayerAnimationRegistry.getAnimation(Identifier.of(SeasonalAdventures.MOD_ID, "guiding_skinth_activation"));

            var builder = anim.mutableCopy();
            anim = builder.build();
            animationContainer.setAnimation(new KeyframeAnimationPlayer(anim));
        }
        */
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

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return COLLISION_SHAPE;
    }
    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.down()).isOpaque();
    }
}
