package net.flyingmylife.seasonal_adventures.api.jade;

import net.flyingmylife.seasonal_adventures.block.custom.LockedChestBlock;
import net.flyingmylife.seasonal_adventures.block.entity.lockedChests.CopperLCBlockEntity;
import net.flyingmylife.seasonal_adventures.block.entity.lockedChests.IronLCBlockEntity;
import net.flyingmylife.seasonal_adventures.api.jade.provider.block.LockedChestComponentProvider;
import snownee.jade.api.*;

@WailaPlugin
public class JadeAPI implements IWailaPlugin {
    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(LockedChestComponentProvider.INSTANCE, CopperLCBlockEntity.class);
        registration.registerBlockDataProvider(LockedChestComponentProvider.INSTANCE, IronLCBlockEntity.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        IWailaPlugin.super.registerClient(registration);
        registration.registerBlockComponent(LockedChestComponentProvider.INSTANCE, LockedChestBlock.class);
    }
}
