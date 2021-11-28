package bomb.modules.np.neutralization;

import bomb.Widget;
import bomb.enumerations.Indicator;
import bomb.modules.s.souvenir.Souvenir;
import bomb.tools.filter.Regex;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static bomb.Widget.IndicatorFilter.ALL_PRESENT;
import static bomb.tools.filter.RegexFilter.VOWEL_FILTER;
import static bomb.tools.filter.RegexFilter.ultimateFilter;
import static javafx.scene.paint.Color.BLUE;
import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.RED;
import static javafx.scene.paint.Color.YELLOW;

/**
 * This class deals with the Neutralization module. This puzzle has the Defuser specify the color and amount of
 * acid there is in the test tube. The Expert must then find the base that will titrate the acid with a specific
 * drop count and determine if the titration needs a filter or not.
 */
public class Neutralization extends Widget {
    public static final String NO_FILTER_TEXT = "No Filter", FILTER_TEXT = "Filter";

    static final String OUTPUT_SEPARATOR = "-";

    private static Chemical.Acid currentAcid;
    private static Chemical.Base currentBase;

    /**
     * @param acidVol The volume of the acid.
     *                There are no bound limitations, but the game only uses 5 <= x <= 20
     * @param acidColor The color of acid
     * @return The name and formula of the base, the drop count and whether the titration needs a filter
     */
    public static String titrate(int acidVol, Color acidColor) throws IllegalArgumentException {
        if (isSouvenirActive)
            Souvenir.addRelic("Neutralization Acid volume", String.valueOf(acidVol));
        checkSerialCode();
        getAcid(acidColor);
        getBase();
        return currentBase.name().replace('_', ' ') + OUTPUT_SEPARATOR + currentBase.getFormula()
                + OUTPUT_SEPARATOR + calculateDropCount(acidVol, acidConcentration(acidVol), baseConcentration())
                + OUTPUT_SEPARATOR + calculateSolubility();
    }

    private static void getAcid(Color color) throws IllegalArgumentException {
        if (color == RED)
            currentAcid = Chemical.Acid.Hydrobromic_Acid;
        else if (color == BLUE)
            currentAcid = Chemical.Acid.Hydroiodic_Acid;
        else if (color == Color.YELLOW)
            currentAcid = Chemical.Acid.Hydrofluoric_Acid;
        else if (color == Color.GREEN)
            currentAcid = Chemical.Acid.Hydrochoric_Acid;
        else
            throw new IllegalArgumentException("Incorrect Color was inserted");
        if (isSouvenirActive)
            Souvenir.addRelic("Neutralization Acid color", getColorName(color));
    }

    private static void getBase() {
        if (hasLitIndicator(Indicator.NSA) && getAllBatteries() == 3)
            currentBase = Chemical.Base.Ammonia;
        else if (hasAnyFollowingIndicators())
            currentBase = Chemical.Base.Potassium_Hydroxide;
        else if (calculateTotalPorts() == 0 && hasVowelInSerialCode())
            currentBase = Chemical.Base.Lithium_Hydroxide;
        else if (doesIndicatorLetterMatch())
            currentBase = Chemical.Base.Potassium_Hydroxide;
        else if (numDBatteries > numDoubleAs)
            currentBase = Chemical.Base.Ammonia;
        else if (currentAcid.getAtomicNum() < 20)
            currentBase = Chemical.Base.Sodium_Hydroxide;
        else
            currentBase = Chemical.Base.Lithium_Hydroxide;
    }

    private static boolean doesIndicatorLetterMatch() {
        Set<String> uniqueCharacterSet = getFilteredSetOfIndicators(ALL_PRESENT)
                .stream()
                .map(indicator -> indicator.name().toLowerCase().split(""))
                .flatMap(Arrays::stream)
                .collect(Collectors.toSet());

        String regex = uniqueCharacterSet.toString().replaceAll(", ", "");
        String acidFormula = currentAcid.getFormula().toLowerCase();
        Regex indicatorLetterMatch = new Regex(regex, acidFormula);

        return indicatorLetterMatch.hasMatch();
    }

    private static double acidConcentration(int acidVol) {
        double concentrate = currentAcid.getAtomicNum() - currentBase.getAtomicNum();

        if (!ultimateFilter(currentAcid.getSymbol(), VOWEL_FILTER).isEmpty() ||
                !ultimateFilter(currentBase.getSymbol(), VOWEL_FILTER).isEmpty())
            //Either the cation or the anion contains a vowel
            concentrate -= 4;

        if (currentAcid.getSymbol().length() == currentBase.getSymbol().length())
            //Length of characters for the anion is equal to the cation symbol length
            concentrate *= 3;
        concentrate = Math.abs(concentrate) % 10;
        if (concentrate == 0)
            concentrate = acidVol * 2 / 5.0;
        return concentrate / 10.0;
    }

    private static int baseConcentration() {
        if (!overrule()) {
            if (numHolders > countPortTypes() && numHolders > countIndicators(ALL_PRESENT))
                return 5;
            else if (countPortTypes() > numHolders && countPortTypes() > countIndicators(ALL_PRESENT))
                return 10;
            else if (countIndicators(ALL_PRESENT) > numHolders &&
                    countIndicators(ALL_PRESENT) > countPortTypes())
                return 20;
            return closestNum();
        }
        return 20;
    }

    private static boolean overrule() {
        return (currentAcid == Chemical.Acid.Hydroiodic_Acid &&
                currentBase == Chemical.Base.Potassium_Hydroxide) ||
                (currentAcid == Chemical.Acid.Hydrochoric_Acid &&
                        currentBase == Chemical.Base.Ammonia);
    }

    private static int closestNum() {
        int holderDistance = Math.abs(currentBase.getAtomicNum() - 5),
                portDistance = Math.abs(currentBase.getAtomicNum() - 10),
                indDistance = Math.abs(currentBase.getAtomicNum() - 20);

        if (holderDistance < portDistance && holderDistance < indDistance)
            return 5;
        else if (portDistance < holderDistance && portDistance < indDistance)
            return 10;
        return 20;
    }

    private static int calculateDropCount(int acidVol, double acid, int base) {
        return (int) ((20 / base) * acid * acidVol);
    }

    private static String calculateSolubility() {
        return findCombinations() ?
                NO_FILTER_TEXT :
                FILTER_TEXT;
    }

    private static boolean findCombinations() {
        return bromideCombos() || fluorideCombos() || chlorineCombos() || iodineCombos();
    }

    private static boolean bromideCombos() {
        return currentAcid == Chemical.Acid.Hydrobromic_Acid && (currentBase == Chemical.Base.Ammonia ||
                currentBase == Chemical.Base.Sodium_Hydroxide);
    }

    private static boolean fluorideCombos() {
        return currentAcid == Chemical.Acid.Hydrofluoric_Acid && (currentBase == Chemical.Base.Potassium_Hydroxide
                || currentBase == Chemical.Base.Sodium_Hydroxide);
    }

    private static boolean chlorineCombos() {
        return currentAcid == Chemical.Acid.Hydrochoric_Acid && currentBase == Chemical.Base.Lithium_Hydroxide;
    }

    private static boolean iodineCombos() {
        return currentAcid == Chemical.Acid.Hydroiodic_Acid && currentBase != Chemical.Base.Sodium_Hydroxide;
    }

    private static String getColorName(Color color) {
        if (color == RED)
            return "Red";
        else if (color == YELLOW)
            return "Yellow";
        else if (color == GREEN)
            return "Green";
        else
            return "Blue";
    }

    private static boolean hasAnyFollowingIndicators() {
        return hasLitIndicator(Indicator.CAR) || hasLitIndicator(Indicator.IND) || hasLitIndicator(Indicator.FRQ);
    }
}
