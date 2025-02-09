package net.packages.seasonal_adventures.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
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
import net.packages.seasonal_adventures.block.Blocks;
import net.packages.seasonal_adventures.block.entity.lockedChests.LockedChestLvLCopperBlockEntity;
import net.packages.seasonal_adventures.block.entity.lockedChests.LockedChestLvLIronBlockEntity;
import net.packages.seasonal_adventures.config.ConfigReader;
import net.packages.seasonal_adventures.gui.handler.LockpickScreenHandler;
import net.packages.seasonal_adventures.item.Items;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class LockedChestBlock extends BlockWithEntity {

    private final int lockLevel;

    public static final EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;

    public LockedChestBlock(Settings settings, int lockLevel) {
        super(settings);
        this.lockLevel = lockLevel;
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }
    public static final VoxelShape CHEST_SHAPE = VoxelShapes.cuboid(
            0.0625f,
            0f,
            0.0625f,
            0.9375f,
            0.875f,
            0.9375f
    );

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (Objects.requireNonNull(ConfigReader.readConfig()).betaFeatures){
            if (player.getInventory().getMainHandStack().isOf(Items.LOCKPICK)) {
                player.openHandledScreen(new NamedScreenHandlerFactory() {
                    @Override
                    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
                        return new LockpickScreenHandler(syncId, inv);
                    }

                    @Override
                    public Text getDisplayName() {
                        return Text.empty();
                    }
                });
                return ActionResult.SUCCESS;
            } else {
                player.sendMessage(Text.translatable("message.seasonal_adventures.lock.fail.lockpick_required").formatted(Formatting.DARK_RED), true);
                return ActionResult.FAIL;
            }
        } else {
            player.sendMessage(Text.translatable("message.seasonal_adventures.feature_disabled").formatted(Formatting.DARK_RED), true);
            return ActionResult.FAIL;
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(FACING);
    }
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        if (state.getBlock().equals(Blocks.LOCKED_CHEST_LVL_COPPER)) return new LockedChestLvLCopperBlockEntity(pos, state);
        if (state.getBlock().equals(Blocks.LOCKED_CHEST_LVL_IRON)) return new LockedChestLvLIronBlockEntity(pos, state);
        else return null;
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
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return CHEST_SHAPE;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return CHEST_SHAPE;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return null;
    }
}
