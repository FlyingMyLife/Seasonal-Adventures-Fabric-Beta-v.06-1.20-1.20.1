package dev.flyingmylife.seasonal_adventures.block.entity.lockedChests;

import net.minecraft.nbt.NbtCompound;
import dev.flyingmylife.seasonal_adventures.SA;

import java.util.Random;
import java.util.Vector;

public class PinAngles {
    private final Vector<Integer> pinAngles = new Vector<>();
    private Integer lockLevel = null;
    private int pins;
    private static final int[] pinsByLevel = {5, 7, 8, 10, 12};

    public PinAngles(int lockLevel) {
        this.lockLevel = lockLevel;
        pins = pinsByLevel[lockLevel];
    }

    public void generate() {
        if (lockLevel != null) {
            pins = pinsByLevel[lockLevel];
            pinAngles.clear();
            for (int i = 0; i < pins; i++) {
                pinAngles.add(0);
            }
            Random pinAngle = new Random();
            if (lockLevel >= 0 && lockLevel <= 2) {
                for (int i = 0; i < pins; i++) {
                    boolean broken_loop = false;
                    do {
                        int random = pinAngle.nextInt(10, 350);
                        for (int j = 0; j < pins; j++) {
                            if (Math.abs(random - pinAngles.get(j)) >= 25) {
                                broken_loop = true;
                                break;
                            }
                        }
                        if (broken_loop) {
                            pinAngles.set(i, random);
                        }
                    } while (!broken_loop);
                }
            } else if (lockLevel == 3 || lockLevel == 4) {
                pins = pinsByLevel[lockLevel];
                pinAngles.clear();
                for (int i = 0; i < pins; i++) {
                    pinAngles.add((i * 360) / pins);
                }
            }
            SA.LOGGER.info("{}", pinAngles);
        } else {
            SA.LOGGER.error("Lock level value is not initialized");
        }
    }

    public int getPin(int index) {
        if (pinAngles.size() > index && pinAngles.elementAt(index) != null) {
            return pinAngles.elementAt(index);
        } else {
            SA.LOGGER.error("Invalid pin index or empty PinAngles, returning -1");
            return -1;
        }
    }

    public Integer getLockLevel() {
        return lockLevel;
    }
    public boolean isEmpty() {
        return pinAngles.isEmpty();
    }

    public NbtCompound getNbt() {
        NbtCompound nbt = new NbtCompound();
        nbt.putInt("lock_level", lockLevel != null ? lockLevel : -1);
        for (int i = 0; i < pinAngles.size(); i++) {
            nbt.putInt("pin" + i, pinAngles.get(i));
        }
        return nbt;
    }

    public void readNbt(NbtCompound nbt) {
        lockLevel = nbt.contains("lock_level") ? nbt.getInt("lock_level") : null;
        pinAngles.clear();
        if (lockLevel != null) {
            pins = pinsByLevel[lockLevel];
            for (int i = 0; i < pins; i++) {
                pinAngles.add(nbt.getInt("pin" + i));
            }
        }
    }
}