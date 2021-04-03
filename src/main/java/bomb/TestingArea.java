package bomb;

import bomb.modules.dh.hexamaze.hexalgorithm.Hex;
import bomb.modules.dh.hexamaze.hexalgorithm.HexComparator;
import bomb.modules.dh.hexamaze.hexalgorithm.HexGrid;
import bomb.modules.dh.hexamaze.hexalgorithm.Maze;
import bomb.modules.dh.hexamaze.hexalgorithm.ThreadedHexComparator;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class TestingArea {
    public static DecimalFormat format = new DecimalFormat("###,###,###,###");

    public static void main(String[] args) {
        Maze maze;
        HexGrid bestCase = fromLine("n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,c,n,n,n,n");
        HexGrid worstCase = fromLine("n,n,n,rt,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n");
        HexGrid nullCase = fromLine("n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n");
        try{
            maze = new Maze();
            testComparators(maze, bestCase);
            testComparators(maze, worstCase);
            testComparators(maze, nullCase);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private static HexGrid fromLine(String line){
        ArrayList<Hex.HexNode> list = new ArrayList<>();
        for (String shape : line.split(","))
            list.add(new Hex.HexNode(Hex.decodeShape(shape), null));
        return new HexGrid(new Hex(list), 0);
    }

    private static void testComparators(Maze fullMaze, HexGrid testGrid){
        long linearStart = System.nanoTime();
        HexComparator.evaluate(fullMaze, testGrid);
        long linearStop = System.nanoTime();

        long threadedStart = System.nanoTime();
        ThreadedHexComparator.evaluate(fullMaze, testGrid);
        long threadedStop = System.nanoTime();

        System.out.println("Linear Time: " + format.format(linearStop - linearStart));
        System.out.println("Threaded time: " + format.format(threadedStop - threadedStart));
    }
}