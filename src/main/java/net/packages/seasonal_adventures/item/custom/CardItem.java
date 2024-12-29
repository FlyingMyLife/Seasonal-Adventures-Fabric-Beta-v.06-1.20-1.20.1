package net.packages.seasonal_adventures.item.custom;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import net.minecraft.text.Text;
import net.packages.seasonal_adventures.item.Items;

import java.util.List;

public class CardItem extends Item {
    public CardItem(Settings settings) {
        super(settings);
    }

    public static ItemStack createCardItem(String cardId) {
        ItemStack cardItem = new ItemStack(Items.CARD);
        NbtCompound tag = cardItem.getOrCreateNbt();
        tag.putString("cardId", cardId);
        return cardItem;
    }
    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        NbtCompound nbt = stack.getOrCreateNbt();
        String cardId = nbt.getString("cardId");
        MutableText grayText = Text.translatable("tooltip.seasonal_adventures.cardId").setStyle(Style.EMPTY.withColor(Formatting.GRAY));
        Text purpleCardId = Text.literal(cardId).setStyle(Style.EMPTY.withColor(Formatting.LIGHT_PURPLE));
        Text tooltipText = grayText.append(purpleCardId);
        tooltip.add(tooltipText);
        if (context == TooltipContext.ADVANCED) {
            tooltip.add(Text.empty());
            tooltip.add(Text.translatable("tooltip.seasonal_adventures.card_tooltip").setStyle(Style.EMPTY.withColor(Formatting.GRAY).withItalic(true)));
            tooltip.add(Text.translatable("tooltip.seasonal_adventures.card_tooltip.nl").setStyle(Style.EMPTY.withColor(Formatting.GRAY).withItalic(true)));
        }
    }
}
