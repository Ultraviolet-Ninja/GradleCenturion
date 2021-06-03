package bomb.tools;

import bomb.modules.s.simon.Simon.Screams;
import java.util.Arrays;

import static bomb.modules.s.simon.Simon.Screams.*;

public class OldStar {
    private final Screams[] colorOrder;

    public OldStar(Screams[] order) throws IllegalArgumentException{
        if (order.length == 6) {
            this.colorOrder = order;
            setPrev();
            setNext();
        } else throw new IllegalArgumentException("Size doesn't equal 6");
    }

    private void setPrev(){
        Screams prev = null;
        for (int i = 0; i <= colorOrder.length; i++) {
            if (i != 0) colorOrder[i% colorOrder.length].setLeft(prev);
            prev = colorOrder[i% colorOrder.length];
        }
    }

    private void setNext(){
        Screams next = null;
        for (int i = colorOrder.length; i >= 0; i--){
            if (i != colorOrder.length) colorOrder[i% colorOrder.length].setRight(next);
            next = colorOrder[i% colorOrder.length];
        }
    }

    public boolean threeAdjacency(Screams[] flashOrder){
        for (int i = 0; i < flashOrder.length-2; i++)
            if (threeAdjacency(flashOrder[i], flashOrder[i+1], flashOrder[i+2])) return true;
        return false;
    }

    //Three that are adjacent in clockwise order
    public boolean threeAdjacency(Screams first, Screams second, Screams third){
        for (Screams color : colorOrder)
            if (color == first && color.getRight() == second && color.getRight().getRight() == third)
                return true;
        return false;
    }

    //Color, Adjacent, then color again
    public boolean oneTwoOne(Screams[] flashOrder){
        int track = sameColor(flashOrder);
        return track != -1 && twoAdjacency(flashOrder[track], flashOrder[track + 1]);
    }

    //Finding if the same color flashed twice with one color in between
    private int sameColor(Screams[] flashOrder){
        int tracker = -1;
        for (int i = 0; i < flashOrder.length-2; i++)
            if (flashOrder[i] == flashOrder[i+2]) tracker = i;
        return tracker;
    }

    //If no or one primary colors are flashing
    public boolean primaryRule(Screams[] flashOrder){
        Screams[] unique = Arrays.stream(flashOrder).distinct().toArray(Screams[]::new);
        int counter = 0;
        for (Screams temp : unique){
            if (temp == RED || temp == YELLOW || temp == BLUE)
                counter++;
        }
        return counter < 2;
    }

    //Complementary Color Not Flashed Rule
    public boolean complementRule(Screams[] flashOrder){
        int counter  = 0;
        for (int i = 0; i < colorOrder.length/2; i++){
           for (Screams flashColor : flashOrder)
               if (flashColor == colorOrder[i] || flashColor == colorOrder[i + 3]) counter++;
        }
        return counter == 0;
    }

    public boolean twoAdjacency(Screams[] flashOrder){
        for (int i = 0; i < flashOrder.length-1; i++)
            if (twoAdjacency(flashOrder[i], flashOrder[i+1])) return true;
        return false;
    }

    //Two that are adjacent in clockwise order
    public boolean twoAdjacency(Screams first, Screams second){
        for (Screams color : colorOrder)
            if (color == first && color.getRight() == second)
                return true;
        return false;
    }
}