package bomb.modules.t.bulb;

import bomb.modules.s.souvenir.Souvenir;
import bomb.enumerations.Indicators;
import bomb.Widget;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.NoSuchElementException;
import java.util.Optional;

import static bomb.tools.Mechanics.ultimateFilter;

//TODO - Finish Javadocs
public class TheBulb extends Widget {
    private static boolean stepOneWentOff, currentLight, shutdownAtPressOne;
    private static Bulb.Light stepOnePressed;
    private static Indicators remembered;
    private static final String arrow = " -> ";

    /**
     *
     *
     * @param next
     * @return
     */
    public static String entry(Bulb next){
        StringBuilder script = new StringBuilder();
        String scriptOut = findSet(next, 1, script);
        if (Souvenir.getSet())
            Souvenir.addRelic("The Bulb", ultimateFilter(scriptOut
                            .replaceAll("Screw it back in", ""), "i", "o", " ")
                    .toUpperCase());
        return scriptOut;
    }

    /**
     *
     *
     * @param next
     * @param stage
     * @param instruction
     * @return
     */
    private static String findSet(Bulb next, int stage, StringBuilder instruction){
        //TODO - Break down
        switch (stage){
            case 1: {
                if (next.getLight() == Bulb.Light.ON){
                    if (next.getOpacity() == Bulb.Opacity.TRANSLUCENT){
                        stepOnePressed = Bulb.Light.ON;
                        instruction.append("Press I");
                        lightConfirmation(next, instruction.toString(), true);
                        instruction.append(arrow);
                        return findSet(next, 2, instruction);
                    }
                    stepOnePressed = Bulb.Light.OFF;
                    instruction.append("Press O");
                    lightConfirmation(next, instruction.toString(), true);
                    instruction.append(arrow);
                    return findSet(next, 3, instruction);
                }
                unscrew(next);
                instruction.append("Unscrew").append(arrow);
                return findSet(next, 4, instruction);
            }
            case 2: {
                if (next.getColor() == Bulb.Color.RED){
                    instruction.append("Press I").append(arrow).append("Unscrew").append(arrow);
                    return findSet(next, 5, instruction);
                } else if (next.getColor() == Bulb.Color.WHITE){
                    instruction.append("Press O").append(arrow).append("Unscrew").append(arrow);
                    unscrew(next);
                    return findSet(next, 6, instruction);
                }
                unscrew(next);
                instruction.append("Unscrew").append(arrow);
                return findSet(next, 7, instruction);
            }
            case 3: {
                unscrew(next);
                if (next.getColor() == Bulb.Color.GREEN){
                    instruction.append("Press I");
                    offAtPressI(next, instruction.toString());
                    instruction.append(arrow).append("Unscrew").append(arrow);
                    return findSet(next, 6, instruction);
                } else if (next.getColor() == Bulb.Color.PURPLE){
                    instruction.append("Press O").append(arrow).append("Unscrew").append(arrow);
                    return findSet(next, 5, instruction);
                }
                instruction.append("Unscrew").append(arrow);
                return findSet(next, 8, instruction);
            }
            case 4: {
                if (Widget.hasFollowingInds(Indicators.CAR, Indicators.IND, Indicators.MSA, Indicators.SND)){
                    instruction.append("Press I").append(arrow);
                    return findSet(next, 9, instruction);
                }
                instruction.append("Press O").append(arrow);
                return findSet(next, 10, instruction);
            }
            case 5: {
                if (stepOneWentOff){
                    instruction.append("Press ").append(stepOnePressed == Bulb.Light.ON?"I":"O").append(arrow);
                    return instruction.append("Screw the Bulb").toString();
                }
                instruction.append("Press ").append(stepOnePressed == Bulb.Light.ON?"O":"I")
                        .append(arrow).append("Screw the Bulb");
                return instruction.toString();
            }
            case 6: {
                //TODO - Figure out a way to scan this
                if (shutdownAtPressOne){
                    return instruction.append("Press the button from step 1").toString();
                }
                return instruction.append("Press the button from the previous step").toString();
            }
            case 7: {
                if (next.getColor() == Bulb.Color.GREEN){
                    remembered = Indicators.SIG;
                    instruction.append("Press I").append(arrow);
                    return findSet(next, 11, instruction);
                } else if (next.getColor() == Bulb.Color.PURPLE){
                    instruction.append("Press I").append(arrow).append("Screw it back in").append(arrow);
                    screw(next);
                    return findSet(next, 12, instruction);
                } else if (next.getColor() == Bulb.Color.BLUE){
                    remembered = Indicators.CLR;
                    instruction.append("Press O");
                    return findSet(next, 11, instruction);
                }
                instruction.append("Press O").append(arrow).append("Screw it back in").append(arrow);
                return findSet(next, 13, instruction);
            }
            case 8: {
                if (next.getColor() == Bulb.Color.WHITE){
                    remembered = Indicators.FRQ;
                    instruction.append("Press I").append(arrow);
                    return findSet(next, 11, instruction);
                } else if (next.getColor() == Bulb.Color.RED){
                    instruction.append("Press I").append(arrow).append("Screw it back in").append(arrow);
                    return findSet(next, 13, instruction);
                } else if (next.getColor() == Bulb.Color.YELLOW){
                    remembered = Indicators.FRK;
                    instruction.append("Press O").append(arrow);
                    return findSet(next, 11, instruction);
                }
                instruction.append("Press O").append(arrow).append("Screw it back in").append(arrow);
                return findSet(next, 12, instruction);
            }
            case 9:{
               switch (next.getColor()){
                   case BLUE: {
                       instruction.append("Press I").append(arrow);
                       return findSet(next, 14, instruction);
                   }
                   case GREEN: {
                       instruction.append("Press I").append(arrow).append("Screw it back in")
                               .append(arrow);
                       return findSet(next, 12, instruction);
                   }
                   case YELLOW: {
                       instruction.append("Press O").append(arrow);
                       return findSet(next, 15, instruction);
                   }
                   case WHITE: {
                       instruction.append("Press O").append(arrow).append("Screw it back in")
                               .append(arrow);
                       return findSet(next, 13, instruction);
                   }
                   case PURPLE: {
                       instruction.append("Screw it back in").append(arrow).append("Press I")
                               .append(arrow);
                       return findSet(next, 12, instruction);
                   }
                   default: {
                       instruction.append("Screw it back in").append(arrow).append("Press O")
                               .append(arrow);
                       return findSet(next, 13, instruction);
                   }
               }
            }
            case 10: {
                switch (next.getColor()){
                    case PURPLE: {
                        instruction.append("Press I").append(arrow);
                        return findSet(next, 14, instruction);
                    }
                    case RED: {
                        instruction.append("Press I").append(arrow).append("Screw it back in")
                            .append(arrow);
                        return findSet(next, 13, instruction);
                    }
                    case BLUE: {
                        instruction.append("Press O").append(arrow);
                        return findSet(next, 15, instruction);
                    }
                    case YELLOW: {
                        instruction.append("Press O").append(arrow).append("Screw it back in")
                                .append(arrow);
                        return findSet(next, 12, instruction);
                    }
                    case GREEN: {
                        instruction.append("Screw it back in").append(arrow).append("Press I")
                            .append(arrow);
                        return findSet(next, 13, instruction);
                    }
                    default: {
                        instruction.append("Screw it back in").append(arrow).append("Press O")
                                .append(arrow);
                        return findSet(next, 12, instruction);
                    }
                }
            }
            case 11: {
                if (hasRem()){
                    return instruction.toString() + "Press I" +arrow + "Screw it back in";
                }
                return instruction.toString() + "Press O" +arrow + "Screw it back in";
            }
            case 12:{
                lightConfirmation(next, instruction.toString(), false);
                return instruction.toString() + (currentLight?"Press I":"Press O");
            }
            case 13: {
                lightConfirmation(next, instruction.toString(), false);
                return instruction.toString() + (currentLight?"Press O":"Press I");
            }
            case 14: {
                if (next.getOpacity() == Bulb.Opacity.OPAQUE){
                    return instruction.toString() + "Press I" + arrow + "Screw it back in";
                }
                return instruction.toString() + "Press O" + arrow + "Screw it back in";
            }
            default: {
                if (next.getOpacity() == Bulb.Opacity.TRANSLUCENT){
                    return instruction.toString() + "Press I" + arrow + "Screw it back in";
                }
                return instruction.toString() + "Press O" + arrow + "Screw it back in";
            }
        }
    }

    /**
     *
     *
     * @param next
     */
    private static void unscrew(Bulb next){
        next.setPosition(Bulb.Position.UNSCREWED);
    }

    /**
     *
     *
     * @param next
     */
    private static void screw(Bulb next){
        next.setPosition(Bulb.Position.SCREWED);
    }

    /**
     *
     *
     * @param next
     * @param instr
     * @param isStepOne
     */
    private static void lightConfirmation(Bulb next, String instr, boolean isStepOne){
        //TODO - Break down????
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Light Confirmation");
        alert.setContentText("Is the bulb now on or off?");
        alert.setHeaderText(instr);

        ButtonType on = new ButtonType("On"),
                off = new ButtonType("Off");

        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(on, off);
        Optional<ButtonType> options = alert.showAndWait();

        try {
            if (options.isPresent()) {
                if (isStepOne) {
                    stepOneWentOff = options.get() == off;
                    if (stepOneWentOff) {
                        next.setLight(Bulb.Light.OFF);
                    }
                } else {
                    currentLight = options.get() == on;
                    if (currentLight) {
                        next.setLight(Bulb.Light.ON);
                    }
                }
            }
        } catch (NoSuchElementException noElement){
            Alert wrong = new Alert(Alert.AlertType.ERROR);
            wrong.setContentText("This shouldn't be happening");
            wrong.showAndWait();
        }
    }

    /**
     *
     *
     * @param next
     * @param instr
     */
    private static void offAtPressI(Bulb next, String instr){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Off at the Press I");
        alert.setContentText("Did the Bulb turn off when you pressed I?");
        alert.setHeaderText(instr);

        ButtonType no = new ButtonType("No"),
                yes = new ButtonType("Yes");

        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(yes, no);
        Optional<ButtonType> options = alert.showAndWait();

        try {
            if (options.isPresent()) {
                shutdownAtPressOne = options.get() == yes;
                if (shutdownAtPressOne) {
                    next.setLight(Bulb.Light.OFF);
                    currentLight = false;
                }
            }
        } catch (NoSuchElementException noElement){
            Alert wrong = new Alert(Alert.AlertType.ERROR);
            wrong.setContentText("This shouldn't be happening");
            wrong.showAndWait();
        }
    }

    /**
     *
     *
     * @return
     */
    private static boolean hasRem(){
        return remembered != null && Widget.hasIndicator(remembered);
    }
}
