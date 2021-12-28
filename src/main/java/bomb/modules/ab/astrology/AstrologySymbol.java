package bomb.modules.ab.astrology;

/**
 * This enum deals with the symbols from Astrology, each containing an index in arrays
 * when used in the Astrology class
 */
public enum AstrologySymbol {
    /**
     * The Elemental symbols
     */
    FIRE(0), WATER(1), EARTH(2), AIR(3),
    /**
     * The Celestial symbols
     */
    SUN(0), MOON(1), MERCURY(2), VENUS(3), MARS(4),
    JUPITER(5), SATURN(6), URANUS(7), NEPTUNE(8), PLUTO(9),
    /**
     * The Zodiac symbols
     */
    ARIES(0), TAURUS(1), GEMINI(2), CANCER(3), LEO(4), VIRGO(5),
    LIBRA(6), SCORPIO(7), SAGITTARIUS(8), CAPRICORN(9),
    AQUARIUS(10), PISCES(11);

    private final byte index;

    public int getIndex() {
        return index;
    }

    /**
     * AstroSymbols constructor
     *
     * @param index Its index number found in the bomb manual page for Astrology
     */
    AstrologySymbol(int index) {
        this.index = (byte) index;
    }

    public static AstrologySymbol[] getElementalSymbols() {
        return new AstrologySymbol[]{FIRE, WATER, EARTH, AIR};
    }

    public static AstrologySymbol[] getCelestialSymbols() {
        return new AstrologySymbol[]{SUN, MOON, MERCURY, VENUS, MARS, JUPITER, SATURN, URANUS, NEPTUNE, PLUTO};
    }

    public static AstrologySymbol[] getZodiacSymbols() {
        return new AstrologySymbol[]{ARIES, TAURUS, GEMINI, CANCER, LEO, VIRGO, LIBRA, SCORPIO, SAGITTARIUS, CAPRICORN,
                AQUARIUS, PISCES};
    }
}
