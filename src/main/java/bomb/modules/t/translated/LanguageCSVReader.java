package bomb.modules.t.translated;

import bomb.abstractions.Index;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class LanguageCSVReader {
    public static List<String> getLanguageContent(LanguageColumn languageColumn)
            throws CsvException, IOException, IllegalArgumentException {
        if (languageColumn == null) throw new IllegalArgumentException("Language is null");

        int columnIndex = languageColumn.getIndex();
        InputStream in = LanguageCSVReader.class.getResourceAsStream("dictionary.csv");
        CSVReader csvReader = new CSVReader(new InputStreamReader(Objects.requireNonNull(in), StandardCharsets.UTF_8));
        List<String> dictionaryContent = csvReader.readAll()
                .stream()
                .map(array -> array[columnIndex])
                .collect(Collectors.toList());

        csvReader.close();
        return dictionaryContent;
    }

    public enum LanguageRow implements Index {
        YES_ROW(1), NO_ROW(2), FANCY_CHAR_ROW(3), MODULE_LABEL_ROW(4), BUTTON_LABEL_ROW(5),
        PASSWORD_ROW(6), FREQUENCY_ROW(7), WOF_ROW(8);

        private final byte index;

        LanguageRow(int index) {
            this.index = (byte) index;
        }

        @Override
        public int getIndex() {
            return index;
        }
    }

    public enum LanguageColumn implements Index {
        BRAZILIAN(1), CZECH(2), DANISH(3), DUTCH(4), ENGLISH(5), ESPERANTO(6),
        FRENCH(7), FINNISH(8), GERMAN(9), ITALIAN(10), NORWEGIAN(11), POLISH(12),
        SPANISH(13), SWEDISH(14);

        private final byte index;

        LanguageColumn(int index) {
            this.index = (byte) index;
        }

        @Override
        public int getIndex() {
            return index;
        }
    }
}
