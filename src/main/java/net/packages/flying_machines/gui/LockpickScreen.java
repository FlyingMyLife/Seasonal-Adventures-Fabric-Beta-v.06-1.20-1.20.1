package net.packages.flying_machines.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.packages.flying_machines.gui.widgets.RotatingLockpick;
import net.packages.flying_machines.item.Items;
import net.packages.flying_machines.network.SpecificItemRemovalPacket;

import java.util.Random;

public class LockpickScreen extends HandledScreen<LockpickScreenHandler> {

    private int lockLevel;

    private int lockCount;

    private int [] lockAngles = new int[13];

    private boolean [] locks = new boolean[13];

    private float lockpickspeed = 1.0f;

    private boolean methodCalled = false;

    private RotatingLockpick lockpick;
    
    private static final Identifier LOCKPICK_TEXTURE = new Identifier("flying_machines", "textures/gui/lockpick.png");
    private static final Identifier LOCK_UNACTIVATED = new Identifier("flying_machines", "textures/gui/lock_unactivated.png");
    private static final Identifier LOCK_AVAILABLE = new Identifier("flying_machines", "textures/gui/lock_activated.png");
    private static final Identifier BACKGROUND_TEXTURE = new Identifier("flying_machines", "textures/gui/lockpick_background.png");

    public LockpickScreen(LockpickScreenHandler handler, PlayerInventory inventory, Text title, int lockLevel) {
        super(handler, inventory, title);
        this.lockLevel = lockLevel;
        if (lockLevel == 0) this.lockpickspeed = 1.4f;
        if (lockLevel == 1) this.lockpickspeed = 2.2f;
        if (lockLevel == 2) this.lockpickspeed = 2.8f;
        if (lockLevel == 3) this.lockpickspeed = 3.6f;
        if (lockLevel == 4) this.lockpickspeed = 4.2f;
    }

    @Override
    protected void init() {
        super.init();
        int buttonSize = 192;
        int x = this.width / 2 - buttonSize / 2;
        int y = this.height / 2 - buttonSize / 2;

        this.lockpick = new RotatingLockpick(
                x, y, buttonSize, buttonSize,
                0, 0, 0, LOCKPICK_TEXTURE, 192, 192,
                lockpickspeed,
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
        this.locks[lock] = true;
        this.lockpick.toggleRotationDirection();
        this.lockCount--;
        if (lockCount <= 0) {
            this.close();
            this.client.player.sendMessage(Text.translatable("message.lock.success").formatted(Formatting.GREEN), true);
        }
    }

    private void onClick() {
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
                this.locks[6] = true;
                this.lockpick.toggleRotationDirection();
                this.lockCount--;
            } else {
                SpecificItemRemovalPacket.removeItemStack(this.client.player, Items.LOCKPICK, 1);
            }
            if (lockCount <= 0) {
                this.close();
                this.client.player.sendMessage(Text.translatable("message.lock.success").formatted(Formatting.GREEN), true);
            }
        } else if (currentAngle <= lockAngles[7]+10 && currentAngle >= lockAngles[7]-10 && !locks[7]) {
            if (lockLevel >= 1) {
                this.locks[7] = true;
                this.lockpick.toggleRotationDirection();
                this.lockCount--;
            } else {
                SpecificItemRemovalPacket.removeItemStack(this.client.player, Items.LOCKPICK, 1);
            }
            if (lockCount <= 0) {
                this.close();
                this.client.player.sendMessage(Text.translatable("message.lock.success").formatted(Formatting.GREEN), true);
            }
        } else if (currentAngle <= lockAngles[8]+10 && currentAngle >= lockAngles[8]-10 && !locks[8]) {
            if (lockLevel >= 2) {
                this.locks[8] = true;
                this.lockpick.toggleRotationDirection();
                this.lockCount--;
            } else {
                SpecificItemRemovalPacket.removeItemStack(this.client.player, Items.LOCKPICK, 1);
            }
            if (lockCount <= 0) {
                this.close();
                this.client.player.sendMessage(Text.translatable("message.lock.success").formatted(Formatting.GREEN), true);
            }
        } else if (currentAngle <= lockAngles[9]+10 && currentAngle >= lockAngles[9]-10 && !locks[9]) {
            if (lockLevel >= 3) {
                this.locks[9] = true;
                this.lockpick.toggleRotationDirection();
                this.lockCount--;
            } else {
                SpecificItemRemovalPacket.removeItemStack(this.client.player, Items.LOCKPICK, 1);
            }
            if (lockCount <= 0) {
                this.close();
                this.client.player.sendMessage(Text.translatable("message.lock.success").formatted(Formatting.GREEN), true);
            }
        } else if (currentAngle <= lockAngles[9]+10 && currentAngle >= lockAngles[9]-10 && !locks[10]) {
            if (lockLevel >= 3) {
                this.locks[10] = true;
                this.lockpick.toggleRotationDirection();
                this.lockCount--;
            } else {
                SpecificItemRemovalPacket.removeItemStack(this.client.player, Items.LOCKPICK, 1);
            }
            if (lockCount <= 0) {
                this.close();
                this.client.player.sendMessage(Text.translatable("message.lock.success").formatted(Formatting.GREEN), true);
            }
        } else if (currentAngle <= lockAngles[11]+10 && currentAngle >= lockAngles[11]-10 && !locks[11]) {
            if (lockLevel == 4) {
                this.locks[11] = true;
                this.lockpick.toggleRotationDirection();
                this.lockCount--;
            } else {
                SpecificItemRemovalPacket.removeItemStack(this.client.player, Items.LOCKPICK, 1);
            }
            if (lockCount <= 0) {
                this.close();
                this.client.player.sendMessage(Text.translatable("message.lock.success").formatted(Formatting.GREEN), true);
            }
        } else if (currentAngle <= lockAngles[12]+10 && currentAngle >= lockAngles[12]-10 && !locks[12]) {
            if (lockLevel == 4) {
                this.locks[12] = true;
                this.lockpick.toggleRotationDirection();
                this.lockCount--;
            } else {
                SpecificItemRemovalPacket.removeItemStack(this.client.player, Items.LOCKPICK, 1);
            }
            if (lockCount <= 0) {
                this.close();
                this.client.player.sendMessage(Text.translatable("message.lock.success").formatted(Formatting.GREEN), true);
            }
        } else {
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
        if (lockCount > 0) lockAngles[1] = locks[0];
        if (lockCount > 1) lockAngles[2] = locks[1];
        if (lockCount > 2) lockAngles[3] = locks[2];
        if (lockCount > 3) lockAngles[4] = locks[3];
        if (lockCount > 4) lockAngles[5] = locks[4];
        if (lockCount > 5) lockAngles[6] = locks[5];
        if (lockCount > 6) lockAngles[7] = locks[6];
        if (lockCount > 7) lockAngles[8] = locks[7];
        if (lockCount > 8) lockAngles[9] = locks[8];
        if (lockCount > 9) lockAngles[10] = locks[9];
        if (lockCount > 10) lockAngles[11] = locks[10];
        if (lockCount > 11) lockAngles[12] = locks[11];
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
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {

    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {

    }

    @Override
    public boolean shouldCloseOnEsc() {
        methodCalled = false;
        return true;
    }


    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (!this.client.player.getInventory().contains(new ItemStack(Items.LOCKPICK))) {
            this.client.player.sendMessage(Text.translatable("message.lock.fail").formatted(Formatting.DARK_RED), true);
            this.close();
        }

        renderBackground(context);

        if (!locks[1]) renderRotatedLocks(context, LOCK_UNACTIVATED, lockAngles[1]);
        if (!locks[2]) renderRotatedLocks(context, LOCK_UNACTIVATED, lockAngles[2]);
        if (!locks[3]) renderRotatedLocks(context, LOCK_UNACTIVATED, lockAngles[3]);
        if (!locks[4]) renderRotatedLocks(context, LOCK_UNACTIVATED, lockAngles[4]);
        if (!locks[5]) renderRotatedLocks(context, LOCK_UNACTIVATED, lockAngles[5]);
        if (!locks[6]) if (lockLevel >= 1) renderRotatedLocks(context, LOCK_UNACTIVATED, lockAngles[6]);
        if (!locks[7]) if (lockLevel >= 1) renderRotatedLocks(context, LOCK_UNACTIVATED, lockAngles[7]);
        if (!locks[8]) if (lockLevel >= 2) renderRotatedLocks(context, LOCK_UNACTIVATED, lockAngles[8]);
        if (!locks[9]) if (lockLevel >= 3) renderRotatedLocks(context, LOCK_UNACTIVATED, lockAngles[9]);
        if (!locks[10]) if (lockLevel >= 3) renderRotatedLocks(context, LOCK_UNACTIVATED, lockAngles[10]);
        if (!locks[11]) if (lockLevel == 4) renderRotatedLocks(context, LOCK_UNACTIVATED, lockAngles[11]);
        if (!locks[12]) if (lockLevel == 4) renderRotatedLocks(context, LOCK_UNACTIVATED, lockAngles[12]);

        if (locks[1]) renderRotatedLocks(context, LOCK_AVAILABLE, lockAngles[1]);
        if (locks[2]) renderRotatedLocks(context, LOCK_AVAILABLE, lockAngles[2]);
        if (locks[3]) renderRotatedLocks(context, LOCK_AVAILABLE, lockAngles[3]);
        if (locks[4]) renderRotatedLocks(context, LOCK_AVAILABLE, lockAngles[4]);
        if (locks[5]) renderRotatedLocks(context, LOCK_AVAILABLE, lockAngles[5]);
        if (locks[6]) if (lockLevel >= 1) renderRotatedLocks(context, LOCK_AVAILABLE, lockAngles[6]);
        if (locks[7]) if (lockLevel >= 1) renderRotatedLocks(context, LOCK_AVAILABLE, lockAngles[7]);
        if (locks[8]) if (lockLevel >= 2) renderRotatedLocks(context, LOCK_AVAILABLE, lockAngles[8]);
        if (locks[9]) if (lockLevel >= 3) renderRotatedLocks(context, LOCK_AVAILABLE, lockAngles[9]);
        if (locks[10]) if (lockLevel >= 3) renderRotatedLocks(context, LOCK_AVAILABLE, lockAngles[10]);
        if (locks[11]) if (lockLevel == 4) renderRotatedLocks(context, LOCK_AVAILABLE, lockAngles[11]);
        if (locks[12]) if (lockLevel == 4) renderRotatedLocks(context, LOCK_AVAILABLE, lockAngles[12]);

        super.render(context, mouseX, mouseY, delta);
    }
}
