package net.packages.seasonal_adventures.gui.widgets;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

public class NumericTextFieldWidget extends TextFieldWidget {
    private static final long MAX_VALUE = 99999999L;

    public NumericTextFieldWidget(TextRenderer textRenderer, int x, int y, int width, int height, Text message) {
        super(textRenderer, x, y, width, height, message);
        setText("0");
    }

    @Override
    public boolean charTyped(char chr, int keyCode) {
        if (chr == '\n') {
            this.setFocused(false);
            return true;
        }

        if (chr == '-' || chr == '0' || chr == '1' || chr == '2' || chr == '3' || chr == '4' || chr == '5' || chr == '6' || chr == '7' || chr == '8' || chr == '9') {
            return super.charTyped(chr, keyCode);
        }
        return false;
    }

    @Override
    public void write(String text) {
        if (isValidLong(text)) {
            super.write(text);
        }
    }

    private boolean isValidLong(String text) {
        if (text.isEmpty()) return true;
        try {
            long value = Long.parseLong(text);
            return value >= 0 && value <= MAX_VALUE;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    @Override
    public void setFocused(boolean focused) {
        super.setFocused(focused);
        if (focused) {
            this.setEditable(true);
        }
    }
}
