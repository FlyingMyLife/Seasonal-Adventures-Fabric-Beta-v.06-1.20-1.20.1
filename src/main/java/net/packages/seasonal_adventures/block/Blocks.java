package net.packages.seasonal_adventures.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import net.packages.seasonal_adventures.block.custom.AdvancedAutomatonBodyBlock;
import net.packages.seasonal_adventures.block.custom.AnimatedBlock;
import net.packages.seasonal_adventures.block.custom.EnergyInjector;
import net.packages.seasonal_adventures.block.custom.LaptopBlock;
import net.packages.seasonal_adventures.SeasonalAdventures;
import net.minecraft.block.Block;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class Blocks {
    private static final List<String> itemsWithNoFunctional = Arrays.asList("laptop",
            "photolithography_machine"
    );

    public static final Block TITANIUM_BLOCK = registerBlock("titanium_block",
            new Block(FabricBlockSettings.copyOf(net.minecraft.block.Blocks.NETHERITE_BLOCK).sounds(BlockSoundGroup.NETHERITE)));
    public static final Block GRADE_5_TITANIUM_BLOCK = registerBlock("grade_5_titanium_block",
            new Block(FabricBlockSettings.copyOf(net.minecraft.block.Blocks.NETHERITE_BLOCK).sounds(BlockSoundGroup.COPPER)));
    public static final Block ALUMINIUM_ORE = registerBlock("aluminium_ore",
            new ExperienceDroppingBlock(FabricBlockSettings.copyOf(net.minecraft.block.Blocks.STONE).strength(2f), UniformIntProvider.create(2, 5)));
    public static final Block TITANIUM_ORE = registerBlock("titanium_ore",
            new ExperienceDroppingBlock(FabricBlockSettings.copyOf(net.minecraft.block.Blocks.STONE).strength(2f), UniformIntProvider.create(2, 5)));
    public static final Block DEEPSLATE_TITANIUM_ORE = registerBlock("deepslate_titanium_ore",
            new ExperienceDroppingBlock(FabricBlockSettings.copyOf(net.minecraft.block.Blocks.DEEPSLATE).strength(4f), UniformIntProvider.create(2, 5)));
    public static final Block LITHIUM_ORE = registerBlock("lithium_ore",
            new ExperienceDroppingBlock(FabricBlockSettings.copyOf(net.minecraft.block.Blocks.STONE).strength(2f), UniformIntProvider.create(2, 5)));
    public static final Block DEEPSLATE_LITHIUM_ORE = registerBlock("deepslate_lithium_ore",
            new ExperienceDroppingBlock(FabricBlockSettings.copyOf(net.minecraft.block.Blocks.DEEPSLATE).strength(4f), UniformIntProvider.create(2, 5)));
    public static final Block DEEPSLATE_ALUMINIUM_ORE = registerBlock("deepslate_aluminium_ore",
            new ExperienceDroppingBlock(FabricBlockSettings.copyOf(net.minecraft.block.Blocks.DEEPSLATE).strength(4f), UniformIntProvider.create(2, 5)));
    public static final Block LAPTOP = registerBlock("laptop",
            new LaptopBlock(FabricBlockSettings.copyOf(net.minecraft.block.Blocks.CRAFTING_TABLE).strength(0.2f).nonOpaque().sounds(BlockSoundGroup.STONE)));
    public static final Block ANIMATED_BLOCK = registerBlock("animated_block",
            new AnimatedBlock(FabricBlockSettings.copyOf(net.minecraft.block.Blocks.CRAFTING_TABLE).strength(0.2f).nonOpaque().sounds(BlockSoundGroup.STONE)));
    public static final Block ENERGY_INJECTOR_BLOCK = registerBlock("energy_injector",
            new EnergyInjector(FabricBlockSettings.copyOf(net.minecraft.block.Blocks.STONE).strength(0.2f).nonOpaque().sounds(BlockSoundGroup.STONE)));
    public static final Block ADVANCED_AUTOMATON_BODY = registerBlock("advanced_automaton_body",
            new AdvancedAutomatonBodyBlock(FabricBlockSettings.copyOf(net.minecraft.block.Blocks.CRAFTING_TABLE).strength(0.2f).nonOpaque().sounds(BlockSoundGroup.STONE)));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(SeasonalAdventures.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
            return Registry.register(Registries.ITEM, new Identifier(SeasonalAdventures.MOD_ID, name),
                    new BlockItem(block, new FabricItemSettings()) {
                        @Override
                        public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
                            if (itemsWithNoFunctional.contains(name)) {
                                if (Screen.hasShiftDown()) {
                                    tooltip.add(Text.translatable("tooltip.seasonal_adventures.without_functional.detailed").formatted(Formatting.GRAY));
                                } else {
                                    tooltip.add(Text.translatable("tooltip.seasonal_adventures.without_functional.hint").formatted(Formatting.RED));
                                }
                            }
                        }
            });
    }


    public static void registerModBlocks() {
        SeasonalAdventures.LOGGER.info("Registering ModBlocks for " + SeasonalAdventures.MOD_ID);
    }

}
