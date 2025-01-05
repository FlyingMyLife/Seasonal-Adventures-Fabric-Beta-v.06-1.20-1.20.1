package net.packages.seasonal_adventures.gui.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class ConfigSettingsScreen extends Screen {
    private Screen parent;
    public ConfigSettingsScreen(Text title, Screen parent) {
        super(title);
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public void close() {
        client.setScreen(parent);
    }
    public void openAnimation() {

    }
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        MatrixStack stack = context.getMatrices();
        context.fill(width/8, height/8, (width/8) * 7, (height/8) * 7,  0x4C000000);
        stack.push();
    }
}
