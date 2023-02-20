package bomb.modules.m.microcontroller.chip;

import bomb.modules.m.microcontroller.Pin;

public final class CountdownController extends AbstractController {
    public static final String ACRONYM = "CNTD";

    public CountdownController(int pinCount) {
        super(pinCount, ACRONYM);
    }

    @Override
    protected void setSixPin() {
        pinOrder.add(Pin.GND);
        pinOrder.add(Pin.AIN);
        pinOrder.add(Pin.PWM);
        pinOrder.add(Pin.VCC);
        pinOrder.add(Pin.DIN);
        pinOrder.add(Pin.RST);
    }

    @Override
    protected void setEightPin() {
        pinOrder.add(Pin.PWM);
        pinOrder.add(Pin.GND);
        pinOrder.add(Pin.GND);
        pinOrder.add(Pin.VCC);
        pinOrder.add(Pin.AIN);
        pinOrder.add(Pin.GND);
        pinOrder.add(Pin.DIN);
        pinOrder.add(Pin.RST);
    }

    @Override
    protected void setTenPin() {
        pinOrder.add(Pin.PWM);
        pinOrder.add(Pin.DIN);
        pinOrder.add(Pin.AIN);
        pinOrder.add(Pin.GND);
        pinOrder.add(Pin.GND);
        pinOrder.add(Pin.VCC);
        pinOrder.add(Pin.GND);
        pinOrder.add(Pin.GND);
        pinOrder.add(Pin.RST);
        pinOrder.add(Pin.GND);
    }
}
