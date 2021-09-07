package bomb.modules.m.microcontroller.chip;

import bomb.modules.m.microcontroller.Pin;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractController {
    public static final byte SIX_PIN = 6, EIGHT_PIN = 8, TEN_PIN = 10;

    protected final List<Pin> pinOrder;

    private final byte pinCount;

    public AbstractController(int pinCount) {
        this.pinCount = (byte) pinCount;
        pinOrder = new ArrayList<>();
        setController();
    }

    private void setController() throws IllegalArgumentException {
        switch (pinCount) {
            case SIX_PIN -> setSixPin();
            case EIGHT_PIN -> setEightPin();
            case TEN_PIN -> setTenPin();
            default -> throw new IllegalArgumentException("Illegal pin count");
        }
    }

    protected abstract void setSixPin();

    protected abstract void setEightPin();

    protected abstract void setTenPin();

    public List<Color> traversePins(final int index) {
        List<Color> outputList = new ArrayList<>();
        for (Pin pin : pinOrder) outputList.add(pin.getColorAtIndex(index));
        return outputList;
    }
}
