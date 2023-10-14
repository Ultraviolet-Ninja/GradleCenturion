package bomb.modules.np.neutralization;

import bomb.Widget;
import bomb.annotation.DisplayComponent;
import bomb.modules.np.neutralization.Chemical.Acid;
import bomb.modules.np.neutralization.Chemical.Base;
import bomb.modules.s.souvenir.Souvenir;
import bomb.tools.filter.Regex;
import javafx.scene.paint.Color;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Set;

import static bomb.Widget.IndicatorFilter.ALL_PRESENT;
import static bomb.enumerations.Indicator.CAR;
import static bomb.enumerations.Indicator.FRQ;
import static bomb.enumerations.Indicator.IND;
import static bomb.enumerations.Indicator.NSA;
import static bomb.modules.np.neutralization.Chemical.Acid.HYDROBROMIC_ACID;
import static bomb.modules.np.neutralization.Chemical.Acid.HYDROCHORIC_ACID;
import static bomb.modules.np.neutralization.Chemical.Acid.HYDROFLUORIC_ACID;
import static bomb.modules.np.neutralization.Chemical.Acid.HYDROIODIC_ACID;
import static bomb.modules.np.neutralization.Chemical.Base.AMMONIA;
import static bomb.modules.np.neutralization.Chemical.Base.LITHIUM_HYDROXIDE;
import static bomb.modules.np.neutralization.Chemical.Base.POTASSIUM_HYDROXIDE;
import static bomb.modules.np.neutralization.Chemical.Base.SODIUM_HYDROXIDE;
import static bomb.tools.filter.RegexFilter.EMPTY_FILTER_RESULTS;
import static bomb.tools.filter.RegexFilter.VOWEL_FILTER;
import static java.util.stream.Collectors.toUnmodifiableSet;
import static javafx.scene.paint.Color.BLUE;
import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.RED;
import static javafx.scene.paint.Color.YELLOW;

/**
 * This class deals with the Neutralization module. This puzzle has the Defuser specify the color and amount of
 * acid there is in the test tube. The Expert must then find the base that will titrate the acid with a specific
 * drop count and determine if the titration needs a filter or not.
 */
@DisplayComponent(resource = "neutralization.fxml", buttonLinkerName = "Neutralization")
public final class Neutralization extends Widget {
    public static final String NO_FILTER_TEXT, FILTER_TEXT;

    static final String OUTPUT_SEPARATOR;

    private static Acid currentAcid;
    private static Base currentBase;

    static {
        OUTPUT_SEPARATOR = "-";
        NO_FILTER_TEXT = "No Filter";
        FILTER_TEXT = "Filter";
    }

    /**
     * @param acidVol The volume of the acid.
     *                There are no bound limitations, but the game only uses 5 <= x <= 20
     * @param acidColor The color of acid
     * @return The name and formula of the base, the drop count and whether the titration needs a filter
     */
    public static @NotNull String titrate(int acidVol, @NotNull Color acidColor) throws IllegalArgumentException {
        checkSerialCode();

        if (isSouvenirActive)
            Souvenir.addRelic("Neutralization Acid volume", String.valueOf(acidVol));

        getAcid(acidColor);
        getBase();
        return currentBase.name().replace('_', ' ') + OUTPUT_SEPARATOR + currentBase.getFormula()
                + OUTPUT_SEPARATOR + calculateDropCount(acidVol, acidConcentration(acidVol), baseConcentration())
                + OUTPUT_SEPARATOR + calculateSolubility();
    }

    private static void getAcid(Color color) throws IllegalArgumentException {
        if (color == RED)
            currentAcid = HYDROBROMIC_ACID;
        else if (color == BLUE)
            currentAcid = HYDROIODIC_ACID;
        else if (color == YELLOW)
            currentAcid = HYDROFLUORIC_ACID;
        else if (color == GREEN)
            currentAcid = HYDROCHORIC_ACID;
        else
            throw new IllegalArgumentException("Incorrect Color was inserted");

        if (isSouvenirActive)
            Souvenir.addRelic("Neutralization Acid color", getColorName(color));
    }

    private static void getBase() {
        if (hasLitIndicator(NSA) && getAllBatteries() == 3)
            currentBase = AMMONIA;
        else if (hasAnyFollowingIndicators())
            currentBase = POTASSIUM_HYDROXIDE;
        else if (calculateTotalPorts() == 0 && hasVowelInSerialCode())
            currentBase = LITHIUM_HYDROXIDE;
        else if (doesIndicatorLetterMatch())
            currentBase = POTASSIUM_HYDROXIDE;
        else if (numDBatteries > numDoubleAs)
            currentBase = AMMONIA;
        else if (currentAcid.getAtomicNum() < 20)
            currentBase = SODIUM_HYDROXIDE;
        else
            currentBase = LITHIUM_HYDROXIDE;
    }

    private static boolean doesIndicatorLetterMatch() {
        Set<String> uniqueCharacterSet = getFilteredSetOfIndicators(ALL_PRESENT)
                .stream()
                .map(Enum::name)
                .map(String::toLowerCase)
                .map(name -> name.split(""))
                .flatMap(Arrays::stream)
                .collect(toUnmodifiableSet());

        @Language("regexp")
        String regex = uniqueCharacterSet.toString().replaceAll(", ", "");
        String acidFormula = currentAcid.getFormula().toLowerCase();
        Regex indicatorLetterMatch = new Regex(regex, acidFormula);

        return indicatorLetterMatch.hasMatch();
    }

    private static double acidConcentration(int acidVol) {
        double concentrate = currentAcid.getAtomicNum() - currentBase.getAtomicNum();
        String acidSymbol = currentAcid.getSymbol();
        String baseSymbol = currentBase.getSymbol();

        if (!EMPTY_FILTER_RESULTS.test(acidSymbol, VOWEL_FILTER) ||
                !EMPTY_FILTER_RESULTS.test(baseSymbol, VOWEL_FILTER))
            //Either the cation or the anion contains a vowel
            concentrate -= 4;

        if (acidSymbol.length() == baseSymbol.length())
            //Length of characters for the anion is equal to the cation symbol length
            concentrate *= 3;
        concentrate = Math.abs(concentrate) % 10;
        if (concentrate == 0.0)
            concentrate = acidVol * 2 / 5.0;
        return concentrate / 10.0;
    }

    private static int baseConcentration() {
        if (isProcessOverruled()) return 20;
        int allIndicators = countIndicators(ALL_PRESENT);
        int portTypeCount = countPortTypes();

        if (numHolders > portTypeCount && numHolders > allIndicators)
            return 5;
        else if (portTypeCount > numHolders && portTypeCount > allIndicators)
            return 10;
        else if (allIndicators > numHolders && allIndicators > portTypeCount)
            return 20;
        return getClosestDistance();
    }

    private static boolean isProcessOverruled() {
        return (currentAcid == HYDROIODIC_ACID && currentBase == POTASSIUM_HYDROXIDE) ||
                (currentAcid == HYDROCHORIC_ACID && currentBase == AMMONIA);
    }

    private static int getClosestDistance() {
        int atomicNum = currentBase.getAtomicNum();
        int holderDistance = Math.abs(atomicNum - 5),
                portDistance = Math.abs(atomicNum - 10),
                indDistance = Math.abs(atomicNum - 20);

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
        return currentAcid == HYDROBROMIC_ACID && (currentBase == AMMONIA ||
                currentBase == SODIUM_HYDROXIDE);
    }

    private static boolean fluorideCombos() {
        return currentAcid == HYDROFLUORIC_ACID && (currentBase == POTASSIUM_HYDROXIDE
                || currentBase == SODIUM_HYDROXIDE);
    }

    private static boolean chlorineCombos() {
        return currentAcid == HYDROCHORIC_ACID && currentBase == LITHIUM_HYDROXIDE;
    }

    private static boolean iodineCombos() {
        return currentAcid == HYDROIODIC_ACID && currentBase != SODIUM_HYDROXIDE;
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
        return hasLitIndicator(CAR) || hasLitIndicator(IND) || hasLitIndicator(FRQ);
    }
}
