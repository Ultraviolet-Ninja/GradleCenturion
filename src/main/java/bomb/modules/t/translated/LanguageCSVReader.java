package bomb.modules.t.translated;

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

        int columnIndex = languageColumn.getColumnIndex();
        InputStream in = LanguageCSVReader.class.getResourceAsStream("dictionary.csv");
        CSVReader csvReader = new CSVReader(new InputStreamReader(Objects.requireNonNull(in), StandardCharsets.UTF_8));
        List<String> dictionaryContent = csvReader.readAll()
                .stream()
                .map(array -> array[columnIndex])
                .collect(Collectors.toList());

        csvReader.close();
        return dictionaryContent;
    }

    public enum LanguageRow {
        YES_ROW, NO_ROW, FANCY_CHAR_ROW, MODULE_LABEL_ROW, BUTTON_LABEL_ROW, PASSWORD_ROW,
        FREQUENCY_ROW, WOF_ROW;

        public int getRowIndex() {
            return ordinal() + 1;
        }
    }

    public enum LanguageColumn {
        BRAZILIAN, CZECH, DANISH, DUTCH, ENGLISH, ESPERANTO, FRENCH, FINNISH,
        GERMAN, ITALIAN, NORWEGIAN, POLISH, SPANISH, SWEDISH;

        public int getColumnIndex() {
            return ordinal() + 1;
        }
    }
}
