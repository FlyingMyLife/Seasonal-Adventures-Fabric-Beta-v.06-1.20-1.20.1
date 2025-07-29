package net.flyingmylife.seasonal_adventures.gui.widget;

import net.flyingmylife.seasonal_adventures.SA;
import net.flyingmylife.seasonal_adventures.gui.screen.ingame.DuckerScreen;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ScrollableTextWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class DuckerDialogueWindowWidget extends ScrollableTextWidget {
    private static final Identifier SCROLLER_TEXTURE = Identifier.of(SA.MOD_ID, "ducker/scroller");

    private final List<String> lines = new ArrayList<>(List.of("SYS> Что вы хотите сделать?"));
    private final StringBuilder currentInput = new StringBuilder();
    private long lastBlink = 0;
    private boolean blinkOn = false;
    private boolean typing = false;

    public DuckerDialogueWindowWidget(int x, int y, int width, int height, TextRenderer textRenderer) {
        super(x, y, width, height, Text.literal(""), textRenderer);
    }

    public void tick() {
        if (System.currentTimeMillis() - lastBlink > 800) {
            lastBlink = System.currentTimeMillis();
            blinkOn = !blinkOn;
            updateText();
        }
    }

    public void typeChar(char c) {
        typing = true;
        currentInput.append(c);
        updateText();
    }

    public void backspace() {
        if (currentInput.length() > 0) {
            currentInput.setLength(currentInput.length() - 1);
        }
        typing = true;
        updateText();
    }

    public void enter() {
        lines.add("> " + currentInput.toString());
        lines.add("SYS> TEST");
        currentInput.setLength(0);
        typing = false;
        blinkOn = false;
        updateText();
    }

    private void updateText() {
        StringBuilder all = new StringBuilder();
        for (String line : lines) {
            all.append(line).append("\n");
        }

        if (typing) {
            all.append("> ").append(currentInput);
            all.append(blinkOn ? "_" : " ");
        } else {
            all.append(blinkOn ? ">" : ">_");
        }

        setMessage(Text.literal(all.toString()).styled(style -> style.withColor(DuckerScreen.BASE_COLOR)));
    }

    @Override
    public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        super.renderWidget(context, mouseX, mouseY, delta);
    }
}
