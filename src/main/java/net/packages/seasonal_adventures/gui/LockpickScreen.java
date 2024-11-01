package net.packages.seasonal_adventures.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import net.packages.seasonal_adventures.block.entity.LockedChestLvLCopperBlockEntity;
import net.packages.seasonal_adventures.gui.handlers.LockpickScreenHandler;
import net.packages.seasonal_adventures.gui.widgets.RotatableLockpick;
import net.packages.seasonal_adventures.item.Items;
import net.packages.seasonal_adventures.network.RestoreChestPacket;
import net.packages.seasonal_adventures.network.SpecificItemRemovalPacket;
import net.packages.seasonal_adventures.sound.Sounds;
import org.jetbrains.annotations.Nullable;

import java.util.Random;
public class LockpickScreen extends HandledScreen<LockpickScreenHandler> implements NamedScreenHandlerFactory {

    private int lockLevel;

    private int lockCount;

    private int [] lockAngles = new int[13];

    private boolean [] locks = new boolean[13];

    private float lockpickSpeed = 1.0f;

    private boolean methodCalled = false;

    private RotatableLockpick lockpick;
    
    private static final Identifier LOCKPICK_TEXTURE = new Identifier("seasonal_adventures", "textures/gui/lockpick.png");
    private static final Identifier PIN_UNACTIVATED = new Identifier("seasonal_adventures", "textures/gui/pin_unactivated.png");
    private static final Identifier PIN_ACTIVATED = new Identifier("seasonal_adventures", "textures/gui/pin_activated.png");
    private static final Identifier BACKGROUND_TEXTURE = new Identifier("seasonal_adventures", "textures/gui/lockpick_background.png");

    public LockpickScreen(LockpickScreenHandler handler, PlayerInventory inventory, Text title, int lockLevel) {
        super(handler, inventory, title);
        this.lockLevel = lockLevel;
        if (lockLevel == 0) this.lockpickSpeed = 5.0f;
        if (lockLevel == 1) this.lockpickSpeed = 12.2f;
        if (lockLevel == 2) this.lockpickSpeed = 18.4f;
        if (lockLevel == 3) this.lockpickSpeed = 26.6f;
        if (lockLevel == 4) this.lockpickSpeed = 35.8f;
    }

    @Override
    protected void init() {
        super.init();
        int buttonSize = 192;
        int x = this.width / 2 - buttonSize / 2;
        int y = this.height / 2 - buttonSize / 2;

        this.lockpick = new RotatableLockpick(
                x, y, buttonSize, buttonSize,
                0, 0, 0, LOCKPICK_TEXTURE, 192, 192,
                lockpickSpeed,
                button -> onClick()
        );

        this.onScreenOpen();
        this.addDrawableChild(this.lockpick);
    }
    private void onScreenOpen() {
        if (!methodCalled) {
            generateLockDegrees();
            if (lockLevel == 0)  this.lockCount = 5;
            if (lockLevel == 1)  this.lockCount = 7;
            if (lockLevel == 2)  this.lockCount = 8;
            if (lockLevel == 3)  this.lockCount = 10;
            if (lockLevel == 4)  this.lockCount = 12;
            methodCalled = true;
        }
    }

    private void onLockClick (int lock) {
        playSound(Sounds.PICK_PIN_SOUND, 0.9f);
        this.locks[lock] = true;
        this.lockpick.toggleRotationDirection();
        this.lockCount--;
    }
    private void playSound(SoundEvent sound, float pitch) {
        assert this.client != null;
        assert this.client.player != null;
        this.client.player.getWorld().playSound(
                this.client.player,
                this.client.player.getX(),
                this.client.player.getY(),
                this.client.player.getZ(),
                sound,
                SoundCategory.PLAYERS,
                1.0F,
                pitch
        );
    }
    private void onClick() {
        assert this.client.player != null;
        int currentAngle = (int) this.lockpick.getRotationAngle();
        if (currentAngle <= lockAngles[1]+10 && currentAngle >= lockAngles[1]-10 && !locks[1] && lockLevel <= 2) {
            onLockClick(1);
        } else if (currentAngle <= 10 && currentAngle >= 0 && !locks[1] && lockLevel >= 3) {
            onLockClick(1);
        } else if (currentAngle <= 360 && currentAngle >= 350 && !locks[1] && lockLevel >= 3) {
            onLockClick(1);
        } else if (currentAngle <= lockAngles[2]+10 && currentAngle >= lockAngles[2]-10 && !locks[2]) {
            onLockClick(2);
        } else if (currentAngle <= lockAngles[3]+10 && currentAngle >= lockAngles[3]-10 && !locks[3]) {
            onLockClick(3);
        } else if (currentAngle <= lockAngles[4]+10 && currentAngle >= lockAngles[4]-10 && !locks[4]) {
            onLockClick(4);
        } else if (currentAngle <= lockAngles[5]+10 && currentAngle >= lockAngles[5]-10 && !locks[5]) {
            onLockClick(5);
        } else if (currentAngle <= lockAngles[6]+10 && currentAngle >= lockAngles[6]-10 && !locks[6]) {
            if (lockLevel >= 1) {
                onLockClick(6);
            } else {
                SpecificItemRemovalPacket.removeItemStack(this.client.player, Items.LOCKPICK, 1);
            }
        } else if (currentAngle <= lockAngles[7]+10 && currentAngle >= lockAngles[7]-10 && !locks[7]) {
            if (lockLevel >= 1) {
                onLockClick(7);
            } else {
                SpecificItemRemovalPacket.removeItemStack(this.client.player, Items.LOCKPICK, 1);
            }
        } else if (currentAngle <= lockAngles[8]+10 && currentAngle >= lockAngles[8]-10 && !locks[8]) {
            if (lockLevel >= 2) {
                onLockClick(8);
            } else {
                SpecificItemRemovalPacket.removeItemStack(this.client.player, Items.LOCKPICK, 1);
            }
        } else if (currentAngle <= lockAngles[9]+10 && currentAngle >= lockAngles[9]-10 && !locks[9]) {
            if (lockLevel >= 3) {
                onLockClick(9);
            } else {
                SpecificItemRemovalPacket.removeItemStack(this.client.player, Items.LOCKPICK, 1);
            }
        } else if (currentAngle <= lockAngles[9]+10 && currentAngle >= lockAngles[9]-10 && !locks[10]) {
            if (lockLevel >= 3) {
                onLockClick(10);
            } else {
                SpecificItemRemovalPacket.removeItemStack(this.client.player, Items.LOCKPICK, 1);
            }
        } else if (currentAngle <= lockAngles[11]+10 && currentAngle >= lockAngles[11]-10 && !locks[11]) {
            if (lockLevel == 4) {
                onLockClick(11);
            } else {
                SpecificItemRemovalPacket.removeItemStack(this.client.player, Items.LOCKPICK, 1);
            }
        } else if (currentAngle <= lockAngles[12]+10 && currentAngle >= lockAngles[12]-10 && !locks[12]) {
            if (lockLevel == 4) {
                onLockClick(12);
            } else {
                SpecificItemRemovalPacket.removeItemStack(this.client.player, Items.LOCKPICK, 1);
            }
        } else {
            playSound(SoundEvents.ENTITY_ITEM_BREAK, 1.6f);
            SpecificItemRemovalPacket.removeItemStack(this.client.player, Items.LOCKPICK, 1);
        }
    }

    public void generateLockDegrees() {
        Random random = new Random();
        int[] locks = new int[12];
        int lockCount = 0;

        if (lockLevel <= 2) {
            int minSpacing = 30;

            locks[lockCount++] = generateFirstLock(random);

            int maxLocks = lockLevel == 0 ? 5 : lockLevel == 1 ? 7 : 8;

            while (lockCount < maxLocks) {
                locks[lockCount] = generateNextDegree(locks, lockCount, minSpacing, random);
                lockCount++;
            }

            assignLocks(locks, lockCount);

        } else if (lockLevel == 3) {
            int spacing = 36;
            for (int i = 0; i < 10; i++) {
                locks[i] = (i * spacing) % 360;
            }
            assignLocks(locks, 10);

        } else if (lockLevel == 4) {
            int spacing = 30;
            for (int i = 0; i < 12; i++) {
                locks[i] = (i * spacing) % 360;
            }
            assignLocks(locks, 12);
        }
    }

    private int generateFirstLock(Random random) {
        int firstLock;
        while (true) {
            firstLock = random.nextInt(360);
            if (!isNearProhibitedRange(firstLock)) {
                break;
            }
        }
        return firstLock;
    }

    private static int generateNextDegree(int[] locks, int lockCount, int minSpacing, Random random) {
        int newDegree;
        while (true) {
            newDegree = random.nextInt(360);
            boolean isValid = true;
            for (int i = 0; i < lockCount; i++) {
                int diff = Math.abs(newDegree - locks[i]);
                diff = Math.min(diff, 360 - diff);
                if (diff < minSpacing || isNearProhibitedRange(newDegree)) {
                    isValid = false;
                    break;
                }
            }
            if (isValid) {
                break;
            }
        }
        return newDegree;
    }

    private static boolean isNearProhibitedRange(int degree) {
        return (degree <= 5 || degree >= 355);
    }

    private void assignLocks(int[] locks, int lockCount) {
        for (int i = 0; i < 12; i++) {
            if (lockCount > i) lockAngles[i+1] = locks[i];
        }
    }

        @Override
    public void renderBackground(DrawContext context) {
        super.renderBackground(context);
        RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);
        int x = this.width / 2 - 96;
        int y = this.height / 2 - 96;
        context.drawTexture(BACKGROUND_TEXTURE, x, y, 0, 0, 192, 192, 192, 192);
    }

    protected void renderRotatedLocks(DrawContext context, Identifier texture, float rotationAngle) {
        MatrixStack matrixStack = context.getMatrices();
        matrixStack.push();

        int screenWidth = MinecraftClient.getInstance().getWindow().getScaledWidth();
        int screenHeight = MinecraftClient.getInstance().getWindow().getScaledHeight();

        float centerX = screenWidth / 2.0f;
        float centerY = screenHeight / 2.0f;

        matrixStack.translate(centerX, centerY, 0.0f);

        matrixStack.multiply(net.minecraft.util.math.RotationAxis.POSITIVE_Z.rotationDegrees(rotationAngle));

        matrixStack.translate(-99.0f, -99.0f, 0.0f);

        context.drawTexture(texture, 0, 0, 198, 198, 0, 0, 198, 198, 198, 198);

        matrixStack.pop();
    }

    @Override
    public boolean shouldCloseOnEsc() {
        methodCalled = false;
        return true;
    }

    private static BlockPos playerFacingBlock(PlayerEntity player) {
        BlockHitResult raycastedBlock = player.getWorld().raycast(new RaycastContext(
                player.getCameraPosVec(1.0F),
                player.getCameraPosVec(1.0F).add(player.getRotationVec(1.0F).multiply(4.0f)),
                RaycastContext.ShapeType.OUTLINE,
                RaycastContext.FluidHandling.NONE,
                player
        ));
        return raycastedBlock.getBlockPos();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        assert this.client != null;
        PlayerEntity player = this.client.player;
        assert this.client.player != null;
        if (lockCount <= 0) {
            playSound(Sounds.LOCKPICK_UNLOCK_SOUND, 1.0f);
            player.sendMessage(Text.translatable("message.lock.success").formatted(Formatting.GREEN), true);
            BlockPos pos = playerFacingBlock(player);
            BlockEntity lockedChestBlockEntity = player.getWorld().getBlockEntity(pos);
            if (lockedChestBlockEntity instanceof LockedChestLvLCopperBlockEntity) {
                RestoreChestPacket.sendRestoreChestRequest(pos, 0);
            }
            this.close();
        }
        if (!player.getInventory().contains(new ItemStack(Items.LOCKPICK))) {
            player.sendMessage(Text.translatable("message.lock.fail").formatted(Formatting.DARK_RED), true);
            this.close();
        }
        renderBackground(context);
        renderLocks(context);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
    }

    @Override
    public Text getDisplayName() {
        return Text.empty();
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new LockpickScreenHandler(syncId, playerInventory);
    }
    private void renderLocks(DrawContext context) {
        if (!locks[1]) renderRotatedLocks(context, PIN_UNACTIVATED, lockAngles[1]);
        if (!locks[2]) renderRotatedLocks(context, PIN_UNACTIVATED, lockAngles[2]);
        if (!locks[3]) renderRotatedLocks(context, PIN_UNACTIVATED, lockAngles[3]);
        if (!locks[4]) renderRotatedLocks(context, PIN_UNACTIVATED, lockAngles[4]);
        if (!locks[5]) renderRotatedLocks(context, PIN_UNACTIVATED, lockAngles[5]);
        if (!locks[6]) if (lockLevel >= 1) renderRotatedLocks(context, PIN_UNACTIVATED, lockAngles[6]);
        if (!locks[7]) if (lockLevel >= 1) renderRotatedLocks(context, PIN_UNACTIVATED, lockAngles[7]);
        if (!locks[8]) if (lockLevel >= 2) renderRotatedLocks(context, PIN_UNACTIVATED, lockAngles[8]);
        if (!locks[9]) if (lockLevel >= 3) renderRotatedLocks(context, PIN_UNACTIVATED, lockAngles[9]);
        if (!locks[10]) if (lockLevel >= 3) renderRotatedLocks(context, PIN_UNACTIVATED, lockAngles[10]);
        if (!locks[11]) if (lockLevel == 4) renderRotatedLocks(context, PIN_UNACTIVATED, lockAngles[11]);
        if (!locks[12]) if (lockLevel == 4) renderRotatedLocks(context, PIN_UNACTIVATED, lockAngles[12]);

        if (locks[1]) renderRotatedLocks(context, PIN_ACTIVATED, lockAngles[1]);
        if (locks[2]) renderRotatedLocks(context, PIN_ACTIVATED, lockAngles[2]);
        if (locks[3]) renderRotatedLocks(context, PIN_ACTIVATED, lockAngles[3]);
        if (locks[4]) renderRotatedLocks(context, PIN_ACTIVATED, lockAngles[4]);
        if (locks[5]) renderRotatedLocks(context, PIN_ACTIVATED, lockAngles[5]);
        if (locks[6]) if (lockLevel >= 1) renderRotatedLocks(context, PIN_ACTIVATED, lockAngles[6]);
        if (locks[7]) if (lockLevel >= 1) renderRotatedLocks(context, PIN_ACTIVATED, lockAngles[7]);
        if (locks[8]) if (lockLevel >= 2) renderRotatedLocks(context, PIN_ACTIVATED, lockAngles[8]);
        if (locks[9]) if (lockLevel >= 3) renderRotatedLocks(context, PIN_ACTIVATED, lockAngles[9]);
        if (locks[10]) if (lockLevel >= 3) renderRotatedLocks(context, PIN_ACTIVATED, lockAngles[10]);
        if (locks[11]) if (lockLevel == 4) renderRotatedLocks(context, PIN_ACTIVATED, lockAngles[11]);
        if (locks[12]) if (lockLevel == 4) renderRotatedLocks(context, PIN_ACTIVATED, lockAngles[12]);
    }
}
