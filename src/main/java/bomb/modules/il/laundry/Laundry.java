package bomb.modules.il.laundry;

import bomb.Widget;
import bomb.annotation.DisplayComponent;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.stream.Stream;

import static bomb.enumerations.Indicator.BOB;
import static bomb.modules.il.laundry.Clothing.ARTICLE;
import static bomb.modules.il.laundry.Clothing.Color.JADE;
import static bomb.modules.il.laundry.Clothing.Color.MALINITE;
import static bomb.modules.il.laundry.Clothing.Color.PEARL;
import static bomb.modules.il.laundry.Clothing.Color.RUBY;
import static bomb.modules.il.laundry.Clothing.Color.SAPPHIRE;
import static bomb.modules.il.laundry.Clothing.Color.STAR;
import static bomb.modules.il.laundry.Clothing.Item.CORSET;
import static bomb.modules.il.laundry.Clothing.Item.SCARF;
import static bomb.modules.il.laundry.Clothing.Item.SHIRT;
import static bomb.modules.il.laundry.Clothing.Item.SHORTS;
import static bomb.modules.il.laundry.Clothing.Item.SKIRT;
import static bomb.modules.il.laundry.Clothing.Item.SKORT;
import static bomb.modules.il.laundry.Clothing.Material.CORDUROY;
import static bomb.modules.il.laundry.Clothing.Material.COTTON;
import static bomb.modules.il.laundry.Clothing.Material.LEATHER;
import static bomb.modules.il.laundry.Clothing.Material.NYLON;
import static bomb.modules.il.laundry.Clothing.Material.POLYESTER;
import static bomb.modules.il.laundry.Clothing.Material.WOOL;
import static bomb.tools.filter.RegexFilter.CHAR_FILTER;
import static bomb.tools.filter.RegexFilter.filter;
import static bomb.tools.number.MathUtils.negativeSafeModulo;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toUnmodifiableSet;

/**
 * This class deals with the Laundry module. The module requires a plethora of conditions that involve the
 * widget data, the number of solved modules, and the number of needy bomb modules to determine the clothing
 * item, material, and color. These will lead to the instructions for washing, drying, ironing,
 * and dry cleaning your clothing.
 */
@DisplayComponent(resource = "laundry.fxml", buttonLinkerName = "Laundry")
public final class Laundry extends Widget {
    public static final String THANKS_BOB = "Thanks, Bob! :)";

    private static final int NUMBER_OF_OPTIONS = 6;

    private static int needy;

    /**
     * Picks out the right conditions to clean the current laundry load
     *
     * @param solved The number of solved modules on the bomb
     * @param needy  The number of needy modules on the bomb
     * @return The array of Strings containing the solve conditions
     * @throws IllegalArgumentException Whether serial code, needy or solved fields are empty
     */
    public static @NotNull String[] clean(@NotNull String solved, @NotNull String needy)
            throws IllegalArgumentException {
        validateInput(solved, needy);
        Laundry.needy = Integer.parseInt(needy);
        setClothing(Integer.parseInt(solved));
        return conditions();
    }

    /**
     * Handles the exceptions that protect the module from running into any empty fields
     *
     * @param solved The number of solved modules on the bomb
     * @param needy  The number of needy modules on the bomb
     * @throws IllegalArgumentException Whether serial code, needy or solved fields are empty
     */
    private static void validateInput(String solved, String needy) throws IllegalArgumentException {
        if (serialCode.isEmpty()) throw new
                IllegalArgumentException("Serial Code must be typed in");
        else if (needy.isEmpty()) throw new
                IllegalArgumentException("This module needs to know about the number of needy modules");
        else if (solved.isEmpty()) throw new
                IllegalArgumentException("This module needs the number of currently solved modules");
    }

    /**
     * Determines the type of clothing based its Material, color and article
     *
     * @param solved The number of solved modules
     */
    private static void setClothing(int solved) throws IllegalArgumentException {
        if (numModules == 0)
            throw new IllegalArgumentException("This needs the number of modules to function");

        setMaterial(solved);
        setColor();
        setItem(numModules - solved - needy);
    }

    /**
     * Sets the material based on...
     * No. of Solved Modules + Total Ports - the No. of Battery Holders
     *
     * @param solved The number of solved modules
     */
    private static void setMaterial(int solved) {
        int number = solved + calculateTotalPorts() - numHolders;
        int selector = negativeSafeModulo(number, NUMBER_OF_OPTIONS);
        Clothing.Material foundMaterial = switch (selector) {
            case 0 -> POLYESTER;
            case 1 -> COTTON;
            case 2 -> WOOL;
            case 3 -> NYLON;
            case 4 -> CORDUROY;
            default -> LEATHER;
        };

        ARTICLE.setMaterial(foundMaterial);
    }

    /**
     * Sets the color based on...
     * Last Digit of the Serial Code + the No. of All Batteries
     */
    private static void setColor() {
        int selector = (getSerialCodeLastDigit() + getAllBatteries()) % NUMBER_OF_OPTIONS;
        Clothing.Color foundColor = switch (selector) {
            case 0 -> RUBY;
            case 1 -> STAR;
            case 2 -> SAPPHIRE;
            case 3 -> JADE;
            case 4 -> PEARL;
            default -> MALINITE;
        };

        ARTICLE.setColor(foundColor);
    }

    /**
     * Sets the item based on...
     * The No. of Unsolved Modules + the No. of All Indicators
     *
     * @param unsolved The number of unsolved modules
     */
    private static void setItem(int unsolved) {
        int selector = (unsolved + countIndicators(IndicatorFilter.ALL_PRESENT)) % NUMBER_OF_OPTIONS;
        Clothing.Item foundItem = switch (selector) {
            case 0 -> CORSET;
            case 1 -> SHIRT;
            case 2 -> SKIRT;
            case 3 -> SKORT;
            case 4 -> SHORTS;
            default -> SCARF;
        };

        ARTICLE.setItem(foundItem);
    }

    /**
     * Creates the solve conditions for the module
     *
     * @return The array of Strings for the solve conditions
     */
    private static String[] conditions() {
        if (checkBobConditions()) return returnBob();

        String[] attributes = new String[5];
        fillThird(attributes);

        if (ARTICLE.getMaterial() == LEATHER || ARTICLE.getColor() == JADE)
            attributes[0] = "wash/80F.png";

        if (ARTICLE.getMaterial() == WOOL || ARTICLE.getColor() == STAR)
            attributes[1] = "dry/High Heat.png";

        attributes[4] = ARTICLE.getMaterial().name() + " - " + ARTICLE.getColor().name()
                + " - " + ARTICLE.getItem().name();
        return fillRest(attributes);
    }

    /**
     * Creates the win conditions for when Bob does the work for us
     * Thanks Bob, you the man
     *
     * @return The win conditions
     */
    private static String[] returnBob() {
        return new String[]{ARTICLE.getMaterial().getLabel(), ARTICLE.getColor().getLabel(),
                ARTICLE.getItem().getIroningAndSpecialWords()[0], ARTICLE.getItem().getIroningAndSpecialWords()[1],
                ARTICLE.getMaterial().name() + " - " + ARTICLE.getColor().name() + " - " + ARTICLE.getItem().name()
                , THANKS_BOB};
    }

    /**
     * Fills the forth attribute based on conditions specified on the manual page
     *
     * @param att The array of attributes to fill
     */
    private static void fillThird(String[] att) {
        if (ARTICLE.getColor() == PEARL)
            att[3] = "Non-Chlorine Bleach";
        else if (ARTICLE.getItem() == CORSET || ARTICLE.getMaterial() == CORDUROY)
            att[3] = ARTICLE.getMaterial().getSpecialInstr();
        else if (letterMatch())
            att[3] = ARTICLE.getColor().getSpecialInstr();
    }

    /**
     * Fills in the rest of the win conditions
     *
     * @param att The array of attributes to fill
     * @return The completed win conditions
     */
    private static String[] fillRest(String[] att) {
        for (int i = 0; i < 4; i++) {
            if (att[i] == null) {
                switch (i) {
                    case 0 -> att[i] = ARTICLE.getMaterial().getLabel();
                    case 1 -> att[i] = ARTICLE.getColor().getLabel();
                    case 2 -> att[i] = ARTICLE.getItem().getIroningAndSpecialWords()[0];
                    default -> att[i] = ARTICLE.getItem().getIroningAndSpecialWords()[1];
                }
            }
        }
        return att;
    }

    /**
     * Determines if Bob did all the work for us
     *
     * @return True if the edgework matches the BOB conditions
     */
    private static boolean checkBobConditions() {
        return getAllBatteries() == 4 && numHolders == 2 && hasLitIndicator(BOB);
    }

    /**
     * Checks to see if a letter in the Serial Code matches to a letter
     * in the clothing material
     *
     * @return True if a letter from the Serial Code matches a letter in the clothing material
     */
    private static boolean letterMatch() {
        Set<String> clothingArticleLetters = stream(ARTICLE.getMaterial().name().split(""))
                .map(String::toLowerCase)
                .collect(toUnmodifiableSet());

        return Stream.of(filter(serialCode, CHAR_FILTER).split(""))
                .anyMatch(clothingArticleLetters::contains);
    }
}
