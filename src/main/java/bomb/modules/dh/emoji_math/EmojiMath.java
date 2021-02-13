package bomb.modules.dh.emoji_math;

import bomb.Widget;
import bomb.enumerations.Emojis;

import static bomb.tools.Mechanics.ultimateFilter;

/**
 * This class deals with the Emoji Math module.
 * It's simple math, but replacing numbers with text emojis.
 */
public class EmojiMath extends Widget {

    //TODO - Complete Javadocs
    /**
     *
     *
     * @param input
     * @return
     */
    public static int calculate(String input){
        input = ultimateFilter(input, ":", "|", "(", ")", "=", "+", "-");
        boolean toAdd = input.contains("+");
        String plugIn = toAdd ?
                resolve(input.split("\\+"), true) :
                resolve(input.split("-"), false);

        return calculator(plugIn, toAdd);
    }

    /**
     *
     *
     * @param samples
     * @param add
     * @return
     */
    private static String resolve(String[] samples, boolean add){
        //TODO - Break down
        StringBuilder result = new StringBuilder();
        if (samples[0].length() == 4) {
            result.append(find(samples[0].substring(0, samples[0].length()/2)));
            result.append(find(samples[0].substring(samples[0].length()/2)));
        } else {
            result.append(find(samples[0]));
        }
        result.append(add?"+":"-");
        if (samples[1].length() == 4) {
            result.append(find(samples[1].substring(0, samples[1].length()/2)));
            result.append(find(samples[1].substring(samples[1].length()/2)));
        } else {
            result.append(find(samples[1]));
        }
        return result.toString();
    }

    /**
     *
     *
     * @param emoji
     * @return
     */
    private static String find(String emoji){
        for (Emojis emo : Emojis.values()) {
            if (emo.getLabel().equals(emoji)){
                return String.valueOf(emo.getIdx());
            }
        }
        return null;
    }

    /**
     *
     *
     * @param equation
     * @param add Whether the equation should be added or subtracted
     * @return
     */
    private static int calculator(String equation, boolean add){
        String[] toNum = equation.split(add?"\\+":"-");
        int[] nums = new int[toNum.length];
        nums[0] = Integer.parseInt(toNum[0]);
        nums[1] = Integer.parseInt(toNum[1]);
        return add?nums[0]+nums[1]:nums[0]-nums[1];
    }
}