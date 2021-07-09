package bomb.modules.dh.fast_math;

import bomb.Widget;
import bomb.enumerations.Indicator;
import bomb.enumerations.Port;
import bomb.tools.Regex;

import static bomb.tools.Filter.ultimateFilter;

public class FastMath extends Widget {
    private static final int[][] INTERNAL_GRID = new int[][]{
            {25,11,53,97,2,42,51,97,12,86,55,73,33}, {54,7,32,19,84,33,27,78,26,46,9,13,58},
            {86,37,44,1,5,26,93,49,18,69,23,40,22}, {54,28,77,93,11,0,35,61,27,48,13,72,80},
            {99,36,23,95,67,5,26,17,44,60,26,41,67}, {74,95,3,4,56,23,54,29,52,38,10,76,98},
            {88,46,37,96,2,52,81,37,12,70,14,36,78}, {54,43,12,65,94,3,47,23,16,62,73,46,21},
            {7,33,26,1,67,26,27,77,83,14,27,93,9}, {63,64,94,27,48,84,33,10,16,74,43,99,4},
            {35,39,3,25,47,62,38,45,88,48,34,31,27}, {67,30,27,71,9,11,44,37,18,40,32,15,78},
            {13,23,26,85,92,12,73,56,81,7,75,47,99}
    };

    public static String solve(String letters){
        if (letters == null || letters.length() != 2)
            throw new IllegalArgumentException("Input 2 letters, please");
        serialCodeChecker();
        int preconditions = edgework();
        int leftNum = translateLetter(letters.substring(0,1));
        int rightNum = translateLetter(letters.substring(1));
        return String.valueOf(postConditions(INTERNAL_GRID[leftNum][rightNum] + preconditions));
    }

    private static int translateLetter(String letter) throws IllegalArgumentException{
        switch (letter.toUpperCase()){
            case "A": return 0;
            case "B": return 1;
            case "C": return 2;
            case "D": return 3;
            case "E": return 4;
            case "G": return 5;
            case "K": return 6;
            case "N": return 7;
            case "P": return 8;
            case "S": return 9;
            case "T": return 10;
            case "X": return 11;
            case "Z": return 12;
            default:
                throw new IllegalArgumentException("Illegal letter given");
        }
    }

    private static int edgework(){
        int output = hasLitIndicator(Indicator.MSA) ? 20 : 0; //If the bomb has a lit MSA indicator
        output += portExists(Port.SERIAL) ? 14 : 0; //If the bomb has a Serial Port
        //If the serial number has the letters F A S T
        output -= !ultimateFilter(serialCode, new Regex("[fast]")).isEmpty() ? 5 : 0;
        output += portExists(Port.RJ45) ? 27 : 0; //If the bomb has an RJ-45 Port
        output -= getAllBatteries() > 3 ? 15 : 0; //If the bomb has more than 3 batteries
        return output;
    }

    private static int postConditions(int num){
        if (num > 100) return num % 100;
        if (num < 0) return num + 50;
        return num;
    }
}
