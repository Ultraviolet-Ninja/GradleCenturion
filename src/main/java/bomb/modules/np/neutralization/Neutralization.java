package bomb.modules.np.neutralization;

import bomb.modules.s.souvenir.Souvenir;
import bomb.enumerations.Chemical;
import bomb.enumerations.Indicators;
import bomb.Widget;
import javafx.scene.paint.Color;

import static bomb.enumerations.TriState.UNKNOWN;
import static bomb.tools.Mechanics.ultimateFilter;
import static bomb.tools.Mechanics.VOWEL_REGEX;
import static javafx.scene.paint.Color.BLUE;
import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.RED;
import static javafx.scene.paint.Color.YELLOW;
//TODO - Probably change variable names, finish Javadocs

/**
 *
 */
public class Neutralization extends Widget {
    public static final String NO_FILTER = "No Filter", FILTER = "Filter";

    private static Chemical.Acid currentAcid;
    private static Chemical.Base currentBase;

    /**
     *
     *
     * @param acidVol
     * @param solColor
     * @return
     */
    public static String titrate(int acidVol, Color solColor) throws IllegalArgumentException{
        if (Souvenir.getSet())
            Souvenir.addRelic("(Neutralization)Acid volume", String.valueOf(acidVol));
        if (serialCode.isEmpty()) throw new IllegalArgumentException("Need to set the serial code");
        getAcid(solColor);
        getBase();
        return currentBase.name().replace('_', ' ') + "-" + currentBase.getFormula()
                + "-" + dropCount(acidVol, acidConcentration(acidVol), baseConcentration())
                + "-" + solubility();
    }

    /**
     *
     *
     * @param color
     * @throws IllegalArgumentException
     */
    private static void getAcid(Color color) throws IllegalArgumentException{
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
        if (Souvenir.getSet())
            Souvenir.addRelic("(Neutralization)Acid color", getColorName(color));
    }

    /**
     *
     */
    private static void getBase(){
        if (hasLitIndicator(Indicators.NSA) && getAllBatteries() == 3)
            currentBase = Chemical.Base.Ammonia;
        else if (hasAny())
            currentBase = Chemical.Base.Potassium_Hydroxide;
        else if (getTotalPorts() == 0 && hasVowel())
            currentBase = Chemical.Base.Lithium_Hydroxide;
        else if (indicatorMatch())
            currentBase = Chemical.Base.Potassium_Hydroxide;
        else if (numDBatteries > numDoubleAs)
            currentBase = Chemical.Base.Ammonia;
        else if (currentAcid.getAtomicNum() < 20)
            currentBase = Chemical.Base.Sodium_Hydroxide;
        else
            currentBase = Chemical.Base.Lithium_Hydroxide;
    }

    /**
     *
     *
     * @return
     */
    private static boolean indicatorMatch(){
        for (Indicators ind : list){
            if (ind.getProp() != UNKNOWN){
                for (char letter : ind.name().toCharArray()){
                    if (charMatch(currentAcid.getFormula().toUpperCase(), letter)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     *
     *
     * @param acidVol
     * @return
     */
    private static double acidConcentration(int acidVol){
        double concentrate = currentAcid.getAtomicNum() - currentBase.getAtomicNum();

        if (!ultimateFilter(currentAcid.getSymbol(), VOWEL_REGEX).isEmpty() ||
                !ultimateFilter(currentBase.getSymbol(), VOWEL_REGEX).isEmpty())
            //Either the cation or the anion contains a vowel
            concentrate -= 4;

        if (currentAcid.getSymbol().length() == currentBase.getSymbol().length())
            //Length of characters for the anion is equal to the cation symbol length
            concentrate *=3;
        concentrate = Math.abs(concentrate)%10;
        if (concentrate == 0)
            concentrate = acidVol*2/5.0;
        return concentrate/10.0;
    }

    /**
     *
     *
     * @return
     */
    private static int baseConcentration(){
        if (!overrule()) {
            if (numHolders > getPortTypes() && numHolders > countIndicators(true, true))
                return 5;
            else if (getPortTypes() > numHolders && getPortTypes() > countIndicators(true, true))
                return 10;
            else if (countIndicators(true, true) > numHolders &&
                    countIndicators(true, true) > getPortTypes())
                return 20;
            return closestNum();
        }
        return 20;
    }

    /**
     *
     *
     * @return
     */
    private static boolean overrule(){
        return (currentAcid == Chemical.Acid.Hydroiodic_Acid &&
                currentBase == Chemical.Base.Potassium_Hydroxide) ||
                (currentAcid == Chemical.Acid.Hydrochoric_Acid &&
                        currentBase == Chemical.Base.Ammonia);
    }

    /**
     *
     *
     * @return
     */
    private static int closestNum(){
        int holderDistance = Math.abs(currentBase.getAtomicNum() - 5),
                portDistance = Math.abs(currentBase.getAtomicNum() - 10),
                indDistance = Math.abs(currentBase.getAtomicNum() - 20);

        if (holderDistance < portDistance && holderDistance < indDistance)
            return 5;
        else if (portDistance < holderDistance && portDistance < indDistance)
            return 10;
         return 20;
    }

    /**
     *
     *
     * @param acidVol
     * @param acid
     * @param base
     * @return
     */
    private static int dropCount(int acidVol, double acid, int base){
        return (int)((20/base)*acid*acidVol);
    }

    /**
     *
     *
     * @return
     */
    private static String solubility(){
        return combinations() ?
                NO_FILTER :
                FILTER;
        //TODO - Take a look at these combos
    }

    /**
     *
     *
     * @return
     */
    private static boolean combinations(){
        return bromideCombos() || fluorideCombos() || chlorineCombos() || iodineCombos();
    }

    /**
     *
     *
     * @return
     */
    private static boolean bromideCombos(){
        return currentAcid == Chemical.Acid.Hydrobromic_Acid && (currentBase == Chemical.Base.Ammonia||
                currentBase == Chemical.Base.Sodium_Hydroxide);
    }

    /**
     *
     *
     * @return
     */
    private static boolean fluorideCombos(){
        return currentAcid == Chemical.Acid.Hydrofluoric_Acid && (currentBase == Chemical.Base.Potassium_Hydroxide
                || currentBase == Chemical.Base.Sodium_Hydroxide);
    }

    /**
     *
     *
     * @return
     */
    private static boolean chlorineCombos(){
        return currentAcid == Chemical.Acid.Hydrochoric_Acid && currentBase == Chemical.Base.Lithium_Hydroxide;
    }

    /**
     *
     *
     * @return
     */
    private static boolean iodineCombos() {
        return currentAcid == Chemical.Acid.Hydroiodic_Acid && currentBase != Chemical.Base.Sodium_Hydroxide;
    }

    /**
     *
     *
     * @param comp
     * @param instance
     * @return
     */
    private static boolean charMatch(String comp, char instance){
        return comp.indexOf(instance) != -1;
    }

    /**
     *
     *
     * @param color
     * @return
     */
    private static String getColorName(Color color){
       if (color == RED)
           return "Red";
       else if (color == YELLOW)
           return "Yellow";
       else if (color == GREEN)
           return "Green";
       else
           return "Blue";
    }

    /**
     *
     *
     * @return
     */
    private static boolean hasAny(){
        return hasLitIndicator(Indicators.CAR) || hasLitIndicator(Indicators.IND) || hasLitIndicator(Indicators.FRQ);
    }
}
