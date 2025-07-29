package net.flyingmylife.seasonal_adventures.gui.screen.ingame;

import net.flyingmylife.seasonal_adventures.SA;
import net.flyingmylife.seasonal_adventures.gui.data.UnlockingData;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
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
import net.flyingmylife.seasonal_adventures.block.entity.lockedChests.LockedChestBlockEntity;
import net.flyingmylife.seasonal_adventures.gui.data.ButtonRenderData;
import net.flyingmylife.seasonal_adventures.gui.handler.UnlockingScreenHandler;
import net.flyingmylife.seasonal_adventures.gui.widget.LockpickWidget;
import net.flyingmylife.seasonal_adventures.item.SAItems;
import net.flyingmylife.seasonal_adventures.network.packet.c2s.RestoreChestPacket;
import net.flyingmylife.seasonal_adventures.network.packet.c2s.RemoveItemPacket;
import net.flyingmylife.seasonal_adventures.sound.SASounds;

import java.util.Optional;

public class UnlockingScreen extends HandledScreen<UnlockingScreenHandler> {

    private UnlockingData data = new UnlockingData();
    private int lockLevel = 0;
    private int pinsLeft;
    private boolean[] pinTriggerState = new boolean[12];
    private float lockpickSpeed = 1.0f;

    private static final float[] lockpickSpeedValues = {5.0f, 12.2f, 18.4f, 26.6f, 35.8f};

    private LockpickWidget lockpick;

    private static final Identifier LOCKPICK_TEXTURE = Identifier.of(SA.MOD_ID, "textures/gui/sprites/unlocking/lockpick.png");
    private static final Identifier PIN_DEFAULT = Identifier.of(SA.MOD_ID, "textures/gui/sprites/unlocking/pin_default.png");
    private static final Identifier PIN_TRIGGERED = Identifier.of(SA.MOD_ID, "textures/gui/sprites/unlocking/pin_triggered.png");
    private static final Identifier BACKGROUND_TEXTURE = Identifier.of(SA.MOD_ID, "textures/gui/unlocking.png");

    public UnlockingScreen(UnlockingScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        lockLevel = handler.getLockLevel();
        data.generate(playerFacingBlock(client.player), lockLevel);
        pinsLeft = data.getPinCount();
        lockpickSpeed = lockpickSpeedValues[lockLevel];
        int lockpickSize = 192;
        int x = this.width / 2 - lockpickSize / 2;
        int y = this.height / 2 - lockpickSize / 2;

        this.lockpick = new LockpickWidget(
                x, y, ButtonRenderData.of(lockpickSize, lockpickSize, LOCKPICK_TEXTURE),
                lockpickSpeed,
                button -> onClick()
        );

        this.addDrawableChild(this.lockpick);
    }

    private void onPinAction(int pin) {
        if (pin <= data.getPinCount()) {
            playSound(SASounds.PICK_PIN_SOUND, 0.9f);
            this.pinTriggerState[pin] = true;
            this.lockpick.toggleRotationDirection();
            this.pinsLeft--;
        } else {
            assert this.client != null;
            RemoveItemPacket.removeItem(SAItems.LOCKPICK, 1);
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
        for (int i = 0; i < data.getPinCount(); i++) {
            if (currentAngle <= data.getPin(i) + 10 && currentAngle >= data.getPin(i) - 10 && !pinTriggerState[i]) {
                onPinAction(i);
                activated = true;
            }
        }
        if (!activated) {
            playSound(SoundEvents.ENTITY_ITEM_BREAK, 1.6f);

        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        int pinsToRender = data.getPinCount();
        for (int i = 0; i < pinsToRender; i++) {
            renderRotatedLock(context, pinTriggerState[i], data.getPin(i));
        }

        super.render(context, mouseX, mouseY, delta);
        close();
    }

    @Override
    public void close() {
        assert this.client != null;
        PlayerEntity player = this.client.player;

        if (pinsLeft <= 0) {
            playSound(SASounds.LOCKPICK_UNLOCK_SOUND, 1.0f);
            player.sendMessage(Text.translatable("message.seasonal_adventures.lock.success").formatted(Formatting.GREEN), true);
            unlockChest(player);
            client.mouse.unlockCursor();
            super.close();
        } else if (!player.getInventory().contains(new ItemStack(SAItems.LOCKPICK))) {
            player.sendMessage(Text.translatable("message.seasonal_adventures.lock.fail").formatted(Formatting.DARK_RED), true);
            client.mouse.unlockCursor();
            super.close();
        }
    }

    private void renderRotatedLock(DrawContext context, boolean triggered, float rotationAngle) {
        Identifier texture = triggered ? PIN_TRIGGERED : PIN_DEFAULT;
        MatrixStack matrixStack = context.getMatrices();
        matrixStack.push();

        int centerX = this.width / 2;
        int centerY = this.height / 2;

        matrixStack.translate(centerX, centerY, 0.0f);

        matrixStack.multiply(net.minecraft.util.math.RotationAxis.POSITIVE_Z.rotationDegrees(rotationAngle));

        matrixStack.translate(-99.0f, -99.0f, 0.0f);

        context.drawTexture(texture, x, y, 0, 0, 192, 192, 192, 192);

        matrixStack.pop();
    }

    private void unlockChest(PlayerEntity player) {
        BlockPos pos = playerFacingBlock(player);
        BlockEntity blockEntity = player.getWorld().getBlockEntity(pos);

        if (blockEntity instanceof LockedChestBlockEntity) {
            RestoreChestPacket.restoreChest(pos);
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
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int x = this.width / 2 - 96;
        int y = this.height / 2 - 96;
        context.drawTexture(BACKGROUND_TEXTURE, x, y, 0, 0, 192, 192, 192, 192);
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {}

    @Override
    public Optional<Element> hoveredElement(double mouseX, double mouseY) {
        return super.hoveredElement(mouseX, mouseY);
    }
}