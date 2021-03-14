package bomb.modules.dh.hexamaze.hexalgorithm;

import bomb.tools.data.structures.FixedArrayQueue;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Scanner;

import static bomb.modules.dh.hexamaze.hexalgorithm.Hex.decodeShape;
import static bomb.modules.dh.hexamaze.hexalgorithm.Hex.decodeWalls;

/**
 * This class represents the entire maze that the expert sees on the manual page.The class takes in a file
 * that has contains data pertaining to the walls and shape for each HexNode.
 */
public class Maze {
    private final Hex hexamaze;

    /**
     * Initializes a Hex object with a side length of 12, representing the hexamaze on the manual, then
     * decodes the file to add the walls and shapes to HexNodes that'll get streamed into the maze
     *
     * @throws IOException If the file is not found at the designated source
     */ 
    public Maze() throws IOException {
        hexamaze = new Hex(12);
        try {
            hexamaze.injectList(decodeDoc());
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
    private ArrayList<Hex.HexNode> decodeDoc() throws IOException, URISyntaxException {
        ArrayList<Hex.HexNode> nodes = new ArrayList<>();
        Scanner docScan = new Scanner(new File(getClass().getResource("HexMaze.txt").toURI()).toPath());
        while (docScan.hasNextLine()) {
            String[] elements = docScan.nextLine().split(" ");
            nodes.add(new Hex.HexNode(decodeShape(elements[1]), decodeWalls(elements[0])));
        }
        return nodes;
    }

    /**
     * Exports the Hex object
     *
     * @return The hexamaze
     */
    public Hex hexport(){
        return hexamaze;
    }

    /**
     * Exports the list of lists representing the hexagon
     *
     * @return The Hex.export() method
     */
    public FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> exportTo2DQueue(){
        return hexamaze.exportTo2DQueue();
    }
}
