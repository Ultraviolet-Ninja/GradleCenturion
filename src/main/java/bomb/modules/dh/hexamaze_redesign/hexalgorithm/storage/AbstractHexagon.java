package bomb.modules.dh.hexamaze_redesign.hexalgorithm.storage;

import java.util.stream.IntStream;

import static java.util.Arrays.copyOf;

public abstract class AbstractHexagon {
    protected HexagonDataStructure hexagon;

    public AbstractHexagon() {
        hexagon = null;
    }

    public AbstractHexagon(HexagonDataStructure hexagon) {
        this.hexagon = hexagon;
    }

    public HexagonDataStructure getHexagon() {
        return hexagon;
    }

    public static int[] calculateColumnLengthStream(int sideLength) {
        int span = HexagonDataStructure.CALCULATE_SPAN.applyAsInt(sideLength);
        int[] firstHalf = IntStream.rangeClosed(sideLength, span).toArray();
        int[] secondHalf = reverseOrder(IntStream.rangeClosed(sideLength, span - 1).toArray());

        int[] result = copyOf(firstHalf, firstHalf.length + secondHalf.length);
        System.arraycopy(secondHalf, 0, result, firstHalf.length, secondHalf.length);
        return result;
    }

    private static int[] reverseOrder(int[] array) {
        int index;
        int length = array.length;
        int[] result = new int[length];
        for (int i = length - 1; i >= 0; i--) {
            index = length - (i + 1);
            result[index] = array[i];
        }
        return result;
    }
}
