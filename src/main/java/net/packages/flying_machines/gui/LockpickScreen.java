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
import org.lwjgl.glfw.GLFW;

import java.util.Random;

public class LockpickScreen extends HandledScreen<LockpickScreenHandler> {
    private int lockLevel;
    private int lockCount;
    private int lock1a = 0;
    private int lock2a = 0;
    private int lock3a = 0;
    private int lock4a = 0;
    private int lock5a = 0;
    private int lock6a = 0;
    private int lock7a = 0;
    private int lock8a = 0;
    private int lock9a = 0;
    private int lock10a = 0;
    private int lock11a = 0;
    private int lock12a = 0;

    private boolean lock1s;
    private boolean lock2s;
    private boolean lock3s;
    private boolean lock4s;
    private boolean lock5s;
    private boolean lock6s;
    private boolean lock7s;
    private boolean lock8s;
    private boolean lock9s;
    private boolean lock10s;
    private boolean lock11s;
    private boolean lock12s;

    private float lockpickspeed = 1.0f;

    private boolean methodCalled = false;

    private RotatingLockpick lockpick;
    private static final Identifier LOCKPICK_TEXTURE = new Identifier("flying_machines", "textures/gui/lockpick.png");
    private static final Identifier LOCK_AVAILABLE = new Identifier("flying_machines", "textures/gui/lock_unavailable.png");
    private static final Identifier LOCK_UNAVAILABLE = new Identifier("flying_machines", "textures/gui/lock_available.png");
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
                button -> {
                    int currentAngle = (int) this.lockpick.getRotationAngle();
                    if (currentAngle <= lock1a+10 && currentAngle >= lock1a-10 && !lock1s && lockLevel <= 2) {
                        this.lock1s = true;
                        this.lockpick.toggleRotationDirection();
                        this.lockCount--;
                        if (lockCount <= 0) {
                            this.close();
                            this.client.player.sendMessage(Text.translatable("message.lock.success").formatted(Formatting.GREEN), true);
                        }
                    }
                    else if (currentAngle <= 10 && currentAngle >= 0 && !lock1s && lockLevel >= 3) {
                        this.lock1s = true;
                        this.lockpick.toggleRotationDirection();
                        this.lockCount--;
                        if (lockCount <= 0) {
                            this.close();
                            this.client.player.sendMessage(Text.translatable("message.lock.success").formatted(Formatting.GREEN), true);
                        }
                    }
                    else if (currentAngle <= 360 && currentAngle >= 350 && !lock1s && lockLevel >= 3) {
                        this.lock1s = true;
                        this.lockpick.toggleRotationDirection();
                        this.lockCount--;
                        if (lockCount <= 0) {
                            this.close();
                            this.client.player.sendMessage(Text.translatable("message.lock.success").formatted(Formatting.GREEN), true);
                        }
                    }
                    else if (currentAngle <= lock2a+10 && currentAngle >= lock2a-10 && !lock2s) {
                        this.lock2s = true;
                        this.lockpick.toggleRotationDirection();
                        this.lockCount--;
                        if (lockCount <= 0) {
                            this.close();
                            this.client.player.sendMessage(Text.translatable("message.lock.success").formatted(Formatting.GREEN), true);
                        }
                    } else if (currentAngle <= lock3a+10 && currentAngle >= lock3a-10 && !lock3s) {
                        this.lock3s = true;
                        this.lockpick.toggleRotationDirection();
                        this.lockCount--;
                        if (lockCount <= 0) {
                            this.close();
                            this.client.player.sendMessage(Text.translatable("message.lock.success").formatted(Formatting.GREEN), true);
                        }
                    } else if (currentAngle <= lock4a+10 && currentAngle >= lock4a-10 && !lock4s) {
                        this.lock4s = true;
                        this.lockpick.toggleRotationDirection();
                        this.lockCount--;
                        if (lockCount <= 0) {
                            this.close();
                            this.client.player.sendMessage(Text.translatable("message.lock.success").formatted(Formatting.GREEN), true);
                        }
                    } else if (currentAngle <= lock5a+10 && currentAngle >= lock5a-10 && !lock5s) {
                        this.lock5s = true;
                        this.lockpick.toggleRotationDirection();
                        this.lockCount--;
                        if (lockCount <= 0) {
                            this.close();
                            this.client.player.sendMessage(Text.translatable("message.lock.success").formatted(Formatting.GREEN), true);
                        }
                    } else if (currentAngle <= lock6a+10 && currentAngle >= lock6a-10 && !lock6s) {
                        if (lockLevel >= 1) {
                            this.lock6s = true;
                            this.lockpick.toggleRotationDirection();
                            this.lockCount--;
                        } else {
                            SpecificItemRemovalPacket.removeItemStack(this.client.player, Items.LOCKPICK, 1);
                        }
                        if (lockCount <= 0) {
                            this.close();
                            this.client.player.sendMessage(Text.translatable("message.lock.success").formatted(Formatting.GREEN), true);
                        }
                    } else if (currentAngle <= lock7a+10 && currentAngle >= lock7a-10 && !lock7s) {
                        if (lockLevel >= 1) {
                            this.lock7s = true;
                            this.lockpick.toggleRotationDirection();
                            this.lockCount--;
                        } else {
                            SpecificItemRemovalPacket.removeItemStack(this.client.player, Items.LOCKPICK, 1);
                        }
                        if (lockCount <= 0) {
                            this.close();
                            this.client.player.sendMessage(Text.translatable("message.lock.success").formatted(Formatting.GREEN), true);
                        }
                    } else if (currentAngle <= lock8a+10 && currentAngle >= lock8a-10 && !lock8s) {
                        if (lockLevel >= 2) {
                            this.lock8s = true;
                            this.lockpick.toggleRotationDirection();
                            this.lockCount--;
                        } else {
                            SpecificItemRemovalPacket.removeItemStack(this.client.player, Items.LOCKPICK, 1);
                        }
                        if (lockCount <= 0) {
                            this.close();
                            this.client.player.sendMessage(Text.translatable("message.lock.success").formatted(Formatting.GREEN), true);
                        }
                    } else if (currentAngle <= lock9a+10 && currentAngle >= lock9a-10 && !lock9s) {
                        if (lockLevel >= 3) {
                            this.lock9s = true;
                            this.lockpick.toggleRotationDirection();
                            this.lockCount--;
                        } else {
                            SpecificItemRemovalPacket.removeItemStack(this.client.player, Items.LOCKPICK, 1);
                        }
                        if (lockCount <= 0) {
                            this.close();
                            this.client.player.sendMessage(Text.translatable("message.lock.success").formatted(Formatting.GREEN), true);
                        }
                    } else if (currentAngle <= lock10a+10 && currentAngle >= lock10a-10 && !lock10s) {
                        if (lockLevel >= 3) {
                            this.lock10s = true;
                            this.lockpick.toggleRotationDirection();
                            this.lockCount--;
                        } else {
                            SpecificItemRemovalPacket.removeItemStack(this.client.player, Items.LOCKPICK, 1);
                        }
                        if (lockCount <= 0) {
                            this.close();
                            this.client.player.sendMessage(Text.translatable("message.lock.success").formatted(Formatting.GREEN), true);
                        }
                    } else if (currentAngle <= lock11a+10 && currentAngle >= lock11a-10 && !lock11s) {
                        if (lockLevel == 4) {
                            this.lock11s = true;
                            this.lockpick.toggleRotationDirection();
                            this.lockCount--;
                        } else {
                            SpecificItemRemovalPacket.removeItemStack(this.client.player, Items.LOCKPICK, 1);
                        }
                        if (lockCount <= 0) {
                            this.close();
                            this.client.player.sendMessage(Text.translatable("message.lock.success").formatted(Formatting.GREEN), true);
                        }
                    } else if (currentAngle <= lock12a+10 && currentAngle >= lock12a-10 && !lock12s) {
                        if (lockLevel == 4) {
                            this.lock12s = true;
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
        if (lockCount > 0) lock1a = locks[0];
        if (lockCount > 1) lock2a = locks[1];
        if (lockCount > 2) lock3a = locks[2];
        if (lockCount > 3) lock4a = locks[3];
        if (lockCount > 4) lock5a = locks[4];
        if (lockCount > 5) lock6a = locks[5];
        if (lockCount > 6) lock7a = locks[6];
        if (lockCount > 7) lock8a = locks[7];
        if (lockCount > 8) lock9a = locks[8];
        if (lockCount > 9) lock10a = locks[9];
        if (lockCount > 10) lock11a = locks[10];
        if (lockCount > 11) lock12a = locks[11];
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
        if (!this.client.player.getInventory().contains(new ItemStack(Items.LOCKPICK))){
            this.client.player.sendMessage(Text.translatable("message.lock.fail").formatted(Formatting.DARK_RED), true);
            this.close();
        }
        renderBackground(context);
        if (!lock1s) renderRotatedLocks(context, LOCK_AVAILABLE, lock1a);
        if (!lock2s) renderRotatedLocks(context, LOCK_AVAILABLE, lock2a);
        if (!lock3s) renderRotatedLocks(context, LOCK_AVAILABLE, lock3a);
        if (!lock4s) renderRotatedLocks(context, LOCK_AVAILABLE, lock4a);
        if (!lock5s) renderRotatedLocks(context, LOCK_AVAILABLE, lock5a);
        if (!lock6s) if (lockLevel >= 1) renderRotatedLocks(context, LOCK_AVAILABLE, lock6a);
        if (!lock7s) if (lockLevel >= 1) renderRotatedLocks(context, LOCK_AVAILABLE, lock7a);
        if (!lock8s) if (lockLevel >= 2) renderRotatedLocks(context, LOCK_AVAILABLE, lock8a);
        if (!lock9s) if (lockLevel >= 3) renderRotatedLocks(context, LOCK_AVAILABLE, lock9a);
        if (!lock10s) if (lockLevel >= 3) renderRotatedLocks(context, LOCK_AVAILABLE, lock10a);
        if (!lock11s) if (lockLevel == 4) renderRotatedLocks(context, LOCK_AVAILABLE, lock11a);
        if (!lock12s) if (lockLevel == 4) renderRotatedLocks(context, LOCK_AVAILABLE, lock12a);

        if (lock1s) renderRotatedLocks(context, LOCK_UNAVAILABLE, lock1a);
        if (lock2s) renderRotatedLocks(context, LOCK_UNAVAILABLE, lock2a);
        if (lock3s) renderRotatedLocks(context, LOCK_UNAVAILABLE, lock3a);
        if (lock4s) renderRotatedLocks(context, LOCK_UNAVAILABLE, lock4a);
        if (lock5s) renderRotatedLocks(context, LOCK_UNAVAILABLE, lock5a);
        if (lock6s) if (lockLevel >= 1) renderRotatedLocks(context, LOCK_UNAVAILABLE, lock6a);
        if (lock7s) if (lockLevel >= 1) renderRotatedLocks(context, LOCK_UNAVAILABLE, lock7a);
        if (lock8s) if (lockLevel >= 2) renderRotatedLocks(context, LOCK_UNAVAILABLE, lock8a);
        if (lock9s) if (lockLevel >= 3) renderRotatedLocks(context, LOCK_UNAVAILABLE, lock9a);
        if (lock10s) if (lockLevel >= 3) renderRotatedLocks(context, LOCK_UNAVAILABLE, lock10a);
        if (lock11s) if (lockLevel == 4) renderRotatedLocks(context, LOCK_UNAVAILABLE, lock11a);
        if (lock12s) if (lockLevel == 4) renderRotatedLocks(context, LOCK_UNAVAILABLE, lock12a);

        super.render(context, mouseX, mouseY, delta);
    }
}
