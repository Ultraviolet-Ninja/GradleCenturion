package bomb.modules.s.simon.screams;

import bomb.modules.s.simon.SimonColors.Screams;
import bomb.tools.data.structures.ring.ReadOnlyRing;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Star {
    private static final byte LIMIT = 6;

    private final ReadOnlyRing<Screams> colorOrder;

    public Star(Screams[] order){
        checkUniqueColors(order);
        colorOrder = new ReadOnlyRing<>(LIMIT);
        for (Screams instance : order) colorOrder.add(instance);
    }

    private void checkUniqueColors(Screams[] order){
        Set<Screams> set = new HashSet<>(Arrays.asList(order));
        if (set.size() != LIMIT) throw new IllegalArgumentException("Size doesn't equal 6");
    }

    public boolean threeAdjacencyRule(Screams[] flashOrder){
        for (int i = 0; i < flashOrder.length - 2; i++)
            if (threeAdjacencyRule(flashOrder[i], flashOrder[i+1], flashOrder[i+2]))
                return true;
        return false;
    }

    //Three that are adjacent in clockwise order
    public boolean threeAdjacencyRule(Screams first, Screams second, Screams third){
        int firstIdx = colorOrder.findAbsoluteIndex(first);
        int secondIdx = colorOrder.findAbsoluteIndex(second);
        int thirdIdx = colorOrder.findAbsoluteIndex(third);
        return thirdIdx - secondIdx == 1 && secondIdx - firstIdx == 1;
    }

    //Color, Adjacent, then color again
    public boolean oneTwoOneRule(Screams[] flashOrder){
        int track = sameColor(flashOrder);
        return track != -1 && twoAdjacencyRule(flashOrder[track + 1], flashOrder[track + 2]);
    }

    //Finding if the same color flashed twice with one color in between
    private int sameColor(Screams[] flashOrder){
        for (int i = 0; i < flashOrder.length-2; i++)
            if (flashOrder[i] == flashOrder[i+2]) return i;
        return -1;
    }

    //If no or one primary colors are flashing
    public boolean primaryRule(Screams[] flashOrder){
        Set<Screams> unique = new HashSet<>();
        Collections.addAll(unique, flashOrder);

        int counter = 0;
        for (Screams instance : unique) {
            switch (instance) {
                case RED:
                case BLUE:
                case YELLOW:
                    counter++;
                    break;
                default:
            }
        }
        return counter < 2;
    }

    //Complementary Color Not Flashed Rule
    public boolean complementRule(Screams[] flashOrder){
        int counter  = 0;
        for (int i = 0; i < LIMIT/2; i++){
            for (Screams flashColor : flashOrder) {
                int index = colorOrder.findRelativeIndex(flashColor);
                if (index == i || index == i + LIMIT/2)
                    counter++;
            }
        }
        return counter == 0;
    }

    public boolean twoAdjacencyRule(Screams[] flashOrder){
        for (int i = 0; i < flashOrder.length-1; i++) {
            if (twoAdjacencyRule(flashOrder[i], flashOrder[i + 1])) return true;
        }
        return false;
    }

    //Two that are adjacent in clockwise order
    public boolean twoAdjacencyRule(Screams first, Screams second){
        int firstIdx = colorOrder.findAbsoluteIndex(first);
        int secondIdx = colorOrder.findAbsoluteIndex(second);
        return secondIdx - firstIdx == 1;
    }
}