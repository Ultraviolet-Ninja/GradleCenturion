package bomb;

import bomb.tools.filter.Regex;

import java.io.IOException;
import java.text.DecimalFormat;

public class TestingArea {
    public static DecimalFormat format = new DecimalFormat("###,###,###,###");

    public static void main(String[] args) throws IOException {
        Regex labelFilter = new Regex("\"([^\"]*)\",?");
        Regex frequencyFilter = new Regex("frequencies\\.put\\(\"([^\"]+)\", (\\d\\.\\d{1,3})\\);");
        Regex whoMapFilter = new Regex("stepTwoMap\\.put\\(\"([^\"]+)\", \"([^\"]+)\"\\);");

//        Widget.setSerialCode("e60xa6");
//        String[] moveArray = {"c5", "D5", "a-1", "F-3", "d1", "e4"};
//        List<String> moves = Arrays.asList(moveArray);
//        System.out.println(Chess.solve(moves));
    }
}