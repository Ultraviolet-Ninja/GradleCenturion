package bomb.modules.c.caesar.cipher;

import bomb.Widget;
import bomb.annotation.DisplayComponent;
import org.jetbrains.annotations.NotNull;

import java.util.stream.IntStream;

import static bomb.enumerations.Indicator.CAR;
import static bomb.enumerations.Indicator.NSA;
import static bomb.enumerations.Port.PARALLEL;

@DisplayComponent(resource = "caesar_cipher.fxml", buttonLinkerName = "Caesar Cipher")
public final class Caesar extends Widget {
    private static final int MAX_LETTER_COUNT = 26;

    public static @NotNull String reshuffle(@NotNull String input) throws IllegalArgumentException {
        checkSerialCode();
        return shift(input, createOffset());
    }

    private static String shift(String encoded, int offset) {
        encoded = encoded.toUpperCase();
        StringBuilder result = new StringBuilder();

        IntStream.range(0, encoded.length())
                .map(encoded::charAt)
                .map(letter -> letter - offset)
                .map(Caesar::moduloUpperBound)
                .map(Caesar::moduloLowerBound)
                .forEach(result::appendCodePoint);

        return result.toString();
    }

    private static int createOffset() {
        if (hasLitIndicator(NSA) && doesPortExists(PARALLEL))
            return 0;

        int out = 0;
        out += getAllBatteries();
        if (hasVowelInSerialCode()) out--;
        if (getSerialCodeLastDigit() % 2 == 0) out++;
        if (hasIndicator(CAR)) out++;
        return out;
    }

    private static int moduloUpperBound(int value) {
        return value <= 'Z' ?
                value :
                value - MAX_LETTER_COUNT;
    }

    private static int moduloLowerBound(int value) {
        return  value - 'A' >= 0 ?
                value :
                value + MAX_LETTER_COUNT;
    }
}
