package bomb.modules.t.translated.solutions.morse;

import bomb.modules.t.translated.LanguageCSVReader;
import bomb.tools.filter.Regex;
import bomb.tools.filter.RegexFilter;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings("ConstantConditions")
public final class MorseCode {
    private static final String FILENAME = "morseCode.csv";
    private static final Map<String, String> MORSE_CIPHER_MAP;
    private static final Map<String, Double> FREQUENCY_MAP;

    static {
        try {
            MORSE_CIPHER_MAP = loadMorseCode();
        } catch (CsvException | IOException e) {
            throw new IllegalStateException(e);
        }
        FREQUENCY_MAP = new HashMap<>();
    }

    private static Map<String, String> loadMorseCode() throws CsvException, IOException {
        try(var in = LanguageCSVReader.class.getResourceAsStream(FILENAME);
            var csvReader = new CSVReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
            return csvReader.readAll()
                    .stream()
                    .collect(Collectors.toUnmodifiableMap(
                            row -> row[0],
                            row -> row[1]
                    ));
        }
    }

    public static void setFrequencyMap(String frequencies) {
        FREQUENCY_MAP.clear();
        Regex frequencyReader = new Regex("\\[(\\D+) (3\\.[0-9]{1,3})]");

        for (String frequencyInstance : frequencies.split("\\|")) {
            frequencyReader.loadText(frequencyInstance);
            frequencyReader.hasMatch();
            String word = frequencyReader.captureGroup(1);
            double frequency = Double.parseDouble(frequencyReader.captureGroup(2));
            FREQUENCY_MAP.put(word, frequency);
        }
    }

    public static void setUniqueCharacters(String uniqueCharacters) {

    }

    public static String translate(String text) throws IllegalArgumentException, IllegalStateException {
        validateInput(text);
        if (FREQUENCY_MAP.isEmpty())
            throw new IllegalStateException("Internal frequency map is empty");

        var stringBuilder = new StringBuilder();
        for (String morseCodePhrase : text.split(" ")) {
            String letterSet = MORSE_CIPHER_MAP.get(morseCodePhrase);
            calculateNextCorrectLetter(stringBuilder, letterSet);
        }

        return null;
    }

    private static void validateInput(String text) {
        RegexFilter.MORSE_CODE_PATTERN.loadText(text);
        if (!RegexFilter.MORSE_CODE_PATTERN.matchesRegex()){
            throw new IllegalArgumentException("Only periods and hyphens are allowed as input, separated by spaces");
        }
    }

    private static void calculateNextCorrectLetter(StringBuilder sb, String letterSet) {
        if (!letterSet.contains("_")) {
            sb.append(letterSet);
            return;
        }

        String[] possibleLetters = letterSet.toLowerCase().split("_");

    }
}
