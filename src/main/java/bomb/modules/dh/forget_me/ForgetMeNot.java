package bomb.modules.dh.forget_me;

import bomb.enumerations.Indicator;
import bomb.enumerations.Ports;
import bomb.tools.data.structures.ChainList;
import bomb.Widget;

import static bomb.tools.Filter.NUMBER_PATTERN;

/**
 *
 */
public class ForgetMeNot extends Widget {
    private static int prev1 = 0, prev2;
    private static int greatestNum = 0;
    private static ChainList<Integer> forgot = new ChainList<>();

    //TODO - Probably rename some variables and complete the Javadocs
    /**
     *
     */
    public static void reset(){
        forgot = new ChainList<>();
        prev1 = 0;
        prev2 = 0;
        greatestNum = 0;
    }

    /**
     *
     */
    public static void updateGreatest(){
        NUMBER_PATTERN.loadText(serialCode);
        if (!NUMBER_PATTERN.findAllMatches().isEmpty()) {
            for (String num : NUMBER_PATTERN) {
                if (Integer.parseInt(num) > greatestNum)
                    greatestNum = Integer.parseInt(num);
            }
        }
    }

    /**
     *
     *
     * @return
     */
    public static String flush(){
        StringBuilder builder = new StringBuilder();

        int iter = 0;
        while (forgot.peek() != null){
            builder.append(forgot.poll());
            iter++;
            if (iter%24==0) builder.append("\n");
            else if (iter%3==0) builder.append("-");
        }

        return builder.toString();
    }

    /**
     *
     */
    public static void undo(){
        if (forgot.peek() != null) {
            forgot.poll();
            prev1 = prev2;
            prev2 = forgot.peek();
        }
    }

    /**
     *
     *
     * @param iterator
     * @param number
     * @throws IllegalArgumentException
     */
    public static void add(int iterator, int number) throws IllegalArgumentException{
        if (!serialCode.isEmpty()) {
            if (iterator == 1) addFirst(number);
            else if (iterator == 2) addSecond(number);
            else addNext(number);
        } else throw new IllegalArgumentException("Type in the serial code");
    }

    /**
     *
     *
     * @param num
     */
    private static void addFirst(int num){
        if (hasUnlitIndicator(Indicator.CAR)) forgot.offer(nextBuffer(leastSigDig(num+2)));
        else if (countIndicators(false, false) > countIndicators(true, false))
            forgot.offer(nextBuffer(leastSigDig(num+7)));
        else if (countIndicators(false, false) == 0)
            forgot.offer(nextBuffer(leastSigDig(num+countIndicators(true, false))));
        else forgot.offer(nextBuffer(leastSigDig(num+lastDigit())));
    }

    /**
     *
     *
     * @param num
     */
    private static void addSecond(int num){
        if (portExists(Ports.SERIAL) && serialCodeNumbers() > 2)
            forgot.offer(nextBuffer(leastSigDig(num+3)));
        else if (prev1%2 == 0)forgot.offer(nextBuffer(leastSigDig(prev1+1+num)));
        else forgot.offer(nextBuffer(leastSigDig(num+prev1-1)));
    }

    /**
     *
     *
     * @param num
     */
    private static void addNext(int num){
        if (prev1 == 0 || prev2 == 0)
            forgot.offer(nextBuffer(leastSigDig(num+greatestNum)));
        else if(bothEven()) forgot.offer(nextBuffer(leastSigDig(num + smallestOddDigit())));
        else forgot.offer(nextBuffer(leastSigDig(mostSigDig() + num)));
    }

    /**
     *
     *
     * @return
     */
    private static boolean bothEven(){
        return prev2%2==0 && prev1%2==0;
    }

    /**
     *
     *
     * @param num
     * @return
     */
    private static int nextBuffer(int num) {
        prev2 = prev1;
        prev1 = num;
        return num;
    }

    /**
     *
     *
     * @return
     */
    private static int mostSigDig(){
        int sum = prev1 + prev2;
        return sum >= 10 ? sum / 10 : sum;
    }

    /**
     *
     *
     * @param num
     * @return
     */
    private static int leastSigDig(int num){
        return num%10;
    }

    /**
     *
     *
     * @return
     */
    private static int smallestOddDigit(){
        int compare = 10;
        NUMBER_PATTERN.loadText(serialCode);
        for (String num : NUMBER_PATTERN){
            if (Integer.parseInt(num) < compare)
                compare = Integer.parseInt(num);
        }
        return (compare%2==1)?compare:9;
    }
}
