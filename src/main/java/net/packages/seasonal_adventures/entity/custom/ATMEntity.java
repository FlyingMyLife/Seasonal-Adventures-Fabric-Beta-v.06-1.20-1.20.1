package net.packages.seasonal_adventures.entity.custom;

import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.packages.seasonal_adventures.gui.handler.ATMScreenHandler;
import net.packages.seasonal_adventures.item.SAItems;
import net.packages.seasonal_adventures.network.s2c.ConfigSyncPacket;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;

public class ATMEntity extends LivingEntity {
    private boolean isAtmBreakable(PlayerEntity player) {
        AtomicBoolean isAtmBreakable = new AtomicBoolean();
        ConfigSyncPacket.getConfigFromServerAsync(config -> {
            if (config != null) {
                isAtmBreakable.set(config.atmBreakable);
            }
        });
        if (isAtmBreakable.get()) {
            return true;
        } else {
            return player.hasPermissionLevel(4);
        }
    }
    public ATMEntity(EntityType<? extends LivingEntity> type, World world) {
        super(type, world);
    }
    public static DefaultAttributeContainer createATMAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(EntityAttributes.MAX_HEALTH, 0.0)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.0D)
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
    public boolean damage(ServerWorld world, DamageSource source, float amount) {
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
        if (player instanceof ServerPlayerEntity) {
            if (player.isSneaking() && !player.isCreative() && isAtmBreakable(player)) {
                this.setHealth(0);
                this.setRemoved(RemovalReason.KILLED);
                dropStack((ServerWorld) this.getWorld(), new ItemStack(SAItems.ATM));
                return ActionResult.SUCCESS;
            } else if (player.isSneaking() && player.isCreative() && isAtmBreakable(player)) {
                this.setHealth(0);
                this.setRemoved(RemovalReason.KILLED);
                return ActionResult.SUCCESS;
            }
            if (!player.isSneaking()) {
                player.openHandledScreen(new NamedScreenHandlerFactory() {
                    @Override
                    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
                        return new ATMScreenHandler(syncId, inv);
                    }

                    @Override
                    public Text getDisplayName() {
                        return Text.empty();
                    }
                });
            } else {
                return ActionResult.FAIL;
            }
    }
        return ActionResult.FAIL;
    }
}
