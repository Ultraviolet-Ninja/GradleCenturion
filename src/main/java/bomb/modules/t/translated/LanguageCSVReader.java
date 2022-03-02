package bomb.modules.t.translated;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

@SuppressWarnings("ConstantConditions")
public class LanguageCSVReader {
    public static List<String> getLanguageContent(@NotNull LanguageColumn languageColumn)
            throws IllegalArgumentException, IllegalStateException {
        if (languageColumn == null) throw new IllegalArgumentException("Language cannot be empty");

        int columnIndex = languageColumn.getColumnIndex();
        InputStream in = LanguageCSVReader.class.getResourceAsStream("dictionary.csv");

        try (CSVReader csvReader = new CSVReader(new InputStreamReader(in, UTF_8))) {
            return csvReader.readAll()
                    .stream()
                    .map(array -> array[columnIndex])
                    .toList();
        } catch (IOException | CsvException e) {
            throw new IllegalStateException(e);
        }
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
