package bomb.modules.c;

import bomb.enumerations.CFlash;
import bomb.Widget;

//TODO - Complete, then make Javadocs

public class ColorFlash extends Widget {
    static class Combo {
        private final CFlash.Word word;
        private final CFlash.Color color;

        Combo(CFlash.Word word, CFlash.Color color) {
            this.color = color;
            this.word = word;
        }

        public CFlash.Word getWord() {
            return word;
        }

        public CFlash.Color getColor() {
            return color;
        }
    }

//    public static String decypher(CFlash.Word[] words, CFlash.Color[] colors) {
//        Combo[] combos = forgeCombos(words, colors);
//        String output;
//
//       switch (words[words.length - 1]) {
//            case RED -> {
//                if (iterateWord(combos, CFlash.Word.GREEN) >= 3)
//                    return "Press Yes on the 3rd time Green is used as either the word or color";
//                else if (iterateColor(combos, CFlash.Color.BLUE) == 1)
//                   return "Press No when the word is Magenta";
//                return "Press Yes on the last time White is a word or color";
//            }
//            case YELLOW -> {
//                if (matches(combos, CFlash.Color.GREEN, CFlash.Word.BLUE))
//                    return  "Press Yes on the first Green Color";
//                else if (matches(combos, CFlash.Color.WHITE, CFlash.Word.WHITE) ||
//                        matches(combos, CFlash.Color.RED, CFlash.Word.WHITE))
//                    return  "";
//                return "";
//            }
//            case GREEN -> {
//                if ()
//                    return "";
//                else if (iterateWord(combos, CFlash.Word.MAGENTA) >= 3)
//                    return "";
//                return "";
//            }
//            case BLUE -> {
//
//            }
//            case MAGENTA -> {
//
//            }
//            default -> {
//
//            }
//        }
//        return output;
//    }

    private static Combo[] forgeCombos(CFlash.Word[] words, CFlash.Color[] colors){
        Combo[] combos = new Combo[8];
        for (int i = 0; i < 8; i++){
            combos[i] = new Combo(words[i], colors[i]);
        }
        return combos;
    }

    private static int iterateWord(Combo[] combos, CFlash.Word word){
        int found = 0;
        for (Combo combo : combos){
            if (combo.getWord() == word){
                found++;
            }
        }
        return found;
    }

    private static int iterateColor(Combo[] combos, CFlash.Color color){
        int found = 0;
        for (Combo combo : combos){
            if (combo.getColor() == color){
                found++;
            }
        }
        return found;
    }

    private static boolean matches(Combo[] combos, CFlash.Color color, CFlash.Word word){
        for (Combo combo : combos){
            if (combo.getWord() == word && combo.getColor() == color){
                return true;
            }
        }
        return false;
    }
}