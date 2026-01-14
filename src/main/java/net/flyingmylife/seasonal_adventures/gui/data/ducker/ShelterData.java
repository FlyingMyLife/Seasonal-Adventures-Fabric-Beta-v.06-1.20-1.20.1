package net.flyingmylife.seasonal_adventures.gui.data.ducker;

import net.flyingmylife.seasonal_adventures.block.entity.DuckerControlSystemBlockEntity;
import net.flyingmylife.seasonal_adventures.gui.widget.DuckerIOWidget;
import net.flyingmylife.seasonal_adventures.network.service.ServerDataQueryService;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public record ShelterData(List<DuckerIOWidget.Entry> entries, Deque<String> response, String lastResponse) {

    public NbtCompound toNbt() {
        NbtCompound nbt = new NbtCompound();
        NbtCompound entriesNbt = new NbtCompound();

        int entrySize = entries.size();
        entriesNbt.putInt("size", entrySize);
        NbtCompound entryList = new NbtCompound();

        for (int i = 0; i < entrySize; i++) {
            NbtCompound entryNbt = new NbtCompound();
            DuckerIOWidget.Entry e = entries.get(i);

            entryNbt.putString("content", e.getContent());
            entryNbt.putString("type", e.getType().toString());

            entryList.put(String.valueOf(i), entryNbt);
        }
        entriesNbt.put("list", entryList);
        nbt.put("entries", entriesNbt);

        Deque<String> response = this.response;
        NbtCompound responseNbt = new NbtCompound();

        for (int i = 0; i < response.size(); i++) {
            responseNbt.putString(String.valueOf(i), response.pollFirst());
        }
        nbt.put("response", responseNbt);

        nbt.putString("lastResponse", this.lastResponse);
        return nbt;
    }

    public static ShelterData create(NbtCompound nbt) {
        return parse(nbt);
    }

    public static NbtCompound get(ServerDataQueryService.Context context) {
        BlockPos pos = BlockPos.fromLong(context.requestData().getLong("pos"));
        ServerWorld world = context.player().getServerWorld();

        if (world.getBlockEntity(pos) instanceof DuckerControlSystemBlockEntity blockEntity) {
            return blockEntity.getData(world, pos).toNbt();
        } else {
            return new NbtCompound();
        }
    }

    public static ShelterData parse(NbtCompound requestData) {
        NbtCompound entriesNbt = requestData.getCompound("entries");

        int entrySize = entriesNbt.getInt("size");

        List<DuckerIOWidget.Entry> entries = new ArrayList<>();

        if (entrySize > 0) {
            NbtCompound entryList = entriesNbt.getCompound("list");

            for (int i = 0; i < entrySize; i++) {
                NbtCompound entryNbt = entryList.getCompound(String.valueOf(i));
                String content = entryNbt.getString("content");
                DuckerIOWidget.EntryType type = DuckerIOWidget.EntryType.valueOf(entryNbt.getString("type"));
                DuckerIOWidget.Entry entry = new DuckerIOWidget.Entry(type, content);
                entries.add(entry);
            }
        }

        NbtCompound responseNbt = requestData.getCompound("response");
        Deque<String> response = new ArrayDeque<>();
        for (int i = 0; ; i++) {
            if (!responseNbt.contains(String.valueOf(i))) {
                break;
            }

            response.addLast(responseNbt.getString(String.valueOf(i)));
        }
        String lastResponse = requestData.getString("lastResponse");

        return new ShelterData(entries, response, lastResponse);
    }
}
