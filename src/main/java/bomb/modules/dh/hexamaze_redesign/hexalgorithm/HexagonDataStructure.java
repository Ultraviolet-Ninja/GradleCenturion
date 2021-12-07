package bomb.modules.dh.hexamaze_redesign.hexalgorithm;

import bomb.tools.data.structures.queue.BufferedQueue;

import java.util.List;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class HexagonDataStructure {
    public static final IntUnaryOperator CALCULATE_SPAN = length -> 2 * length - 1,
            NODAL_AREA = length -> (int)(3 * Math.pow(length, 2)) - (3 * length) + 1,
            NODAL_SIDE_LENGTH = area -> {
                double math = (3 + Math.sqrt(12 * area - 3)) / 6;
                if (math % 1 != 0.0) //Is not an integer
                    return -1;
                return (int) math;
            };


    private BufferedQueue<BufferedQueue<HexNode>> hexagon;
    private final int sideLength;

    public HexagonDataStructure(int sideLength) {
        this.sideLength = sideLength;
        hexagon = createHexagon(sideLength);
    }

    public HexagonDataStructure(BufferedQueue<BufferedQueue<HexNode>> hexagon, int sideLength) {
        this.sideLength = sideLength;
        this.hexagon = hexagon;
    }

    public HexagonDataStructure(List<HexNode> nodeList) throws IllegalArgumentException {
        sideLength = NODAL_SIDE_LENGTH.applyAsInt(nodeList.size());
        if (sideLength == -1)
            throw new IllegalArgumentException("Given List would not create a complete Hexagon");
        hexagon = generateFromList(nodeList);
    }

    private BufferedQueue<BufferedQueue<HexNode>> generateFromList(List<HexNode> nodeList) {
        BufferedQueue<BufferedQueue<HexNode>> temp = createHexagon(sideLength);
        for (HexNode hexNode : nodeList) {
            if (!add(temp, hexNode))
                throw new IllegalArgumentException("We have extra nodes being added");
        }
        return temp;
    }

    private boolean add(BufferedQueue<BufferedQueue<HexNode>> toFill, HexNode toAdd) {
        int capacity = toFill.getCapacity();
        for (int i = 0; i < capacity; i++) {
            BufferedQueue<HexNode> column = toFill.get(i);
            if (!column.isFull()) {
                column.add(toAdd);
                return true;
            }
        }
        return false;
    }

    private BufferedQueue<BufferedQueue<HexNode>> createHexagon(int sideLength) {
        if (sideLength <= 2)
            throw new IllegalArgumentException("Size is too small");
        int span = CALCULATE_SPAN.applyAsInt(sideLength);
        BufferedQueue<BufferedQueue<HexNode>> hex = new BufferedQueue<>(span);
        IntStream.concat(
                IntStream.rangeClosed(sideLength, span),
                IntStream.rangeClosed(span - 1, sideLength)
                )
                .mapToObj(size -> new BufferedQueue<HexNode>(size))
                .forEach(hex::add);
        return hex;
    }

    public List<HexNode> asList() {
        return hexagon.stream()
                .flatMap(BufferedQueue::stream)
                .collect(toList());
    }
}
