package net.flyingmylife.seasonal_adventures.world.data;

import net.flyingmylife.seasonal_adventures.world.data.client.HashedWorldSeed;

public class ClientData {
    public static HashedWorldSeed hashedWorldSeed;


    public static void reset() {
        hashedWorldSeed = new HashedWorldSeed(-1);
    }

    public static void setSeed(long hashedSeed) {
        hashedWorldSeed = new HashedWorldSeed(hashedSeed);
    }
}
