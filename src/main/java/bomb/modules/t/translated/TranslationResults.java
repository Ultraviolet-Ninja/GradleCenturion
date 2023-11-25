package bomb.modules.t.translated;

import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static bomb.modules.t.translated.TranslationResults.ExtraInfoCsvRow.FANCY_CHAR_ROW;
import static bomb.modules.t.translated.TranslationResults.ExtraInfoCsvRow.NO_ROW;
import static bomb.modules.t.translated.TranslationResults.ExtraInfoCsvRow.YES_ROW;

public record TranslationResults(String ventGasResponseYes, String ventGasResponseNo,
                                 String[] specialCharacters, String[] buttonLabels, String[] moduleLabels,
                                 String[] passwords, String[] onFirstWords,
                                 Map<String, String> frequencyToWordMap) {
    @SuppressWarnings("unchecked")
    public static TranslationResults fromFutures(
            CompletableFuture<Optional<String[]>> extraInfoFuture,
            CompletableFuture<Optional<String[]>> onFirstInfoFuture,
            CompletableFuture<Optional<String[]>> passwordInfoFuture,
            CompletableFuture<Optional<String[]>> buttonLabelInfoFuture,
            CompletableFuture<Optional<String[]>> moduleLabelInfoFuture,
            CompletableFuture<Optional<Map<String, String>>> frequencyMapFuture) {
        var extraInfo = extractFromFuture(extraInfoFuture);
        var onFirstWords = extractFromFuture(onFirstInfoFuture).orElse(null);
        var passwords = extractFromFuture(passwordInfoFuture).orElse(null);
        var buttonLabels = extractFromFuture(buttonLabelInfoFuture).orElse(null);
        var moduleLabels = extractFromFuture(moduleLabelInfoFuture).orElse(null);
        var frequencyToWordMap = extractFromFuture(frequencyMapFuture).orElse(Collections.EMPTY_MAP);

        if (extraInfo.isPresent()) {
            var info = extraInfo.get();
            var ventGasResponseYes = info[YES_ROW.ordinal()];
            var ventGasResponseNo = info[NO_ROW.ordinal()];
            var specialCharacters = info[FANCY_CHAR_ROW.ordinal()].split("\\s");
            return new TranslationResults(ventGasResponseYes, ventGasResponseNo, specialCharacters,
                    buttonLabels, moduleLabels, passwords, onFirstWords, frequencyToWordMap
            );
        } else {
            return new TranslationResults("", "", null,
                    buttonLabels, moduleLabels, passwords, onFirstWords, frequencyToWordMap
            );
        }
    }

    public static <T> Optional<T> extractFromFuture(CompletableFuture<Optional<T>> future) {
        try {
            return future.get();
        } catch (ExecutionException | InterruptedException e) {
            return Optional.empty();
        }
    }

    public boolean hasResponseYes() {
        return ventGasResponseYes != null && !ventGasResponseYes.isBlank();
    }

    public boolean hasResponseNo() {
        return ventGasResponseNo != null && !ventGasResponseNo.isBlank();
    }

    public String @Nullable [] getParsedPasswords() {
        if (passwords != null) {
            boolean arePasswordsPopulated = Arrays.stream(passwords)
                    .noneMatch(line -> line != null && line.isBlank());
            return arePasswordsPopulated ? passwords : null;
        }

        return null;
    }

    enum ExtraInfoCsvRow {
        YES_ROW, NO_ROW, FANCY_CHAR_ROW
    }
}
