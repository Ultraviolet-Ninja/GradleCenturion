package bomb.modules.dh.hexamaze.hexalgorithm;

import bomb.modules.dh.hexamaze.hexalgorithm.HexNodeProperties.HexShape;
import bomb.modules.dh.hexamaze.hexalgorithm.HexNodeProperties.HexWall;
import bomb.modules.dh.hexamaze.hexalgorithm.HexagonDataStructure.HexNode;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import static bomb.modules.dh.hexamaze.hexalgorithm.HexagonDataStructure.decodeShape;
import static bomb.modules.dh.hexamaze.hexalgorithm.HexagonDataStructure.decodeWalls;

/**
 * This class represents the entire maze that the expert sees on the manual page.The class takes in a file
 * that has contains data pertaining to the walls and shape for each HexNode.
 */
@SuppressWarnings("ConstantConditions")
public class Maze extends AbstractHexagon {
    private static final byte WALLS_INDEX = 0, SHAPE_INDEX = 1;
    private static final byte FULL_MAZE_SIDE_LENGTH = 12;

    /**
     * Initializes a Hex object with a side length of 12, representing the hexamaze on the manual, then
     * decodes the file to add the walls and shapes to HexNodes that'll get streamed into the maze
     */
    public Maze() {
        super(new HexagonDataStructure(FULL_MAZE_SIDE_LENGTH));

        try {
            hexagon.readInNodeList(decodeCsvFile());
        } catch (CsvValidationException | IOException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    /**
     * Scans through the document to create new Shapes and wall arrays to be put into new HexNodes
     *
     * @return A decoded List of HexNodes to be streamed into the maze
     */
    private List<HexNode> decodeCsvFile() throws CsvValidationException, IOException {
        List<HexNode> nodes = new ArrayList<>();

        InputStream in = Maze.class.getResourceAsStream("maze.csv");
        CSVReader csvReader = new CSVReader(new InputStreamReader(in));
        String[] rowOfNodes;
        while ((rowOfNodes = csvReader.readNext()) != null) {
            for (String node : rowOfNodes) {
                String[] nodeInfo = node.split(" ");
                EnumSet<HexWall> decodedWalls = decodeWalls(nodeInfo[WALLS_INDEX]);
                HexShape decodedShape = decodeShape(nodeInfo[SHAPE_INDEX]);
                nodes.add(new HexNode(decodedShape, decodedWalls));
            }
        }

        csvReader.close();
        return nodes;
    }
}
