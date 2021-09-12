package bomb;

import bomb.modules.c.chess.Chess;
import bomb.modules.c.colored_switches.ColoredSwitches;
import bomb.modules.c.colored_switches.SwitchColor;
import bomb.modules.dh.hexamaze.hexalgorithm.HexGrid;
import bomb.modules.dh.hexamaze.hexalgorithm.HexagonDataStructure;
import bomb.tools.filter.Regex;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestingArea {
    public static DecimalFormat format = new DecimalFormat("###,###,###,###");

    public static void main(String[] args) throws IOException {
        Regex labelFilter = new Regex("\"([^\"]*)\",?");
        Regex frequencyFilter = new Regex("frequencies\\.put\\(\"([^\"]+)\", (\\d\\.\\d{1,3})\\);");
        Regex whoMapFilter = new Regex("stepTwoMap\\.put\\(\"([^\"]+)\", \"([^\"]+)\"\\);");

        Widget.setSerialCode("e60xa6");
        String[] moveArray = {"c5", "D5", "a-1", "F-3", "d1", "e4"};
        List<String> moves = Arrays.asList(moveArray);
        System.out.println(Chess.solve(moves));
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