package net.flyingmylife.seasonal_adventures.gui.data;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.*;

@Environment(EnvType.CLIENT)
public class EntityTrackingPool {

    private static final Set<Entry> pool = new HashSet<>();
    private static boolean initialized = false;

    public static List<Entry> getEntityPool(Identifier id) {
        List<Entry> list = new ArrayList<>();
        for (Entry e : pool) {
            if (e.entityId.equals(id)) {
                list.add(e);
            }
        }
        return list;
    }

    public static Collection<Entry> getAll() {
        return Collections.unmodifiableCollection(pool);
    }

    public static void init() {
        if (initialized) return;
        initialized = true;

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.world == null) return;
            Set<Integer> alive = new HashSet<>();

            for (Entity entity : client.world.getEntities()) {
                if (entity == null || !entity.isAlive()) continue;

                int uuidHash = entity.getUuid().hashCode();
                Identifier typeId = EntityType.getId(entity.getType());

                List<Vec3d> corners = getBoundingBoxCorners(entity.getBoundingBox());

                Entry entry = new Entry(corners, uuidHash, typeId);
                pool.removeIf(e -> e.entityUUID == uuidHash);
                pool.add(entry);

                alive.add(uuidHash);
            }

            pool.removeIf(e -> !alive.contains(e.entityUUID));
        });
    }

    private static List<Vec3d> getBoundingBoxCorners(Box box) {
        List<Vec3d> points = new ArrayList<>(8);
        double minX = box.minX, minY = box.minY, minZ = box.minZ;
        double maxX = box.maxX, maxY = box.maxY, maxZ = box.maxZ;

        points.add(new Vec3d(minX, minY, minZ));
        points.add(new Vec3d(minX, minY, maxZ));
        points.add(new Vec3d(minX, maxY, minZ));
        points.add(new Vec3d(minX, maxY, maxZ));
        points.add(new Vec3d(maxX, minY, minZ));
        points.add(new Vec3d(maxX, minY, maxZ));
        points.add(new Vec3d(maxX, maxY, minZ));
        points.add(new Vec3d(maxX, maxY, maxZ));

        return points;
    }

    public record Entry(List<Vec3d> vec3ds, int entityUUID, Identifier entityId) {}
}