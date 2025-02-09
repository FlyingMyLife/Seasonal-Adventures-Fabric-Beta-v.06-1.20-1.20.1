package net.packages.seasonal_adventures.gui.screen.ingame;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.packages.seasonal_adventures.gui.handler.DylanSettingsScreenHandler;

public class DylanSettingsScreen extends HandledScreen<DylanSettingsScreenHandler> {
    private final DylanSettingsScreenHandler handler;
    private Screen parent;
    public DylanSettingsScreen(DylanSettingsScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.handler = handler;
    }
    public void setParentScreen(Screen parentScreen) {
        this.parent = parentScreen;
    }

    @Override
    public void close() {
        client.setScreen(parent);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
    }
}
