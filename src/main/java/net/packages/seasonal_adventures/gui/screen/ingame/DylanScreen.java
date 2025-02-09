package net.packages.seasonal_adventures.gui.screen.ingame;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registries;
import net.packages.seasonal_adventures.gui.handler.DylanScreenHandler;
import net.packages.seasonal_adventures.gui.handler.DylanSettingsScreenHandler;
import net.packages.seasonal_adventures.network.server.ItemRemovalPacket;

import java.util.Arrays;
import java.util.List;

public class DylanScreen extends HandledScreen<DylanScreenHandler> {
    private static final List<Identifier> POSITIVE_ITEMS = Arrays.asList(
            Identifier.of("seasonal_adventures", "beef_tartare"),
            Identifier.of("candlelight", "beef_tartare"),
            Identifier.of("minecraft", "cooked_mutton"),
            Identifier.of("minecraft", "cooked_beef"),
            Identifier.of("minecraft", "cooked_porkchop"),
            Identifier.of("minecraft", "diamond"),
            Identifier.of("minecraft", "netherite_ingot"),
            Identifier.of("minecraft", "cookie"),
            Identifier.of("minecraft", "cooked_chicken"),
            Identifier.of("minecraft", "cooked_cod"),
            Identifier.of("minecraft", "cooked_salmon"),
            Identifier.of("minecraft", "cake")
    );
    private static final Item SCHEME_CUSTOM = Registries.ITEM.get(Identifier.of("seasonal_adventures", "dylan_mk1_scheme"));
    private static final List<Item> NEUTRAL_ITEMS = Arrays.asList(

    );
    private final DylanScreenHandler handler;
    public DylanScreen(DylanScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.handler = handler;
    }

    public Text dylanNameTag = Text.literal("[Дилан] ").styled(style -> style.withColor(0x3e81e0));
    private static final long COOLDOWN_TICKS = 12000;
    private static long lastPressedTime = 0;
    private MultiTextureButtonWidget button1;
    private MultiTextureButtonWidget button2;
    private MultiTextureButtonWidget button3;
    private TextWidget textWidget;
    private static final Identifier BUTTON_TEXTURE_1 = Identifier.of("seasonal_adventures", "textures/gui/deprecated/button_1.png");
    private static final Identifier BUTTON_TEXTURE_2 = Identifier.of("seasonal_adventures", "textures/gui/deprecated/button_2.png");
    private static final Identifier BUTTON_TEXTURE_3 = Identifier.of("seasonal_adventures", "textures/gui/deprecated/button_3.png");

    private static final Identifier UNAVAILABLE_BUTTON_TEXTURE_1 = Identifier.of("seasonal_adventures", "textures/gui/deprecated/unavailable_button_1.png");
    private static final Identifier UNAVAILABLE_BUTTON_TEXTURE_2 = Identifier.of("seasonal_adventures", "textures/gui/deprecated/unavailable_button_2.png");
    private static final Identifier UNAVAILABLE_BUTTON_TEXTURE_3 = Identifier.of("seasonal_adventures", "textures/gui/deprecated/unavailable_button_3.png");

    @Override
    protected void init() {
        super.init();


        int leftOffset = 5;
        int buttonWidth = 120;
        int buttonHeight = 20;
        int buttonSpacing = 5;
        int textSpacing = 5;

        int buttonX = leftOffset;
        int textX = leftOffset + (buttonWidth / 2) - (90 / 2);

        int buttonY1 = 85;
        int buttonY2 = buttonY1 + buttonHeight + buttonSpacing;
        int buttonY3 = buttonY2 + buttonHeight + buttonSpacing;

        int textY = buttonY1 - textSpacing - 25;

        textWidget = new TextWidget(textX, textY, 90, 45, Text.translatable("gui.seasonal_adventures.entity.dylan.text").styled(style -> style.withColor(0x82878c)), this.textRenderer);
        this.addSelectableChild(textWidget);


        this.button1 = this.addDrawableChild(
                new MultiTextureButtonWidget(
                        buttonX,
                        buttonY1,
                        buttonWidth,
                        buttonHeight,
                        0,
                        0,
                        0,
                        BUTTON_TEXTURE_1,
                        UNAVAILABLE_BUTTON_TEXTURE_1,
                        buttonWidth,
                        buttonHeight,
                        button -> handle_make_a_giftPress(),
                        Text.translatable("gui.seasonal_adventures.button.make_a_gift")
                ));

        this.button2 = this.addDrawableChild(
                new MultiTextureButtonWidget(
                        buttonX,
                        buttonY2,
                        buttonWidth,
                        buttonHeight,
                        0,
                        0,
                        0,
                        BUTTON_TEXTURE_2,
                        UNAVAILABLE_BUTTON_TEXTURE_2,
                        buttonWidth,
                        buttonHeight,
                        button -> handle_repairPress(),
                        Text.translatable("gui.seasonal_adventures.button.repair")
                )
        );

        this.button3 = this.addDrawableChild(
                new MultiTextureButtonWidget(
                        buttonX,
                        buttonY3,
                        buttonWidth,
                        buttonHeight,
                        0,
                        0,
                        0,
                        BUTTON_TEXTURE_3,
                        UNAVAILABLE_BUTTON_TEXTURE_3,
                        buttonWidth,
                        buttonHeight,
                        button -> handle_settings(),
                        Text.translatable("gui.seasonal_adventures.button.settings")
                )
        );

        updateButtonState();
    }
    private void updateButtonState() {
        PlayerEntity player = this.client.player;
        if (player == null) return;

        ItemStack heldItemStack = player.getStackInHand(Hand.MAIN_HAND);
        boolean hasItem = !heldItemStack.isEmpty();
        boolean isCooldownOver = this.client.world.getTime() - lastPressedTime > COOLDOWN_TICKS;

        Identifier itemId = Identifier.of("seasonal_adventures", "laptop");
        Item item = Registries.ITEM.get(itemId);
        boolean hasItemInHotbar = false;
        for (int i = 0; i < 9; i++) {
            ItemStack stack = player.getInventory().getStack(i);
            if (stack.getItem() == item) {
                hasItemInHotbar = true;
                break;
            }
        }
        this.button1.active = hasItem && isCooldownOver;
        this.button2.active = false;
        this.button3.active = false;
        if (hasItemInHotbar && heldItemStack.getItem().equals(Registries.ITEM.get(Identifier.of("seasonal_adventures", "sys_cable")))) {
            this.button3.active = true;
        }
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
    }

    private void handle_make_a_giftPress() {
        PlayerEntity player = this.client.player;
        if (player != null) {
            ItemStack heldItemStack = player.getStackInHand(Hand.MAIN_HAND);
            if (!heldItemStack.isEmpty()) {
                if (POSITIVE_ITEMS.contains(heldItemStack.getItem())) {
                    Text normalText = Text.literal("Это лучше чем донат в моей любимой игре").styled(style -> style.withColor(0xFFFFFF));
                    player.sendMessage(((MutableText) dylanNameTag).append(normalText), false);
                    ItemRemovalPacket.ItemStackRemove();
                    this.close();
                } else if (SCHEME_CUSTOM.equals(heldItemStack.getItem())) {
                    Text normalText = Text.literal("Ты нафига мне мою схему подсунул?").styled(style -> style.withColor(0xFFFFFF));
                    player.sendMessage(((MutableText) dylanNameTag).append(normalText), false);
                    ItemRemovalPacket.ItemStackRemove();
                    this.close();
                } else if (NEUTRAL_ITEMS.contains(heldItemStack.getItem())) {
                    Text neutralText = Text.literal("Ну окей, спасибо наверное").styled(style -> style.withColor(0xFFFFFF));
                    player.sendMessage(((MutableText) dylanNameTag).append(neutralText), false);
                    ItemRemovalPacket.ItemStackRemove();
                    this.close();
                } else {
                    Text negativeText = Text.literal("Фу, убери от меня это").styled(style -> style.withColor(0xFFFFFF));
                    player.sendMessage(((MutableText) dylanNameTag).append(negativeText), false);
                    ItemRemovalPacket.ItemStackRemove();
                    this.close();
                }
            }
        }
    }

    private void handle_repairPress() {
    }
    private void handle_settings() {
        PlayerEntity player = this.client.player;
        if (player != null) {
            DylanSettingsScreen settingsScreen = new DylanSettingsScreen(new DylanSettingsScreenHandler(0, player.getInventory()), player.getInventory(), Text.translatable("gui.dylan_settings_screen"));
            settingsScreen.setParentScreen(this);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        this.updateButtonState();
        textWidget.render(context, mouseX, mouseY, delta);
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
    }
}