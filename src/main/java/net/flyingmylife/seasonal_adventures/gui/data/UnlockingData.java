
package net.flyingmylife.seasonal_adventures.gui.data;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.flyingmylife.seasonal_adventures.SA;
import net.minecraft.util.math.BlockPos;

import java.util.Random;
import java.util.Vector;

public class UnlockingData {
    private final Vector<Integer> pinAngles = new Vector<>();
    private int pins;
    private static final int[] pinsByLevel = {5, 7, 8, 10, 12};

    public UnlockingData() {
    }

    public void generate(BlockPos pos, int lockLevel) {
        int blockPosHash = pos.hashCode();
        long seed = blockPosHash + lockLevel;

        if (lockLevel >= 0) {
            pins = pinsByLevel[lockLevel];
            pinAngles.clear();
            for (int i = 0; i < pins; i++) {
                pinAngles.add(0);
            }
            Random pinAngle = new Random(seed);
            if (lockLevel <= 2) {
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
            } else if (lockLevel >= 3) {
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

    @Override
    public String toString() {
        return "data {" +
                "pinAngles= " + pinAngles +
                ", pins= " + pins +
                '}';
    }

    public int getPin(int index) {
        if (pinAngles.size() > index && pinAngles.elementAt(index) != null) {
            return pinAngles.elementAt(index);
        } else {
            SA.LOGGER.error("Invalid pin index or empty PinAngles, returning -1");
            return -1;
        }
    }

    public boolean isEmpty() {
        return pinAngles.isEmpty();
    }
    public int getPinCount() {
        return pins;
    }
}