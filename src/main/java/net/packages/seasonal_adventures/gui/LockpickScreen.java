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
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.RaycastContext;
import net.packages.seasonal_adventures.block.entity.lockedChests.LockedChestLvLCopperBlockEntity;
import net.packages.seasonal_adventures.gui.handlers.LockpickScreenHandler;
import net.packages.seasonal_adventures.gui.widgets.RotatableLockpick;
import net.packages.seasonal_adventures.item.Items;
import net.packages.seasonal_adventures.network.ItemRemovalPacket;
import net.packages.seasonal_adventures.network.RestoreChestPacket;
import net.packages.seasonal_adventures.network.SpecificItemRemovalPacket;
import net.packages.seasonal_adventures.sound.Sounds;
import net.packages.seasonal_adventures.util.PinAngles;
import org.jetbrains.annotations.Nullable;

public class LockpickScreen extends HandledScreen<LockpickScreenHandler> implements NamedScreenHandlerFactory {
    
    private PinAngles pinAngles = new PinAngles();

    private int lockLevel;

    private int pinsLeft;

    private boolean [] pinTriggerState = new boolean[12];

    private float lockpickSpeed = 1.0f;

    private static final int[] pinDefValues = {5, 7, 8, 10, 12};

    private static final  float[] lockpickSpeedValues = {5.0f, 12.2f, 18.4f, 26.6f, 35.8f};

    private RotatableLockpick lockpick;

    private static final Identifier LOCKPICK_TEXTURE = new Identifier("seasonal_adventures", "textures/gui/lockpick.png");
    private static final Identifier PIN_DEFAULT = new Identifier("seasonal_adventures", "textures/gui/pin_default.png");
    private static final Identifier PIN_TRIGGERED = new Identifier("seasonal_adventures", "textures/gui/pin_triggered.png");
    private static final Identifier BACKGROUND_TEXTURE = new Identifier("seasonal_adventures", "textures/gui/lockpick_background.png");

    public LockpickScreen(LockpickScreenHandler handler, PlayerInventory inventory, Text title, int lockLevel) {
        super(handler, inventory, title);
        this.lockLevel = lockLevel;
        pinAngles.setLockLevel(lockLevel);
        pinAngles.generate();
        if (lockLevel >= 0 && lockLevel < pinDefValues.length) {
            this.pinsLeft = pinDefValues[lockLevel];
            this.lockpickSpeed = lockpickSpeedValues[lockLevel];
        }
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

        this.addDrawableChild(this.lockpick);
    }

    private void onPinAction(int pin) {
        if (pin <= pinDefValues[lockLevel]) {
            playSound(Sounds.PICK_PIN_SOUND, 0.9f);
            this.pinTriggerState[pin] = true;
            this.lockpick.toggleRotationDirection();
            this.pinsLeft--;
        } else {
            assert this.client != null;
            assert this.client.player != null;
            SpecificItemRemovalPacket.removeItemStack(this.client.player, Items.LOCKPICK, 1);
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
        assert this.client.player != null;
        int currentAngle = (int) this.lockpick.getRotationAngle();

        for (int i = 0; i < pinDefValues[lockLevel]; i++) {
            if (currentAngle <= pinAngles.getPin(i)+10 && currentAngle >= pinAngles.getPin(i)-10 && !pinTriggerState[i]) {
                onPinAction(i);
            }
        }
        if (currentAngle <= 10 && currentAngle >= 0 && !pinTriggerState[1] && lockLevel >= 3) {
            onPinAction(1);
        } else if (currentAngle <= 360 && currentAngle >= 350 && !pinTriggerState[1] && lockLevel >= 3) {
            onPinAction(1);
        } else {
            playSound(SoundEvents.ENTITY_ITEM_BREAK, 1.6f);
            this.client.player.getInventory().getMainHandStack().decrement(1);
        }
    }
        @Override
    public void renderBackground(DrawContext context) {
        RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);
        int x = this.width / 2 - 96;
        int y = this.height / 2 - 96;
        context.drawTexture(BACKGROUND_TEXTURE, x, y, 0, 0, 192, 192, 192, 192);
    }

    private void renderRotatedLocks(DrawContext context, boolean triggered, float rotationAngle) {
        Identifier texture;
        if (triggered) texture = PIN_TRIGGERED;
        else texture = PIN_DEFAULT;
        MatrixStack matrixStack = context.getMatrices();
        matrixStack.push();

        int screenWidth = MinecraftClient.getInstance().getWindow().getScaledWidth();
        int screenHeight = MinecraftClient.getInstance().getWindow().getScaledHeight();

        float centerX = screenWidth / 2.0f;
        float centerY = screenHeight / 2.0f;

        matrixStack.translate(centerX, centerY, 0.0f);

        matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(rotationAngle));

        matrixStack.translate(-99.0f, -99.0f, 0.0f);

        context.drawTexture(texture, 0, 0, 198, 198, 0, 0, 198, 198, 198, 198);

        matrixStack.pop();
    }

    @Override
    public boolean shouldCloseOnEsc() {
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
        renderBackground(context);
        handleClosingActions();
        int pinsToRender = pinDefValues[lockLevel];

        for (int i = 0; i < pinsToRender; i++) {

            float rotationAngle = pinAngles.getPin(i);

            boolean triggered = pinTriggerState[i];

            renderRotatedLocks(context, triggered, rotationAngle);
        }

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

    private void handleClosingActions() {
        assert this.client != null;
        PlayerEntity player = this.client.player;
        assert this.client.player != null;
        if (pinsLeft <= 0) {
            playSound(Sounds.LOCKPICK_UNLOCK_SOUND, 1.0f);
            player.sendMessage(Text.translatable("message.seasonal_adventures.lock.success").formatted(Formatting.GREEN), true);
            BlockPos pos = playerFacingBlock(player);
            BlockEntity lockedChestBlockEntity = player.getWorld().getBlockEntity(pos);
            if (lockedChestBlockEntity instanceof LockedChestLvLCopperBlockEntity) {
                RestoreChestPacket.sendRestoreChestRequest(pos, 0);
            }
            this.close();
        }
        if (!player.getInventory().contains(new ItemStack(Items.LOCKPICK))) {
            player.sendMessage(Text.translatable("message.seasonal_adventures.lock.fail").formatted(Formatting.DARK_RED), true);
            this.close();
        }
    }
    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new LockpickScreenHandler(syncId, playerInventory);
    }
}
