package net.packages.seasonal_adventures.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.packages.seasonal_adventures.gui.handler.ATMScreenHandler;
import net.packages.seasonal_adventures.util.rendering.RenderingUtils;
import net.packages.seasonal_adventures.gui.widgets.TexturedButtonWidget;
import net.packages.seasonal_adventures.gui.widgets.NumericTextFieldWidget;
import net.packages.seasonal_adventures.item.Items;
import net.packages.seasonal_adventures.network.server.BankingOperationsPacket;
import net.packages.seasonal_adventures.network.server.ItemGivenPacket;
import net.packages.seasonal_adventures.network.server.SpecificItemRemovalPacket;
import net.packages.seasonal_adventures.util.game.InventoryUtils;
import net.packages.seasonal_adventures.util.enums.BankingOperationType;
import net.packages.seasonal_adventures.world.PlayerLinkedData;
import net.packages.seasonal_adventures.world.data.persistent_state.WorldDataPersistentState;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

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

    private static final ItemStack [] denominations = { new ItemStack(Items.V1), new ItemStack(Items.V5), new ItemStack(Items.V10),
            new ItemStack(Items.V50), new ItemStack(Items.V100), new ItemStack(Items.V500),
            new ItemStack(Items.V1000), new ItemStack(Items.V10000)
    };

    int [] denominationMultipliers = {1, 5, 10, 50, 100, 500, 1000, 10000};

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
                         0,
                        REPLENISH_BUTTON,
                        UN_REPLENISH_BUTTON,
                        topButtonsWidth,
                        topButtonsHeight,
                        button -> handleReplenish(),
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
                        0,
                        WITHDRAW_BUTTON,
                        UN_WITHDRAW_BUTTON,
                        topButtonsWidth,
                        topButtonsHeight,
                        button -> handleWithdraw(),
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
                        0,
                        DEFAULT_BUTTON,
                        UN_DEFAULT_BUTTON,
                        defaultButtonWidth,
                        defaultButtonHeight,
                        button -> handlePlus(),
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
                        0,
                        DEFAULT_BUTTON,
                        UN_DEFAULT_BUTTON,
                        defaultButtonWidth,
                        defaultButtonHeight,
                        button -> handleMinus(),
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
                        0,
                        DEFAULT_BUTTON,
                        UN_DEFAULT_BUTTON,
                        defaultButtonWidth,
                        defaultButtonHeight,
                        button -> handleRequestCard(),
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
                        0,
                        ENTER_BUTTON,
                        UN_ENTER_BUTTON,
                        defaultButtonWidth,
                        defaultButtonHeight,
                        button -> handleEnter(),
                        Text.literal("")
                ));

        updateButtonState();
    }

    private void handleEnter() {
        assert this.client != null;
        PlayerEntity player = this.client.player;
        ItemStack cardStack = player.getInventory().getMainHandStack();
        if (!cardStack.isOf(Items.CARD)) {
            player.sendMessage(Text.translatable("message.seasonal_adventures.atm.fail.card_required").formatted(Formatting.RED, Formatting.BOLD), true);
            this.close();
            return;
        }
        NbtCompound cardNbt = cardStack.getNbt();
        assert cardNbt != null;
        String cardId = cardNbt.getString("cardId");
        AtomicReference<PlayerLinkedData> owner = new AtomicReference<>();
        WorldDataPersistentState.getServerState(Objects.requireNonNull(this.client.getServer())).playerBankingData.forEach((((uuid, playerLinkedData) -> {
            if (Objects.equals(cardId, playerLinkedData.cardId)){
                owner.set(playerLinkedData);
            }
        })));
        if (owner.get() == null) {
            player.sendMessage(Text.translatable("message.seasonal_adventures.atm.fail.identifying_player").formatted(Formatting.RED), true);
            this.close();
            return;
        }
        if (!Objects.equals(owner.get().nickname, player.getEntityName())) {
            player.sendMessage(Text.translatable("message.seasonal_adventures.atm.fail.identifying_player.contacting_owner").formatted(Formatting.RED, Formatting.BOLD), true);
            this.close();
            return;
        }

        int enteredValue = getUserInputValue();
        if (replenishMode) {
            if (getInventoryVAmount() < enteredValue) {
                player.sendMessage(Text.translatable("message.seasonal_adventures.atm.fail.insufficient_funds.replenish").formatted(Formatting.DARK_RED, Formatting.BOLD), true);
                this.close();
                return;
            }

            int leftValue = enteredValue;
            int[] availableDenominations = new int[denominations.length];
            int[] toRemove = new int[denominations.length];

            for (int i = 0; i < denominations.length; i++) {
                availableDenominations[i] = InventoryUtils.getItemAmount(player, denominations[i]);
            }

            for (int i = 0; i < denominations.length; i++) {
                if (enteredValue >= denominationMultipliers[i]) {
                    int num = Math.min(enteredValue / denominationMultipliers[i], availableDenominations[i]);
                    enteredValue -= num * denominationMultipliers[i];
                    toRemove[i] = num;
                }
            }

            if (enteredValue > 0) {
                player.sendMessage(Text.translatable("message.seasonal_adventures.atm.fail.changing_fail").formatted(Formatting.DARK_RED, Formatting.BOLD), true);
                this.close();
                return;
            }

            for (int i = 0; i < denominations.length; i++) {
                if (toRemove[i] > 0) {
                    removeItemStackFromPlayer(denominations[i], toRemove[i]);
                }
            }

            player.sendMessage(Text.translatable("message.seasonal_adventures.atm.success").formatted(Formatting.GREEN), true);
            BankingOperationsPacket.executeBasicOperations(BankingOperationType.REPLENISH, leftValue);
            this.close();
        } else {
            int onCardValue = getOnCardValue();
            if (onCardValue < enteredValue) {
                player.sendMessage(Text.translatable("message.seasonal_adventures.atm.fail.insufficient_funds.withdrawal").formatted(Formatting.RED, Formatting.BOLD), true);
                this.close();
                return;
            }

            int leftValue = enteredValue;
            int [] toAdd = new int[denominations.length];
            for (int i = denominations.length - 1; i >= 0; i--) {
                toAdd[i] = leftValue / denominationMultipliers[i];
                leftValue = leftValue % denominationMultipliers[i];
            }

            for (int i = 0; i < denominations.length; i++) {
                addItemStackToPlayer(denominations[i], toAdd[i]);
            }
            player.sendMessage(Text.translatable("message.seasonal_adventures.atm.success").formatted(Formatting.GREEN), true);
            BankingOperationsPacket.executeBasicOperations(BankingOperationType.WITHDRAW, enteredValue);
            this.close();
        }
    }
    private void renderBalance(DrawContext context, int mouseX, int mouseY, float delta) {
        PlayerEntity player = client.player;
        if (player.getInventory().getMainHandStack().isOf(Items.CARD)) {
            RenderingUtils.renderItemWithTooltip(context, player.getMainHandStack(), client.player, textRenderer, width - 96, 16, mouseX, mouseY);
            TextWidget balance = new TextWidget(width - 80, 16,64, 16, Text.literal(getOnCardValue() + "V"), textRenderer);
            balance.render(context, mouseX, mouseY, delta);
        } else {
            TextWidget unidentifiedBalance = new TextWidget(width - 80, 16,64, 16, Text.literal("-"), textRenderer);
            unidentifiedBalance.render(context, mouseX, mouseY, delta);
        }
    }

    private void removeItemStackFromPlayer(ItemStack itemStack, int count) {
        SpecificItemRemovalPacket.sendItemStackRemovalRequest(itemStack, count);
    }

    private void addItemStackToPlayer(ItemStack itemStack, int count) {
        ItemGivenPacket.sendItemStackGivenRequest(itemStack, count);
    }


    public int getOnCardValue() {
        assert client != null;
        assert client.player != null;
        ItemStack cardStack = client.player.getMainHandStack();
        NbtCompound cardNbt = cardStack.getNbt();
        assert cardNbt != null;
        String cardId = cardNbt.getString("cardId");
        AtomicInteger balance = new AtomicInteger();
        WorldDataPersistentState.getServerState(Objects.requireNonNull(this.client.getServer())).playerBankingData.forEach((((uuid, playerLinkedData) -> {
            if (Objects.equals(cardId, playerLinkedData.cardId)){
                balance.set(playerLinkedData.balance);
            }
        })));
        return balance.get();
    }
    public int getInventoryVAmount() {
        assert client != null;
        PlayerEntity player = client.player;
        assert player != null;
        int value = 0;
        for (int i = 0; i < denominations.length-1; i++) {
            value += InventoryUtils.getItemAmount(player, denominations[i]) * denominationMultipliers[i];
        }
        return value;
    }

    private void handleRequestCard() {
        BankingOperationsPacket.executeRequestCardOperation();
        this.close();
    }

    private void handlePlus() {
        updateUserInputValue();
        long value = getUserInputValue();
        if (value + 50 <= 99999999) {
            numericTextFieldWidget.setText(String.valueOf(value + 50));
        }
    }

    private void handleMinus() {
        updateUserInputValue();
        long value = getUserInputValue();
        if (value - 50 >= 0) {
            numericTextFieldWidget.setText(String.valueOf(value - 50));
        }
    }

    private void handleWithdraw() {
        if (replenishMode) {
            this.replenishMode = false;
        }
    }

    private void handleReplenish() {
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
        PlayerEntity player = this.client.player;
        assert player != null;
        long currentValue = getUserInputValue();
        minusButton.active = currentValue > 50;
        enterButton.active = currentValue >= 50 && currentValue <= 9999999;
        plusButton.active = currentValue < 9999950;
        requestCardButton.active = !BankingOperationsPacket.getPlayerCardStatus(client.getServer(), player);
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
        assert client != null;
        assert client.player != null;
        renderBalance(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        this.updateButtonState();
        this.updateUserInputValue();
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {

    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {

    }
}
