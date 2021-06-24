package bomb;

import bomb.modules.dh.hexamaze.hexalgorithm.HexagonDataStructure;
import bomb.modules.dh.hexamaze.hexalgorithm.maze_finding.OldHexComparator;
import bomb.modules.dh.hexamaze.hexalgorithm.HexGrid;
import bomb.modules.dh.hexamaze.hexalgorithm.Maze;
import bomb.modules.dh.hexamaze.hexalgorithm.maze_finding.HexComparator;
import bomb.tools.Regex;

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

//        try{
//            HexGrid bestCase = fromLine("n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,c,n,n,n,n");
//            Maze maze = new Maze();
//            HexHashLibrary.initialize(maze, bestCase);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        for (String body : new String[]{"12T4h65is5 %i34s2 a s(5en34t6e4nce.", "53212323u6434123", "42^&35é"}) {
//            long mechanicStart = System.nanoTime();
//            System.out.println("Mechanic result: " + Mechanics.ultimateFilter(body, Mechanics.LOWERCASE_REGEX));
//            long mechanicEnd = System.nanoTime();
//
//            long filterStart = System.nanoTime();
//            CHAR_FILTER.loadText(body);
//            System.out.println("Filter result: " + CHAR_FILTER.toNewString());
//            long filterEnd = System.nanoTime();
//
//            System.out.println("Mechanic Time: " + format.format(mechanicEnd - mechanicStart));
//            System.out.println("Filter Time: " + format.format(filterEnd - filterStart));
//        }


//        Screams[] colorOrder = {RED, PURPLE, YELLOW, GREEN, BLUE, ORANGE};
//
//        Screams[] flashOrder = {RED, PURPLE, RED};
//        Star newStar = new Star(colorOrder);
//
//        System.out.println(newStar.primaryRule(flashOrder));
//        Maze maze;
//        try{
//            maze = new Maze();
//            HexGrid bestCase = fromLine("n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,c,n,n,n,n");
//            HexGrid full = HexComparator.evaluate(maze, bestCase);
//            MazeRunner.getPegInformation(Color.CYAN, 23, full.sideLength());
//            System.out.println(MazeRunner.runMaze(full));
//        } catch (IOException e){
//            e.printStackTrace();
//        }
        Regex regex = new Regex("(s)", "Astrology");
        System.out.println(regex.hasMatch());
    }

    private static HexGrid fromLine(String line){
        ArrayList<HexagonDataStructure.HexNode> list = new ArrayList<>();
        for (String shape : line.split(","))
            list.add(new HexagonDataStructure.HexNode(HexagonDataStructure.decodeShape(shape), null));
        return new HexGrid(new HexagonDataStructure(list));
    }

    private static void testComparators(Maze fullMaze, HexGrid testGrid){
        long linearStart = System.nanoTime();
        OldHexComparator.evaluate(fullMaze, testGrid);
        long linearStop = System.nanoTime();

        long threadedStart = System.nanoTime();
        HexComparator.findSubsection(fullMaze, testGrid);
        long threadedStop = System.nanoTime();

        System.out.println("Linear Time: " + format.format(linearStop - linearStart));
        System.out.println("Threaded time: " + format.format(threadedStop - threadedStart));
    }
}