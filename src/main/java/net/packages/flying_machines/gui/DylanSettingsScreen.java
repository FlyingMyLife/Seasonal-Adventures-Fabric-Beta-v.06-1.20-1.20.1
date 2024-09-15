package net.packages.flying_machines.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;

public class DylanSettingsScreen extends HandledScreen<DylanSettingsScreenHandler> {




    private final DylanSettingsScreenHandler handler;
    public DylanSettingsScreen(DylanSettingsScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.handler = handler;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {

    }
}
