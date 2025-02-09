package net.packages.seasonal_adventures.gui.screen.ingame;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import net.packages.seasonal_adventures.block.entity.lockedChests.LockedChestLvLCopperBlockEntity;
import net.packages.seasonal_adventures.block.entity.lockedChests.LockedChestLvLIronBlockEntity;
import net.packages.seasonal_adventures.gui.handler.LockpickScreenHandler;
import net.packages.seasonal_adventures.gui.widgets.RotatableLockpick;
import net.packages.seasonal_adventures.item.SAItems;
import net.packages.seasonal_adventures.network.c2s.ItemRemovalPacket;
import net.packages.seasonal_adventures.network.c2s.RestoreChestPacket;
import net.packages.seasonal_adventures.network.c2s.SpecificItemRemovalPacket;
import net.packages.seasonal_adventures.sound.SASounds;
import net.packages.seasonal_adventures.util.data.PinAngles;

import java.util.Optional;

public class UnlockingScreen extends HandledScreen<LockpickScreenHandler> {

    private PinAngles pinAngles = new PinAngles();
    private int lockLevel = 0;
    private int pinsLeft;
    private boolean[] pinTriggerState = new boolean[12];
    private float lockpickSpeed = 1.0f;

    private static final int[] pinDefValues = {5, 7, 8, 10, 12};
    private static final float[] lockpickSpeedValues = {5.0f, 12.2f, 18.4f, 26.6f, 35.8f};

    private RotatableLockpick lockpick;

    private static final Identifier LOCKPICK_TEXTURE = Identifier.of("seasonal_adventures", "textures/gui/screen/unlocking/lockpick.png");
    private static final Identifier PIN_DEFAULT = Identifier.of("seasonal_adventures", "textures/gui/screen/unlocking/pin_default.png");
    private static final Identifier PIN_TRIGGERED = Identifier.of("seasonal_adventures", "textures/gui/screen/unlocking/pin_triggered.png");
    private static final Identifier BACKGROUND_TEXTURE = Identifier.of("seasonal_adventures", "textures/gui/screen/unlocking/background.png");

    public UnlockingScreen(LockpickScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }
    @Override
    protected void init() {
        super.init();
        lockLevel = getLockLevel();
        pinAngles.setLockLevel(lockLevel);
        pinAngles.generate();
        pinsLeft = pinDefValues[lockLevel];
        lockpickSpeed = lockpickSpeedValues[lockLevel];
        int lockpickSize = 192;
        int x = this.width / 2 - lockpickSize / 2;
        int y = this.height / 2 - lockpickSize / 2;

        this.lockpick = new RotatableLockpick(
                x, y, lockpickSize, lockpickSize,
                0, 0, 0, LOCKPICK_TEXTURE, 192, 192,
                lockpickSpeed,
                button -> onClick()
        );

        this.addDrawableChild(this.lockpick);
    }
    private int getLockLevel() {
        assert client != null;
        assert client.player != null;
        BlockPos pos = playerFacingBlock(client.player);
        BlockEntity blockEntity = client.player.getWorld().getBlockEntity(pos);
        if (blockEntity instanceof LockedChestLvLCopperBlockEntity) {
            return 0;
        } else if (blockEntity instanceof LockedChestLvLIronBlockEntity) {
            return 1;
        } else return -1;
    }
    private void onPinAction(int pin) {
        if (pin <= pinDefValues[lockLevel]) {
            playSound(SASounds.PICK_PIN_SOUND, 0.9f);
            this.pinTriggerState[pin] = true;
            this.lockpick.toggleRotationDirection();
            this.pinsLeft--;
        } else {
            assert this.client != null;
            SpecificItemRemovalPacket.sendItemStackRemovalRequest(new ItemStack(SAItems.LOCKPICK), 1);
            client.player.getInventory().markDirty();
        }
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
        assert this.client != null;
        int currentAngle = (int) this.lockpick.getRotationAngle();
        boolean activated = false;
        for (int i = 0; i < pinDefValues[lockLevel]; i++) {
            if (currentAngle <= pinAngles.getPin(i) + 10 && currentAngle >= pinAngles.getPin(i) - 10 && !pinTriggerState[i]) {
                onPinAction(i);
                activated = true;
            }
        }
        if (!activated) {
            playSound(SoundEvents.ENTITY_ITEM_BREAK, 1.6f);
            ItemRemovalPacket.ItemStackRemove();
            client.player.getInventory().updateItems();
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);

        int pinsToRender = pinDefValues[lockLevel];
        for (int i = 0; i < pinsToRender; i++) {
            renderRotatedLocks(context, pinTriggerState[i], pinAngles.getPin(i));
        }

        super.render(context, mouseX, mouseY, delta);

        if (pinsLeft <= 0 || !client.player.getInventory().contains(new ItemStack(SAItems.LOCKPICK))) {
            handleClosingActions();
        }
    }

    private void renderRotatedLocks(DrawContext context, boolean triggered, float rotationAngle) {
        Identifier texture = triggered ? PIN_TRIGGERED : PIN_DEFAULT;
        MatrixStack matrixStack = context.getMatrices();
        matrixStack.push();

        int centerX = this.width / 2;
        int centerY = this.height / 2;

        matrixStack.translate(centerX, centerY, 0.0f);

        matrixStack.multiply(net.minecraft.util.math.RotationAxis.POSITIVE_Z.rotationDegrees(rotationAngle));

        matrixStack.translate(-99.0f, -99.0f, 0.0f);

        context.drawTexture(texture, 0, 0, 198, 198, 0, 0, 198, 198, 198, 198);

        matrixStack.pop();
    }

    @Override
    public void renderBackground(DrawContext context) {
        RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);
        int x = this.width / 2 - 96;
        int y = this.height / 2 - 96;
        context.drawTexture(BACKGROUND_TEXTURE, x, y, 0, 0, 192, 192, 192, 192);
    }

    private void handleClosingActions() {
        assert this.client != null;
        PlayerEntity player = this.client.player;

        if (pinsLeft <= 0) {
            playSound(SASounds.LOCKPICK_UNLOCK_SOUND, 1.0f);
            player.sendMessage(Text.translatable("message.seasonal_adventures.lock.success").formatted(Formatting.GREEN), true);
            unlockChest(player);
            this.close();
        } else if (!player.getInventory().contains(new ItemStack(SAItems.LOCKPICK))) {
            player.sendMessage(Text.translatable("message.seasonal_adventures.lock.fail").formatted(Formatting.DARK_RED), true);
            this.close();
        }
    }

    private void unlockChest(PlayerEntity player) {
        BlockPos pos = playerFacingBlock(player);
        BlockEntity blockEntity = player.getWorld().getBlockEntity(pos);

        if (blockEntity instanceof LockedChestLvLCopperBlockEntity) {
            RestoreChestPacket.sendRestoreChestRequest(pos, 0);
        } else if (blockEntity instanceof LockedChestLvLIronBlockEntity) {
            RestoreChestPacket.sendRestoreChestRequest(pos, 1);
        }
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
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {}

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {}

    @Override
    public Optional<Element> hoveredElement(double mouseX, double mouseY) {
        return super.hoveredElement(mouseX, mouseY);
    }
}
