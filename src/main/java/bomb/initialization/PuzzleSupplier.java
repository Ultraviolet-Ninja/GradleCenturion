package bomb.initialization;

import bomb.modules.ab.alphabet.Alphabet;
import bomb.modules.ab.astrology.Astrology;

import java.util.List;

public class PuzzleSupplier {
    public static List<Class<?>> provideClassList() {
        return List.of(Alphabet.class, Astrology.class);
    }
}
