package net.packages.seasonal_adventures.item.custom;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.packages.seasonal_adventures.network.ItemRemovalPacket;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AtmItem extends Item {

    private final EntityType<? extends LivingEntity> entityType;

    public AtmItem(Settings settings, EntityType<? extends LivingEntity> entityType) {
        super(settings);
        this.entityType = entityType;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.seasonal_adventures.atm_tooltip").formatted(Formatting.DARK_RED));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        PlayerEntity player = context.getPlayer();
        BlockPos pos = context.getBlockPos();
        Direction direction = context.getSide();
        if (world.isClient) {
            return ActionResult.SUCCESS;
        } else {
            BlockPos spawnPos = pos.offset(direction);
            spawnEntityAt(world, player, spawnPos);
            if(!player.isCreative()) {
                ItemRemovalPacket.ItemStackRemove();
            }
            return ActionResult.CONSUME;
        }
    }
    private void spawnEntityAt(World world, PlayerEntity player, BlockPos pos) {
        LivingEntity entity = entityType.create(world);
        if (entity != null) {
            entity.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
            float[] angles = getEntityRotationToPlayer(entity, player);
            entity.setYaw(angles[0]);
            entity.headYaw = angles[0];
            entity.setPitch(angles[1]);
            world.spawnEntity(entity);
        }
    }
    private float[] getEntityRotationToPlayer(LivingEntity entity, PlayerEntity player) {
        double deltaX = player.getX() - entity.getX();
        double deltaZ = player.getZ() - entity.getZ();
        double distance = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
        float yaw = (float) (MathHelper.atan2(deltaZ, deltaX) * (180 / Math.PI)) - 90.0F;
        float pitch = (float) (-(MathHelper.atan2(player.getY() - (entity.getY() + entity.getStandingEyeHeight()), distance) * (180 / Math.PI)));
        yaw = Math.round(yaw / 45.0F) * 45.0F;
        if (yaw < 0) {
            yaw += 360.0F;
        }
        return new float[]{yaw, pitch};
    }
}
