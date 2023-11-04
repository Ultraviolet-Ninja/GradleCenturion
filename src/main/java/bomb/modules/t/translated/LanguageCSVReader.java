package bomb.modules.t.translated;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import static java.nio.charset.StandardCharsets.UTF_8;

@SuppressWarnings("ConstantConditions")
public final class LanguageCSVReader {
    private static final Map<Integer, List<String>> INDEX_CACHE_MAP =
            new WeakHashMap<>(LanguageColumn.values().length << 1);
    public static List<String> getLanguageContent(@NotNull LanguageColumn languageColumn)
            throws IllegalArgumentException, IllegalStateException {
        if (languageColumn == null) throw new IllegalArgumentException("Language cannot be empty");

        int columnIndex = languageColumn.getColumnIndex();
        if (INDEX_CACHE_MAP.containsKey(columnIndex)) {
            return INDEX_CACHE_MAP.get(columnIndex);
        }
        var inputStream = LanguageCSVReader.class.getResourceAsStream("dictionary.csv");

        try (var csvReader = new CSVReader(new InputStreamReader(inputStream, UTF_8))) {
            var list = csvReader.readAll()
                    .stream()
                    .map(array -> array[columnIndex])
                    .toList();
            INDEX_CACHE_MAP.put(columnIndex, list);
            return list;
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
