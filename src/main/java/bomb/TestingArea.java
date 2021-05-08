package bomb;

import bomb.modules.dh.hexamaze.hexalgorithm.Hex;
import bomb.modules.dh.hexamaze.hexalgorithm.HexComparator;
import bomb.modules.dh.hexamaze.hexalgorithm.HexGrid;
import bomb.modules.dh.hexamaze.hexalgorithm.HexHashLibrary;
import bomb.modules.dh.hexamaze.hexalgorithm.Maze;
import bomb.modules.dh.hexamaze.hexalgorithm.ThreadedHexComparator;

import java.io.IOException;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Base64;

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
//        } catch (IOException e){
//            e.printStackTrace();
//        }

        for (int i = 0; i< 1000; i++){
            byte[] arr = BigInteger.valueOf(i).toByteArray();
            String encoded = Base64.getEncoder().encodeToString(arr);
            System.out.println(Character.digit((char)i, 64));
        }
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
}