package bomb.modules.t.bulb;

import bomb.Widget;
import bomb.enumerations.Indicator;

import java.util.function.Consumer;

import static bomb.modules.t.bulb.Bulb.Color.GREEN;
import static bomb.modules.t.bulb.Bulb.Color.PURPLE;
import static bomb.modules.t.bulb.Bulb.Color.RED;
import static bomb.modules.t.bulb.Bulb.Color.WHITE;
import static bomb.modules.t.bulb.Bulb.THE_BULB;

public class TheNewBulb extends Widget {
    private static final Consumer<Bulb> UNSCREW_ACTION = theBulb -> theBulb.setPosition(Bulb.Position.UNSCREWED),
            SCREW_ACTION = theBulb -> theBulb.setPosition(Bulb.Position.SCREWED);

    private static final String PRESS_I = "Press I ", PRESS_O = "Press O ",
            UNSCREW = "Unscrew it ", SCREW = "Screw it ", ARROW = "-> ";

    private static Indicator rememberedIndicator;

    public static String solve(){
        //TODO Add Souvenir
        return stepOne();
    }

    private static String stepOne(){
        if (THE_BULB.getLight() == Bulb.Light.ON){
            return THE_BULB.getOpacity() == Bulb.Opacity.TRANSLUCENT ?
                    PRESS_I + ARROW + stepTwo() :
                    PRESS_O + ARROW + stepThree();
        }
        UNSCREW_ACTION.accept(THE_BULB);
        return UNSCREW + ARROW + stepFour();
    }

    private static String stepTwo(){
        UNSCREW_ACTION.accept(THE_BULB);
        if (THE_BULB.getColor() == RED)
            return PRESS_I + ARROW + UNSCREW + ARROW + stepFive();
        if (THE_BULB.getColor() == WHITE)
            return PRESS_O + ARROW + UNSCREW + ARROW + stepSix();
        return UNSCREW + ARROW + stepSeven();
    }

    private static String stepThree(){
        UNSCREW_ACTION.accept(THE_BULB);
        if (THE_BULB.getColor() == GREEN)
            return PRESS_I + ARROW + UNSCREW + ARROW + stepSix();
        if (THE_BULB.getColor() == PURPLE)
            return PRESS_O + ARROW + UNSCREW + ARROW + stepFive();
        return UNSCREW + ARROW + stepEight();
    }

    private static String stepFour(){
        if (hasFollowingInds(Indicator.CAR, Indicator.IND, Indicator.MSA, Indicator.SND))
            return PRESS_I + ARROW + stepNine();
        return PRESS_O + ARROW + stepTen();
    }

    private static String stepFive(){
        return null;
    }

    private static String stepSix(){
        return null;
    }

    private static String stepSeven(){
        return null;
    }

    private static String stepEight(){
        return null;
    }

    private static String stepNine(){
        return null;
    }

    private static String stepTen(){
        return null;
    }

    private static String stepEleven(){
        return null;
    }

    private static String stepTwelve(){
        return null;
    }

    private static String stepThirteen(){
        return null;
    }

    private static String stepFourteen(){
        return (THE_BULB.getOpacity() == Bulb.Opacity.OPAQUE ? PRESS_I : PRESS_O) +
                ARROW + SCREW + " back in";
    }

    private static String stepFifteen(){
        return (THE_BULB.getOpacity() == Bulb.Opacity.TRANSLUCENT ? PRESS_I : PRESS_O) +
                ARROW + SCREW + " back in";
    }
}
