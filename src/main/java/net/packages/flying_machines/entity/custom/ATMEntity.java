package net.packages.flying_machines.entity.custom;

import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.packages.flying_machines.events.PlayerCardHandler;
import net.packages.flying_machines.flying_machines;
import net.packages.flying_machines.gui.ATMScreenHandler;
import net.packages.flying_machines.item.Items;

import java.util.Collections;

public class ATMEntity extends LivingEntity {
    private boolean isAtmBreakable(PlayerEntity player) {
        if (getEntityWorld().getGameRules().getBoolean(flying_machines.IS_ATMS_BREAKABLE)) {
            return true;
        } else {
            if (player.hasPermissionLevel(4)) {
                return true;
            }else {
                return false;
            }
        }
    }
    public ATMEntity(EntityType<? extends LivingEntity> type, World world) {
        super(type, world);
    }
    public static DefaultAttributeContainer createATMAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 0.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.0D)
                .build();
    }


    @Override
    public void tick() {
        super.tick();
        if (!this.hasNoGravity()) {
            this.setVelocity(this.getVelocity().add(0.0, -0.02, 0.0));
        }
        this.move(MovementType.SELF, this.getVelocity());
    }
    @Override
    protected void pushAway(Entity entity) {
    }

    @Override
    public void takeKnockback(double strength, double x, double z) {
    }

    @Override
    public boolean isCollidable() {
        return true;
    }

    @Override
    public void pushAwayFrom(Entity entity) {
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        this.setHealth(0);
        this.setRemoved(RemovalReason.KILLED);
    }

    @Override
    public Arm getMainArm() {
        return null;
    }


    @Override
    public boolean damage(DamageSource source, float amount) {
        return false;
    }

    @Override
    public Iterable<ItemStack> getArmorItems() {
        return Collections.singleton(ItemStack.EMPTY);
    }


    @Override
    public ItemStack getEquippedStack(EquipmentSlot slot) {
        return ItemStack.EMPTY;
    }


    @Override
    public void equipStack(EquipmentSlot slot, ItemStack stack) {
    }

    @Override
    public boolean isBaby() {
        return false;
    }
    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        if (player instanceof ServerPlayerEntity serverPlayer) {
            if (player.isSneaking() && !player.isCreative() && isAtmBreakable(player)) {
                this.setHealth(0);
                this.setRemoved(RemovalReason.KILLED);
                dropItem(new ItemStack(Items.ATM, 1).getItem());
                return ActionResult.SUCCESS;
            } else if (player.isSneaking() && player.isCreative() && isAtmBreakable(player)) {
                this.setHealth(0);
                this.setRemoved(RemovalReason.KILLED);
                return ActionResult.SUCCESS;
            }
            if (!player.isSneaking()) {
                serverPlayer.openHandledScreen(new SimpleNamedScreenHandlerFactory(
                        (syncId, inventory, playerEntity) -> new ATMScreenHandler(syncId, inventory),
                            Text.of("ATM")));
                    return ActionResult.SUCCESS;
                } else {
                return ActionResult.FAIL;
        }
    }
        return ActionResult.FAIL;
    }
}
