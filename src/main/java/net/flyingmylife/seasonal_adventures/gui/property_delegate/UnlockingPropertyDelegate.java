package net.flyingmylife.seasonal_adventures.gui.property_delegate;

import net.flyingmylife.seasonal_adventures.SA;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.PropertyDelegate;

import java.util.Random;

public class UnlockingPropertyDelegate implements PropertyDelegate {
    private static final int[] pinsByLevel = {5, 7, 8, 10, 12};
    private final int lockLevel;
    private final int pins;
    private final int[] data;

    public UnlockingPropertyDelegate(int lockLevel) {
        this.lockLevel = lockLevel;
        this.pins = pinsByLevel[lockLevel];
        this.data = new int[pins];
        generate();
    }

    public void generate() {
        Random random = new Random();

        if (lockLevel >= 0 && lockLevel <= 2) {
            for (int i = 0; i < pins; i++) {
                boolean isValid = false;
                int value = 0;
                while (!isValid) {
                    value = random.nextInt(10, 350);
                    isValid = true;
                    for (int j = 0; j < i; j++) {
                        if (Math.abs(value - data[j]) < 25) {
                            isValid = false;
                            break;
                        }
                    }
                }
                data[i] = value;
            }
        } else if (lockLevel == 3 || lockLevel == 4) {
            for (int i = 0; i < pins; i++) {
                data[i] = (i * 360) / pins;
            }
        }

        SA.LOGGER.info("Pin angles: {}", (Object) data);
    }

    public int getPin(int index) {
        if (index >= 0 && index < pins) {
            return data[index];
        }
        SA.LOGGER.error("Invalid pin index: {}, returning -1", index);
        return -1;
    }

    public int getPinCount() {
        return pins;
    }

    public int getLockLevel() {
        return lockLevel;
    }

    public boolean isEmpty() {
        for (int value : data) {
            if (value != 0) return false;
        }
        return true;
    }

    public NbtCompound getNbt() {
        NbtCompound nbt = new NbtCompound();
        nbt.putInt("lock_level", lockLevel);
        for (int i = 0; i < pins; i++) {
            nbt.putInt("pin" + i, data[i]);
        }
        return nbt;
    }

    public void readNbt(NbtCompound nbt) {
        for (int i = 0; i < pins; i++) {
            data[i] = nbt.getInt("pin" + i);
        }
    }

    @Override
    public int get(int index) {
        if (index >= 0 && index < pins) {
            return data[index];
        }
        return 0;
    }

    @Override
    public void set(int index, int value) {
        if (index >= 0 && index < pins) {
            data[index] = value;
        }
    }

    @Override
    public int size() {
        return pins;
    }
}
