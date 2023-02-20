package bomb.modules.s.simon.screams;

import bomb.modules.s.simon.SimonColors.ScreamColor;
import bomb.tools.data.structures.ring.ArrayRing;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

import static bomb.modules.s.simon.SimonColors.ScreamColor.BLUE;
import static bomb.modules.s.simon.SimonColors.ScreamColor.RED;
import static bomb.modules.s.simon.SimonColors.ScreamColor.YELLOW;
import static java.util.Arrays.asList;

public final class Star {
    private static final byte LIMIT = 6;

    private final ArrayRing<ScreamColor> colorOrder;

    public Star(@NotNull ScreamColor[] order) throws IllegalArgumentException {
        checkUniqueColors(order);
        colorOrder = new ArrayRing<>(LIMIT);
        for (ScreamColor instance : order) colorOrder.add(instance);
    }

    private static void checkUniqueColors(ScreamColor[] order) throws IllegalArgumentException {
        Set<ScreamColor> set = EnumSet.copyOf(asList(order));
        if (set.size() != LIMIT) throw new IllegalArgumentException("Size doesn't equal 6");
    }

    public boolean threeAdjacencyRule(ScreamColor @NotNull [] flashOrder) {
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
        Set<ScreamColor> uniqueFlashes = EnumSet.noneOf(ScreamColor.class);
        Collections.addAll(uniqueFlashes, flashOrder);

        return uniqueFlashes.stream()
                .filter(color -> color == RED || color == BLUE || color == YELLOW)
                .count() < 2;
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

    public boolean twoAdjacencyRule(ScreamColor @NotNull [] flashOrder) {
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