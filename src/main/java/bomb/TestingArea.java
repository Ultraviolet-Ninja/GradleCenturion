package bomb;

import bomb.modules.c.chess.Chess;
import bomb.tools.filter.Regex;
import bomb.tools.filter.RegexFilter;
import bomb.tools.filter.StreamFilter;

import java.io.IOException;
import java.text.DecimalFormat;
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


        String plainText = "asjndwiaivkjwoajs2i49u3ijasif24iejrkjdsi3ji3j4efsdf3iu534r4o6r";
        long streamStart = System.nanoTime();
        String streamResults = StreamFilter.ultimateFilter(plainText, StreamFilter.NUMBER_REGEX);
        long streamStop = System.nanoTime();

        long regexStart = System.nanoTime();
        String regexResults = RegexFilter.ultimateFilter(plainText, RegexFilter.NUMBER_PATTERN);
        long regexStop = System.nanoTime();

        System.out.printf("Stream Filter: %,d%n", streamStop - streamStart);
        System.out.printf("Regex Filter: %,d%n", regexStop - regexStart);
        System.out.println(regexResults.equals(streamResults));
    }
}