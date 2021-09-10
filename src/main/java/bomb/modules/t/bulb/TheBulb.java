package bomb.modules.t.bulb;

import bomb.Widget;
import bomb.enumerations.Indicator;
import bomb.modules.s.souvenir.Souvenir;
import bomb.tools.filter.Regex;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import static bomb.enumerations.Indicator.CAR;
import static bomb.enumerations.Indicator.IND;
import static bomb.enumerations.Indicator.MSA;
import static bomb.enumerations.Indicator.SND;
import static bomb.modules.t.bulb.Bulb.Color.GREEN;
import static bomb.modules.t.bulb.Bulb.Color.PURPLE;
import static bomb.modules.t.bulb.Bulb.Color.RED;
import static bomb.modules.t.bulb.Bulb.Color.WHITE;
import static bomb.modules.t.bulb.Bulb.Light.OFF;
import static bomb.modules.t.bulb.Bulb.Opacity.TRANSLUCENT;
import static bomb.modules.t.bulb.Bulb.Position.SCREWED;
import static bomb.modules.t.bulb.Bulb.Position.UNSCREWED;

public class TheBulb extends Widget {
    private static final String PRESS_I = "Press I", PRESS_O = "Press O",
            UNSCREW = "Unscrew it", SCREW = "Screw it back in";

    private static final BiConsumer<Bulb, List<String>>
            UNSCREW_ACTION = (theBulb, list) -> {
        theBulb.setPosition(UNSCREWED);
        list.add(UNSCREW);
    },
            SCREW_ACTION = (theBulb, list) ->
            {
                theBulb.setPosition(SCREWED);
                list.add(SCREW);
            };

    private static Indicator rememberedIndicator;

    public static List<String> solve(Bulb bulb) {
        List<String> outputList = new ArrayList<>();
        stepOne(bulb, outputList);
        if (isSouvenirActive)
            sendInfoToSouvenir(outputList);
        return outputList;
    }

    private static void stepOne(Bulb bulb, List<String> outputList) {
        if (bulb.getLight() == OFF) {
            UNSCREW_ACTION.accept(bulb, outputList);
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
            UNSCREW_ACTION.accept(bulb, outputList);
            stepFive(bulb, outputList);
            return;
        }

        if (bulb.getColor() == WHITE) {
            outputList.add(PRESS_O);
            UNSCREW_ACTION.accept(bulb, outputList);
            stepSix(bulb, outputList);
            return;
        }

        UNSCREW_ACTION.accept(bulb, outputList);
        stepSeven(bulb, outputList);
    }

    private static void stepThree(Bulb bulb, List<String> outputList) {
        if (bulb.getColor() == GREEN) {
            outputList.add(PRESS_I);
            UNSCREW_ACTION.accept(bulb, outputList);
            stepSix(bulb, outputList);
            return;
        }

        if (bulb.getColor() == PURPLE) {
            outputList.add(PRESS_O);
            UNSCREW_ACTION.accept(bulb, outputList);
            stepFive(bulb, outputList);
            return;
        }

        UNSCREW_ACTION.accept(bulb, outputList);
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

    }

    private static void stepSix(Bulb bulb, List<String> outputList) {

    }

    private static void stepSeven(Bulb bulb, List<String> outputList) {

    }

    private static void stepEight(Bulb bulb, List<String> outputList) {

    }

    private static void stepNine(Bulb bulb, List<String> outputList) {

    }

    private static void stepTen(Bulb bulb, List<String> outputList) {

    }

    private static void stepEleven(Bulb bulb, List<String> outputList) {

    }

    private static void stepTwelve(Bulb bulb, List<String> outputList) {

    }

    private static void stepThirteen(Bulb bulb, List<String> outputList) {

    }

    private static void stepFourteen(Bulb bulb, List<String> outputList) {

    }

    private static void stepFifteen(Bulb bulb, List<String> outputList) {

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
}
