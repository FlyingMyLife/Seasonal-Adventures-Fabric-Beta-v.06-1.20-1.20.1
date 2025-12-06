package net.flyingmylife.seasonal_adventures.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import net.flyingmylife.seasonal_adventures.SA;
import net.flyingmylife.seasonal_adventures.gui.data.ducker.ShelterData;
import net.flyingmylife.seasonal_adventures.gui.screen.in_game.DuckerScreen;
import net.flyingmylife.seasonal_adventures.gui.utils.RenderingUtils;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.*;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Function;

public class DuckerIOWidget extends ClickableWidget {
    private static final Identifier SCROLLER_TEXTURE = Identifier.of(SA.MOD_ID, "ducker/scroller");
    private static final String TYPING_ANIM_CHARS = "qwertyuiopуasdffghjklzxcvbnm|/_><123478993?⚠?";
    private static final String LOADING_CHARS = "|/-\\";

    private static final Map<Character, Function<Style, Style>> STYLE_MAP =
            Map.of(
                    'o', style -> style.withObfuscated(true),
                    'i', style -> style.withItalic(true),
                    'b', style -> style.withBold(true),
                    'r', style -> style.withColor(DuckerScreen.MALFUNCTION_COLOR)
            );

    private final TextRenderer textRenderer;

    private List<Entry> entries = new ArrayList<>();
    private StringBuilder currentInput = new StringBuilder();
    private Deque<String> response = new ArrayDeque<>();
    private StringBuilder lastResponse = new StringBuilder("Что вы хотите сделать ?");

    private double typingSpeed = 14.5; //ch/s

    private long lastCharTypedTime = 0;
    private long lastKeyPressTime = 0;

    private boolean acceptingInput = false;

    private double deltaSeconds = 0d;
    private double animStartTime = 0d;
    private double scrollAmount = 0d;

    private long lastTime = System.currentTimeMillis();
    private int loadingIndex = 0;
    private double nextCharTime = 0d;

    public boolean isTyping = true;
    private boolean atBottom = true;

    private int relativeHeight;
    private int totalHeight;
    private int visibleHeight;

    int hueAngle = 152;
    int rgbColor = 0;
    double rgbStartTime = 0d;

    private boolean scrolling = false;

    int deltaX = -1; // debug
    int deltaY = -1; // debug

    private DuckerIOWidget(int x, int y, int width, int height, TextRenderer textRenderer, List<Entry> entries, Deque<String> response, String lastResponse) {
        super(x, y, width, height, Text.empty());
        this.textRenderer = textRenderer;
        this.entries = entries;
        this.lastResponse = new StringBuilder(lastResponse);
        this.response = response;
    }

    public DuckerIOWidget(int x, int y, int width, int height, TextRenderer textRenderer, ShelterData data) {
        this(x, y, width, height, textRenderer, data.entries(), data.response(), data.lastResponse());
    }

    public DuckerIOWidget(int x, int y, int width, int height, TextRenderer textRenderer) {
        super(x, y, width, height, Text.empty());
        this.textRenderer = textRenderer;
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
    }

    public void updateData(ShelterData data) {
        this.entries = data.entries();
        this.lastResponse = new StringBuilder(data.lastResponse());
        this.response = data.response();

        deltaSeconds = System.currentTimeMillis() / 1000d;
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

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (isMouseOverScrollbar(mouseX, mouseY) && button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
            scrolling = true;
        }

        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        scrolling = false;
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        scrollAmount -= verticalAmount * 10;
        if (verticalAmount > 0.5) atBottom = false;
        if (scrollAmount < 0) scrollAmount = 0;
        return true;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT && scrolling) {
            scrollAmount += deltaY * 3.5d;
            if (scrollAmount < 0) scrollAmount = 0;

            this.deltaX = (int) deltaX;
            this.deltaY = (int) deltaY;
        }

        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        updateTimers();
        if (SA.DEV_ENVIRONMENT) {
            renderDebugInfo(context, mouseX, mouseY);
        }
        updateLoadingAnimation();

        byte state = (byte) ((int) (deltaSeconds * 2) % 2);
        updateColor();

        atBottom = relativeHeight == scrollAmount;

        StringBuilder message = buildMessage(state);
        List<MutableText> messageText = wrapAndParseMessage(message);

        int maxWidth = this.width - 8;
        List<StringVisitable> lines = textRenderer.getTextHandler().wrapLines(String.valueOf(message), maxWidth, Style.EMPTY);
        totalHeight = lines.size() * (textRenderer.fontHeight + 1);
        visibleHeight = this.height - 4;

        if (scrollAmount > totalHeight - visibleHeight) {
            scrollAmount = Math.max(totalHeight - visibleHeight, 0);
        }

        int baseX = getX() + 4;
        int baseY = getY() + 2 - (int) scrollAmount;

        context.enableScissor(getX(), getY(), getX() + width, getY() + height);

        renderMessageText(context, messageText, baseX, baseY);

        context.disableScissor();

        relativeHeight = totalHeight - visibleHeight;

        if (atBottom) {
            scrollAmount = Math.max(0, totalHeight - visibleHeight);
        }
        drawScrollbar(context, mouseX, mouseY);
    }

    private void handleEnter() {
        if (!currentInput.isEmpty()){
            entries.add(new Entry(EntryType.INPUT, currentInput.toString()));
            Response output = ResponseHandler.reviewInput(currentInput.toString());
            String outputMessage = output.getOutput().getFirst();
            lastResponse = new StringBuilder(outputMessage);
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

    private void updateColor() {
        if (rgbStartTime >= 0) {
            hueAngle = (int) ((deltaSeconds - rgbStartTime) / ((double) 1 / ((double) 360 / 6)));
            hueAngle = Math.abs(hueAngle);
            hueAngle %= 360;

            rgbColor = Color.HSBtoRGB(hueAngle / 360f, 1, 1);
        }
    }

    private boolean isMouseOverScrollbar(double mouseX, double mouseY) {
        int i = this.getScrollbarThumbHeight();
        int j = this.getX() + this.width - 1;
        int k = Math.max(this.getY() - 3, (int)this.scrollAmount * (this.height - i) / this.relativeHeight + this.getY() - 4);

        return RenderingUtils.isMouseWithinBounds(j, k, 6, i, mouseX, mouseY);
    }

    private void updateTimers() {
        double now = System.currentTimeMillis();
        deltaSeconds += (now - lastTime) / 1000;
        lastTime = (long) now;
    }

    private void renderDebugInfo(DrawContext context, int mouseX, int mouseY) {
        int y = 5;
        context.drawText(textRenderer, "mouseX: " + mouseX + ", mouseY" + mouseY, 5, y, DuckerScreen.MALFUNCTION_COLOR, false);
        y += textRenderer.fontHeight + 3;
        context.drawText(textRenderer, "deltaX: " + deltaX + ", deltaY" + deltaY, 5, y, DuckerScreen.MALFUNCTION_COLOR, false);
        y += textRenderer.fontHeight + 3;
        context.drawText(textRenderer, "atBottom: " + atBottom, 5, y, DuckerScreen.MALFUNCTION_COLOR, false);
    }

    private void updateLoadingAnimation() {
        if (deltaSeconds >= nextCharTime) {
            loadingIndex = (loadingIndex < LOADING_CHARS.length() - 1) ? loadingIndex + 1 : 0;
            nextCharTime = deltaSeconds + 0.08d;
        }
    }

    private StringBuilder buildMessage(byte state) {
        StringBuilder message = new StringBuilder();

        for (Entry entry : entries) {
            message.append(entry.getType() == EntryType.OUTPUT ? "SYS> " : "> ")
                    .append(entry.getContent())
                    .append("\n");
        }

        if (acceptingInput) {
            if (state == 0 && currentInput.isEmpty()) {
                message.append("> ");
            } else if (currentInput.isEmpty()){
                message.append(" ");
            }

            if (!currentInput.isEmpty()) {
                message.append("> ").append(currentInput).append((state == 0) ? "_" : "");
            }
        } else {
            double elapsed = deltaSeconds - animStartTime;
            int visibleChars = (int) (elapsed * typingSpeed);
            message.append("SYS> ");

            if (visibleChars < 0) {
                message.append(LOADING_CHARS.charAt(loadingIndex));
            } else if (visibleChars >= lastResponse.length()) {
                visibleChars = lastResponse.length();
                message.append(lastResponse, 0, visibleChars);

                entries.add(new Entry(EntryType.OUTPUT, lastResponse.toString()));
                lastResponse.delete(0, lastResponse.length());

                if (response.isEmpty()) {
                    acceptingInput = true;
                } else {
                    String nextResponse = response.removeFirst();
                    lastResponse.append(nextResponse);

                    animStartTime = deltaSeconds + (nextResponse.length() * 0.1) * 0.26;
                }
                isTyping = false;
            } else {
                isTyping = true;
                message.append(lastResponse, 0, visibleChars);

                Random random = new Random(System.currentTimeMillis());
                message.append(TYPING_ANIM_CHARS.charAt(random.nextInt(TYPING_ANIM_CHARS.length())));
            }
        }

        return message;
    }

    private List<MutableText> wrapAndParseMessage(StringBuilder message) {
        int maxWidth = this.width - 8;
        List<StringVisitable> lines = textRenderer.getTextHandler().wrapLines(String.valueOf(message), maxWidth, Style.EMPTY);

        List<MutableText> messageText = new ArrayList<>();

        for (StringVisitable line : lines) {
            String lineString = line.getString();
            if (lineString.contains("§")) {
                MutableText lineText = Text.empty();
                Style currentStyle = Style.EMPTY.withColor(DuckerScreen.BASE_COLOR);

                for (int x = 0; x < lineString.length(); ) {
                    int index = lineString.indexOf("§", x);
                    if (index == -1 || index + 1 >= lineString.length()) {
                        lineText.append(Text.literal(lineString.substring(x)).setStyle(currentStyle));
                        break;
                    }

                    if (index > x) {
                        lineText.append(Text.literal(lineString.substring(x, index)).setStyle(currentStyle));
                    }

                    char modifier = lineString.charAt(index + 1);

                    if (STYLE_MAP.containsKey(modifier)) {
                        currentStyle = STYLE_MAP.get(modifier).apply(currentStyle);
                    } else if (modifier == '#') {
                        currentStyle = currentStyle.withColor(rgbColor);
                    } else if (modifier == 'd') {
                        currentStyle = Style.EMPTY.withColor(DuckerScreen.BASE_COLOR);
                    } else if (modifier == 'n') {
                        lineText.append("\n");
                    }

                    x = index + 2;
                }

                messageText.add(lineText);
            } else {
                messageText.add(Text.literal(lineString).withColor(DuckerScreen.BASE_COLOR));
            }
        }

        return messageText;
    }

    private void renderMessageText(DrawContext context, List<MutableText> messageText, int baseXStart, int baseYStart) {
        int baseX = baseXStart;
        int baseY = baseYStart;

        for (MutableText line : messageText) {
            if (baseY + textRenderer.fontHeight >= getY() && baseY <= getY() + height) {
                if (!line.getSiblings().isEmpty()) {
                    for (Text sibling : line.getSiblings()) {
                        if (Objects.isNull(sibling.getStyle().getColor())) {
                            context.drawText(textRenderer, sibling, baseX, baseY, DuckerScreen.BASE_COLOR, true);
                            baseX += textRenderer.getWidth(sibling);
                            continue;
                        }
                        context.drawText(textRenderer, sibling, baseX, baseY, Objects.requireNonNull(sibling.getStyle().getColor()).getRgb(), true);
                        baseX += textRenderer.getWidth(sibling);
                    }
                    baseX = getX() + 4;
                } else {
                    context.drawText(textRenderer, line, baseX, baseY, Objects.requireNonNull(line.getStyle().getColor()).getRgb(), true);
                }
            }
            baseY += textRenderer.fontHeight + 1;
        }
    }

    private void drawScrollbar(DrawContext context, int mouseX, int mouseY) {
        if (relativeHeight > 0) {
            float alpha = 0.8f;
            if (scrolling || isMouseOverScrollbar(mouseX, mouseY)) {
                alpha = 1.0f;
            }

            int i = this.getScrollbarThumbHeight();
            int j = this.getX() + this.width - 1;
            int k = Math.max(this.getY() - 3, (int) this.scrollAmount * (this.height - i) / this.relativeHeight + this.getY() - 4);
            RenderSystem.enableBlend();
            RenderSystem.setShaderColor(1f, 1f, 1f, alpha);
            context.drawGuiTexture(SCROLLER_TEXTURE, j, k, 6, i);
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
            RenderSystem.disableBlend();
        }
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
        private static final String FILTER_CHARS = "~!@#$%^&_+-=()[]{},./\\";
        public static final String UNKNOWN_RESPONSE = "Пожалуйста уточните ваш запрос";
        public static final String DEFAULT_RESPONSE = "Что вы хотите сделать ?";

        public static void addResponse(String tokens, int minMatch, String output) {
            responses.add(new Response(tokens, minMatch, output));
        }

        public static void addResponse(String tokens, int minMatch, List<String> output) {
            responses.add(new Response(tokens, minMatch, output, true));
        }

        public static void reset() {
            responses.clear();
        }

        public static Response reviewInput(String input) {
            for (char it : FILTER_CHARS.toCharArray()) {
                input = input.replace(String.valueOf(it), "");
            }

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

                if (matched >= minMatch) {
                    return true;
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