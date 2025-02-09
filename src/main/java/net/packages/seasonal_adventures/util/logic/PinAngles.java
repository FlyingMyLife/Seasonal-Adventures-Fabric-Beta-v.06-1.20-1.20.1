package net.packages.seasonal_adventures.util.logic;

import net.packages.seasonal_adventures.SeasonalAdventures;

import java.util.Random;
import java.util.Vector;

public class PinAngles {

    private Vector<Integer> PinAngles = new Vector<>(12);
    private Integer lockLevel = null;
    private int pins;
    private static final int[] pinsByLevel = {5, 7, 8 ,10, 12};
    public PinAngles() {

    }
    public void generate() {
        if (lockLevel != null) {
            pins = pinsByLevel[lockLevel];

            PinAngles.clear();
            for (int i = 0; i < pins; i++) {
                PinAngles.add(0);
            }
            Random pinAngle = new Random();
            if (lockLevel >= 0 && lockLevel <= 2) {
                for (int i = 0; i < pins; i++) {
                    boolean broken_loop = false;
                    do {
                        int random = pinAngle.nextInt(10, 350);
                        for (int j = 0; j < pins; j++) {
                            if (Math.abs(random - PinAngles.get(j)) >= 25) {
                                broken_loop = true;
                                break;
                            }
                        }
                        if (broken_loop) {
                            PinAngles.set(i, random);
                        }
                    } while (!broken_loop);
                }
            } else if (lockLevel == 3 || lockLevel == 4) {
                pins = pinsByLevel[lockLevel];
                PinAngles.clear();
                for (int i = 0; i < pins; i++) {
                    PinAngles.add((i * 360) / pins);
                }
            }
            SeasonalAdventures.LOGGER.info("{}", PinAngles);
        } else {
            SeasonalAdventures.LOGGER.error("Lock level value is not initialized");
        }
    }

    public int getPin(int index) {
        if (PinAngles.size() > index && PinAngles.elementAt(index) != null) {
            return PinAngles.elementAt(index);
        } else {
            SeasonalAdventures.LOGGER.error("Invalid pin index or empty PinAngles, returning -1");
            return -1;
        }
    }

    public void setLockLevel(Integer lockLevel) {
        this.lockLevel = lockLevel;
    }

    public Integer getLockLevel() {
        return lockLevel;
    }
}
