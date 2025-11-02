package net.flyingmylife.seasonal_adventures.gui.widget;

import net.flyingmylife.seasonal_adventures.SA;
import net.flyingmylife.seasonal_adventures.gui.screen.in_game.DuckerScreen;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DuckerDialogueWindowWidget extends ClickableWidget {
    private static final Identifier SCROLLER_TEXTURE = Identifier.of(SA.MOD_ID, "ducker/scroller");
    private static final String TYPING_ANIM_CHARS = "qwertyuiopEasdffghjklzxcvbnm|/_><12345678993?⚠�";
    private static final double TYPING_SPEED = 14.0;

    private final TextRenderer textRenderer;

    private final List<Entry> entries = new ArrayList<>();
    private final StringBuilder currentInput = new StringBuilder();
    private final StringBuilder response = new StringBuilder("Что вы хотите сделать ?");

    private boolean acceptingInput = false;
    private double deltaSeconds = 0d;
    private double animStartTime = 0d;
    private double scrollAmount = 0d;

    private long lastTimeNs = System.nanoTime();

    public DuckerDialogueWindowWidget(int x, int y, int width, int height, TextRenderer textRenderer) {
        super(x, y, width, height, Text.empty());
        this.textRenderer = textRenderer;
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        animStartTime = deltaSeconds;
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        return super.charTyped(chr, modifiers);
    }

    public void tick() {
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        scrollAmount -= verticalAmount * 10;
        if (scrollAmount < 0) scrollAmount = 0;
        return true;
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        long now = System.nanoTime();
        deltaSeconds += (now - lastTimeNs) / 1000000000.0;
        lastTimeNs = now;

        byte state = (byte) ((int) (deltaSeconds * 2) % 2);

        StringBuilder message = new StringBuilder();
        for (Entry entry : entries) {
            message.append(entry.getType() == EntryType.OUTPUT ? "SYS> " : "< ")
                    .append(entry.getContent()).append("\n");
        }

        if (acceptingInput) {
            if (currentInput.isEmpty()) {
                if (state == 0) message.append("< ");
            } else {
                message.append("< ").append(currentInput);
                if (state == 0) message.append("_");
            }
        } else {
            double elapsed = deltaSeconds - animStartTime;
            int visibleChars = (int) (elapsed * TYPING_SPEED);
            int end = Math.min(visibleChars, response.length());
            message.append("SYS> ").append(response, 0, end);
            if (visibleChars >= response.length()) {
                acceptingInput = true;
                entries.add(new Entry(EntryType.OUTPUT, response.toString()));
                response.setLength(0);
            } else {
                Random random = new Random(System.currentTimeMillis());
                message.append(TYPING_ANIM_CHARS.charAt(random.nextInt(TYPING_ANIM_CHARS.length())));
            }
        }

        String[] lines = message.toString().split("\n");
        int totalHeight = lines.length * (textRenderer.fontHeight + 1);
        int visibleHeight = this.height - 4;
        if (scrollAmount > totalHeight - visibleHeight) {
            scrollAmount = Math.max(totalHeight - visibleHeight, 0);
        }

        int baseY = this.getY() + 2 - (int) scrollAmount;
        int baseX = this.getX() + 4;

        context.enableScissor(getX(), getY(), getX() + width, getY() + height);

        int textColor = DuckerScreen.BASE_COLOR;
        for (String line : lines) {
            if (baseY + textRenderer.fontHeight >= getY() && baseY <= getY() + height) {
                context.drawText(textRenderer, line, baseX, baseY, textColor, false);
            }
            baseY += textRenderer.fontHeight + 1;
        }

        context.disableScissor();
    }

    public static class Entry {
        private final EntryType type;
        private String content;

        public Entry(EntryType type, String content) {
            this.type = type;
            this.content = content;
        }

        public EntryType getType() {
            return type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public enum EntryType {
        INPUT,
        OUTPUT,
    }
}