package bomb.modules.t.translated;

import bomb.abstractions.Index;
import bomb.modules.dh.hexamaze.hexalgorithm.Maze;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NewTranslationCenter {
    public static List<String> getLanguageContent(Language language) throws CsvValidationException, IOException, IllegalArgumentException {
        if (language == null) throw new IllegalArgumentException("Language is null");

        List<String> dictionaryContent = new ArrayList<>();
        InputStream in = Maze.class.getResourceAsStream("dictionary.csv");
        CSVReader csvReader = new CSVReader(new InputStreamReader(Objects.requireNonNull(in)));
        String[] dataRow;
        while ((dataRow = csvReader.readNext()) != null) {
            dictionaryContent.add(dataRow[language.ordinal()]);
        }

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
}
