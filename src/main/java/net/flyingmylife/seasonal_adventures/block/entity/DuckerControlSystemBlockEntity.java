package net.flyingmylife.seasonal_adventures.block.entity;

import net.flyingmylife.seasonal_adventures.gui.data.ducker.ShelterData;
import net.flyingmylife.seasonal_adventures.gui.handler.DuckerScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DuckerControlSystemBlockEntity extends BlockEntity {
    private BlockPos parentPos;
    private ShelterData data = new ShelterData(List.of(), new ArrayDeque<>(), "");

    public DuckerControlSystemBlockEntity(BlockPos pos, BlockState state, BlockPos parentPos) {
        super(SABlockEntities.DUCKER_CONTROL_SYSTEM_BLOCK_ENTITY, pos, state);
        this.parentPos = parentPos;
    }
    public DuckerControlSystemBlockEntity(BlockPos pos, BlockState state) {
        this(pos, state, null);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        if (Objects.nonNull(parentPos)) {
            nbt.putLong("parentPos", parentPos.asLong());
        }

        nbt.put("data", data.toNbt());
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        parentPos = BlockPos.fromLong(nbt.getLong("parentPos"));

        if (nbt.contains("data", NbtCompound.COMPOUND_TYPE)) {
            data = ShelterData.create(nbt.getCompound("data"));
        }
    }

    public ShelterData getData(ServerWorld world, BlockPos pos) {
        if (this.parentPos.equals(pos)) {
            return this.data;
        } else if (world.getBlockEntity(pos) instanceof DuckerControlSystemBlockEntity parent){
            return parent.getData(world, pos);
        }
        return null;
    }

    @Nullable
    public NamedScreenHandlerFactory createScreenHandlerFactory(ServerWorld world, BlockPos pos) {
        if (this.pos.equals(this.parentPos)) {
            return new SimpleNamedScreenHandlerFactory((syncId, inventory, player) -> new DuckerScreenHandler(syncId, inventory, pos), Text.empty());
        }

        if (world.getBlockEntity(parentPos) instanceof DuckerControlSystemBlockEntity parent) {
            return parent.createScreenHandlerFactory(world, parentPos);
        }

        return null;
    }


}
