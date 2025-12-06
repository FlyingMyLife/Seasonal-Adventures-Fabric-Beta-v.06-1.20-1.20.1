package net.flyingmylife.seasonal_adventures.gui.screen.in_game;

import jdk.jfr.Description;
import net.flyingmylife.seasonal_adventures.SA;
import net.flyingmylife.seasonal_adventures.gui.data.ducker.ShelterData;
import net.flyingmylife.seasonal_adventures.gui.handler.DuckerScreenHandler;
import net.flyingmylife.seasonal_adventures.gui.widget.DuckerIOWidget;
import net.flyingmylife.seasonal_adventures.network.service.ServerDataQueryService;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.TestOnly;
import org.lwjgl.glfw.GLFW;

import java.lang.annotation.Documented;
import java.util.*;
import java.util.concurrent.Future;

public class DuckerScreen extends HandledScreen<DuckerScreenHandler> {
    private int x;
    private int y;
    private static final String ANIM_CHARS = "ABCDEF99301_<>⚠�";
    private static final int WIDTH = 52;
    private static final int MAX_LIFESPAN = 122;
    public static final int BASE_COLOR = 0x2fce6d;
    public static final int MALFUNCTION_COLOR = 0xe76a5b;
    private static final Identifier TEXTURE_ATLAS = Identifier.of(SA.MOD_ID, "textures/gui/sprites/ducker/ducker_systems.png");
    private static final Random RANDOM = new Random();
    private final List<FallingCharacter> low_speed_characters = new ArrayList<>();
    private final List<FallingCharacter> high_speed_characters = new ArrayList<>();
    private DuckerIOWidget console;
    private Future<ShelterData> dataFuture;

    double deltaSeconds = 0.0D;
    private long lastTime = System.currentTimeMillis();
    private byte duckState = 0;
    private double nextDuckDelay = 0.03 + Math.random() * 0.16;
    private double duckTimer = 0;

    public boolean isStable = true;

    public DuckerScreen(DuckerScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }


    @Override
    protected void init() {
        super.init();
        BlockPos pos = handler.getPos();
        NbtCompound nbt = new NbtCompound();
        nbt.putLong("pos", pos.asLong());

        dataFuture = ServerDataQueryService.Manager.requestData(Identifier.of(SA.MOD_ID, "shelter_data"), nbt);

        x = this.width / 2 - 192;
        y = this.height / 2 - 123;
        console = new DuckerIOWidget(
                x + 28,
                y + 27,
                257,
                192,
                textRenderer
        );
        console.visible = true;
        addDrawableChild(console);
        updateResponses();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (!dataFuture.isDone()) {
            return;
        }
        long now = System.currentTimeMillis();
        deltaSeconds = (double) (now - lastTime) / 1000;
        lastTime = now;

        duckTimer += deltaSeconds;

        if (duckTimer > nextDuckDelay) {
            duckTimer = 0.0D;
            duckState = (byte) ((duckState == 1) ? 0 : 1);
            nextDuckDelay = 0.03 + Math.random() * 0.16;
        }

        renderBackground(context, mouseX, mouseY, delta);

        for (FallingCharacter character : low_speed_characters) {
            context.drawText(textRenderer, Character.toString(character.character), x + character.x + 299, character.y + 98, BASE_COLOR, true);
        }
        for (FallingCharacter character : high_speed_characters) {
            context.drawText(textRenderer, Character.toString(character.character), x + character.x + 299, character.y + 98, BASE_COLOR, true);
        }

        super.render(context, mouseX, mouseY, delta);
        if (duckState == 1 && console.isTyping) {
            context.drawTexture(TEXTURE_ATLAS, x + 291, y + 24, 70, 247, 68, 74, 384, 384);

        } else {
            context.drawTexture(TEXTURE_ATLAS, x + 291, y + 24, 0, 247, 68, 74, 384, 384);
        }
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        context.drawTexture(TEXTURE_ATLAS, x, y, 0, 0, 384, 246, 384, 384);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        console.charTyped(chr, modifiers);
        return true;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        console.keyPressed(keyCode, scanCode, modifiers);
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            this.close();
        }
        return true;
    }

    @Override
    protected void handledScreenTick() {
        super.handledScreenTick();
        charFallUpdate();
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
    @TestOnly
    //TODO: Responses should be registered only server-side with data resources
    public static void updateResponses() {
        DuckerIOWidget.ResponseHandler.reset();
        DuckerIOWidget.ResponseHandler.addResponse(
                "отключи выключи подсветку подсветка выруби мигающие лампочки бункер бункера УТКЭР УТКЭРА",
                3,
                List.of(
                        "Модель вашего контрольного модуля не подразумевает отключения подсветки.",
                        "Если хотите сделать кнопки настраиваемыми, обратитесь в службу поддержки."
                )
        );
        DuckerIOWidget.ResponseHandler.addResponse(
                "отключи выключи очистку воздуха фильтры вентиляцию фильтр вентилятор воздух систему вентиляции бункера УТКЭР УТКЭРА",
                3,
                List.of(
                        "Отключение системы очистки воздуха невозможно: центральный модуль защищён от ручного и программного вмешательства.",
                        "Для получения расширенных возможностей обслуживания свяжитесь сo службой поддержки У.Т.К.Э.Р."
                )
        );

        DuckerIOWidget.ResponseHandler.addResponse(
                "отключи выключи воду подачу воду водоснабжение водоснабжения водопровод насос система систему воды бункера УТКЭР УТКЭРА",
                3,
                List.of(
                        "Полное отключение системы водоснабжения невозможно: насосы задействованы в охлаждении реактора.",
                        "Для временного аварийного прекращения подачи воды в жилые модули используйте ручной вентиль в техническом помещении.",
                        "Для получения инструкции по эксплуатации обратитесь в службу поддержки"
                )
        );
        DuckerIOWidget.ResponseHandler.addResponse(
                "отключи выключи генератор питание энергию электричество электропитание бункера УТКЭР УТКЭРА реактор энергия подача электричества ток питание генераторы",
                3,
                List.of(
                        "§rОтключение энергосистемы невозможно:§d реактор поддерживает критические подсистемы жизнеобеспечения.",
                        "Состояние реактора - §b[В НОРМЕ]§d. Замена топливных стержней не требуется.",
                        "Для подробной инструкции по техобслуживанию обратитесь в службу поддержки"
                )
        );
        DuckerIOWidget.ResponseHandler.addResponse(
                "кто твой создатель создал тебя УТКЭР УТКА бункер",
                3,
                List.of(
                        "Меня создала небольшая команда энтузиастов:",
                        "- §#AsynchroDev",
                        "- §#ModernDel (большинство текстур)",
                        "- §#brrrkuda",
                        "§iБольше о проекте: §it.me/seasonal_adventures"
                )
        );

        if (SA.DEV_ENVIRONMENT) {
            List<String> scrollList = new ArrayList<>();
            for (int i = 0; i < 60; i++) {
                scrollList.add("Line: " + i);
            }
            DuckerIOWidget.ResponseHandler.addResponse(
                    "devscroll",
                    1,
                    scrollList
            );
            DuckerIOWidget.ResponseHandler.addResponse(
                    "devform",
                    1,
                    List.of(
                            "§dDefault §rRed §#RGB",
                            "§dDefault §iItalic §bBold",
                            "Obfuscated: §oMclovin"
                    )
            );
        }
    }
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if (console.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount)) {
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        console.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
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
            this.character = ANIM_CHARS.charAt(RANDOM.nextInt(ANIM_CHARS.length()));
            this.x = x;
            this.y = y;
            this.startY = y;
        }

        public enum Group {

        }
    }
}
