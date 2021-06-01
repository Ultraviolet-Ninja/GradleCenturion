package bomb;

import bomb.modules.dh.hexamaze.HexTraits;
import bomb.modules.dh.hexamaze.hexalgorithm.Hex;
import bomb.modules.dh.hexamaze.hexalgorithm.HexComparator;
import bomb.modules.dh.hexamaze.hexalgorithm.HexGrid;
import bomb.modules.dh.hexamaze.hexalgorithm.HexHashLibrary;
import bomb.modules.dh.hexamaze.hexalgorithm.Maze;
import bomb.modules.dh.hexamaze.hexalgorithm.ThreadedHexComparator;
import bomb.modules.np.neutralization.Chemical;
import bomb.tools.Base91;
import bomb.tools.Filter;
import bomb.tools.Mechanics;
import bomb.tools.Regex;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class TestingArea {
    public static DecimalFormat format = new DecimalFormat("###,###,###,###");

    public static void main(String[] args) {
//        Maze maze;
//        HexGrid bestCase = fromLine("n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,c,n,n,n,n");
//        HexGrid worstCase = fromLine("n,n,n,rt,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n");
//        HexGrid nullCase = fromLine("n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n");
//        try{
//            maze = new Maze();
//            testComparators(maze, bestCase);
//            testComparators(maze, worstCase);
//            testComparators(maze, nullCase);
//        } catch (IOException e){
//            e.printStackTrace();
//        }

//        try{
//            HexGrid bestCase = fromLine("n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,c,n,n,n,n");
//            Maze maze = new Maze();
//            HexHashLibrary.initialize(maze, bestCase);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        filterComparison("12T4h65is5 %i34s2 a s(5en34t6e4nce.");
        filterComparison("53212323u6434123");
        filterComparison("42^&35é");
    }

    private static HexGrid fromLine(String line){
        ArrayList<Hex.HexNode> list = new ArrayList<>();
        for (String shape : line.split(","))
            list.add(new Hex.HexNode(Hex.decodeShape(shape), null));
        return new HexGrid(new Hex(list));
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

    private static void filterComparison(String body){
        long mechanicStart = System.nanoTime();
        System.out.println("Mechanic result: " + Mechanics.ultimateFilter(body, Mechanics.LOWERCASE_REGEX));
        long mechanicEnd = System.nanoTime();

        long filterStart = System.nanoTime();
        Regex r = new Regex(Filter.CHAR_REGEX, body, Pattern.CASE_INSENSITIVE);
        System.out.println("Filter result: " + r.toNewString());
        long filterEnd = System.nanoTime();

        System.out.println("Mechanic Time: " + format.format(mechanicEnd -  mechanicStart));
        System.out.println("Filter Time: " + format.format(filterEnd - filterStart));
    }
}