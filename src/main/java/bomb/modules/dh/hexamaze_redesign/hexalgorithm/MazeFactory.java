package bomb.modules.dh.hexamaze_redesign.hexalgorithm;

import bomb.modules.dh.hexamaze.hexalgorithm.Maze;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static bomb.modules.dh.hexamaze_redesign.hexalgorithm.HexNode.HexShape.CIRCLE;
import static bomb.modules.dh.hexamaze_redesign.hexalgorithm.HexNode.HexShape.DOWN_TRIANGLE;
import static bomb.modules.dh.hexamaze_redesign.hexalgorithm.HexNode.HexShape.HEXAGON;
import static bomb.modules.dh.hexamaze_redesign.hexalgorithm.HexNode.HexShape.LEFT_TRIANGLE;
import static bomb.modules.dh.hexamaze_redesign.hexalgorithm.HexNode.HexShape.RIGHT_TRIANGLE;
import static bomb.modules.dh.hexamaze_redesign.hexalgorithm.HexNode.HexShape.UP_TRIANGLE;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

public class MazeFactory {
    public static List<HexNode> createMaze() throws IOException, CsvException {
        InputStream in = Maze.class.getResourceAsStream("maze.csv");
        CSVReader csvReader = new CSVReader(new InputStreamReader(Objects.requireNonNull(in)));
        return csvReader.readAll().stream()
                .flatMap(Arrays::stream)
                .map(line -> line.split(" "))
                .map(data -> new HexNode(decodeShape(data[1]), decodeWalls(data[0])))
                .collect(toList());
    }

    public static HexNode.HexShape decodeShape(String code) {
        return switch(code) {
            case "c" -> CIRCLE;
            case "h" -> HEXAGON;
            case "lt" -> LEFT_TRIANGLE;
            case "rt" -> RIGHT_TRIANGLE;
            case "ut" -> UP_TRIANGLE;
            case "dt" -> DOWN_TRIANGLE;
            default -> null;
        };
    }

    public static EnumSet<HexNode.HexWall> decodeWalls(String code) {
        HexNode.HexWall[] allWalls = HexNode.HexWall.values();

        return stream(code.split(""))
                .mapToInt(Integer::parseInt)
                .mapToObj(num -> allWalls[num])
                .collect(Collectors.toCollection(() -> EnumSet.noneOf(HexNode.HexWall.class)));
    }
}
