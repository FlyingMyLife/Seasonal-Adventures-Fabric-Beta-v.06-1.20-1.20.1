package net.packages.seasonal_adventures.world.dimension;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.DimensionTypes;
import net.packages.seasonal_adventures.SeasonalAdventures;

import java.util.OptionalLong;

public class Dimensions {
    public static final RegistryKey<DimensionOptions> DIMENSION_OF_DREAMS_KEY = RegistryKey.of(RegistryKeys.DIMENSION,
            new Identifier(SeasonalAdventures.MOD_ID, "dimension_of_dreams"));
    public static final RegistryKey<World> DIMENSION_OF_DREAMS_LEVEL_KEY = RegistryKey.of(RegistryKeys.WORLD,
            new Identifier(SeasonalAdventures.MOD_ID, "dimension_of_dreams"));
    public static final RegistryKey<DimensionType> DIMENSION_OF_DREAMS_TYPE = RegistryKey.of(RegistryKeys.DIMENSION_TYPE,
            new Identifier(SeasonalAdventures.MOD_ID, "dimension_of_dreams_type"));

    public static void bootstrapType(Registerable<DimensionType> context) {
        context.register(DIMENSION_OF_DREAMS_TYPE, new DimensionType(
                OptionalLong.of(5700), // fixedTime
                false, // hasSkylight
                false, // hasCeiling
                false, // ultraWarm
                true, // natural
                1.0, // coordinateScale
                true, // bedWorks
                false, // respawnAnchorWorks
                -64, // minY
                256, // height
                256, // logicalHeight
                BlockTags.INFINIBURN_OVERWORLD,
                DimensionTypes.OVERWORLD_ID, // effectsLocation
                1.0f, // ambientLight
                new DimensionType.MonsterSettings(false, false, UniformIntProvider.create(0, 0), 0)));
    }
}
