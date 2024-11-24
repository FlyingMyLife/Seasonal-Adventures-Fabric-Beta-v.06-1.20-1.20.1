package net.packages.seasonal_adventures.util;

import net.packages.seasonal_adventures.SeasonalAdventures;

import java.util.Random;
import java.util.Vector;

public class PinAngles {
    private static Vector<Integer> PinAngles = new Vector<>(12);
    private static Integer LockLevel = null;
    private static int pins;
    private static final int[] pinsByLevel = {5, 7, 8 ,10, 12};

    public void generate() {
        if (LockLevel != null) {
            SeasonalAdventures.LOGGER.info("Generated pin angles on level of: {}", LockLevel);
            pins = pinsByLevel[LockLevel];

            PinAngles.clear();
            for (int i = 0; i < pins; i++) {
                PinAngles.add(0);
            }
            Random pinAngle = new Random();
            if (LockLevel >= 0 && LockLevel <= 2) {
                for (int i = 0; i < pins; i++) {
                    boolean tested = false;
                    do {
                        boolean broken_loop = false;
                        int random = pinAngle.nextInt(10, 350);
                        for (int j = 0; j < pins; j++) {
                            if (Math.abs(random - PinAngles.get(j)) <= 20) {
                                broken_loop = true;
                                break;
                            }
                        }
                        if (!broken_loop) {
                            PinAngles.set(i, random);
                            tested = true;
                        }
                    } while (!tested);
                }
            }
        } else {
            SeasonalAdventures.LOGGER.error("Lock level value is not initialized");
        }
    }

    public Vector<Integer> getPinAnglesAsVector() {
        return PinAngles;
    }

    public void setPinAnglesAsVector(Vector<Integer> vector) {
        PinAngles = vector;
    }

    public int getPin(int index) {
        if (index <= pins && PinAngles.elementAt(index) != null) {
            SeasonalAdventures.LOGGER.info("Pins: {}, level: {}, index: {}", pins, LockLevel, index);
            return PinAngles.elementAt(index);
        } else {
            SeasonalAdventures.LOGGER.error("Invalid index returning -1");
            return -1;
        }
    }

    public void setLockLevel(Integer lockLevel) {
        LockLevel = lockLevel;
    }

    public static Integer getLockLevel() {
        return LockLevel;
    }
}
