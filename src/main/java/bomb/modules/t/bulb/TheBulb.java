package bomb.modules.t.bulb;

import bomb.Widget;
import bomb.enumerations.Indicator;
import bomb.modules.s.souvenir.Souvenir;
import bomb.tools.filter.Regex;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static bomb.enumerations.Indicator.CAR;
import static bomb.enumerations.Indicator.CLR;
import static bomb.enumerations.Indicator.FRK;
import static bomb.enumerations.Indicator.FRQ;
import static bomb.enumerations.Indicator.IND;
import static bomb.enumerations.Indicator.MSA;
import static bomb.enumerations.Indicator.SIG;
import static bomb.enumerations.Indicator.SND;
import static bomb.modules.t.bulb.Bulb.Color.BLUE;
import static bomb.modules.t.bulb.Bulb.Color.GREEN;
import static bomb.modules.t.bulb.Bulb.Color.PURPLE;
import static bomb.modules.t.bulb.Bulb.Color.RED;
import static bomb.modules.t.bulb.Bulb.Color.WHITE;
import static bomb.modules.t.bulb.Bulb.Color.YELLOW;
import static bomb.modules.t.bulb.Bulb.Light.OFF;
import static bomb.modules.t.bulb.Bulb.Opacity.OPAQUE;
import static bomb.modules.t.bulb.Bulb.Opacity.TRANSLUCENT;
import static bomb.modules.t.bulb.Bulb.Position.SCREWED;
import static bomb.modules.t.bulb.Bulb.Position.UNSCREWED;

public class TheBulb extends Widget {
    private static final String PRESS_I = "Press I", PRESS_O = "Press O",
            UNSCREW = "Unscrew it", SCREW = "Screw it back in";

    private static boolean isLightOffAtStepOne;
    private static Indicator rememberedIndicator = null;

    public static List<String> solve(Bulb bulb) {
        List<String> outputList = new ArrayList<>();
        stepOne(bulb, outputList);
        if (isSouvenirActive)
            sendInfoToSouvenir(outputList);
        rememberedIndicator = null;
        return outputList;
    }

    private static void stepOne(Bulb bulb, List<String> outputList) {
        if (bulb.getLight() == OFF) {
            unscrewBulb(bulb, outputList);
            stepFour(bulb, outputList);
            return;
        }

        if (bulb.getOpacity() == TRANSLUCENT) {
            outputList.add(PRESS_I);
            stepTwo(bulb, outputList);
            return;
        }

        outputList.add(PRESS_O);
        stepThree(bulb, outputList);
    }

    private static void stepTwo(Bulb bulb, List<String> outputList) {
        if (bulb.getColor() == RED) {
            outputList.add(PRESS_I);
            unscrewBulb(bulb, outputList);
            stepFive(bulb, outputList);
            return;
        }

        if (bulb.getColor() == WHITE) {
            outputList.add(PRESS_O);
            stepSix(outputList);
            unscrewBulb(bulb, outputList);
            return;
        }

        unscrewBulb(bulb, outputList);
        stepSeven(bulb, outputList);
    }

    private static void stepThree(Bulb bulb, List<String> outputList) {
        if (bulb.getColor() == GREEN) {
            outputList.add(PRESS_I);
            checkIfLightTurnsOff(bulb);
            unscrewBulb(bulb, outputList);
            stepSix(outputList);
            return;
        }

        if (bulb.getColor() == PURPLE) {
            outputList.add(PRESS_O);
            unscrewBulb(bulb, outputList);
            stepFive(bulb, outputList);
            return;
        }

        unscrewBulb(bulb, outputList);
        stepEight(bulb, outputList);
    }

    private static void stepFour(Bulb bulb, List<String> outputList) {
        if (hasFollowingIndicators(CAR, IND, MSA, SND)) {
            outputList.add(PRESS_O);
            stepNine(bulb, outputList);
            return;
        }

        outputList.add(PRESS_O);
        stepTen(bulb, outputList);
    }

    private static void stepFive(Bulb bulb, List<String> outputList) {
        if (true) {
            outputList.add("");
        }

        screwBulb(bulb, outputList);
    }

    private static void stepSix(List<String> outputList) {
        if (isLightOffAtStepOne) {
            outputList.add(outputList.get(0));
        }

        //Adds the previous button press
        outputList.add(outputList.get(outputList.size() - 1));
    }

    private static void stepSeven(Bulb bulb, List<String> outputList) {
        if (bulb.getColor() == GREEN) {
            rememberedIndicator = SIG;
            outputList.add(PRESS_I);
            stepEleven(bulb, outputList);
            return;
        }

        if (bulb.getColor() == PURPLE) {
            outputList.add(PRESS_I);
            screwBulb(bulb, outputList);
            stepTwelve(outputList);
            return;
        }

        if (bulb.getColor() == BLUE) {
            rememberedIndicator = CLR;
            outputList.add(PRESS_O);
            stepEleven(bulb, outputList);
            return;
        }

        outputList.add(PRESS_O);
        screwBulb(bulb, outputList);
        stepThirteen(outputList);
    }

    private static void stepEight(Bulb bulb, List<String> outputList) {
        if (bulb.getColor() == WHITE) {
            rememberedIndicator = FRQ;
            outputList.add(PRESS_I);
            stepEleven(bulb, outputList);
            return;
        }

        if (bulb.getColor() == RED) {
            outputList.add(PRESS_I);
            screwBulb(bulb, outputList);
            stepThirteen(outputList);
            return;
        }

        if (bulb.getColor() == YELLOW) {
            rememberedIndicator = FRK;
            outputList.add(PRESS_O);
            stepEleven(bulb, outputList);
            return;
        }

        outputList.add(PRESS_O);
        screwBulb(bulb, outputList);
        stepTwelve(outputList);
    }

    private static void stepNine(Bulb bulb, List<String> outputList) {
        if (bulb.getColor() == BLUE) {
            outputList.add(PRESS_I);
            stepFourteen(bulb, outputList);
            return;
        }

        if (bulb.getColor() == GREEN) {
            outputList.add(PRESS_I);
            screwBulb(bulb, outputList);
            stepTwelve(outputList);
            return;
        }

        if (bulb.getColor() == YELLOW) {
            outputList.add(PRESS_O);
            stepFifteen(bulb, outputList);
            return;
        }

        if (bulb.getColor() == WHITE) {
            outputList.add(PRESS_O);
            screwBulb(bulb, outputList);
            stepThirteen(outputList);
            return;
        }

        if (bulb.getColor() == PURPLE) {
            screwBulb(bulb, outputList);
            outputList.add(PRESS_I);
            stepTwelve(outputList);
            return;
        }

        screwBulb(bulb, outputList);
        outputList.add(PRESS_O);
        stepThirteen(outputList);
    }

    private static void stepTen(Bulb bulb, List<String> outputList) {
        if (bulb.getColor() == PURPLE) {
            outputList.add(PRESS_I);
            stepFourteen(bulb, outputList);
            return;
        }

        if (bulb.getColor() == RED) {
            outputList.add(PRESS_I);
            screwBulb(bulb, outputList);
            stepThirteen(outputList);
            return;
        }

        if (bulb.getColor() == BLUE) {
            outputList.add(PRESS_O);
            stepFifteen(bulb, outputList);
            return;
        }

        if (bulb.getColor() == YELLOW) {
            outputList.add(PRESS_O);
            screwBulb(bulb, outputList);
            stepTwelve(outputList);
            return;
        }

        if (bulb.getColor() == GREEN) {
            screwBulb(bulb, outputList);
            outputList.add(PRESS_I);
            stepThirteen(outputList);
            return;
        }

        screwBulb(bulb, outputList);
        outputList.add(PRESS_O);
        stepTwelve(outputList);
    }

    private static void stepEleven(Bulb bulb, List<String> outputList) {
        boolean hasRememberedIndicator = rememberedIndicator != null && hasIndicator(rememberedIndicator);
        outputList.add(hasRememberedIndicator ? PRESS_I : PRESS_O);
        screwBulb(bulb, outputList);
    }

    private static void stepTwelve(List<String> outputList) {
        boolean isLightOn = confirmLightIsOn();
        outputList.add(isLightOn ? PRESS_I : PRESS_O);
    }

    private static void stepThirteen(List<String> outputList) {
        boolean isLightOn = confirmLightIsOn();
        outputList.add(isLightOn ? PRESS_O : PRESS_I);
    }

    private static void stepFourteen(Bulb bulb, List<String> outputList) {
        outputList.add(bulb.getOpacity() == OPAQUE ? PRESS_I : PRESS_O);
        screwBulb(bulb, outputList);
    }

    private static void stepFifteen(Bulb bulb, List<String> outputList) {
        outputList.add(bulb.getOpacity() == TRANSLUCENT ? PRESS_I : PRESS_O);
        screwBulb(bulb, outputList);
    }

    private static void sendInfoToSouvenir(List<String> outputList) {
        Regex findButtonPresses = new Regex("Press [IO]");
        findButtonPresses.loadCollection(outputList);
        List<String> matches = findButtonPresses.findAllMatches();
        StringBuilder toSouvenir = new StringBuilder();

        for (int i = 0; i < matches.size(); i++) {
            toSouvenir.append(matches.get(i));
            if (i != matches.size() - 1)
                toSouvenir.append("\n");
        }
        Souvenir.addRelic("The Bulb button presses", toSouvenir.toString());
    }

    private static void checkIfLightTurnsOff(Bulb bulb) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Off at the Press I");
        alert.setContentText("Did the Bulb turn off when you pressed I?");

        ButtonType no = new ButtonType("No"),
                yes = new ButtonType("Yes");

        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(yes, no);
        Optional<ButtonType> options = alert.showAndWait();

        options.ifPresent(buttonType -> isLightOffAtStepOne = buttonType == yes);

        if (isLightOffAtStepOne) {
            bulb.setLight(OFF);
        }
    }

    private static boolean confirmLightIsOn() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Light Confirmation");
        alert.setContentText("Is the bulb now on or off?");

        ButtonType on = new ButtonType("On"),
                off = new ButtonType("Off");

        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(on, off);
        Optional<ButtonType> options = alert.showAndWait();

        if (options.isEmpty())
            throw new IllegalStateException("Unexpected state");
        return options.get() == on;
    }

    private static void unscrewBulb(Bulb theBulb, List<String> list) {
        if (theBulb.getPosition() == UNSCREWED) throw new IllegalStateException("Invalid Unscrew state");
        theBulb.setPosition(UNSCREWED);
        list.add(UNSCREW);
    }

    private static void screwBulb(Bulb theBulb, List<String> list) {
        if (theBulb.getPosition() == SCREWED) throw new IllegalStateException("Invalid Screw state");
        theBulb.setPosition(SCREWED);
        list.add(SCREW);
    }
}
