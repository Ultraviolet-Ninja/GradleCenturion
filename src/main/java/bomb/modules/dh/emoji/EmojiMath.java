package bomb.modules.dh.emoji;

import bomb.Widget;

import static bomb.tools.Filter.ultimateFilter;

/**
 * This class deals with the Emoji Math module.
 * It's simple math, but replacing numbers with text emojis.
 */
public class EmojiMath extends Widget {
    private static final String EMOJI_REGEX = Emojis.generateCaptureGroup();
    private static final String
            ADDITION = EMOJI_REGEX + "?" + EMOJI_REGEX + "\\+" + EMOJI_REGEX + EMOJI_REGEX + "?";

    /**
     * Calculates the sum/difference from the equation of emojis
     *
     * @param input The equation from the TextField
     * @return The values gathered from the emoji equation
     */
    public static int calculate(String input){
        String temp = ultimateFilter(input, ADDITION);
        boolean toAdd = !temp.isEmpty();
        String translatedEq = toAdd ?
                translateEmojis(input.split("\\+"), true) :
                translateEmojis(input.split("-"), false);

        return calculateRealNumbers(translatedEq, toAdd);
    }

    /**
     * Translates the emojis in the equation to real numbers
     *
     * @param samples The 2 sides of the equation split by the + or - symbol
     * @param add Whether the equation will be added or not
     * @return The equation translated from emojis to numbers
     */
    private static String translateEmojis(String[] samples, boolean add){
        StringBuilder result = new StringBuilder();
        boolean flag = true;
        for (String half : samples){
            if (half.length() == 4){
                result.append(findEmoji(half.substring(0, half.length()/2)));
                result.append(findEmoji(half.substring(half.length()/2)));
            } else result.append(findEmoji(half));
            if(flag){
                flag = false;
                result.append(add?"+":"-");
            }
        }
        return result.toString();
    }

    /**
     * Translates a single emoji into the number associated with it
     *
     * @param emoji The emoji to translate
     * @return The number
     */
    private static String findEmoji(String emoji){
        for (Emojis emo : Emojis.values()) {
            if (emo.getLabel().equals(emoji)){
                return String.valueOf(emo.getIdx());
            }
        }
        return null;
    }

    /**
     * Calculates the sum or difference of the translated equation
     *
     * @param equation The equation put into real numbers
     * @param add Whether the equation should be added or subtracted
     * @throws NumberFormatException When a non-number or null is entered into the equation
     * @return The sum or difference of the translated numbers
     */
    private static int calculateRealNumbers(String equation, boolean add) throws NumberFormatException{
        String[] toNum = equation.split(add?"\\+":"-");
        int[] nums = new int[toNum.length];
        nums[0] = Integer.parseInt(toNum[0]);
        nums[1] = Integer.parseInt(toNum[1]);
        return add ?
                nums[0] + nums[1] :
                nums[0] - nums[1];
    }
}