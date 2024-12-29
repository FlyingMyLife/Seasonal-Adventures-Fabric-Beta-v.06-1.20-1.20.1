package net.packages.seasonal_adventures.entity.custom;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.packages.seasonal_adventures.entity.ai.DylanAttackGoal;
import net.packages.seasonal_adventures.gui.handler.DylanScreenHandler;
import net.packages.seasonal_adventures.sound.Sounds;
import org.jetbrains.annotations.Nullable;

import static java.lang.Boolean.TRUE;

public class DylanEntity extends AnimalEntity {

    private static final TrackedData<Boolean> ATTACKING = DataTracker.registerData(DylanEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> IMMUNE_TO_DAMAGE = DataTracker.registerData(DylanEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> BROKEN_TEXTURE = DataTracker.registerData(DylanEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public final AnimationState attackAnimationState = new AnimationState();
    public int attackAnimationTimeout = 0;
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    public int relationship_level;
    public DylanEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }
    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.age);
        } else {
            --this.idleAnimationTimeout;
        }
        if(this.isAttacking() && attackAnimationTimeout <= 0) {
            attackAnimationTimeout = 40;
            attackAnimationState.start(this.age);
        } else{
            --this.attackAnimationTimeout;
        }
        if (!this.isAttacking()) {
            attackAnimationState.stop();
        }
    }
    @Override
    protected void updateLimbs(float posDelta) {
        float f = this.getPose() == EntityPose.STANDING ? Math.min(posDelta * 6.0f, 1.0f) : 0.0f;
        this.limbAnimator.updateLimbs(f, 0.1f);
    }

    @Override
    public void tick() {
        super.tick();
        this.getBrokenTexture = this.dataTracker.get(BROKEN_TEXTURE);
        if(this.getWorld().isClient()) {
            setupAnimationStates();
        }
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new DylanAttackGoal(this, 1d, true));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1D));
        this.goalSelector.add(2, new LookAtEntityGoal(this, PlayerEntity.class, 6f));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.targetSelector.add(1, new RevengeGoal(this));
    }

    public static DefaultAttributeContainer.Builder createDylanAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 56)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2f)
                .add(EntityAttributes.GENERIC_ARMOR, 1.8f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 18)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 4);
    }    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ATTACKING, false);
        this.dataTracker.startTracking(BROKEN_TEXTURE, false);
        this.dataTracker.startTracking(IMMUNE_TO_DAMAGE, false);
    }
    @Override
    public boolean damage(DamageSource source, float amount) {
        if (!this.getWorld().isClient && this.isAlive() && !this.isDead()) {
            if (this.getHealth() <= 16 && !this.dataTracker.get(IMMUNE_TO_DAMAGE)) {
                this.dataTracker.set(IMMUNE_TO_DAMAGE, true);
                this.setHealth(1.0f);
                this.setAiDisabled(true);
                this.dataTracker.set(BROKEN_TEXTURE, TRUE);
            }
            if (this.dataTracker.get(IMMUNE_TO_DAMAGE)) {
                return false;
            }
        }
        return super.damage(source, amount);
    }
    public void setImmuneToDamage(boolean immune) {
        this.dataTracker.set(IMMUNE_TO_DAMAGE, immune);
    }
    public boolean isImmuneToDamage() {
        return this.dataTracker.get(IMMUNE_TO_DAMAGE);
    }
    public void setAttacking(boolean attacking) {
        this.dataTracker.set(ATTACKING, attacking);
    }
    public boolean getBrokenTexture;
    @Override
    public boolean isAttacking() {
        return this.dataTracker.get(ATTACKING);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return Sounds.DYLAN_HURT_SOUND;
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("ImmuneToDamage", this.isImmuneToDamage());
        nbt.putBoolean("isBroken", this.dataTracker.get(BROKEN_TEXTURE));
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setImmuneToDamage(nbt.getBoolean("ImmuneToDamage"));
        this.dataTracker.set(BROKEN_TEXTURE, nbt.getBoolean("isBroken"));
    }
    @Override
    public boolean isBaby() {
        return false;
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (!this.getWorld().isClient) {
            NamedScreenHandlerFactory screenHandlerFactory = new NamedScreenHandlerFactory() {
                @Override
                public Text getDisplayName() {
                    return Text.translatable("screen.custom_entity");
                }

                @Nullable
                @Override
                public DylanScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
                    return new DylanScreenHandler(syncId, inv);
                }
            };
            player.openHandledScreen(screenHandlerFactory);
        }
        return ActionResult.SUCCESS;
    }
}
