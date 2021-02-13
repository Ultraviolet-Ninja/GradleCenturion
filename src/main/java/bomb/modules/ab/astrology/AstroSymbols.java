package bomb.modules.ab.astrology;

import bomb.interfaces.Index;

/**
 * This enum deals with the symbols from Astrology, each containing an index in arrays
 * when used in the Astrology class
 */
public enum AstroSymbols implements Index {
    /**
     * The Elemental symbols
     */
    FIRE(0), WATER(1), EARTH(2), AIR(3),
    /**
     * The Planetary symbols
     */
    SUN(0), MOON(1), MERCURY(2), VENUS(3), MARS(4),
    JUPITER(5), SATURN(6), URANUS(7), NEPTUNE(8), PLUTO(9),
    /**
     * The Astrological symbols
     */
    ARIES(0), TAURUS(1), GEMINI(2), CANCER(3), LEO(4), VIRGO(5),
    LIBRA(6), SCORPIO(7), SAGITTARIUS(8), CAPRICORN(9),
    AQUARIUS(10), PISCES(11);

    private final int index;

    @Override
    public int getIdx() {
        return index;
    }

    /**
     * AstroSymbols constructor
     *
     * @param index Its index number found in the bomb manual page for Astrology
     */
    AstroSymbols(int index){
        this.index = index;
    }
}
