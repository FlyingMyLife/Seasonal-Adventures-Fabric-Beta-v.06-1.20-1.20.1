package net.packages.flying_machines.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
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
import net.packages.flying_machines.gui.widgets.CustomTexturedButtonWidget;
import net.packages.flying_machines.network.ItemRemovalPacket;

import java.util.Arrays;
import java.util.List;

public class DylanScreen extends HandledScreen<DylanScreenHandler> {
    private static final List<Item> POSITIVE_ITEMS = Arrays.asList(
            Registries.ITEM.get(new Identifier("flying_machines", "beef_tartare")),
            Registries.ITEM.get(new Identifier("candlelight", "beef_tartare")),
            Registries.ITEM.get(new Identifier("minecraft", "cooked_mutton")),
            Registries.ITEM.get(new Identifier("minecraft", "cooked_beef")),
            Registries.ITEM.get(new Identifier("minecraft", "cooked_porkchop")),
            Registries.ITEM.get(new Identifier("minecraft", "diamond")),
            Registries.ITEM.get(new Identifier("minecraft", "netherite_ingot")),
            Registries.ITEM.get(new Identifier("minecraft", "cookie")),
            Registries.ITEM.get(new Identifier("minecraft", "cooked_chicken")),
            Registries.ITEM.get(new Identifier("minecraft", "cooked_cod")),
            Registries.ITEM.get(new Identifier("minecraft", "cooked_salmon")),
            Registries.ITEM.get(new Identifier("minecraft", "cake"))
    );
    private static final List<Item> SCHEME_CUSTOM = Arrays.asList(
            Registries.ITEM.get(new Identifier("flying_machines", "dylan_mk1_scheme"))
    );
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
    private CustomTexturedButtonWidget button1;
    private CustomTexturedButtonWidget button2;
    private CustomTexturedButtonWidget button3;
    private TextWidget textWidget;
    private static final Identifier BUTTON_TEXTURE_1 = new Identifier("flying_machines", "textures/gui/button_1.png");
    private static final Identifier BUTTON_TEXTURE_2 = new Identifier("flying_machines", "textures/gui/button_2.png");
    private static final Identifier BUTTON_TEXTURE_3 = new Identifier("flying_machines", "textures/gui/button_3.png");

    private static final Identifier UNAVAIBLE_BUTTON_TEXTURE_1 = new Identifier("flying_machines", "textures/gui/unavaible_button_1.png");
    private static final Identifier UNAVAIBLE_BUTTON_TEXTURE_2 = new Identifier("flying_machines", "textures/gui/unavaible_button_2.png");
    private static final Identifier UNAVAIBLE_BUTTON_TEXTURE_3 = new Identifier("flying_machines", "textures/gui/unavaible_button_3.png");

    @Override
    protected void init() {
        super.init();

        int screenWidth = this.width;
        int screenHeight = this.height;

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

        textWidget = new TextWidget(textX, textY, 90, 45, Text.translatable("gui.entity.dylan.text").styled(style -> style.withColor(0x82878c)), this.textRenderer);
        this.addSelectableChild(textWidget);


        this.button1 = this.addDrawableChild(
                new CustomTexturedButtonWidget(
                        buttonX,
                        buttonY1,
                        buttonWidth,
                        buttonHeight,
                        0,
                        0,
                        20,
                        BUTTON_TEXTURE_1,
                        UNAVAIBLE_BUTTON_TEXTURE_1,
                        buttonWidth,
                        buttonHeight,
                        button -> handle_make_a_giftPress(),
                        Text.translatable("gui.button.make_a_gift")
                ));

        this.button2 = this.addDrawableChild(
                new CustomTexturedButtonWidget(
                        buttonX,
                        buttonY2,
                        buttonWidth,
                        buttonHeight,
                        0,
                        0,
                        20,
                        BUTTON_TEXTURE_2,
                        UNAVAIBLE_BUTTON_TEXTURE_2,
                        buttonWidth,
                        buttonHeight,
                        button -> handle_repairPress(),
                        Text.translatable("gui.button.repair")
                )
        );

        this.button3 = this.addDrawableChild(
                new CustomTexturedButtonWidget(
                        buttonX,
                        buttonY3,
                        buttonWidth,
                        buttonHeight,
                        0,
                        0,
                        20,
                        BUTTON_TEXTURE_3,
                        UNAVAIBLE_BUTTON_TEXTURE_3,
                        buttonWidth,
                        buttonHeight,
                        button -> handle_settings(),
                        Text.translatable("gui.button.settings")
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

        Identifier itemId = new Identifier("flying_machines", "laptop");
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
        if (hasItemInHotbar && heldItemStack.getItem().equals(Registries.ITEM.get(new Identifier("flying_machines", "sys_cable")))) {
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
                } else if (SCHEME_CUSTOM.contains(heldItemStack.getItem())) {
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
            Screen settingsScreen = new DylanSettingsScreen(new DylanSettingsScreenHandler(0, player.getInventory()), player.getInventory(), Text.translatable("gui.dylan_settings_screen"));
            MinecraftClient.getInstance().setScreen(settingsScreen);
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