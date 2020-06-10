/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.tec.hermes.utils;

import java.util.BitSet;

/**
 *
 * @author Mauricio
 */
public final class BitManipulator {
    /**
     * Returns the value as integer of the bit set provided as argument.
     * <p/>
     * @param bitset The bit set whose integer value is required.
     * @return The value as integer of the bit set provided as argument.
     */
    public static int toInteger(BitSet bits) {
        int i, value;
        value = 0;
        i = bits.nextSetBit(0);
        while (i >= 0) {
            value += Math.pow(2, i);
            i = bits.nextSetBit(i + 1);
        }
        return value;
    }

    /**
     * Returns the double value of the bit set provided as argument.
     * 
     * @param bitset The bit set whose integer value is required.
     * @param numberOfBits The maximum number of bits that can be used to
     * represent the value.
     * @return The value as double of the bit set provided as argument.
     */
    public static double toDouble(BitSet bits, int numberOfBits, double MAX_VALUE, double MIN_VALUE) {
        int numberOfSteps;
        double stepSize;
        stepSize = (MAX_VALUE - MIN_VALUE) / Math.pow(2, numberOfBits);
        numberOfSteps = toInteger(bits);
        return MIN_VALUE + (stepSize / 2) + numberOfSteps * stepSize;
    }

    public static BitSet toBitSet(int value) {
        int i;
        BitSet bits;
        bits = new BitSet();
        i = 0;
        while (value > 0) {
            bits.set(i++, (value % 2 != 0));
            value = value / 2;
        }
        return bits;
    }

    /**
     * @param numberOfBits The maximum number of bits that can be used to
     * represent the value.
     */
    public static BitSet toBitSet(double value, int numberOfBits, double MIN_VALUE, double MAX_VALUE) {
        double stepSize;
        if (value < MIN_VALUE) {
            value = MIN_VALUE;
        }
        if (value > MAX_VALUE) {
            value = MAX_VALUE;
        }
        stepSize = (MAX_VALUE - MIN_VALUE) / Math.pow(2, numberOfBits);
        return toBitSet((int) Math.round((value + Math.abs(MIN_VALUE + stepSize / 2)) / stepSize));
    }
}
