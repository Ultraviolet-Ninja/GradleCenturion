package bomb.modules.dh.hexamaze_redesign.hexalgorithm;

import bomb.modules.dh.hexamaze_redesign.hexalgorithm.HexNode.HexShape;
import bomb.modules.dh.hexamaze_redesign.hexalgorithm.HexNode.HexWall;
import bomb.tools.Coordinates;
import bomb.tools.data.structures.queue.BufferedQueue;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static bomb.modules.dh.hexamaze_redesign.hexalgorithm.HexNode.HexShape.CIRCLE;
import static bomb.modules.dh.hexamaze_redesign.hexalgorithm.HexNode.HexShape.DOWN_TRIANGLE;
import static bomb.modules.dh.hexamaze_redesign.hexalgorithm.HexNode.HexShape.HEXAGON;
import static bomb.modules.dh.hexamaze_redesign.hexalgorithm.HexNode.HexShape.LEFT_TRIANGLE;
import static bomb.modules.dh.hexamaze_redesign.hexalgorithm.HexNode.HexShape.RIGHT_TRIANGLE;
import static bomb.modules.dh.hexamaze_redesign.hexalgorithm.HexNode.HexShape.UP_TRIANGLE;
import static bomb.modules.dh.hexamaze_redesign.hexalgorithm.HexNode.HexWall.BOTTOM;
import static bomb.modules.dh.hexamaze_redesign.hexalgorithm.HexNode.HexWall.BOTTOM_LEFT;
import static bomb.modules.dh.hexamaze_redesign.hexalgorithm.HexNode.HexWall.BOTTOM_RIGHT;
import static bomb.modules.dh.hexamaze_redesign.hexalgorithm.HexNode.HexWall.TOP;
import static bomb.modules.dh.hexamaze_redesign.hexalgorithm.HexNode.HexWall.TOP_LEFT;
import static bomb.modules.dh.hexamaze_redesign.hexalgorithm.HexNode.HexWall.TOP_RIGHT;
import static java.util.stream.Collectors.toList;

public class HexagonDataStructure implements Iterable<BufferedQueue<HexNode>> {
    public static final IntUnaryOperator CALCULATE_SPAN = length -> 2 * length - 1,
            NODAL_AREA = length -> (int)(3 * Math.pow(length, 2)) - (3 * length) + 1,
            NODAL_SIDE_LENGTH = area -> {
                double result = (3 + Math.sqrt(12 * area - 3)) / 6;
                //If is not an integer
                return result % 1 != 0.0 ? -1 : (int) result;
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

    public void rotate() {
        int span = CALCULATE_SPAN.applyAsInt(sideLength);
        BufferedQueue<BufferedQueue<HexNode>> output = new BufferedQueue<>(span);

        for (int columnIndex = 0; columnIndex < span; columnIndex++) {
            addLeftHalf(output, columnIndex);

            if (columnIndex != 0)
                addRightHalf(output, columnIndex, sideLength);
        }

        output.stream()
                .flatMap(BufferedQueue::stream)
                .forEach(node -> {
                    HexShape newShape = rotateShape(node.getHexShape());
                    EnumSet<HexWall> newWalls = rotateWalls(node.getWalls());
                    node.setHexShape(newShape);
                    node.setWalls(newWalls);
                });
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

    private HexShape rotateShape(HexShape currentShape) {
        if (currentShape == null || currentShape == CIRCLE || currentShape == HEXAGON)
            return currentShape;

        return switch (currentShape) {
            case UP_TRIANGLE -> DOWN_TRIANGLE;
            case DOWN_TRIANGLE -> UP_TRIANGLE;
            case LEFT_TRIANGLE -> RIGHT_TRIANGLE;
            default -> LEFT_TRIANGLE;
        };
    }

    private EnumSet<HexWall> rotateWalls(EnumSet<HexWall> walls) {
        if (walls.isEmpty()) return walls;

        return walls.stream()
                .map(this::rotateSingleWall)
                .collect(Collectors.toCollection(() -> EnumSet.noneOf(HexWall.class)));
    }

    private HexWall rotateSingleWall(HexWall wall) {
        return switch (wall) {
            case TOP_LEFT -> TOP;
            case TOP -> TOP_RIGHT;
            case TOP_RIGHT -> BOTTOM_RIGHT;
            case BOTTOM_RIGHT -> BOTTOM;
            case BOTTOM -> BOTTOM_LEFT;
            default -> TOP_LEFT;
        };
    }

    public HexNode findAtCoordinate(Coordinates coordinates) {
        int x = coordinates.getX();
        if (x < 0 || x >= getSpan()) return null;

        BufferedQueue<HexNode> column = hexagon.get(x);
        int y = coordinates.getY();
        return y < 0 || y >= column.getCapacity() ? null : column.get(y);
    }

    public List<HexNode> asList() {
        return hexagon.stream()
                .flatMap(BufferedQueue::stream)
                .collect(toList());
    }

    public void readInNodeList(List<HexNode> stream) {
        hexagon = generateFromList(stream);
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
}
