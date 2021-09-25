package bomb.modules.t.translated;

public enum Language {
    BRAZILIAN, CZECH, DANISH, DUTCH, ENGLISH, ESPERANTO, FINNISH, GERMAN, ITALIAN, NORWEGIAN, POLISH, SPANISH, SWEDISH;

    public static Language translateText(String language) {
        language = language.toUpperCase();

        for (Language currentLanguage : Language.values()) {
            if (currentLanguage.name().equals(language)) return currentLanguage;
        }

        return null;
    }
}
