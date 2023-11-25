package bomb.modules.t.the.bulb;

import bomb.Widget;
import bomb.annotation.DisplayComponent;
import bomb.enumerations.Indicator;
import bomb.modules.s.souvenir.Souvenir;
import bomb.tools.filter.Regex;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static bomb.enumerations.Indicator.CAR;
import static bomb.enumerations.Indicator.CLR;
import static bomb.enumerations.Indicator.FRK;
import static bomb.enumerations.Indicator.FRQ;
import static bomb.enumerations.Indicator.IND;
import static bomb.enumerations.Indicator.MSA;
import static bomb.enumerations.Indicator.SIG;
import static bomb.enumerations.Indicator.SND;

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

    public static @NotNull List<String> solve(@NotNull BulbModel bulbModel,
                                              @NotNull Predicate<List<String>> checkIfLightIsOff,
                                              @NotNull Predicate<List<String>> confirmLightIsOn) {
        validateBulb(bulbModel);
        requireNonNullPredicate(checkIfLightIsOff);
        requireNonNullPredicate(confirmLightIsOn);
        List<String> outputList = new ArrayList<>();
        stepOne(bulbModel, outputList, checkIfLightIsOff, confirmLightIsOn);

        if (isSouvenirActive)
            sendInfoToSouvenir(outputList);

        rememberedIndicator = null;
        return outputList;
    }

    private static void stepOne(BulbModel bulbModel, List<String> outputList,
                                Predicate<List<String>> checkIfLightTurnsOff,
                                Predicate<List<String>> confirmLightIsOn) {
        if (bulbModel.getLight() == BulbModel.Light.OFF) {
            unscrewBulb(bulbModel, outputList);
            stepFour(bulbModel, outputList, confirmLightIsOn);
            return;
        }

        if (bulbModel.getOpacity() == BulbModel.Opacity.TRANSLUCENT) {
            outputList.add(PRESS_I);

            if (bulbModel.getColor() == BulbModel.Color.WHITE)
                checkIfLightTurnsOff(bulbModel, outputList, checkIfLightTurnsOff);

            stepTwo(bulbModel, outputList, checkIfLightTurnsOff, confirmLightIsOn);
            return;
        }

        outputList.add(PRESS_O);
        stepThree(bulbModel, outputList, checkIfLightTurnsOff, confirmLightIsOn);
    }

    private static void stepTwo(BulbModel bulbModel, List<String> outputList,
                                Predicate<List<String>> checkIfLightTurnsOff,
                                Predicate<List<String>> confirmLightIsOn) {
        if (bulbModel.getColor() == BulbModel.Color.RED) {
            checkIfLightTurnsOff(bulbModel, outputList, checkIfLightTurnsOff);
            outputList.add(PRESS_I);
            unscrewBulb(bulbModel, outputList);
            stepFive(bulbModel, outputList);
            return;
        }

        if (bulbModel.getColor() == BulbModel.Color.WHITE) {
            outputList.add(PRESS_O);
            unscrewBulb(bulbModel, outputList);
            stepSix(bulbModel, outputList);
            return;
        }

        unscrewBulb(bulbModel, outputList);
        stepSeven(bulbModel, outputList, confirmLightIsOn);
    }

    private static void stepThree(BulbModel bulbModel, List<String> outputList,
                                  Predicate<List<String>> checkIfLightTurnsOff,
                                  Predicate<List<String>> confirmLightIsOn) {
        if (bulbModel.getColor() == BulbModel.Color.GREEN) {
            outputList.add(PRESS_I);
            checkIfLightTurnsOff(bulbModel, outputList, checkIfLightTurnsOff);
            unscrewBulb(bulbModel, outputList);
            stepSix(bulbModel, outputList);
            return;
        }

        if (bulbModel.getColor() == BulbModel.Color.PURPLE) {
            checkIfLightTurnsOff(bulbModel, outputList, checkIfLightTurnsOff);
            outputList.add(PRESS_O);
            unscrewBulb(bulbModel, outputList);
            stepFive(bulbModel, outputList);
            return;
        }

        unscrewBulb(bulbModel, outputList);
        stepEight(bulbModel, outputList, confirmLightIsOn);
    }

    private static void stepFour(BulbModel bulbModel, List<String> outputList,
                                 Predicate<List<String>> confirmLightIsOn) {
        if (hasFollowingIndicators(CAR, IND, MSA, SND)) {
            outputList.add(PRESS_I);
            stepNine(bulbModel, outputList, confirmLightIsOn);
            return;
        }

        outputList.add(PRESS_O);
        stepTen(bulbModel, outputList, confirmLightIsOn);
    }

    private static void stepFive(BulbModel bulbModel, List<String> outputList) {
        String previousPress = outputList.get(outputList.size() - 2);

        if (isLightOffAtStepOne) {
            outputList.add(previousPress);
        } else {
            boolean wasPreviousPressI = previousPress.equals(PRESS_I);
            outputList.add(wasPreviousPressI ? PRESS_O : PRESS_I);
        }

        screwBulb(bulbModel, outputList);
    }

    private static void stepSix(BulbModel bulbModel, List<String> outputList) {
        String firstButtonPress = outputList.get(0);
        String lastButtonPress = outputList.get(outputList.size() - 2);

        outputList.add(isLightOffAtStepOne ? firstButtonPress : lastButtonPress);
        screwBulb(bulbModel, outputList);
    }

    private static void stepSeven(BulbModel bulbModel, List<String> outputList,
                                  Predicate<List<String>> confirmLightIsOn) {
        if (bulbModel.getColor() == BulbModel.Color.GREEN) {
            rememberedIndicator = SIG;
            outputList.add(PRESS_I);
            stepEleven(bulbModel, outputList);
            return;
        }

        if (bulbModel.getColor() == BulbModel.Color.PURPLE) {
            outputList.add(PRESS_I);
            screwBulb(bulbModel, outputList);
            stepTwelve(bulbModel, outputList, confirmLightIsOn);
            return;
        }

        if (bulbModel.getColor() == BulbModel.Color.BLUE) {
            rememberedIndicator = CLR;
            outputList.add(PRESS_O);
            stepEleven(bulbModel, outputList);
            return;
        }

        outputList.add(PRESS_O);
        screwBulb(bulbModel, outputList);
        stepThirteen(bulbModel, outputList, confirmLightIsOn);
    }

    private static void stepEight(BulbModel bulbModel, List<String> outputList,
                                  Predicate<List<String>> confirmLightIsOn) {
        if (bulbModel.getColor() == BulbModel.Color.WHITE) {
            rememberedIndicator = FRQ;
            outputList.add(PRESS_I);
            stepEleven(bulbModel, outputList);
            return;
        }

        if (bulbModel.getColor() == BulbModel.Color.RED) {
            outputList.add(PRESS_I);
            screwBulb(bulbModel, outputList);
            stepThirteen(bulbModel, outputList, confirmLightIsOn);
            return;
        }

        if (bulbModel.getColor() == BulbModel.Color.YELLOW) {
            rememberedIndicator = FRK;
            outputList.add(PRESS_O);
            stepEleven(bulbModel, outputList);
            return;
        }

        outputList.add(PRESS_O);
        screwBulb(bulbModel, outputList);
        stepTwelve(bulbModel, outputList, confirmLightIsOn);
    }

    private static void stepNine(BulbModel bulbModel, List<String> outputList,
                                 Predicate<List<String>> confirmLightIsOn) {
        if (bulbModel.getColor() == BulbModel.Color.BLUE) {
            outputList.add(PRESS_I);
            stepFourteen(bulbModel, outputList);
            return;
        }

        if (bulbModel.getColor() == BulbModel.Color.GREEN) {
            outputList.add(PRESS_I);
            screwBulb(bulbModel, outputList);
            stepTwelve(bulbModel, outputList, confirmLightIsOn);
            return;
        }

        if (bulbModel.getColor() == BulbModel.Color.YELLOW) {
            outputList.add(PRESS_O);
            stepFifteen(bulbModel, outputList);
            return;
        }

        if (bulbModel.getColor() == BulbModel.Color.WHITE) {
            outputList.add(PRESS_O);
            screwBulb(bulbModel, outputList);
            stepThirteen(bulbModel, outputList, confirmLightIsOn);
            return;
        }

        if (bulbModel.getColor() == BulbModel.Color.PURPLE) {
            screwBulb(bulbModel, outputList);
            outputList.add(PRESS_I);
            stepTwelve(bulbModel, outputList, confirmLightIsOn);
            return;
        }

        screwBulb(bulbModel, outputList);
        outputList.add(PRESS_O);
        stepThirteen(bulbModel, outputList, confirmLightIsOn);
    }

    private static void stepTen(BulbModel bulbModel, List<String> outputList,
                                Predicate<List<String>> confirmLightIsOn) {
        if (bulbModel.getColor() == BulbModel.Color.PURPLE) {
            outputList.add(PRESS_I);
            stepFourteen(bulbModel, outputList);
            return;
        }

        if (bulbModel.getColor() == BulbModel.Color.RED) {
            outputList.add(PRESS_I);
            screwBulb(bulbModel, outputList);
            stepThirteen(bulbModel, outputList, confirmLightIsOn);
            return;
        }

        if (bulbModel.getColor() == BulbModel.Color.BLUE) {
            outputList.add(PRESS_O);
            stepFifteen(bulbModel, outputList);
            return;
        }

        if (bulbModel.getColor() == BulbModel.Color.YELLOW) {
            outputList.add(PRESS_O);
            screwBulb(bulbModel, outputList);
            stepTwelve(bulbModel, outputList, confirmLightIsOn);
            return;
        }

        if (bulbModel.getColor() == BulbModel.Color.GREEN) {
            screwBulb(bulbModel, outputList);
            outputList.add(PRESS_I);
            stepThirteen(bulbModel, outputList, confirmLightIsOn);
            return;
        }

        screwBulb(bulbModel, outputList);
        outputList.add(PRESS_O);
        stepTwelve(bulbModel, outputList, confirmLightIsOn);
    }

    private static void stepEleven(BulbModel bulbModel, List<String> outputList) {
        boolean hasRememberedIndicator = rememberedIndicator != null && hasIndicator(rememberedIndicator);
        outputList.add(hasRememberedIndicator ? PRESS_I : PRESS_O);
        screwBulb(bulbModel, outputList);
    }

    private static void stepTwelve(BulbModel bulbModel, List<String> outputList,
                                   Predicate<List<String>> confirmLightIsOn) {
        boolean isLightOn = confirmFinalLightIsOn(bulbModel, outputList, confirmLightIsOn);
        outputList.add(isLightOn ? PRESS_I : PRESS_O);
    }

    private static void stepThirteen(BulbModel bulbModel, List<String> outputList,
                                     Predicate<List<String>> confirmLightIsOn) {
        boolean isLightOn = confirmFinalLightIsOn(bulbModel, outputList, confirmLightIsOn);
        outputList.add(isLightOn ? PRESS_O : PRESS_I);
    }

    private static void stepFourteen(BulbModel bulbModel, List<String> outputList) {
        outputList.add(bulbModel.getOpacity() == BulbModel.Opacity.OPAQUE ? PRESS_I : PRESS_O);
        screwBulb(bulbModel, outputList);
    }

    private static void stepFifteen(BulbModel bulbModel, List<String> outputList) {
        outputList.add(bulbModel.getOpacity() == BulbModel.Opacity.TRANSLUCENT ? PRESS_I : PRESS_O);
        screwBulb(bulbModel, outputList);
    }

    private static void sendInfoToSouvenir(List<String> outputList) {
        Regex findButtonPresses = new Regex("Press [IO]");
        List<String> matches = findButtonPresses.filterCollection(outputList);
        String outputText = String.join("\n", matches);
        Souvenir.addRelic("The Bulb button presses", outputText);
    }

    private static void checkIfLightTurnsOff(BulbModel bulbModel, List<String> outputList,
                                             Predicate<List<String>> checkIfLightTurnsOff) {
        isLightOffAtStepOne = checkIfLightTurnsOff.test(outputList);

        bulbModel.setLight(isLightOffAtStepOne ? BulbModel.Light.OFF : BulbModel.Light.ON);
    }

    private static boolean confirmFinalLightIsOn(BulbModel bulbModel, List<String> outputList,
                                                 Predicate<List<String>> confirmLightIsOn) throws IllegalStateException {
        boolean isLightOn = confirmLightIsOn.test(outputList);
        bulbModel.setLight(isLightOn ? BulbModel.Light.ON : BulbModel.Light.OFF);
        return isLightOn;
    }

    private static void unscrewBulb(BulbModel theBulbModel, List<String> list) throws IllegalStateException {
        theBulbModel.setPosition(BulbModel.Position.UNSCREWED);
        list.add(UNSCREW);
    }

    private static void screwBulb(BulbModel theBulbModel, List<String> list) throws IllegalStateException {
        theBulbModel.setPosition(BulbModel.Position.SCREWED);
        list.add(SCREW);
    }

    private static void validateBulb(BulbModel bulbModel) throws IllegalArgumentException {
        if (bulbModel.getPosition() != BulbModel.Position.SCREWED)
            throw new IllegalArgumentException("Bulb must be screwed in at the start");
        if (bulbModel.getLight() == null)
            throw new IllegalArgumentException("Bulb must be lit or unlit");
        if (bulbModel.getColor() == null)
            throw new IllegalArgumentException("Bulb must have a color");
        if (bulbModel.getOpacity() == null)
            throw new IllegalArgumentException("Bulb must be Opaque or Translucent");
    }

    private static void requireNonNullPredicate(Predicate<List<String>> predicate) {
        if (predicate == null) {
           throw new IllegalArgumentException("Cannot have null function");
        }
    }
}
