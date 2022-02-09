package bomb.modules.wz.word_search;

import bomb.Widget;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import static java.util.stream.Collectors.toUnmodifiableMap;

@SuppressWarnings("ConstantConditions")
public class WordSearch extends Widget {
    private static final int ARRAY_SIZE;
    private static final String FILENAME;

    static {
        ARRAY_SIZE = 4;
        FILENAME = "words.txt";
    }

    public static Set<String> findPossibleWords(char[] letters) throws IllegalArgumentException {
        checkSerialCode();
        validate(letters);

        reverse(letters);
        Set<String> possibleWords = new TreeSet<>();
        int makeOdd = getSerialCodeLastDigit() % 2;
        Map<Character, List<String>> wordMap = createWordMap();

        for (int i = 0; i < ARRAY_SIZE; i++) {
            int index = (2 * i) + makeOdd;
            possibleWords.add(wordMap.get(letters[i]).get(index));
        }

        return possibleWords;
    }


    private static void reverse(char[] letters) {
        char temp;
        int size = letters.length;
        int halfSize = size / 2;
        for (int i = 0; i < halfSize; i++) {
            temp = letters[i];
            letters[i] = letters[size - i - 1];
            letters[size - i - 1] = temp;
        }
    }

    private static void validate(char[] letters) throws IllegalArgumentException {
        if (letters == null || letters.length != ARRAY_SIZE)
            throw new IllegalArgumentException("The input does not contain 4 letters");

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
                .collect(toUnmodifiableMap(
                        line -> line.charAt(0),
                        line -> List.of(line.substring(2).split(" "))
                ));
    }
}
