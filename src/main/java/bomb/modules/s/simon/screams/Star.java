package bomb.modules.s.simon.screams;

import bomb.modules.s.simon.SimonColors.ScreamColor;
import bomb.tools.data.structures.ring.ArrayRing;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Star {
    private static final byte LIMIT = 6;

    private final ArrayRing<ScreamColor> colorOrder;

    public Star(ScreamColor[] order) {
        checkUniqueColors(order);
        colorOrder = new ArrayRing<>(LIMIT);
        for (ScreamColor instance : order) colorOrder.add(instance);
    }

    private void checkUniqueColors(ScreamColor[] order) {
        Set<ScreamColor> set = new HashSet<>(Arrays.asList(order));
        if (set.size() != LIMIT) throw new IllegalArgumentException("Size doesn't equal 6");
    }

    public boolean threeAdjacencyRule(ScreamColor[] flashOrder) {
        for (int i = 0; i < flashOrder.length - 2; i++)
            if (threeAdjacencyRule(flashOrder[i], flashOrder[i + 1], flashOrder[i + 2]))
                return true;
        return false;
    }

    //Three that are adjacent in clockwise order
    public boolean threeAdjacencyRule(ScreamColor first, ScreamColor second, ScreamColor third) {
        int firstIdx = colorOrder.findAbsoluteIndex(first);
        int secondIdx = colorOrder.findAbsoluteIndex(second);
        int thirdIdx = colorOrder.findAbsoluteIndex(third);
        return thirdIdx - secondIdx == 1 && secondIdx - firstIdx == 1;
    }

    //Color, Adjacent, then color again
    public boolean oneTwoOneRule(ScreamColor[] flashOrder) {
        int track = sameColor(flashOrder);
        return track != -1 && twoAdjacencyRule(flashOrder[track + 1], flashOrder[track + 2]);
    }

    //Finding if the same color flashed twice with one color in between
    private int sameColor(ScreamColor[] flashOrder) {
        for (int i = 0; i < flashOrder.length - 2; i++)
            if (flashOrder[i] == flashOrder[i + 2]) return i;
        return -1;
    }

    //If there are one or less primary colors are flashing
    public boolean primaryRule(ScreamColor[] flashOrder) {
        Set<ScreamColor> unique = new HashSet<>();
        Collections.addAll(unique, flashOrder);

        int counter = 0;
        for (ScreamColor instance : unique) {
            switch (instance) {
                case RED, BLUE, YELLOW -> counter++;
                default -> {
                }
            }
        }
        return counter < 2;
    }

    //Complementary Color Not Flashed Rule
    public boolean complementRule(ScreamColor[] flashOrder) {
        int counter = 0;
        for (int i = 0; i < LIMIT / 2; i++) {
            for (ScreamColor flashColor : flashOrder) {
                int index = colorOrder.findRelativeIndex(flashColor);
                if (index == i || index == i + LIMIT / 2)
                    counter++;
            }
        }
        return counter == 0;
    }

    public boolean twoAdjacencyRule(ScreamColor[] flashOrder) {
        for (int i = 0; i < flashOrder.length - 1; i++) {
            if (twoAdjacencyRule(flashOrder[i], flashOrder[i + 1])) return true;
        }
        return false;
    }

    //Two that are adjacent in clockwise order
    public boolean twoAdjacencyRule(ScreamColor first, ScreamColor second) {
        int firstIdx = colorOrder.findAbsoluteIndex(first);
        int secondIdx = colorOrder.findAbsoluteIndex(second);
        return secondIdx - firstIdx == 1;
    }
}