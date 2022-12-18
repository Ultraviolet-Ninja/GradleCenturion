package bomb.modules.m.morsematics;

import bomb.Widget;
import bomb.enumerations.Indicator;
import bomb.tools.data.structures.graph.list.ListGraph;
import bomb.tools.data.structures.ring.ArrayRing;
import bomb.tools.number.MathUtils;
import bomb.tools.pattern.factory.MorseCodeGraphFactory;
import org.javatuples.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

import static bomb.Widget.IndicatorFilter.LIT;
import static bomb.Widget.IndicatorFilter.UNLIT;
import static bomb.tools.number.MathUtils.isPerfectSquare;

public final class Morsematics extends Widget {
    public static @NotNull String solve(@NotNull LinkedHashSet<String> inputSet) throws IllegalArgumentException {
        checkSerialCode();
        ListGraph<String> morseGraph = MorseCodeGraphFactory.createGraph();
        List<String> inputLetter = validate(inputSet, morseGraph);
        List<String> letterList = IntStream.rangeClosed('A', 'Z')
                .mapToObj(number -> String.valueOf((char) number))
                .toList();

        Pair<ArrayRing<String>, ArrayRing<String>> rotatingLetters = createStartingLetters(letterList);
        findFinalLetter(rotatingLetters, inputLetter);

        return morseGraph.getTargetVertices(createOutputLetter(rotatingLetters)).get(0);
    }

    private static List<String> validate(Set<String> inputSet, ListGraph<String> graph)
            throws IllegalArgumentException {
        if (inputSet == null || inputSet.size() != 3)
            throw new IllegalArgumentException("""
                    Must have...
                    \t3 morse code segments
                    \t3 letters
                    \t\tOR
                    \ta combination of the 2""");

        for (String input : inputSet) {
            if (!graph.containsVertex(input) && !input.matches("\\b[a-zA-Z]|[.-]{1,4}"))
                throw new IllegalArgumentException("Invalid character in the set: " + input);
        }

        List<String> inputLetters = inputSet.stream()
                .map(input -> !Character.isAlphabetic(input.charAt(0)) ?
                        graph.getTargetVertices(input).get(0) :
                        input.toUpperCase()
                )
                .distinct()
                .toList();
        if (inputLetters.size() != 3)
            throw new IllegalArgumentException(
                    "Repeated value detected in both morse code and the letter form"
            );
        return inputLetters;
    }

    private static Pair<ArrayRing<String>, ArrayRing<String>> createStartingLetters(List<String> letterList) {
        String requiredSerialCodeLetters = serialCode.substring(3,5).toUpperCase();
        ArrayRing<String> firstLetter = new ArrayRing<>(letterList);
        ArrayRing<String> secondLetter = new ArrayRing<>(letterList);
        firstLetter.setToIndex(requiredSerialCodeLetters.substring(0,1));
        secondLetter.setToIndex(requiredSerialCodeLetters.substring(1));
        return new Pair<>(firstLetter, secondLetter);
    }

    private static void findFinalLetter(Pair<ArrayRing<String>, ArrayRing<String>> rotatingLetters,
                                          List<String> inputLetters) {
        final int[] inputLetterValues = inputLetters.stream()
                .mapToInt(letter -> letter.charAt(0) - '@')
                .toArray();
        ArrayRing<String> firstLetter = rotatingLetters.getValue0();
        ArrayRing<String> secondLetter = rotatingLetters.getValue1();

        executeFirstRule(firstLetter, secondLetter, inputLetters);
        executeSecondRule(firstLetter, secondLetter);
        executeThirdRule(firstLetter, inputLetterValues);
        subtractFromRingWithRule(firstLetter, inputLetterValues, MathUtils::isPrime);
        subtractFromRingWithRule(secondLetter, inputLetterValues, MathUtils::isPerfectSquare);

        int numBatteries = getAllBatteries();
        if (numBatteries != 0) {
            IntPredicate divisibleRule = num -> num % numBatteries == 0;
            subtractFromRingWithRule(firstLetter, inputLetterValues, divisibleRule);
            subtractFromRingWithRule(secondLetter, inputLetterValues, divisibleRule);
        }
    }

    private static void executeFirstRule(ArrayRing<String> firstLetter, ArrayRing<String> secondLetter,
                                         List<String> inputLetters) {
        EnumSet<Indicator> litIndicators = getFilteredSetOfIndicators(LIT);
        EnumSet<Indicator> unlitIndicators = getFilteredSetOfIndicators(UNLIT);

        rotateOnIndicatorMatch(litIndicators, firstLetter, inputLetters);
        rotateOnIndicatorMatch(unlitIndicators, secondLetter, inputLetters);
    }

    private static void rotateOnIndicatorMatch(EnumSet<Indicator> indicatorSet, ArrayRing<String> letterRing,
                                               List<String> inputLetters) {
        if (indicatorSet.size() == 0) return;

        indicatorSet.stream()
                .map(Enum::name)
                .forEach(indicatorName -> {
                    for (String letter : inputLetters) {
                        if (indicatorName.contains(letter)) {
                            letterRing.rotateClockwise();
                            return;
                        }
                    }
                });
    }

    private static void executeSecondRule(ArrayRing<String> firstLetter, ArrayRing<String> secondLetter) {
        int firstIndex = firstLetter.getCurrentIndex() + 1;
        int secondIndex = secondLetter.getCurrentIndex() + 1;

        if (isPerfectSquare((secondIndex + firstIndex) % firstLetter.getSize())) {
            firstLetter.rotateClockwise(4);
        } else {
            secondLetter.rotateCounterClockwise(4);
        }
    }

    private static void executeThirdRule(ArrayRing<String> firstLetter, int[] inputLetterValues) {
        int max = 0;
        for (int num : inputLetterValues) {
            if (num > max)
                max = num;
        }
        firstLetter.rotateClockwise(max);
    }

    private static void subtractFromRingWithRule(ArrayRing<String> letter, int[] inputLetterValues,
                                                 IntPredicate predicate) {
        int total = 0;
        for (int num : inputLetterValues) {
            if (predicate.test(num))
                total += num;
        }
        if (total != 0)
            letter.rotateCounterClockwise(total);
    }

    private static String createOutputLetter(Pair<ArrayRing<String>, ArrayRing<String>> rotatingLetters) {
        ArrayRing<String> firstLetter = rotatingLetters.getValue0();
        ArrayRing<String> secondLetter = rotatingLetters.getValue1();
        int firstIndex = firstLetter.getCurrentIndex() + 1;
        int secondIndex = secondLetter.getCurrentIndex() + 1;

        int difference = secondIndex - firstIndex;

        if (difference == 0) return firstLetter.getHeadData();

        return String.valueOf((char)('@' +
                (difference > 0 ?
                        (firstIndex + secondIndex) :
                        -difference))
        );
    }
}
