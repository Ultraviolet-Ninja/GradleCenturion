package bomb.modules.wz.word_search;

import bomb.Widget;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toMap;

@SuppressWarnings("ConstantConditions")
public class WordSearch extends Widget {
    private static final String FILENAME = "words.txt";

    public static Set<String> findPossibleWords(char[] letters) throws IllegalArgumentException {
        checkSerialCode();
        validate(letters);

        Set<String> possibleWords = new HashSet<>();
        int makeOdd = getSerialCodeLastDigit() % 2;
        Map<Character, List<String>> wordMap = createWordMap();

        for (int i = 0; i < 4; i++) {
            int index = (2 * i) + makeOdd;
            possibleWords.add(wordMap.get(letters[i]).get(index));
        }

        return possibleWords;
    }

    private static void validate(char[] letters) throws IllegalArgumentException {
        if (letters == null || letters.length != 4) {
            throw new IllegalArgumentException("The input does not contain 4 letters");
        }
        for (int i = 0; i < letters.length; i++) {
            char letter = letters[i];
            if (!Character.isLetter(letter))
                throw new IllegalArgumentException("Input contains a non-letter character");
            if (Character.isLowerCase(letter))
                letters[i] = Character.toUpperCase(letter);
        }
    }

    private static Map<Character, List<String>> createWordMap() {
        InputStream in = WordSearch.class.getResourceAsStream(FILENAME);
        return new BufferedReader(new InputStreamReader(in))
                .lines()
                .collect(toMap(
                        line -> line.charAt(0),
                        line -> List.of(line.substring(2).split(" "))
                ));
    }
}
