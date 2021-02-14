package bomb.modules.m.microcontroller.chip;

import bomb.modules.m.microcontroller.Pin;

public class DiodeController extends AbstractController{
    public DiodeController(int pinCount) {
        super(pinCount);
    }

    @Override
    protected void setSixPin() {
        pinOrder.add(Pin.PWM);
        pinOrder.add(Pin.RST);
        pinOrder.add(Pin.VCC);
        pinOrder.add(Pin.DIN);
        pinOrder.add(Pin.AIN);
        pinOrder.add(Pin.GND);
    }

    @Override
    protected void setEightPin() {
        pinOrder.add(Pin.PWM);
        pinOrder.add(Pin.DIN);
        pinOrder.add(Pin.VCC);
        pinOrder.add(Pin.GND);
        pinOrder.add(Pin.AIN);
        pinOrder.add(Pin.GND);
        pinOrder.add(Pin.RST);
        pinOrder.add(Pin.GND);
    }

    @Override
    protected void setTenPin() {
        pinOrder.add(Pin.PWM);
        pinOrder.add(Pin.DIN);
        pinOrder.add(Pin.AIN);
        pinOrder.add(Pin.GND);
        pinOrder.add(Pin.GND);
        pinOrder.add(Pin.GND);
        pinOrder.add(Pin.GND);
        pinOrder.add(Pin.RST);
        pinOrder.add(Pin.VCC);
        pinOrder.add(Pin.GND);
    }
}
