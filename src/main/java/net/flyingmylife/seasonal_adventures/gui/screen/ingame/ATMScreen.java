package net.flyingmylife.seasonal_adventures.gui.screen.ingame;

import net.flyingmylife.seasonal_adventures.SA;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.flyingmylife.seasonal_adventures.gui.RenderingUtils;
import net.flyingmylife.seasonal_adventures.gui.data.ButtonRenderData;
import net.flyingmylife.seasonal_adventures.gui.handler.ATMScreenHandler;
import net.flyingmylife.seasonal_adventures.gui.widget.MultiTexturedButtonWidget;
import net.flyingmylife.seasonal_adventures.gui.widget.NumericTextFieldWidget;
import net.flyingmylife.seasonal_adventures.item.SAItems;
import net.flyingmylife.seasonal_adventures.item.custom.CardItem;
import net.flyingmylife.seasonal_adventures.network.packet.c2s.BankingOperationsPacket;
import net.flyingmylife.seasonal_adventures.network.packet.c2s.InsertItemStackPacket;
import net.flyingmylife.seasonal_adventures.network.packet.c2s.RemoveItemPacket;
import net.flyingmylife.seasonal_adventures.util.game.InventoryUtils;
import net.flyingmylife.seasonal_adventures.network.payload.banking.BankingOperationType;
import net.flyingmylife.seasonal_adventures.world.data.PlayerLinkedData;
import net.flyingmylife.seasonal_adventures.world.data.persistent_state.WorldDataPersistentState;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class ATMScreen extends HandledScreen<ATMScreenHandler> {
    private int x;
    private int y;
    private static final int backgroundWidth = 256;
    private static final int backgroundHeight = 192;
    private static final Identifier BACKGROUND_TEXTURE = Identifier.of(SA.MOD_ID, "textures/gui/sprites/atm/atm.png");
    private static final Identifier REPLENISH_BUTTON = Identifier.of(SA.MOD_ID, "textures/gui/sprites/atm/replenish_button.png");
    private static final Identifier WITHDRAW_BUTTON = Identifier.of(SA.MOD_ID, "textures/gui/sprites/atm/withdraw_button.png");
    private static final Identifier DEFAULT_BUTTON = Identifier.of(SA.MOD_ID, "textures/gui/sprites/atm/default_button.png");
    private static final Identifier UN_DEFAULT_BUTTON = Identifier.of(SA.MOD_ID, "textures/gui/sprites/atm/un_default_button.png");
    private static final Identifier UN_REPLENISH_BUTTON = Identifier.of(SA.MOD_ID, "textures/gui/sprites/atm/un_replenish_button.png");
    private static final Identifier UN_WITHDRAW_BUTTON = Identifier.of(SA.MOD_ID, "textures/gui/sprites/atm/un_withdraw_button.png");
    private static final Identifier UN_ENTER_BUTTON = Identifier.of(SA.MOD_ID, "textures/gui/sprites/atm/un_enter_button.png");
    private static final Identifier ENTER_BUTTON = Identifier.of(SA.MOD_ID, "textures/gui/sprites/atm/enter_button.png");

    private int userInputValue = 0;
    private boolean replenishMode = true;

    private static final ItemStack [] denominations = { new ItemStack(SAItems.V1), new ItemStack(SAItems.V5), new ItemStack(SAItems.V10),
            new ItemStack(SAItems.V50), new ItemStack(SAItems.V100), new ItemStack(SAItems.V500),
            new ItemStack(SAItems.V1000), new ItemStack(SAItems.V10000)
    };

    int [] denominationMultipliers = {1, 5, 10, 50, 100, 500, 1000, 10000};

    private NumericTextFieldWidget numericTextFieldWidget;
    private MultiTexturedButtonWidget requestCardButton;
    private MultiTexturedButtonWidget replenishButton;
    private MultiTexturedButtonWidget withdrawButton;
    private MultiTexturedButtonWidget plusButton;
    private MultiTexturedButtonWidget minusButton;
    private MultiTexturedButtonWidget enterButton;
    private TextWidget textB;

    public ATMScreen(ATMScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();

        x = (this.width - 256) / 2;
        y = (this.height - 192) / 2;

        int topButtonsWidth = (int) (256 / 3.7101f);
        int topButtonsHeight = 192 / 8;

        int replenishButtonX = x + 53;
        int replenishButtonY = y + 21;

        int withdrawButtonX = x + 134;
        int withdrawButtonY = y + 21;

        int defaultButtonWidth = 35;
        int defaultButtonHeight = 24;

        int plusButtonX = x + 168;
        int plusButtonY = y + 76;

        int minusButtonX = x + 53;
        int minusButtonY = y + 76;

        int requestCardButtonX = x + 14;
        int requestCardButtonY = y + 147;

        int enterButtonX = x + 207;
        int enterButtonY = y + 147;

        int numericTextFieldWidgetX = x + 105;
        int numericTextFieldWidgetY = y + 84;

        numericTextFieldWidget = new NumericTextFieldWidget(textRenderer, numericTextFieldWidgetX, numericTextFieldWidgetY, 60, 22, Text.literal("Amount"));
        numericTextFieldWidget.setMaxLength(7);
        numericTextFieldWidget.setText("50");

        addDrawableChild(numericTextFieldWidget);

        textB = new TextWidget((this.width /2)-45, y + 146, 90, 28, Text.literal("JDBank LLC").styled(style -> style.withColor(0xf87224)), this.textRenderer);


        replenishButton = addDrawableChild(
                new MultiTexturedButtonWidget(
                        replenishButtonX,
                        replenishButtonY,
                        ButtonRenderData.of(topButtonsWidth, topButtonsHeight, REPLENISH_BUTTON, UN_REPLENISH_BUTTON),
                        Text.translatable("gui.seasonal_adventures.button.replenish"),
                        button -> handleReplenish()
                ));
        withdrawButton = addDrawableChild(
                new MultiTexturedButtonWidget(
                        withdrawButtonX,
                        withdrawButtonY,
                        ButtonRenderData.of(topButtonsWidth, topButtonsHeight, WITHDRAW_BUTTON, UN_WITHDRAW_BUTTON),
                        Text.translatable("gui.seasonal_adventures.button.withdraw"),
                        button -> handleWithdraw()
                ));
        plusButton = addDrawableChild(
                new MultiTexturedButtonWidget(
                        plusButtonX,
                        plusButtonY,
                        ButtonRenderData.of(defaultButtonWidth, defaultButtonHeight, DEFAULT_BUTTON, UN_DEFAULT_BUTTON),
                        Text.literal("+ 50"),
                        button -> handlePlus()
                ));
        minusButton = addDrawableChild(
                new MultiTexturedButtonWidget(
                        minusButtonX,
                        minusButtonY,
                        ButtonRenderData.of(defaultButtonWidth, defaultButtonHeight, DEFAULT_BUTTON, UN_DEFAULT_BUTTON),
                        Text.literal("- 50"),
                        button -> handleMinus()
                ));
        requestCardButton = addDrawableChild(
                new MultiTexturedButtonWidget(
                        requestCardButtonX,
                        requestCardButtonY,
                        ButtonRenderData.of(defaultButtonWidth, defaultButtonHeight, DEFAULT_BUTTON, UN_DEFAULT_BUTTON),
                        Text.literal("+"),
                        button -> handleRequestCard()
                ));
        enterButton = addDrawableChild(
                new MultiTexturedButtonWidget(
                        enterButtonX,
                        enterButtonY,
                        ButtonRenderData.of(defaultButtonWidth, defaultButtonHeight, ENTER_BUTTON, UN_ENTER_BUTTON),
                        Text.empty(),
                        button -> handleEnter()
                ));

        updateButtonState();
    }

    private void handleEnter() {
        assert this.client != null;
        PlayerEntity player = this.client.player;
        ItemStack cardStack = player.getInventory().getMainHandStack();
        if (!cardStack.isOf(SAItems.CARD)) {
            player.sendMessage(Text.translatable("message.seasonal_adventures.atm.fail.card_required").formatted(Formatting.RED, Formatting.BOLD), true);
            this.close();
            return;
        }
        String cardId = cardStack.get(CardItem.CARD_ID_COMPONENT_TYPE);
        AtomicReference<PlayerLinkedData> owner = new AtomicReference<>();
        AtomicReference<UUID> ownerUUID = new AtomicReference<>();
        WorldDataPersistentState.getServerState(Objects.requireNonNull(this.client.getServer())).playerBankingData.forEach((((uuid, playerLinkedData) -> {
            if (Objects.equals(cardId, playerLinkedData.cardId)){
                owner.set(playerLinkedData);
                ownerUUID.set(uuid);
            }
        })));
        if (owner.get() == null) {
            player.sendMessage(Text.translatable("message.seasonal_adventures.atm.fail.identifying_player").formatted(Formatting.RED), true);
            this.close();
            return;
        }
        if (!Objects.equals(owner.get().nickname, Objects.requireNonNull(player.getDisplayName()).getString())) {
            SA.LOGGER.info("Player {} is not the owner of card {}", player.getDisplayName().getString(), cardId);
            player.sendMessage(Text.translatable("message.seasonal_adventures.atm.fail.identifying_player.contacting_owner").formatted(Formatting.RED, Formatting.BOLD), true);
            player.sendMessage(Text.literal("This feature is in development").formatted(Formatting.ITALIC, Formatting.AQUA), false);
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
                    removeItemStackFromPlayer(denominations[i].getItem(), toRemove[i]);
                }
            }

            player.sendMessage(Text.translatable("message.seasonal_adventures.atm.success").formatted(Formatting.GREEN), true);
            BankingOperationsPacket.executeBasicOperations(BankingOperationType.REPLENISH, leftValue);
            this.close();
        }
        else {
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
        if (player.getInventory().getMainHandStack().isOf(SAItems.CARD)) {
            RenderingUtils.renderItemWithTooltip(context, player.getMainHandStack(), client.player, textRenderer, backgroundWidth - 96, 16, mouseX, mouseY);
            TextWidget balance = new TextWidget(width - 80, 16,64, 16, Text.literal(getOnCardValue() + "V"), textRenderer);
            balance.render(context, mouseX, mouseY, delta);
        } else {
            TextWidget unidentifiedBalance = new TextWidget(width - 80, 16,64, 16, Text.literal("-"), textRenderer);
            unidentifiedBalance.render(context, mouseX, mouseY, delta);
        }
    }

    private void removeItemStackFromPlayer(Item item, int count) {
        RemoveItemPacket.removeItem(item, count);
    }

    private void addItemStackToPlayer(ItemStack itemStack, int count) {
        InsertItemStackPacket.insertItems(itemStack.getItem(), count);
    }


    public int getOnCardValue() {
        assert client != null;
        assert client.player != null;
        ItemStack cardStack = client.player.getMainHandStack();
        String cardId = cardStack.get(CardItem.CARD_ID_COMPONENT_TYPE);
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
        for (int i = 0; i < denominations.length; i++) {
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
    public void renderInGameBackground(DrawContext context) {
        super.renderInGameBackground(context);
        context.drawGuiTexture(BACKGROUND_TEXTURE, x, y, backgroundWidth, backgroundHeight);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.textB.render(context, mouseX, mouseY, delta);
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
