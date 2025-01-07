package net.packages.seasonal_adventures.api.jade;

import net.packages.seasonal_adventures.block.custom.LockedChestBlock;
import net.packages.seasonal_adventures.block.entity.lockedChests.LockedChestLvLCopperBlockEntity;
import net.packages.seasonal_adventures.block.entity.lockedChests.LockedChestLvLIronBlockEntity;
import net.packages.seasonal_adventures.entity.custom.DylanEntity;
import net.packages.seasonal_adventures.api.jade.provider.block.LockedChestComponentProvider;
import net.packages.seasonal_adventures.api.jade.provider.entity.DylanEntityComponentProvider;
import snownee.jade.api.*;

@WailaPlugin
public class JadeAPI implements IWailaPlugin {
    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(LockedChestComponentProvider.INSTANCE, LockedChestLvLCopperBlockEntity.class);
        registration.registerBlockDataProvider(LockedChestComponentProvider.INSTANCE, LockedChestLvLIronBlockEntity.class);
        registration.registerEntityDataProvider(DylanEntityComponentProvider.INSTANCE, DylanEntity.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        IWailaPlugin.super.registerClient(registration);
        registration.registerBlockComponent(LockedChestComponentProvider.INSTANCE, LockedChestBlock.class);
        registration.registerEntityComponent(DylanEntityComponentProvider.INSTANCE, DylanEntity.class);
    }
}
