package bomb.modules.t.translated;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.javatuples.Pair;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Optional;
import java.util.WeakHashMap;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;

@SuppressWarnings("ConstantConditions")
public final class LanguageCSVReader {
    private static final Map<LanguageColumn, TranslationResults> INDEX_CACHE_MAP =
            WeakHashMap.newWeakHashMap(LanguageColumn.values().length);

    private static final Logger LOG = LoggerFactory.getLogger(LanguageCSVReader.class);

    private static final String BUTTON_LABEL_FILE = "button-labels.csv",
            MODULE_LABEL_FILE = "module-labels.csv",
            FREQUENCY_FILE = "frequencies.csv",
            PASSWORD_FILE = "passwords.csv",
            WHO_IS_ON_FIRST_FILE = "on-first.csv",
            EXTRA_DETAILS_FILE = "extra-info.csv";

    public static TranslationResults getLanguageContent(@NotNull LanguageColumn languageColumn)
            throws IllegalArgumentException, IllegalStateException {
        if (languageColumn == null) throw new IllegalArgumentException("Language cannot be empty");

        if (INDEX_CACHE_MAP.containsKey(languageColumn)) {
            return INDEX_CACHE_MAP.get(languageColumn);
        }

        int columnIndex = languageColumn.getColumnIndex();
        var extraInfo = extractFromFileAsync(EXTRA_DETAILS_FILE, columnIndex);
        var onFirstInfo = extractFromFileAsync(WHO_IS_ON_FIRST_FILE, columnIndex);
        var passwordInfo = extractFromFileAsync(PASSWORD_FILE, columnIndex);
        var buttonLabelInfo = extractFromFileAsync(BUTTON_LABEL_FILE, columnIndex);
        var moduleLabelInfo = extractFromFileAsync(MODULE_LABEL_FILE, columnIndex);
        var frequencyInfo = extractFrequencyInfoAsync(columnIndex);

        return TranslationResults.fromFutures(extraInfo, onFirstInfo, passwordInfo,
                buttonLabelInfo, moduleLabelInfo, frequencyInfo);
    }

    private static CompletableFuture<Optional<String[]>> extractFromFileAsync(String file, int columnIndex) {
        return CompletableFuture.supplyAsync(() -> extractInfoAtColumnIndex(
                file,
                stream -> stream.map(array -> array[columnIndex])
                        .toArray(String[]::new))
        );
    }

    private static CompletableFuture<Optional<Map<String, String>>> extractFrequencyInfoAsync(int columnIndex) {
        return CompletableFuture.supplyAsync(() -> extractInfoAtColumnIndex(
                FREQUENCY_FILE,
                stream -> stream.map(array -> Pair.with(array[0], array[columnIndex]))
                        .collect(Collectors.toUnmodifiableMap(Pair::getValue0, Pair::getValue1)))
        );
    }

    private static <T> Optional<T> extractInfoAtColumnIndex(String file,
                                                            Function<Stream<String[]>, T> infoParser) {
        var inputStream = LanguageCSVReader.class.getResourceAsStream(file);
        if (inputStream == null) {
            LOG.warn("{} gave a null InputStream", file);
            return Optional.empty();
        }

        try (var csvReader = new CSVReader(new InputStreamReader(inputStream, UTF_8))) {
            var stream = csvReader.readAll()
                    .stream()
                    .skip(1);
            return Optional.of(infoParser.apply(stream));
        } catch (IOException | CsvException e) {
            LOG.warn("Read Error: ", e);
            return Optional.empty();
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
