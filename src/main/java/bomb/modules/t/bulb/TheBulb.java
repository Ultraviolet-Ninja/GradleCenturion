package bomb.modules.t.bulb;

import bomb.Widget;
import bomb.annotation.DisplayComponent;
import bomb.enumerations.Indicator;
import bomb.modules.s.souvenir.Souvenir;
import bomb.tools.filter.Regex;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.jetbrains.annotations.NotNull;

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
import static bomb.modules.t.bulb.Bulb.Light.ON;
import static bomb.modules.t.bulb.Bulb.Opacity.OPAQUE;
import static bomb.modules.t.bulb.Bulb.Opacity.TRANSLUCENT;
import static bomb.modules.t.bulb.Bulb.Position.SCREWED;
import static bomb.modules.t.bulb.Bulb.Position.UNSCREWED;

@DisplayComponent(resource = "the_bulb.fxml", buttonLinkerName = "The Bulb")
public final class TheBulb extends Widget {
    public static final String PRESS_I, PRESS_O, SCREW, UNSCREW;

    static {
        PRESS_I = "Press I";
        PRESS_O = "Press O";
        UNSCREW = "Unscrew it";
        SCREW = "Screw it back in";
    }

    private static boolean isLightOffAtStepOne;
    private static Indicator rememberedIndicator = null;

    public static @NotNull List<String> solve(@NotNull Bulb bulb) {
        validateBulb(bulb);
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

            if (bulb.getColor() == WHITE)
                checkIfLightTurnsOff(bulb, outputList);

            stepTwo(bulb, outputList);
            return;
        }

        outputList.add(PRESS_O);
        stepThree(bulb, outputList);
    }

    private static void stepTwo(Bulb bulb, List<String> outputList) {
        if (bulb.getColor() == RED) {
            checkIfLightTurnsOff(bulb, outputList);
            outputList.add(PRESS_I);
            unscrewBulb(bulb, outputList);
            stepFive(bulb, outputList);
            return;
        }

        if (bulb.getColor() == WHITE) {
            outputList.add(PRESS_O);
            unscrewBulb(bulb, outputList);
            stepSix(bulb, outputList);
            return;
        }

        unscrewBulb(bulb, outputList);
        stepSeven(bulb, outputList);
    }

    private static void stepThree(Bulb bulb, List<String> outputList) {
        if (bulb.getColor() == GREEN) {
            outputList.add(PRESS_I);
            checkIfLightTurnsOff(bulb, outputList);
            unscrewBulb(bulb, outputList);
            stepSix(bulb, outputList);
            return;
        }

        if (bulb.getColor() == PURPLE) {
            checkIfLightTurnsOff(bulb, outputList);
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
            outputList.add(PRESS_I);
            stepNine(bulb, outputList);
            return;
        }

        outputList.add(PRESS_O);
        stepTen(bulb, outputList);
    }

    private static void stepFive(Bulb bulb, List<String> outputList) {
        String previousPress = outputList.get(outputList.size() - 2);

        if (isLightOffAtStepOne) {
            outputList.add(previousPress);
        } else {
            boolean wasPreviousPressI = previousPress.equals(PRESS_I);
            outputList.add(wasPreviousPressI ? PRESS_O : PRESS_I);
        }

        screwBulb(bulb, outputList);
    }

    private static void stepSix(Bulb bulb, List<String> outputList) {
        String firstButtonPress = outputList.get(0);
        String lastButtonPress = outputList.get(outputList.size() - 2);

        outputList.add(isLightOffAtStepOne ? firstButtonPress : lastButtonPress);
        screwBulb(bulb, outputList);
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
            stepTwelve(bulb, outputList);
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
        stepThirteen(bulb, outputList);
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
            stepThirteen(bulb, outputList);
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
        stepTwelve(bulb, outputList);
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
            stepTwelve(bulb, outputList);
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
            stepThirteen(bulb, outputList);
            return;
        }

        if (bulb.getColor() == PURPLE) {
            screwBulb(bulb, outputList);
            outputList.add(PRESS_I);
            stepTwelve(bulb, outputList);
            return;
        }

        screwBulb(bulb, outputList);
        outputList.add(PRESS_O);
        stepThirteen(bulb, outputList);
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
            stepThirteen(bulb, outputList);
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
            stepTwelve(bulb, outputList);
            return;
        }

        if (bulb.getColor() == GREEN) {
            screwBulb(bulb, outputList);
            outputList.add(PRESS_I);
            stepThirteen(bulb, outputList);
            return;
        }

        screwBulb(bulb, outputList);
        outputList.add(PRESS_O);
        stepTwelve(bulb, outputList);
    }

    private static void stepEleven(Bulb bulb, List<String> outputList) {
        boolean hasRememberedIndicator = rememberedIndicator != null && hasIndicator(rememberedIndicator);
        outputList.add(hasRememberedIndicator ? PRESS_I : PRESS_O);
        screwBulb(bulb, outputList);
    }

    private static void stepTwelve(Bulb bulb, List<String> outputList) {
        boolean isLightOn = confirmLightIsOn(bulb, outputList);
        outputList.add(isLightOn ? PRESS_I : PRESS_O);
    }

    private static void stepThirteen(Bulb bulb, List<String> outputList) {
        boolean isLightOn = confirmLightIsOn(bulb, outputList);
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
        List<String> matches = findButtonPresses.filterCollection(outputList);
        String outputText = String.join("\n", matches);
        Souvenir.addRelic("The Bulb button presses", outputText);
    }

    private static void checkIfLightTurnsOff(Bulb bulb, List<String> outputList) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Light Confirmation");
        alert.setContentText("Did the Bulb turn off when you pressed I?");
        String currentInstructions = outputList.toString()
                .replaceAll("[\\[\\]]", "")
                .replace(", ", " -> ");
        alert.setHeaderText(currentInstructions);

        ButtonType no = new ButtonType("No"),
                yes = new ButtonType("Yes");

        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(yes, no);
        Optional<ButtonType> options = alert.showAndWait();

        options.ifPresent(buttonType -> isLightOffAtStepOne = buttonType == yes);

        bulb.setLight(isLightOffAtStepOne ? OFF : ON);
    }

    private static boolean confirmLightIsOn(Bulb bulb, List<String> outputList) throws IllegalStateException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Light Confirmation");
        alert.setContentText("Is the bulb now on or off?");
        String currentInstructions = outputList.toString()
                .replaceAll("[\\[\\]]", "")
                .replace(", ", " -> ");
        alert.setHeaderText(currentInstructions);

        ButtonType on = new ButtonType("On"),
                off = new ButtonType("Off");

        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(on, off);
        Optional<ButtonType> options = alert.showAndWait();

        if (options.isEmpty())
            throw new IllegalStateException("Unexpected state");

        boolean isLightOn = options.get() == on;
        bulb.setLight(isLightOn ? ON : OFF);
        return isLightOn;
    }

    private static void unscrewBulb(Bulb theBulb, List<String> list) throws IllegalStateException {
        theBulb.setPosition(UNSCREWED);
        list.add(UNSCREW);
    }

    private static void screwBulb(Bulb theBulb, List<String> list) throws IllegalStateException {
        theBulb.setPosition(SCREWED);
        list.add(SCREW);
    }

    private static void validateBulb(Bulb bulb) throws IllegalArgumentException {
        if (bulb.getPosition() != SCREWED)
            throw new IllegalArgumentException("Bulb must be screwed in at the start");
        if (bulb.getLight() == null)
            throw new IllegalArgumentException("Bulb must be lit or unlit");
        if (bulb.getColor() == null)
            throw new IllegalArgumentException("Bulb must have a color");
        if (bulb.getOpacity() == null)
            throw new IllegalArgumentException("Bulb must be Opaque or Translucent");
    }
}
