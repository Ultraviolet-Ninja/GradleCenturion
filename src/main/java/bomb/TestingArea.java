package bomb;

import bomb.modules.c.chess.Chess;
import bomb.modules.dh.hexamaze.hexalgorithm.HexGrid;
import bomb.modules.dh.hexamaze.hexalgorithm.HexagonDataStructure;
import bomb.tools.filter.Regex;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
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

        final int capacity = 10000;

        String[] arr = new String[capacity];
        ArrayList<String> arrayList = new ArrayList<>(capacity);
        LinkedList<String> links = new LinkedList<>();

        for (int i = 0; i < capacity; i++) {
            String temp = String.valueOf(i);
            arr[i] = temp;
            arrayList.add(temp);
            links.add(temp);
        }

        long arrayIndexStart = System.nanoTime();
        for (int i = 0; i < capacity; i++) {
            System.out.print(arr[i]);
        }
        long arrayIndexStop = System.nanoTime();


        long arrayIterateStart = System.nanoTime();
        for (String num : arr) {
            System.out.print(num);
        }
        long arrayIterateStop = System.nanoTime();

        long arrayListIndexStart = System.nanoTime();
        for (int i = 0; i < capacity; i++) {
            System.out.print(arrayList.get(i));
        }
        long arrayListIndexStop = System.nanoTime();

        int index = 0;
        long arrayListIterateStart = System.nanoTime();
        for (String num : arrayList){
            System.out.print(num);
            index++;
        }
        long arrayListIterateStop = System.nanoTime();

        long linkedListIterateStart = System.nanoTime();
        for (String num : links){
            System.out.print(num);

        }
        long linkedListIterateStop = System.nanoTime();
        System.out.println();

        System.out.println("Array Index: " + (arrayIndexStop - arrayIndexStart));
        System.out.println("Array Iterate: " + (arrayIterateStop - arrayIterateStart));
        System.out.println("ArrayList Index: " + (arrayListIndexStop - arrayListIndexStart));
        System.out.println("ArrayList Iterate: " + (arrayListIterateStop - arrayListIterateStart));
        System.out.println("LinkedList Iterate: " + (linkedListIterateStop - linkedListIterateStart));
    }

    private static HexGrid fromLine(String line){
        ArrayList<HexagonDataStructure.HexNode> list = new ArrayList<>();
        for (String shape : line.split(","))
            list.add(new HexagonDataStructure.HexNode(HexagonDataStructure.decodeShape(shape), null));
        return new HexGrid(new HexagonDataStructure(list));
    }
}