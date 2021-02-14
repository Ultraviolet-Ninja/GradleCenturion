package bomb.modules.m.microcontroller.chip;

import bomb.modules.m.microcontroller.Pin;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public abstract class AbstractController {
    public static final int SIX_PIN = 6, EIGHT_PIN = 8, TEN_PIN = 10;

    protected final ArrayList<Pin> pinOrder;

    private final int pinCount;

    public AbstractController(int pinCount){
        this.pinCount = pinCount;
        pinOrder = new ArrayList<>();
        setController();
    }

    private void setController(){
        switch (pinCount){
            case SIX_PIN: setSixPin(); break;
            case EIGHT_PIN: setEightPin(); break;
            case TEN_PIN: setTenPin(); break;
            default: throw new IllegalArgumentException("Illegal pin count");
        }
    }

    protected abstract void setSixPin();

    protected abstract void setEightPin();

    protected abstract void setTenPin();

    public ArrayList<Color> traversePins(final int index){
        ArrayList<Color> outputList = new ArrayList<>();
        for (Pin pin : pinOrder) outputList.add(pin.getColor(index));
        return outputList;
    }
}
