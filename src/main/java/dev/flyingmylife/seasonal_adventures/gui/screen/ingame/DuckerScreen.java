package dev.flyingmylife.seasonal_adventures.gui.screen.ingame;

import dev.flyingmylife.seasonal_adventures.SA;
import dev.flyingmylife.seasonal_adventures.gui.handler.DuckerScreenHandler;
import dev.flyingmylife.seasonal_adventures.gui.widgets.DuckerDialogueWindowWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class DuckerScreen extends HandledScreen<DuckerScreenHandler>{
    private int x;
    private int y;
    private static final String CURSED_CHARACTER_LIST = "ABCDEFᔑꙚƵͶӾɊԆƩ99301_<>'.,";
    private static final int WIDTH = 52;
    private static final int MAX_LIFESPAN = 120;
    public static final int BASE_COLOR = 0x2fce6d;
    private static final Identifier BASE_SCREEN_TEXTURES = Identifier.of(SA.MOD_ID, "ducker/ducker_base");
    private static final Random RANDOM = new Random();
    private final List<FallingCharacter> low_speed_characters = new ArrayList<>();
    private final List<FallingCharacter> high_speed_characters = new ArrayList<>();
    DuckerDialogueWindowWidget windowWidget;


    public DuckerScreen(DuckerScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }


    @Override
    protected void init() {
        super.init();
        x = this.width/2 - 192;
        y = this.height/2 - 123;
        windowWidget = new DuckerDialogueWindowWidget(
                x + 28,
                y + 27,
                257,
                192,
                textRenderer
        );
        addDrawableChild(windowWidget);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        windowWidget.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        windowWidget.typeChar(chr);
        return true;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 259) {
            windowWidget.backspace();
            return true;
        } else if (keyCode == 257 || keyCode == 335) {
            windowWidget.enter();
            return true;
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    protected void handledScreenTick() {
        super.handledScreenTick();
        charFallUpdate();
        windowWidget.tick();
    }
    private void charFallUpdate() {
        Iterator<FallingCharacter> low_speed_iterator = low_speed_characters.iterator();
        while (low_speed_iterator.hasNext()) {
            FallingCharacter character = low_speed_iterator.next();
            character.y += 3;
            if (character.y - character.startY >= MAX_LIFESPAN) {
                low_speed_iterator.remove();
            }
        }
        if (RANDOM.nextInt(4) == 0) {
            low_speed_characters.add(new FallingCharacter(RANDOM.nextInt(WIDTH), y));
        }
        Iterator<FallingCharacter> high_speed_iterator = high_speed_characters.iterator();
        while (high_speed_iterator.hasNext()) {
            FallingCharacter character = high_speed_iterator.next();
            character.y += 6;
            if (character.y - character.startY >= MAX_LIFESPAN) {
                high_speed_iterator.remove();
            }
        }
        if (RANDOM.nextInt(4) == 0) {
            high_speed_characters.add(new FallingCharacter(RANDOM.nextInt(WIDTH), y));
        }
    }


    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        super.renderBackground(context, mouseX, mouseY, delta);
        context.drawGuiTexture(RenderLayer::getGuiTextured, BASE_SCREEN_TEXTURES, 384, 384, 0, 0, x, y, 384, 246);
        for (FallingCharacter character : low_speed_characters) {
            context.drawText(MinecraftClient.getInstance().textRenderer, Character.toString(character.character), x + character.x + 299, character.y + 98, DuckerScreen.BASE_COLOR, true);
        }
        for (FallingCharacter character : high_speed_characters) {
            context.drawText(MinecraftClient.getInstance().textRenderer, Character.toString(character.character), x + character.x + 299, character.y + 98, DuckerScreen.BASE_COLOR, true);
        }
        context.drawGuiTexture(RenderLayer::getGuiTextured, BASE_SCREEN_TEXTURES, 384, 384, 0, 247, x + 291, y + 24, 68, 74);
        context.drawGuiTexture(RenderLayer::getGuiTextured, BASE_SCREEN_TEXTURES, 384, 384, 291, 247, x + 291, y + 224,68, 22);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if (windowWidget.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount)) {
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {

    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
    }

    private static class FallingCharacter {
        final char character;
        final int x;
        int y;
        final int startY;

        FallingCharacter(int x, int y) {
            this.character = CURSED_CHARACTER_LIST.charAt(RANDOM.nextInt(CURSED_CHARACTER_LIST.length()));
            this.x = x;
            this.y = y;
            this.startY = y;
        }
    }
}
