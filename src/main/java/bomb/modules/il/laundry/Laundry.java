package bomb.modules.il.laundry;

import bomb.enumerations.Clothing;
import bomb.enumerations.Indicators;
import bomb.Widget;

import static bomb.enumerations.Clothing.ARTICLE;
import static bomb.tools.Mechanics.LOWERCASE_REGEX;
import static bomb.tools.Mechanics.ultimateFilter;

/**
 * This class deals with the Laundry module. The module requires a plethora of conditions that involve the
 * widget data, the number of solved modules, and the number of needy bomb.modules to determine the clothing
 * item, material, and color. These will lead to the instructions for washing, drying, ironing,
 * and dry cleaning your clothing.
 */
public class Laundry extends Widget {
    private static int needy;

    //TODO - Finish Javadocs
    /**
     * Picks out the right conditions to clean the current laundry load
     *
     * @param solved The number of solved modules on the bomb
     * @param needy The number of needy modules on the bomb
     * @return The array of Strings containing the solve conditions
     * @throws IllegalArgumentException Whether serial code, needy or solved fields are empty
     */
    public static String[] clean(String solved, String needy) throws IllegalArgumentException{
        exceptionSafeguard(solved, needy);
        Laundry.needy = Integer.parseInt(needy);
        setClothing(Integer.parseInt(solved));
        return conditions();
    }

    /**
     * Handles the exceptions that protect the module from running into any empty fields
     *
     * @param solved The number of solved modules on the bomb
     * @param needy The number of needy modules on the bomb
     * @throws IllegalArgumentException Whether serial code, needy or solved fields are empty
     */
    private static void exceptionSafeguard(String solved, String needy) throws IllegalArgumentException{
        if (serialCode.isEmpty()) throw new
                IllegalArgumentException("Serial Code must be typed in.");
        else if (needy.isEmpty()) throw new
                IllegalArgumentException("Need to know about the needy modules");
        else if (solved.isEmpty())throw new
                IllegalArgumentException("This needs the number of currently solved modules.");
    }

    /**
     * Determines the type of clothing based its Material, color and article
     *
     * @param solved The number of solved modules
     */
    private static void setClothing(int solved) throws IllegalArgumentException{
        if (numModules != 0) {
            setMaterial(solved);
            setColor();
            setItem(numModules - solved - needy);
        } else throw new IllegalArgumentException("This needs the number of modules to function.");
    }

    /**
     * Sets the material based on...
     * No. of Solved Modules + Total Ports - the No. of Battery Holders
     *
     * @param solved The number of solved modules
     */
    private static void setMaterial(int solved){
        switch (balance(solved + getTotalPorts() - numHolders)){
            case 0: ARTICLE.setMat(Clothing.Material.POLYESTER); break;
            case 1: ARTICLE.setMat(Clothing.Material.COTTON); break;
            case 2: ARTICLE.setMat(Clothing.Material.WOOL); break;
            case 3: ARTICLE.setMat(Clothing.Material.NYLON); break;
            case 4: ARTICLE.setMat(Clothing.Material.CORDUROY); break;
            default: ARTICLE.setMat(Clothing.Material.LEATHER);
        }
    }

    /**
     * Sets the color based on...
     * Last Digit of the Serial Code + the No. of All Batteries
     */
    private static void setColor(){
        switch (balance(lastDigit() + getAllBatteries())){
            case 0: ARTICLE.setColor(Clothing.Color.RUBY); break;
            case 1: ARTICLE.setColor(Clothing.Color.STAR); break;
            case 2: ARTICLE.setColor(Clothing.Color.SAPPHIRE); break;
            case 3: ARTICLE.setColor(Clothing.Color.JADE); break;
            case 4: ARTICLE.setColor(Clothing.Color.PEARL); break;
            default: ARTICLE.setColor(Clothing.Color.MALINITE);
        }
    }

    /**
     * Sets the item based on...
     * The No. of Unsolved Modules + the No. of All Indicators
     *
     * @param unsolved The number of unsolved modules
     */
    private static void setItem(int unsolved){
        switch (balance(unsolved + countIndicators(true, true))){
            case 0: ARTICLE.setItem(Clothing.Item.CORSET); break;
            case 1: ARTICLE.setItem(Clothing.Item.SHIRT); break;
            case 2: ARTICLE.setItem(Clothing.Item.SKIRT); break;
            case 3: ARTICLE.setItem(Clothing.Item.SKORT); break;
            case 4: ARTICLE.setItem(Clothing.Item.SHORTS); break;
            default: ARTICLE.setItem(Clothing.Item.SCARF);
        }
    }

    /**
     * Creates the solve conditions for the module
     *
     * @return The array of Strings for the solve conditions
     */
    private static String[] conditions(){
        if (thanksBob()) return done();

        String[] attributes = new String[5];
        fillThird(attributes);

        if (ARTICLE.getMat() == Clothing.Material.LEATHER || ARTICLE.getColor() == Clothing.Color.JADE)
            attributes[0] = "file:src\\Bomb\\Resources\\Laundry\\Wash\\80F.png";

        if (ARTICLE.getMat() == Clothing.Material.WOOL || ARTICLE.getColor() == Clothing.Color.STAR)
            attributes[1] = "file:src\\Bomb\\Resources\\Laundry\\Dry\\High Heat.png";

        attributes[4] = ARTICLE.getMat().name() + " - " + ARTICLE.getColor().name()
                + " - " + ARTICLE.getItem().name();
        return fillRest(attributes);
    }

    /**
     * Creates the win conditions for when Bob does the work for us
     * Thanks Bob, you the man
     *
     * @return The win conditions
     */
    private static String[] done(){
        return new String[]{ARTICLE.getMat().getLabel(), ARTICLE.getColor().getLabel(),
                ARTICLE.getItem().getWords()[0], ARTICLE.getItem().getWords()[1],
                ARTICLE.getMat().name() + " - " + ARTICLE.getColor().name() + " - " + ARTICLE.getItem().name()
                , "Thanks, Bob!"};
    }

    /**
     *
     *
     * @param att
     */
    private static void fillThird(String[] att){
        if (ARTICLE.getColor() == Clothing.Color.PEARL)
            att[3] = "Non-Chlorine Bleach";
        else if (ARTICLE.getItem() == Clothing.Item.CORSET || ARTICLE.getMat() == Clothing.Material.CORDUROY)
            att[3] = ARTICLE.getMat().getSpecialInstr();
        else if (letterMatch())
            att[3] = ARTICLE.getColor().getSpecialInstr();
    }

    /**
     * Fills in the rest of the win conditions
     *
     * @param att
     * @return The completed win conditions
     */
    private static String[] fillRest(String[] att){
        for (int i = 0; i < 4; i++){
            if (att[i] == null){
                switch (i){
                    case 0: att[i] = ARTICLE.getMat().getLabel(); break;
                    case 1: att[i] = ARTICLE.getColor().getLabel(); break;
                    case 2: att[i] = ARTICLE.getItem().getWords()[0]; break;
                    default : att[i] = ARTICLE.getItem().getWords()[1];
                }
            }
        }
        return att;
    }

    /**
     * Keeps the balance factor between 0 and 6 for determining clothing properties
     *
     * @param in The number
     * @return The balanced number
     */
    private static int balance(int in){
        if (in > 5) in %= 6;
        else if (in < 0) in += 6;
        return in;
    }

    /**
     * Determines if Bob did all the work for us
     *
     * @return True if the edgework matches the BOB conditions
     */
    private static boolean thanksBob(){
        return getAllBatteries() == 4 && numHolders == 2 && hasLitIndicator(Indicators.BOB);
    }

    /**
     * Checks to see if a letter in the Serial Code matches to a letter
     * in the clothing material
     *
     * @return True if a letter from the Serial Code matches a letter in the clothing material
     */
    private static boolean letterMatch(){
        String letters = ultimateFilter(serialCode, LOWERCASE_REGEX);
        for (char instance : letters.toCharArray())
            if (ARTICLE.getMat().name().toLowerCase().indexOf(instance) != -1) return true;
        return false;
    }
}
