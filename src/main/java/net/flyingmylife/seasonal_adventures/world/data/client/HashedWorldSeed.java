package net.flyingmylife.seasonal_adventures.world.data.client;

public class HashedWorldSeed {
    public long seed;

    public HashedWorldSeed(long seed) {
        this.seed = seed;
    }

    public void set(long hashedSeed) {
        seed = hashedSeed;
    }

    public long get() {
        return seed;
    }

    @Override
    public String toString() {
        return Long.toString(seed);
    }
}
