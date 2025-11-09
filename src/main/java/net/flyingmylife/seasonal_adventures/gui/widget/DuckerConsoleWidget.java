package net.flyingmylife.seasonal_adventures.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import net.flyingmylife.seasonal_adventures.SA;
import net.flyingmylife.seasonal_adventures.gui.screen.in_game.DuckerScreen;
import net.flyingmylife.seasonal_adventures.gui.utils.RenderingUtils;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;
import java.util.*;

public class DuckerConsoleWidget extends ClickableWidget {
    private static final Identifier SCROLLER_TEXTURE = Identifier.of(SA.MOD_ID, "ducker/scroller");
    private static final String TYPING_ANIM_CHARS = "qwertyuiopEasdffghjklzxcvbnm|/_><123478993?⚠�";
    private static final String LOADING_CHARS = "|/-\\";
    private static final double TYPING_SPEED = 14.5;

    private final TextRenderer textRenderer;

    private final List<Entry> entries = new ArrayList<>();
    private StringBuilder currentInput = new StringBuilder();
    private final Deque<String> response = new ArrayDeque<>();
    private StringBuilder currentResponse = new StringBuilder("Что вы хотите сделать ?");
    private long lastCharTypedTime = 0;
    private long lastKeyPressTime = 0;
    private boolean acceptingInput = false;
    private double deltaSeconds = 0d;
    private double animStartTime = 0d;
    private double scrollAmount = 0d;

    private long lastTimeMs = System.currentTimeMillis();
    private int loadingIndex = 0;
    private double nextCharTime = 0d;
    public boolean isTyping = true;

    private boolean atBottom = true;
    private int relativeHeight;
    private int totalHeight;
    private int visibleHeight;

    public DuckerConsoleWidget(int x, int y, int width, int height, TextRenderer textRenderer) {
        super(x, y, width, height, Text.empty());
        this.textRenderer = textRenderer;
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        if (System.currentTimeMillis() - lastCharTypedTime < 5) return false;

        if (acceptingInput) {
            currentInput.append(chr);
        }
        lastCharTypedTime = System.currentTimeMillis();
        return super.charTyped(chr, modifiers);
    }
    private void handleEnter() {
        if (!currentInput.isEmpty()){
            entries.add(new Entry(EntryType.INPUT, currentInput.toString()));
            Response output = ResponseHandler.reviewInput(currentInput.toString());
            String outputMessage = output.getOutput().getFirst();
            currentResponse = new StringBuilder(outputMessage);
            if (output.getOutput().size() >= 2) {
                for(String out : output.getOutput().subList(1, output.getOutput().size())) {
                    response.addLast(out);
                }
            }
            if (output.askAgain()) {
                response.addLast(ResponseHandler.DEFAULT_RESPONSE);
            }
            animStartTime = deltaSeconds + (outputMessage.length() * 0.1) * 0.26;
            currentInput = new StringBuilder();
            acceptingInput = false;
        }
    }
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (System.currentTimeMillis() - lastKeyPressTime < 5) return false;
        if (acceptingInput) {
            if (keyCode == GLFW.GLFW_KEY_BACKSPACE) {
                if (!currentInput.isEmpty()) {
                    currentInput.deleteCharAt(currentInput.length() - 1);
                }
            }

            if (keyCode == GLFW.GLFW_KEY_ENTER) {
                handleEnter();
            }
        }
        lastKeyPressTime = System.currentTimeMillis();
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    public void tick() {
    }
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        scrollAmount -= verticalAmount * 10;
        if (verticalAmount > 0.5) atBottom = false;
        if (scrollAmount < 0) scrollAmount = 0;
        return true;
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        double now = System.currentTimeMillis();
        deltaSeconds += (now - lastTimeMs) / 1000;
        lastTimeMs = (long) now;
        if (deltaSeconds >= nextCharTime) {
            loadingIndex = (loadingIndex < LOADING_CHARS.length() - 1) ? loadingIndex + 1 : 0;
            nextCharTime = deltaSeconds + 0.08d;
        }
        byte state = (byte) ((int) (deltaSeconds * 2) % 2);
        atBottom = relativeHeight == scrollAmount;
        //вывод предыдущих запросов
        StringBuilder message = new StringBuilder();
        for (Entry entry : entries) {
            message.append(entry.getType() == EntryType.OUTPUT ? "SYS> " : "> ")
                    .append(entry.getContent())
                    .append("\n");
        }
        //вывод текущего запроса
        if (acceptingInput) {
            if (state == 0 && currentInput.isEmpty()) {
                message.append("> ");
            }

            if (!currentInput.isEmpty()) {
                message.append("> ").append(currentInput).append((state == 0) ? "_" : "");
            }
        } else {
            double elapsed = deltaSeconds - animStartTime;
            int visibleChars = (int) (elapsed * TYPING_SPEED);
            message.append("SYS> ");

            if (visibleChars < 0) {
                message.append(LOADING_CHARS.charAt(loadingIndex));
            } else if (visibleChars >= currentResponse.length()) {
                visibleChars = currentResponse.length();
                message.append(currentResponse, 0, visibleChars);

                entries.add(new Entry(EntryType.OUTPUT, currentResponse.toString()));
                currentResponse.delete(0, currentResponse.length());

                if (response.isEmpty()) {
                    acceptingInput = true;
                    isTyping = false;
                } else {
                    String nextResponse = response.removeFirst();
                    currentResponse.append(nextResponse);

                    animStartTime = deltaSeconds + (nextResponse.length() * 0.1) * 0.26;
                    isTyping = false;
                }
            } else {
                isTyping = true;
                message.append(currentResponse, 0, visibleChars);

                Random random = new Random(System.currentTimeMillis());
                message.append(TYPING_ANIM_CHARS.charAt(random.nextInt(TYPING_ANIM_CHARS.length())));
            }
        }

        int maxWidth = this.width - 8;
        List<OrderedText> lines = textRenderer.wrapLines(Text.of(message.toString()), maxWidth);
        totalHeight = lines.size() * (textRenderer.fontHeight + 1);
        visibleHeight = this.height - 4;

        if (scrollAmount > totalHeight - visibleHeight) {
            scrollAmount = Math.max(totalHeight - visibleHeight, 0);
        }

        int baseX = getX() + 4;
        int baseY = getY() + 2 - (int) scrollAmount;
        int textColor = DuckerScreen.BASE_COLOR;

        context.enableScissor(getX(), getY(), getX() + width, getY() + height);

        for (OrderedText line : lines) {
            if (baseY + textRenderer.fontHeight >= getY() && baseY <= getY() + height) {
                context.drawText(textRenderer, line, baseX, baseY, textColor, false);
            }
            baseY += textRenderer.fontHeight + 1;
        }

        context.disableScissor();

        relativeHeight = totalHeight - visibleHeight;

        if (atBottom) {
            scrollAmount = Math.max(0, totalHeight - visibleHeight);
        }
        drawScrollbar(context);
    }

    private void drawScrollbar(DrawContext context) {
        int i = this.getScrollbarThumbHeight();
        int j = this.getX() + this.width - 1;
        int k = Math.max(this.getY() - 3, (int)this.scrollAmount * (this.height - i) / this.relativeHeight + this.getY() - 4);
        RenderSystem.enableBlend();
        RenderSystem.setShaderColor(1f, 1f, 1f, 0.8f);
        context.drawGuiTexture(SCROLLER_TEXTURE, j, k, 6, i);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.disableBlend();
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
            int i = this.getScrollbarThumbHeight();
            int j = this.getX() + this.width - 1;
            int k = Math.max(this.getY() - 3, (int)this.scrollAmount * (this.height - i) / this.relativeHeight + this.getY() - 4);
            if (RenderingUtils.isMouseWithinBounds(j, k, 6, i, mouseX, mouseY)) {
                scrollAmount += deltaY * 5;
                if (scrollAmount < 0) scrollAmount = 0;
            }
        }
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    private int getScrollbarThumbHeight() {
        return Math.min(height + 6, (int) (height * ((float) visibleHeight / totalHeight)) + 6);
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
    public static class ResponseHandler {
        private static final List<Response> responses = new ArrayList<>();
        public static final String UNKNOWN_RESPONSE = "Пожалуйста уточните ваш запрос";
        public static final String DEFAULT_RESPONSE = "Что вы хотите сделать ?";

        public static void addResponse(String tokens, int minMatch, String output) {
            responses.add(new Response(tokens, minMatch, output));
        }
        public static void addResponse(String tokens, int minMatch, List<String> output) {
            responses.add(new Response(tokens, minMatch, output, true));
        }

        public static Response reviewInput(String input) {
            input = input.replace(',', ' ');
            input = input.replace('.', ' ');
            for (Response response : responses) {
                if (response.reviewInput(input.toLowerCase(Locale.forLanguageTag("ru")))) {
                    return response;
                }
            }
            return new Response("", 0, UNKNOWN_RESPONSE, true);
        }
    }
    public static class Response {
        private final HashSet<String> tokens = new HashSet<>();
        private final int minMatch;
        private final List<String> output;
        private final boolean askAgain;

        public Response(String tokens, int minMatch, String output, boolean askAgain) {
            this(tokens, minMatch, Collections.singletonList(output), askAgain);
        }

        public Response(String tokens, int minMatch, List<String> output, boolean askAgain) {
            StringTokenizer st = new StringTokenizer(tokens.toLowerCase(Locale.forLanguageTag("ru")));
            while (st.hasMoreTokens()) {
                this.tokens.add(st.nextToken());
            }

            this.askAgain = askAgain;
            this.output = output;
            this.minMatch = minMatch;
        }

        public Response(String tokens, int minMatch, List<String> output) {
            this(tokens, minMatch, output, false);
        }

        public Response(String tokens, int minMatch, String output) {
            this(tokens, minMatch, output, true);
        }

        public boolean askAgain() {
            return askAgain;
        }

        public int getMinMatch() {
            return minMatch;
        }

        public boolean reviewInput(String input) {
            StringTokenizer st = new StringTokenizer(input.toLowerCase(Locale.forLanguageTag("ru")));
            int matched = 0;
            while (st.hasMoreTokens()) {
                String token = st.nextToken();
                if (tokens.contains(token)) {
                    matched++;
                }
            }

            return matched >= minMatch;
        }

        public List<String> getOutput() {
            return output;
        }
    }
    public enum EntryType {
        INPUT,
        OUTPUT,
    }
}