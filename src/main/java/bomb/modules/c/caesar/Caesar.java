package bomb.modules.c.caesar;

import bomb.Widget;
import org.jetbrains.annotations.NotNull;

import static bomb.enumerations.Indicator.CAR;
import static bomb.enumerations.Indicator.NSA;
import static bomb.enumerations.Port.PARALLEL;

public class Caesar extends Widget {

    public static String reshuffle(@NotNull String input) {
        return restructure(input, createOffset());
    }

    private static String restructure(String encoded, int numbShift) {
        char extract;
        encoded = encoded.toUpperCase();
        StringBuilder result = new StringBuilder();

        for (char letter : encoded.toCharArray()) {
            extract = (char) (letter - numbShift);
            if (extract < 'A') {
                extract = (char) ('[' - (numbShift - (letter - 'A')));
            }
            result.append(extract);
        }

        return result.toString();
    }

    private static int createOffset() {
        if (hasLitIndicator(NSA) && hasMorePortsThanSpecified(PARALLEL, 0))
            return 0;

        int out = 0;
        out += getAllBatteries();
        if (hasVowelInSerialCode()) out--;
        if (hasEvenNumberInSerialCode()) out++;
        if (hasIndicator(CAR)) out++;
        return out;
    }
}
