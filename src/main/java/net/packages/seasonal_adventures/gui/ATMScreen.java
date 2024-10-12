package net.packages.seasonal_adventures.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.packages.seasonal_adventures.gui.handlers.ATMScreenHandler;
import net.packages.seasonal_adventures.gui.widgets.CustomTexturedButtonWidget;
import net.packages.seasonal_adventures.gui.widgets.NumericTextFieldWidget;
import net.packages.seasonal_adventures.item.Items;
import net.packages.seasonal_adventures.network.CardGivenPacket;
import net.packages.seasonal_adventures.network.ItemGivenPacket;
import net.packages.seasonal_adventures.network.SpecificItemRemovalPacket;

import javax.swing.text.Style;

import static net.packages.seasonal_adventures.util.InventoryUtils.getItemAmount;

public class ATMScreen extends HandledScreen<ATMScreenHandler> {
    private int BackgroundWidth;
    private int BackgroundHeight;
    private int BackgroundX;
    private int BackgroundY;
    private static final Identifier BACKGROUND_TEXTURE = new Identifier("seasonal_adventures", "textures/gui/atm_background.png");
    private static final Identifier REPLENISH_BUTTON = new Identifier("seasonal_adventures", "textures/gui/atm/replenish_button.png");
    private static final Identifier WITHDRAW_BUTTON = new Identifier("seasonal_adventures", "textures/gui/atm/withdraw_button.png");
    private static final Identifier DEFAULT_BUTTON = new Identifier("seasonal_adventures", "textures/gui/atm/default_button.png");
    private static final Identifier UN_DEFAULT_BUTTON = new Identifier("seasonal_adventures", "textures/gui/atm/un_default_button.png");
    private static final Identifier UN_REPLENISH_BUTTON = new Identifier("seasonal_adventures", "textures/gui/atm/un_replenish_button.png");
    private static final Identifier UN_WITHDRAW_BUTTON = new Identifier("seasonal_adventures", "textures/gui/atm/un_withdraw_button.png");
    private static final Identifier UN_ENTER_BUTTON = new Identifier("seasonal_adventures", "textures/gui/atm/un_enter_button.png");
    private static final Identifier ENTER_BUTTON = new Identifier("seasonal_adventures", "textures/gui/atm/enter_button.png");
    private long userInputValue = 0;
    private boolean replenishMode = true;
    private NumericTextFieldWidget numericTextFieldWidget;
    private CustomTexturedButtonWidget request_card_button;
    private CustomTexturedButtonWidget replenish_button;
    private CustomTexturedButtonWidget withdraw_button;
    private CustomTexturedButtonWidget plus_50_button;
    private CustomTexturedButtonWidget minus_50_button;
    private CustomTexturedButtonWidget enter_button;
    private TextWidget textB;
    public ATMScreen(ATMScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        BackgroundWidth = (this.width/4)*2;
        BackgroundHeight = (this.height/6)*4;
        BackgroundX = (this.width - BackgroundWidth) / 2;
        BackgroundY = (this.height - BackgroundHeight) / 2;

        int topButtonsWidth = (int) (BackgroundWidth / 3.7101f);
        int topButtonsHeight = BackgroundHeight / 8;

        int replenishButtonX = (int) (BackgroundX + (BackgroundWidth / 4.7f));
        int replenishButtonY = (int) (BackgroundY + (BackgroundHeight / 8.7f));


        ClientPlayerEntity player = this.client.player;
        numericTextFieldWidget = new NumericTextFieldWidget(textRenderer, 209, 118, 62, 17, Text.literal("Amount"));
        numericTextFieldWidget.setText("50");
        addDrawableChild(numericTextFieldWidget);
        textB = new TextWidget((this.width/2)-45, 184, 90, 28, Text.literal("JDBank LLC").styled(style -> style.withColor(0xf87224)), this.textRenderer);


        replenish_button = addDrawableChild(
                new CustomTexturedButtonWidget(
                        replenishButtonX,
                        replenishButtonY,
                        topButtonsWidth,
                        topButtonsHeight,
                        0,
                        0,
                        20,
                        REPLENISH_BUTTON,
                        UN_REPLENISH_BUTTON,
                        topButtonsWidth,
                        topButtonsHeight,
                        button -> handle_replenish(),
                        Text.translatable("gui.button.replenish")
                ));
        enter_button = addDrawableChild(
                new CustomTexturedButtonWidget(
                        319,
                        186,
                        35,
                        24,
                        0,
                        0,
                        20,
                        ENTER_BUTTON,
                        UN_ENTER_BUTTON,
                        35,
                        24,
                        button -> handle_enter(),
                        Text.literal("")
                ));
        withdraw_button = addDrawableChild(
                new CustomTexturedButtonWidget(
                        246,
                        60,
                        topButtonsWidth,
                        topButtonsHeight,
                        0,
                        0,
                        24,
                        WITHDRAW_BUTTON,
                        UN_WITHDRAW_BUTTON,
                        topButtonsWidth,
                        topButtonsHeight,
                        button -> handle_withdraw(),
                        Text.translatable("gui.button.withdraw")
                ));
        plus_50_button = addDrawableChild(
                new CustomTexturedButtonWidget(
                        279,
                        115,
                        35,
                        24,
                        0,
                        0,
                        24,
                        DEFAULT_BUTTON,
                        UN_DEFAULT_BUTTON,
                        35,
                        24,
                        button -> handle_plus(),
                        Text.literal("+ 50")
                ));
        minus_50_button = addDrawableChild(
                new CustomTexturedButtonWidget(
                        166,
                        115,
                        35,
                        24,
                        0,
                        0,
                        24,
                        DEFAULT_BUTTON,
                        UN_DEFAULT_BUTTON,
                        35,
                        24,
                        button -> handle_minus(),
                        Text.literal("- 50")
                ));
        request_card_button = addDrawableChild(
                new CustomTexturedButtonWidget(
                        126,
                        186,
                        35,
                        24,
                        0,
                        0,
                        24,
                        DEFAULT_BUTTON,
                        UN_DEFAULT_BUTTON,
                        35,
                        24,
                        button -> handle_request_card(),
                        Text.literal("+")
                ));

        updateButtonState();
    }

    private void handle_enter() {
        int fcount = 0;
        if (replenishMode) {
            if (this.client.player.getInventory().getMainHandStack().isOf(Items.CARD))
            {
                ItemStack v1 = new ItemStack(Items.V1);
                ItemStack v5 = new ItemStack(Items.V5);
                ItemStack v10 = new ItemStack(Items.V10);
                ItemStack v50 = new ItemStack(Items.V50);
                ItemStack v100 = new ItemStack(Items.V100);
                ItemStack v500 = new ItemStack(Items.V500);
                ItemStack v1000 = new ItemStack(Items.V1000);
                ItemStack v10000 = new ItemStack(Items.V10000);

                int av1 = getItemAmount(this.client.player, v1);
                int av5 = getItemAmount(this.client.player, v5);
                int av10 = getItemAmount(this.client.player, v10);
                int av50 = getItemAmount(this.client.player, v50);
                int av100 = getItemAmount(this.client.player, v100);
                int av500 = getItemAmount(this.client.player, v500);
                int av1000 = getItemAmount(this.client.player, v1000);
                int av10000 = getItemAmount(this.client.player, v10000);

                int enteredValue = (int) getUserInputValue();

                int totalValue = av1 + av5 * 5 + av10 * 10 + av50 * 50
                        + av100 * 100 + av500 * 500 + av1000 * 1000 + av10000 * 10000;
                int[] denominations = {10000, 1000, 500, 100, 50, 10, 5, 1};
                int[] availableAmounts = {av10000, av1000, av500, av100, av50, av10, av5, av1};
                if (totalValue >= enteredValue) {
                    int used10000 = 0;
                    int used1000 = 0;
                    int used500 = 0;
                    int used100 = 0;
                    int used50 = 0;
                    int used10 = 0;
                    int used5 = 0;
                    int used1 = 0;
                    int valueToEnter = enteredValue;
                    for (int i = 0; i < denominations.length; i++)
                    {
                        if (enteredValue >= denominations[i]) {
                            int num = Math.min(enteredValue / denominations[i], availableAmounts[i]);
                            enteredValue -= num * denominations[i];

                            switch (denominations[i]) {
                                case 10000:
                                    used10000 = num;
                                    break;
                                case 1000:
                                    used1000 = num;
                                    break;
                                case 500:
                                    used500 = num;
                                    break;
                                case 100:
                                    used100 = num;
                                    break;
                                case 50:
                                    used50 = num;
                                    break;
                                case 10:
                                    used10 = num;
                                    break;
                                case 5:
                                    used5 = num;
                                    break;
                                case 1:
                                    used1 = num;
                                    break;
                            }
                        }
                    }

                    if (enteredValue == 0)
                    {
                        int oldValue = (int) getCurrencyAmountFromItem(this.client.player.getInventory().getMainHandStack());
                        Text currentT = Text.translatable("message.atm.success.current_balance").formatted(Formatting.AQUA);
                        Text balanceT = Text.literal("" + (oldValue + valueToEnter)).formatted(Formatting.ITALIC, Formatting.DARK_PURPLE);
                        this.client.player.sendMessage(((MutableText) currentT).append(balanceT), true);
                        setCurrencyAmountToItem(this.client.player.getInventory().getMainHandStack(), oldValue + valueToEnter);
                        SpecificItemRemovalPacket.sendItemRemovalRequest(Items.V1, used1);
                        SpecificItemRemovalPacket.sendItemRemovalRequest(Items.V5, used5);
                        SpecificItemRemovalPacket.sendItemRemovalRequest(Items.V10, used10);
                        SpecificItemRemovalPacket.sendItemRemovalRequest(Items.V50, used50);
                        SpecificItemRemovalPacket.sendItemRemovalRequest(Items.V100, used100);
                        SpecificItemRemovalPacket.sendItemRemovalRequest(Items.V500, used500);
                        SpecificItemRemovalPacket.sendItemRemovalRequest(Items.V1000, used1000);
                        SpecificItemRemovalPacket.sendItemRemovalRequest(Items.V10000, used10000);
                        this.close();
                    } else
                    {
                        this.client.player.sendMessage(Text.translatable("message.atm.fail.changing_fail").formatted(Formatting.DARK_RED, Formatting.BOLD), true);
                        this.close();
                    }
                } else
                {
                    this.client.player.sendMessage(Text.translatable("message.atm.fail.insufficient_funds.replenish").formatted(Formatting.DARK_RED, Formatting.BOLD), true);
                    this.close();
                }
                
            } else
            {
                this.client.player.sendMessage(Text.translatable("message.atm.fail.card_required").formatted(Formatting.RED, Formatting.BOLD), true);
                this.close();
            }

        } else
        {
            if (this.client.player.getInventory().getMainHandStack().isOf(Items.CARD)) {
                int enteredValue = (int) getUserInputValue();
                int onCardValue = (int) getCurrencyAmountFromItem(this.client.player.getInventory().getMainHandStack());

                if (onCardValue >= enteredValue) {

                        int[] denominations = {10000, 1000, 500, 100, 50, 10, 5, 1};

                        int need1v = 0, need5v = 0, need10v = 0, need50v = 0,
                                need100v = 0, need500v = 0, need1000v = 0,
                                need10000v = 0;

                        for (int denomination : denominations) {
                            int count = enteredValue / denomination;
                            enteredValue %= denomination;
                            switch (denomination) {
                                case 10000:
                                    need10000v = count;
                                    break;
                                case 1000:
                                    need1000v = count;
                                    break;
                                case 500:
                                    need500v = count;
                                    break;
                                case 100:
                                    need100v = count;
                                    break;
                                case 50:
                                    need50v = count;
                                    break;
                                case 10:
                                    need10v = count;
                                    break;
                                case 5:
                                    need5v = count;
                                    break;
                                case 1:
                                    need1v = count;
                                    break;
                            }
                        }

                        setCurrencyAmountToItem(this.client.player.getInventory().getMainHandStack(), (getCurrencyAmountFromItem(this.client.player.getInventory().getMainHandStack()) - getUserInputValue()));
                        Text currentT = Text.translatable("message.atm.success.current_balance").formatted(Formatting.AQUA);
                        Text balanceT = Text.literal("" + (getCurrencyAmountFromItem(this.client.player.getInventory().getMainHandStack()))).formatted(Formatting.ITALIC, Formatting.DARK_PURPLE);
                        this.client.player.sendMessage(((MutableText) currentT).append(balanceT), true);
                        ItemGivenPacket.sendItemGivenRequest(Items.V1, need1v);
                        ItemGivenPacket.sendItemGivenRequest(Items.V5, need5v);
                        ItemGivenPacket.sendItemGivenRequest(Items.V10, need10v);
                        ItemGivenPacket.sendItemGivenRequest(Items.V50, need50v);
                        ItemGivenPacket.sendItemGivenRequest(Items.V100, need100v);
                        ItemGivenPacket.sendItemGivenRequest(Items.V500, need500v);
                        ItemGivenPacket.sendItemGivenRequest(Items.V1000, need1000v);
                        ItemGivenPacket.sendItemGivenRequest(Items.V10000, need10000v);
                        this.close();
                } else {
                    this.client.player.sendMessage(Text.translatable("message.atm.fail.insufficient_funds.withdrawal").formatted(Formatting.RED, Formatting.BOLD), true);
                    this.close();
                }
            } else {
                this.client.player.sendMessage(Text.translatable("message.atm.fail.card_required").formatted(Formatting.RED, Formatting.BOLD), true);
                this.close();
            }
        }
    }
    public static void setCurrencyAmountToItem(ItemStack itemStack, long currencyAmount) {
        NbtCompound tag = itemStack.getOrCreateNbt();
        tag.putLong("CurrencyAmount", currencyAmount);
        itemStack.setNbt(tag);
    }
    public static long getCurrencyAmountFromItem(ItemStack itemStack) {
        if (itemStack.hasNbt()) {
            NbtCompound tag = itemStack.getNbt();
            if (tag.contains("CurrencyAmount")) {
                return tag.getLong("CurrencyAmount");
            }
        }
        return 0L;
    }


    private void handle_request_card() {
        ClientPlayerEntity player = this.client.player;
        CardGivenPacket.GiveCard();
        this.close();
    }

    private void handle_plus() {
        updateUserInputValue();
        long value = getUserInputValue();
        if (value + 50 <= 99999999) {
            numericTextFieldWidget.setText(String.valueOf(value + 50));
        }
    }

    private void handle_minus() {
        updateUserInputValue();
        long value = getUserInputValue();
        if (value - 50 >= 0) {
            numericTextFieldWidget.setText(String.valueOf(value - 50));
        }
    }

    private void handle_withdraw() {
        if (replenishMode) {
            this.replenishMode = false;
        }
    }

    private void handle_replenish() {
        if (!replenishMode) {
            this.replenishMode = true;
        }
    }

    private void updateUserInputValue() {
        String text = numericTextFieldWidget.getText();
        try {
            long newValue = Long.parseLong(text);
            if (newValue >= 0 && newValue <= 99999999) {
                userInputValue = newValue;
            }
        } catch (NumberFormatException e) {
        }
    }

    public long getUserInputValue() {
        return userInputValue;
    }

    private void updateButtonState() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.world == null) {
            return;
        }

        long currentValue = getUserInputValue();
        minus_50_button.active = currentValue > 50;
        enter_button.active = currentValue >= 50 && currentValue <= 10000000;
        plus_50_button.active = currentValue < 10000000;

        replenish_button.active = !replenishMode;
        withdraw_button.active = replenishMode;
    }

    @Override
    public void renderBackground(DrawContext context) {
        super.renderBackground(context);
        RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);
        context.drawTexture(BACKGROUND_TEXTURE, BackgroundX, BackgroundY, 0, 0, BackgroundWidth, BackgroundHeight, BackgroundWidth, BackgroundHeight);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.textB.render(context, mouseX, mouseY, delta);
        this.renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        this.updateButtonState();
        this.updateUserInputValue();
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
    }
}
