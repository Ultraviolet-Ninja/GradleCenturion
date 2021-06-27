package bomb.modules.dh.hexamaze.hexalgorithm;

import bomb.modules.dh.hexamaze.hexalgorithm.HexagonDataStructure.HexNode;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import static bomb.modules.dh.hexamaze.hexalgorithm.HexagonDataStructure.decodeShape;
import static bomb.modules.dh.hexamaze.hexalgorithm.HexagonDataStructure.decodeWalls;

/**
 * This class represents the entire maze that the expert sees on the manual page.The class takes in a file
 * that has contains data pertaining to the walls and shape for each HexNode.
 */
public class Maze extends AbstractHexagon{
    /**
     * Initializes a Hex object with a side length of 12, representing the hexamaze on the manual, then
     * decodes the file to add the walls and shapes to HexNodes that'll get streamed into the maze
     *
     * @throws IOException If the file is not found at the designated source
     */ 
    public Maze() throws IOException {
        super(new HexagonDataStructure(12));
        try {
            hexagon.readInNodeList(decodeDoc());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * Scans through the document to create new Shapes and wall arrays to be put into new HexNodes
     *
     * @return A decoded ArrayList of HexNodes to be streamed into the maze
     * @throws IOException If the file is not found at the designated source
     */
    private ArrayList<HexNode> decodeDoc() throws IOException, URISyntaxException {
        ArrayList<HexNode> nodes = new ArrayList<>();
        Scanner docScan = new Scanner(new File(Objects.requireNonNull(
                getClass().getResource("HexMaze.txt")).toURI()).toPath());
        while (docScan.hasNextLine()) {
            String[] elements = docScan.nextLine().split(" ");
            nodes.add(new HexNode(decodeShape(elements[1]), decodeWalls(elements[0])));
        }
        return nodes;
    }
}
