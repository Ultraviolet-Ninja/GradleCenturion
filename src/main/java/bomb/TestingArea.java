package bomb;

import bomb.modules.c.chords.ChordQualities;
import bomb.modules.dh.hexamaze.hexalgorithm.HexGrid;
import bomb.modules.dh.hexamaze.hexalgorithm.HexagonDataStructure;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestingArea {
    public static DecimalFormat format = new DecimalFormat("###,###,###,###");

    public static void main(String[] args) {
        Pattern patt = Pattern.compile("([A])");
        Matcher matcher = patt.matcher("");
        matcher.reset("(AB)C");
        matcher.find();
        System.out.println(matcher.group(1));
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