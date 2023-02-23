package bomb.modules.m.microcontroller.chip;

import bomb.modules.m.microcontroller.Pin;

public final class ExplodeController extends AbstractController {
    public static final String ACRONYM = "EXPL";

    public ExplodeController(int pinCount) {
        super(pinCount, ACRONYM);
    }

    @Override
    protected void setSixPin() {
        pinOrder.add(Pin.PWM);
        pinOrder.add(Pin.VCC);
        pinOrder.add(Pin.RST);
        pinOrder.add(Pin.AIN);
        pinOrder.add(Pin.DIN);
        pinOrder.add(Pin.GND);
    }

    @Override
    protected void setEightPin() {
        pinOrder.add(Pin.AIN);
        pinOrder.add(Pin.GND);
        pinOrder.add(Pin.RST);
        pinOrder.add(Pin.GND);
        pinOrder.add(Pin.VCC);
        pinOrder.add(Pin.GND);
        pinOrder.add(Pin.DIN);
        pinOrder.add(Pin.PWM);
    }

    @Override
    protected void setTenPin() {
        pinOrder.add(Pin.RST);
        pinOrder.add(Pin.DIN);
        pinOrder.add(Pin.VCC);
        pinOrder.add(Pin.GND);
        pinOrder.add(Pin.GND);
        pinOrder.add(Pin.GND);
        pinOrder.add(Pin.AIN);
        pinOrder.add(Pin.GND);
        pinOrder.add(Pin.PWM);
        pinOrder.add(Pin.GND);
    }
}
