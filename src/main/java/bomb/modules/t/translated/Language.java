package bomb.modules.t.translated;

import bomb.abstractions.Index;

public enum Language implements Index {
    BRAZILIAN(1), CZECH(2), DANISH(3), DUTCH(4), ENGLISH(5), ESPERANTO(6), FRENCH(7),
    FINNISH(8), GERMAN(9), ITALIAN(10), NORWEGIAN(11), POLISH(12), SPANISH(13),
    SWEDISH(14);

    private final byte index;

    public static Language translateText(String language) {
        language = language.toUpperCase();

        for (Language currentLanguage : Language.values()) {
            if (currentLanguage.name().equals(language)) return currentLanguage;
        }

        return null;
    }

    Language(int index) {
        this.index = (byte) index;
    }

    @Override
    public int getIndex() {
        return index;
    }
}
