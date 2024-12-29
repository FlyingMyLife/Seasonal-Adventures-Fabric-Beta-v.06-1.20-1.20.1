package net.packages.seasonal_adventures.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.packages.seasonal_adventures.gui.handler.DylanSettingsScreenHandler;

public class DylanSettingsScreen extends HandledScreen<DylanSettingsScreenHandler> {
    private final DylanSettingsScreenHandler handler;
    private final Screen parent;
    public DylanSettingsScreen(DylanSettingsScreenHandler handler, PlayerInventory inventory, Text title, Screen parent) {
        super(handler, inventory, title);
        this.handler = handler;
        this.parent = parent;
    }

    @Override
    public void close() {
        client.setScreen(parent);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
    }
}
