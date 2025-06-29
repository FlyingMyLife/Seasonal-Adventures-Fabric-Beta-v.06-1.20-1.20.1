package net.flyingmylife.seasonal_adventures.item.custom;

import com.mojang.serialization.Codec;
import net.flyingmylife.seasonal_adventures.SA;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.component.ComponentType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BiocomponentItem extends Item {
    public static final ComponentType<String> SERIAL_NUMBER_COMPONENT_TYPE = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(SA.MOD_ID, "serial_number"),
            ComponentType.<String>builder().codec(Codec.STRING).build()
    );
    public final String biocomponentId;
    public BiocomponentItem(Settings settings, String biocomponentId) {
        super(settings);
        this.biocomponentId = biocomponentId;
    }

    @Override
    public void onCraft(ItemStack stack, World world) {
        super.onCraft(stack, world);
        Random random = new Random();
        int randInt = random.nextInt(9999) + 1;
        String production_line = String.format("%04d", randInt);
        stack.set(SERIAL_NUMBER_COMPONENT_TYPE, biocomponentId + "::" + production_line);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        tooltip.add(Text.translatable("tooltip.seasonal_adventures.biocomponent.title").formatted(Formatting.DARK_AQUA));
        tooltip.add(Text.empty());
        if (Screen.hasShiftDown()){
            tooltip.add(Text.translatable("tooltip.seasonal_adventures.biocomponent.serial_number").append(Text.literal(stack.getOrDefault(SERIAL_NUMBER_COMPONENT_TYPE, biocomponentId + "::N/A"))).styled(style -> style.withColor(0xaaaaaa)));
        } else {
            tooltip.add(Text.translatable("tooltip.seasonal_adventures.details_hint"));
        }
    }
}
