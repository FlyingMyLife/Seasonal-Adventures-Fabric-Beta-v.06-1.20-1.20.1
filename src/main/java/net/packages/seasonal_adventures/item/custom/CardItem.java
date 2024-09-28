package net.packages.seasonal_adventures.item.custom;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.packages.seasonal_adventures.item.Items;

import java.util.List;

public class CardItem extends Item {
    public CardItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (!world.isClient) {
            NbtCompound nbt = itemStack.getOrCreateNbt();
            String cardId = nbt.getString("CardID");
            long currencyAmount = nbt.getLong("CurrencyAmount");
        }
        return TypedActionResult.success(itemStack);
    }
    public static ItemStack createCardItem(String cardId, long currencyAmount) {
        ItemStack cardItem = new ItemStack(Items.CARD);
        NbtCompound tag = cardItem.getOrCreateNbt();
        tag.putString("CardID", cardId);
        tag.putLong("CurrencyAmount", currencyAmount);
        return cardItem;
    }
    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        NbtCompound nbt = stack.getOrCreateNbt();
        String cardId = nbt.getString("CardID");
        Text grayText = Text.translatable("tooltip.seasonal_adventures.cardId").setStyle(Style.EMPTY.withColor(Formatting.GRAY));
        Text purpleCardId = Text.literal(cardId).setStyle(Style.EMPTY.withColor(Formatting.LIGHT_PURPLE));
        Text tooltipText = ((MutableText) grayText).append(purpleCardId);
        tooltip.add(tooltipText);
        tooltip.add(Text.empty());
        tooltip.add(Text.translatable("tooltip.seasonal_adventures.cardtooltip.upper").setStyle(Style.EMPTY.withColor(Formatting.GRAY).withItalic(true)));
        tooltip.add(Text.translatable("tooltip.seasonal_adventures.cardtooltip").setStyle(Style.EMPTY.withColor(Formatting.GRAY).withItalic(true)));
    }
}
