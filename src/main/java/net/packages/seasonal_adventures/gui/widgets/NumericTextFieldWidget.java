package net.packages.seasonal_adventures.gui.widgets;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

public class NumericTextFieldWidget extends TextFieldWidget {

    public NumericTextFieldWidget(TextRenderer textRenderer, int x, int y, int width, int height, Text message) {
        super(textRenderer, x, y, width, height, message);
        this.setDrawsBackground(false);
        setText("0");
    }

    @Override
    public boolean charTyped(char chr, int keyCode) {
        if (chr == '0' || chr == '1' || chr == '2' || chr == '3' || chr == '4' || chr == '5' || chr == '6' || chr == '7' || chr == '8' || chr == '9') {
            return super.charTyped(chr, keyCode);
        }
        return false;
    }
}
