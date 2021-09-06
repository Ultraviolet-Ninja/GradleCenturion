package bomb;

import bomb.modules.c.colored_switches.ColoredSwitches;
import bomb.modules.dh.hexamaze.hexalgorithm.HexGrid;
import bomb.modules.dh.hexamaze.hexalgorithm.HexagonDataStructure;
import bomb.tools.filter.Regex;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class TestingArea {
    public static DecimalFormat format = new DecimalFormat("###,###,###,###");

    public static void main(String[] args) throws IOException {
        Regex labelFilter = new Regex("\"([^\"]*)\",?");
        Regex frequencyFilter = new Regex("frequencies\\.put\\(\"([^\"]+)\", (\\d\\.\\d{1,3})\\);");
        Regex whoMapFilter = new Regex("stepTwoMap\\.put\\(\"([^\"]+)\", \"([^\"]+)\"\\);");

        System.out.println(ColoredSwitches.producePreemptiveMoveList((byte) 31));
    }

    private static HexGrid fromLine(String line){
        ArrayList<HexagonDataStructure.HexNode> list = new ArrayList<>();
        for (String shape : line.split(","))
            list.add(new HexagonDataStructure.HexNode(HexagonDataStructure.decodeShape(shape), null));
        return new HexGrid(new HexagonDataStructure(list));
    }

//    private static void testComparators(Maze fullMaze, HexGrid testGrid){
//        long linearStart = System.nanoTime();
//        OldHexComparator.evaluate(fullMaze, testGrid);
//        long linearStop = System.nanoTime();
//
//        long threadedStart = System.nanoTime();
//        HexComparator.findSubsection(fullMaze, testGrid);
//        long threadedStop = System.nanoTime();
//
//        long hashStart = System.nanoTime();
//        HexHashLibrary.find(testGrid);
//        long hashStop = System.nanoTime();
//
//        System.out.println("Old Linear Time: " + format.format(linearStop - linearStart));
//        System.out.println("New Linear time: " + format.format(threadedStop - threadedStart));
//        System.out.println("Hash time: " + format.format(hashStop - hashStart));
//    }
}