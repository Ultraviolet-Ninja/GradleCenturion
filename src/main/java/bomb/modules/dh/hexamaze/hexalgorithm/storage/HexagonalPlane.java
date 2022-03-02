package bomb.modules.dh.hexamaze.hexalgorithm.storage;

import bomb.tools.Coordinates;
import bomb.tools.data.structures.queue.BufferedQueue;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;
import java.util.function.IntUnaryOperator;

import static bomb.modules.dh.hexamaze.hexalgorithm.storage.AbstractHexagon.calculateColumnLengthArray;
import static bomb.tools.number.MathUtils.isAnInteger;
import static java.util.Arrays.stream;

public class HexagonalPlane implements Iterable<BufferedQueue<HexNode>>, Rotatable {
    public static final IntUnaryOperator CALCULATE_SPAN, NODAL_SIDE_LENGTH;

    private final int sideLength;

    private BufferedQueue<BufferedQueue<HexNode>> hexagon;

    public HexagonalPlane(int sideLength) {
        this.sideLength = sideLength;
        hexagon = createHexagon(sideLength);
    }

    public HexagonalPlane(BufferedQueue<BufferedQueue<HexNode>> hexagon, int sideLength) {
        this.sideLength = sideLength;
        this.hexagon = hexagon;
    }

    public HexagonalPlane(@NotNull List<HexNode> nodeList) throws IllegalArgumentException {
        sideLength = NODAL_SIDE_LENGTH.applyAsInt(nodeList.size());
        if (sideLength == -1)
            throw new IllegalArgumentException("Given List would not create a complete Hexagon");
        hexagon = convertFromList(nodeList);
    }

    @Override
    public void rotate() {
        int span = CALCULATE_SPAN.applyAsInt(sideLength);
        BufferedQueue<BufferedQueue<HexNode>> output = createHexagon(sideLength);

        for (int columnIndex = 0; columnIndex < span; columnIndex++) {
            addLeftHalf(output, columnIndex);

            if (columnIndex != 0)
                addRightHalf(output, columnIndex, sideLength);
        }

        output.stream()
                .flatMap(BufferedQueue::stream)
                .forEach(Rotatable::rotate);
        hexagon = output;
    }

    private void addLeftHalf(BufferedQueue<BufferedQueue<HexNode>> output, int columnIndex) {
        for (int firstHalfIndex = 0; firstHalfIndex < sideLength; firstHalfIndex++) {
            int columnCapacity = hexagon.get(firstHalfIndex).getCapacity();
            int position = columnCapacity - columnIndex;

            if (position > 0) {
                int lastIndex = columnCapacity - 1;
                HexNode addition = hexagon.get(firstHalfIndex).get(lastIndex - columnIndex);
                add(output, addition);
            }
        }
    }

    private void addRightHalf(BufferedQueue<BufferedQueue<HexNode>> output, int columnIndex, int rowCounter) {
        for (int secondHalfIndex = columnIndex; secondHalfIndex > 0; secondHalfIndex--) {
            if (rowCounter < hexagon.getCapacity()) {
                int lastIndex = hexagon.get(rowCounter).getCapacity() - secondHalfIndex;
                HexNode addition = hexagon.get(rowCounter++).get(lastIndex);
                add(output, addition);
            } else {
                secondHalfIndex = 0;
            }
        }
    }

    public List<HexNode> asList() {
        return hexagon.stream()
                .flatMap(BufferedQueue::stream)
                .toList();
    }

    public HexNode findAtCoordinate(@NotNull Coordinates coordinates) {
        int x = coordinates.x();
        if (x < 0 || x >= getSpan()) return null;

        BufferedQueue<HexNode> column = hexagon.get(x);
        int y = coordinates.y();
        return y < 0 || y >= column.getCapacity() ? null : column.get(y);
    }

    public void readInNodeList(List<HexNode> stream) {
        hexagon = convertFromList(stream);
    }

    public BufferedQueue<BufferedQueue<HexNode>> getBufferedQueues() {
        return hexagon;
    }

    public int getSideLength() {
        return sideLength;
    }

    public int getSpan() {
        return CALCULATE_SPAN.applyAsInt(sideLength);
    }

    @Override
    public Iterator<BufferedQueue<HexNode>> iterator() {
        return hexagon.iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HexagonalPlane other)) return false;
        return sideLength == other.sideLength && hexagon.equals(other.hexagon);
    }

    @Override
    public int hashCode() {
        return hexagon.hashCode();
    }

    public static <T> @NotNull BufferedQueue<BufferedQueue<T>> convertFromList(@NotNull List<T> list) {
        int sideLength = NODAL_SIDE_LENGTH.applyAsInt(list.size());
        BufferedQueue<BufferedQueue<T>> output = createHexagon(sideLength);

        for (T element : list) {
            if (!add(output, element))
                throw new IllegalArgumentException("We have extra nodes being added");
        }
        return output;
    }

    private static <T> boolean add(BufferedQueue<BufferedQueue<T>> toFill, T toAdd) {
        int capacity = toFill.getCapacity();
        for (int i = 0; i < capacity; i++) {
            BufferedQueue<T> column = toFill.get(i);
            if (!column.isFull()) {
                column.add(toAdd);
                return true;
            }
        }
        return false;
    }

    private static <T> BufferedQueue<BufferedQueue<T>> createHexagon(int sideLength) {
        if (sideLength <= 2) throw new IllegalArgumentException("Size is too small");

        int span = CALCULATE_SPAN.applyAsInt(sideLength);
        BufferedQueue<BufferedQueue<T>> output = new BufferedQueue<>(span);

        stream(calculateColumnLengthArray(sideLength))
                .mapToObj(size -> new BufferedQueue<T>(size))
                .forEach(output::add);

        return output;
    }

    static {
        CALCULATE_SPAN = length -> 2 * length - 1;
        NODAL_SIDE_LENGTH = area -> {
            double result = (3 + Math.sqrt(12 * area - 3)) / 6;
            return isAnInteger(result) ? (int) result : -1;
        };
    }
}
