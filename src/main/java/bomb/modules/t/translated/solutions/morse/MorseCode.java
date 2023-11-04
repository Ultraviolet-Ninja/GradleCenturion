package bomb.modules.t.translated.solutions.morse;

import bomb.modules.t.translated.LanguageCSVReader;
import bomb.tools.filter.Regex;
import bomb.tools.filter.RegexFilter;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("ConstantConditions")
public final class MorseCode {
    private static final String FILENAME = "morseCode.csv";
    private static final Map<String, String> MORSE_CIPHER_MAP;
    private static final Map<String, Double> FREQUENCY_MAP;

    static {
        MORSE_CIPHER_MAP = new HashMap<>();
        FREQUENCY_MAP = new HashMap<>();
        try {
            loadMorseCode();
        } catch (CsvValidationException | IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private static void loadMorseCode() throws CsvValidationException, IOException {
        InputStream in = LanguageCSVReader.class.getResourceAsStream(FILENAME);
        CSVReader csvReader = new CSVReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        String[] dataRow;
        while ((dataRow = csvReader.readNext()) != null) {
            MORSE_CIPHER_MAP.put(dataRow[0], dataRow[1]);
        }
        csvReader.close();
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
