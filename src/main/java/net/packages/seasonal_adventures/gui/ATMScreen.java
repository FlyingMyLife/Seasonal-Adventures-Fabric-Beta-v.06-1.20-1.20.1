package net.packages.seasonal_adventures.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.packages.seasonal_adventures.events.JDBCardHandler;
import net.packages.seasonal_adventures.gui.handlers.ATMScreenHandler;
import net.packages.seasonal_adventures.gui.widgets.TexturedButtonWidget;
import net.packages.seasonal_adventures.gui.widgets.NumericTextFieldWidget;
import net.packages.seasonal_adventures.item.Items;
import net.packages.seasonal_adventures.network.*;
import net.packages.seasonal_adventures.world.PlayerLinkedData;
import net.packages.seasonal_adventures.world.data.PlayerDataPersistentState;

import java.util.Objects;

import static net.packages.seasonal_adventures.util.InventoryUtils.getItemAmount;

public class ATMScreen extends HandledScreen<ATMScreenHandler> {
    private int backgroundX;
    private int backgroundY;

    private static final Identifier BACKGROUND_TEXTURE   = new Identifier("seasonal_adventures", "textures/gui/atm/background.png");
    private static final Identifier REPLENISH_BUTTON = new Identifier("seasonal_adventures", "textures/gui/atm/replenish_button.png");
    private static final Identifier WITHDRAW_BUTTON = new Identifier("seasonal_adventures", "textures/gui/atm/withdraw_button.png");
    private static final Identifier DEFAULT_BUTTON = new Identifier("seasonal_adventures", "textures/gui/atm/default_button.png");
    private static final Identifier UN_DEFAULT_BUTTON = new Identifier("seasonal_adventures", "textures/gui/atm/un_default_button.png");
    private static final Identifier UN_REPLENISH_BUTTON = new Identifier("seasonal_adventures", "textures/gui/atm/un_replenish_button.png");
    private static final Identifier UN_WITHDRAW_BUTTON = new Identifier("seasonal_adventures", "textures/gui/atm/un_withdraw_button.png");
    private static final Identifier UN_ENTER_BUTTON = new Identifier("seasonal_adventures", "textures/gui/atm/un_enter_button.png");
    private static final Identifier ENTER_BUTTON = new Identifier("seasonal_adventures", "textures/gui/atm/enter_button.png");

    private int userInputValue = 0;
    private boolean replenishMode = true;

    private NumericTextFieldWidget numericTextFieldWidget;
    private TexturedButtonWidget requestCardButton;
    private TexturedButtonWidget replenishButton;
    private TexturedButtonWidget withdrawButton;
    private TexturedButtonWidget plusButton;
    private TexturedButtonWidget minusButton;
    private TexturedButtonWidget enterButton;
    private TextWidget textB;

    public ATMScreen(ATMScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        backgroundX = (this.width - 256) / 2;
        backgroundY = (this.height - 192) / 2;

        int topButtonsWidth = (int) (256 / 3.7101f);
        int topButtonsHeight = 192 / 8;

        int replenishButtonX = backgroundX + 53;
        int replenishButtonY = backgroundY + 21;

        int withdrawButtonX = backgroundX + 134;
        int withdrawButtonY = backgroundY + 21;

        int defaultButtonWidth = 35;
        int defaultButtonHeight = 24;

        int plusButtonX = backgroundX + 168;
        int plusButtonY = backgroundY + 76;

        int minusButtonX = backgroundX + 53;
        int minusButtonY = backgroundY + 76;

        int requestCardButtonX = backgroundX + 14;
        int requestCardButtonY = backgroundY + 147;

        int enterButtonX = backgroundX + 207;
        int enterButtonY = backgroundY + 147;

        int numericTextFieldWidgetX = backgroundX + 105;
        int numericTextFieldWidgetY = backgroundY + 84;

        numericTextFieldWidget = new NumericTextFieldWidget(textRenderer, numericTextFieldWidgetX, numericTextFieldWidgetY, 60, 22, Text.literal("Amount"));
        numericTextFieldWidget.setMaxLength(7);
        numericTextFieldWidget.setText("50");

        addDrawableChild(numericTextFieldWidget);

        textB = new TextWidget((this.width/2)-45, backgroundY + 146, 90, 28, Text.literal("JDBank LLC").styled(style -> style.withColor(0xf87224)), this.textRenderer);


        replenishButton = addDrawableChild(
                new TexturedButtonWidget(
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
                        Text.translatable("gui.seasonal_adventures.button.replenish")
                ));
        withdrawButton = addDrawableChild(
                new TexturedButtonWidget(
                        withdrawButtonX,
                        withdrawButtonY,
                        topButtonsWidth,
                        topButtonsHeight,
                        0,
                        0,
                        20,
                        WITHDRAW_BUTTON,
                        UN_WITHDRAW_BUTTON,
                        topButtonsWidth,
                        topButtonsHeight,
                        button -> handle_withdraw(),
                        Text.translatable("gui.seasonal_adventures.button.withdraw")
                ));
        plusButton = addDrawableChild(
                new TexturedButtonWidget(
                        plusButtonX,
                        plusButtonY,
                        defaultButtonWidth,
                        defaultButtonHeight,
                        0,
                        0,
                        24,
                        DEFAULT_BUTTON,
                        UN_DEFAULT_BUTTON,
                        defaultButtonWidth,
                        defaultButtonHeight,
                        button -> handle_plus(),
                        Text.literal("+ 50")
                ));
        minusButton = addDrawableChild(
                new TexturedButtonWidget(
                        minusButtonX,
                        minusButtonY,
                        defaultButtonWidth,
                        defaultButtonHeight,
                        0,
                        0,
                        24,
                        DEFAULT_BUTTON,
                        UN_DEFAULT_BUTTON,
                        defaultButtonWidth,
                        defaultButtonHeight,
                        button -> handle_minus(),
                        Text.literal("- 50")
                ));
        requestCardButton = addDrawableChild(
                new TexturedButtonWidget(
                        requestCardButtonX,
                        requestCardButtonY,
                        defaultButtonWidth,
                        defaultButtonHeight,
                        0,
                        0,
                        24,
                        DEFAULT_BUTTON,
                        UN_DEFAULT_BUTTON,
                        defaultButtonWidth,
                        defaultButtonHeight,
                        button -> handle_request_card(),
                        Text.literal("+")
                ));
        enterButton = addDrawableChild(
                new TexturedButtonWidget(
                        enterButtonX,
                        enterButtonY,
                        defaultButtonWidth,
                        defaultButtonHeight,
                        0,
                        0,
                        20,
                        ENTER_BUTTON,
                        UN_ENTER_BUTTON,
                        defaultButtonWidth,
                        defaultButtonHeight,
                        button -> handle_enter(),
                        Text.literal("")
                ));

        updateButtonState();
    }

    private void handle_enter() {
        assert this.client != null;
        assert this.client.player != null;
        if (!this.client.player.getInventory().getMainHandStack().isOf(Items.CARD)) {
            this.client.player.sendMessage(Text.translatable("message.seasonal_adventures.atm.fail.card_required").formatted(Formatting.RED, Formatting.BOLD), true);
            this.close();
            return;
        }

        int enteredValue = getUserInputValue();
        int[] denominations = {10000, 1000, 500, 100, 50, 10, 5, 1};
        ItemStack[] items = {new ItemStack(Items.V10000), new ItemStack(Items.V1000), new ItemStack(Items.V500),
                new ItemStack(Items.V100), new ItemStack(Items.V50), new ItemStack(Items.V10),
                new ItemStack(Items.V5), new ItemStack(Items.V1)};
        int[] availableAmounts = replenishMode ? new int[denominations.length] : getDenominationsFromCard(denominations);

        BankingOperationsPacket.executeOperation(replenishMode ? OperationType.REPLENISH : OperationType.WITHDRAW, enteredValue);

        if (replenishMode) {
            int totalValue = getTotalValue(denominations, availableAmounts);
            if (totalValue < enteredValue) {
                this.client.player.sendMessage(Text.translatable("message.seasonal_adventures.atm.fail.insufficient_funds.replenish").formatted(Formatting.DARK_RED, Formatting.BOLD), true);
                this.close();
                return;
            }
            processReplenish(enteredValue, denominations, availableAmounts, items);
        } else {
            int onCardValue = getCurrencyAmount();
            if (onCardValue < enteredValue) {
                this.client.player.sendMessage(Text.translatable("message.seasonal_adventures.atm.fail.insufficient_funds.withdrawal").formatted(Formatting.RED, Formatting.BOLD), true);
                this.close();
                return;
            }
            processWithdrawal(enteredValue, denominations, items);
        }
    }

    private int[] getDenominationsFromCard(int[] denominations) {
        assert this.client != null;
        assert this.client.player != null;
        int[] availableAmounts = new int[denominations.length];
        ItemStack[] items = {new ItemStack(Items.V1), new ItemStack(Items.V5), new ItemStack(Items.V10),
                new ItemStack(Items.V50), new ItemStack(Items.V100), new ItemStack(Items.V500),
                new ItemStack(Items.V1000), new ItemStack(Items.V10000)};
        for (int i = 0; i < denominations.length; i++) {
            availableAmounts[i] = getItemAmount(this.client.player, items[i]);
        }
        return availableAmounts;
    }

    private int getTotalValue(int[] denominations, int[] availableAmounts) {
        int totalValue = 0;
        for (int i = 0; i < denominations.length; i++) {
            totalValue += availableAmounts[i] * denominations[i];
        }
        return totalValue;
    }

    private void processReplenish(int enteredValue, int[] denominations, int[] availableAmounts, ItemStack[] items) {
        assert this.client != null;
        assert this.client.player != null;
        int valueToEnter = enteredValue;
        for (int i = 0; i < denominations.length; i++) {
            int num = Math.min(valueToEnter / denominations[i], availableAmounts[i]);
            valueToEnter -= num * denominations[i];
            removeItemFromPlayer(items[i].getItem(), num);
        }
        if (valueToEnter == 0) {
            updateBalanceMessage(enteredValue);
            this.close();
        } else {
            this.client.player.sendMessage(Text.translatable("message.seasonal_adventures.atm.fail.changing_fail").formatted(Formatting.DARK_RED, Formatting.BOLD), true);
            this.close();
        }
    }

    private void processWithdrawal(int enteredValue, int[] denominations, ItemStack[] items) {
        int remainingValue = enteredValue;
        for (int i = 0; i < denominations.length; i++) {
            int count = remainingValue / denominations[i];
            remainingValue %= denominations[i];
            addItemToPlayer(items[i].getItem(), count);
        }
        this.close();
    }

    private void removeItemFromPlayer(Item item, int count) {
        SpecificItemRemovalPacket.sendItemRemovalRequest(item, count);
    }

    private void addItemToPlayer(Item item, int count) {
        ItemGivenPacket.sendItemGivenRequest(item, count);
    }

    private void updateBalanceMessage(int enteredValue) {
        assert this.client != null;
        assert this.client.player != null;
        MutableText currentT = Text.translatable("message.seasonal_adventures.atm.success.current_balance").formatted(Formatting.AQUA);
        Text balanceT = Text.literal("" + enteredValue).formatted(Formatting.ITALIC, Formatting.DARK_PURPLE);
        this.client.player.sendMessage(currentT.append(balanceT), true);
    }

    public int getCurrencyAmount() {
        assert this.client != null;
        assert this.client.player != null;
        PlayerLinkedData playerState = PlayerDataPersistentState.getPlayerState(this.client.player);
        return playerState.currencyAmount;
    }


    private void handle_request_card() {
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
            int newValue = Integer.parseInt(text);
            if (newValue >= 0 && newValue <= 99999999) {
                userInputValue = newValue;
            }
        } catch (NumberFormatException e) {
        }
    }

    public int getUserInputValue() {
        return userInputValue;
    }

    private void updateButtonState() {
        assert this.client != null;
        long currentValue = getUserInputValue();
        minusButton.active = currentValue > 50;
        enterButton.active = currentValue >= 50 && currentValue <= 9999999;
        plusButton.active = currentValue < 9999950;
        requestCardButton.active = !JDBCardHandler.playerHasCard(this.client.player);
        replenishButton.active = !replenishMode;
        withdrawButton.active = replenishMode;
    }

    @Override
    public void renderBackground(DrawContext context) {
        RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);
        context.drawTexture(BACKGROUND_TEXTURE, backgroundX, backgroundY, 0, 0, 256, 192, 256, 192);
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
