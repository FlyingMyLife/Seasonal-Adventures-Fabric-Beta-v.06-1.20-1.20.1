package net.packages.seasonal_adventures.item.custom;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.util.Formatting;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.packages.seasonal_adventures.SeasonalAdventures;
import net.packages.seasonal_adventures.item.SAItems;

import java.util.List;

public class CardItem extends Item {
    public CardItem(Settings settings) {
        super(settings);
    }
    public static final ComponentType<String> CARD_ID = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(SeasonalAdventures.MOD_ID, "card_id"),
            ComponentType.<String>builder().codec(Codec.STRING).build()
    );
    public static ItemStack createCardItem(String cardId) {
        ItemStack cardItem = new ItemStack(SAItems.CARD);
        cardItem.set(CARD_ID, cardId);
        return cardItem;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        String cardId = stack.get(CARD_ID);
        if (cardId != null) {
            MutableText grayText = Text.translatable("tooltip.seasonal_adventures.cardId").setStyle(Style.EMPTY.withColor(Formatting.GRAY));
            Text purpleCardId = Text.literal(cardId).setStyle(Style.EMPTY.withColor(Formatting.LIGHT_PURPLE));
            Text tooltipText = grayText.append(purpleCardId);
            tooltip.add(tooltipText);
        } else {
            tooltip.add(Text.translatable(""));
        }
        if (type == TooltipType.ADVANCED) {
            tooltip.add(Text.empty());
            tooltip.add(Text.translatable("tooltip.seasonal_adventures.card_tooltip").setStyle(Style.EMPTY.withColor(Formatting.GRAY).withItalic(true)));
            tooltip.add(Text.translatable("tooltip.seasonal_adventures.card_tooltip.nl").setStyle(Style.EMPTY.withColor(Formatting.GRAY).withItalic(true)));
        }
    }
}
