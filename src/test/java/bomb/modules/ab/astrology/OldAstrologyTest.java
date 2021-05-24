
package bomb.modules.ab.astrology;

import bomb.Widget;
import bomb.WidgetSimulations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bomb.modules.ab.astrology.AstroSymbols.ARIES;
import static bomb.modules.ab.astrology.AstroSymbols.EARTH;
import static bomb.modules.ab.astrology.AstroSymbols.FIRE;
import static bomb.modules.ab.astrology.AstroSymbols.GEMINI;
import static bomb.modules.ab.astrology.AstroSymbols.JUPITER;
import static bomb.modules.ab.astrology.AstroSymbols.MARS;
import static bomb.modules.ab.astrology.AstroSymbols.MERCURY;
import static bomb.modules.ab.astrology.AstroSymbols.SAGITTARIUS;
import static bomb.modules.ab.astrology.AstroSymbols.TAURUS;
import static bomb.modules.ab.astrology.AstroSymbols.URANUS;
import static bomb.modules.ab.astrology.AstroSymbols.VENUS;
import static bomb.modules.ab.astrology.AstroSymbols.VIRGO;
import static bomb.modules.ab.astrology.AstroSymbols.WATER;
import static bomb.modules.ab.astrology.Astrology.GOOD_OMEN;
import static bomb.modules.ab.astrology.Astrology.POOR_OMEN;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OldAstrologyTest {
    @BeforeEach
    void resetProperties(){
        Widget.resetProperties();
    }

    @Test
    void exceptionTest(){
        assertThrows(IllegalArgumentException.class, () -> Astrology.calculate(EARTH, ARIES, MARS));
        Widget.setSerialCode("412ier");
        assertDoesNotThrow(() -> Astrology.calculate(EARTH, ARIES, MARS));
        assertThrows(IllegalArgumentException.class, () -> Astrology.calculate(ARIES, ARIES, ARIES));
        assertThrows(IllegalArgumentException.class, () -> Astrology.calculate(VENUS, VENUS, VENUS));
        assertThrows(IllegalArgumentException.class, () -> Astrology.calculate(FIRE, FIRE, FIRE));
        assertThrows(IllegalArgumentException.class, () -> Astrology.calculate(ARIES, MARS, EARTH, EARTH));
        assertThrows(IllegalArgumentException.class, () -> Astrology.calculate(VENUS));
    }

    @Test
    void videoTest(){
        Widget.setSerialCode("jt3gu5");
        assertEquals(POOR_OMEN + 4, Astrology.calculate(EARTH, MARS, ARIES));
        assertEquals(GOOD_OMEN + 2, Astrology.calculate(WATER, MERCURY, TAURUS));
        assertEquals(GOOD_OMEN + 3, Astrology.calculate(FIRE, MERCURY, SAGITTARIUS));
    }

    @Test
    void interchangeabilityTest(){
        String expected = POOR_OMEN + 4;
        Widget.setSerialCode("jt3gu5");
        assertEquals(expected, Astrology.calculate(EARTH, MARS, ARIES));
        assertEquals(expected, Astrology.calculate(MARS, EARTH, ARIES));
        assertEquals(expected, Astrology.calculate(MARS, ARIES, EARTH));
        assertEquals(expected, Astrology.calculate(ARIES, MARS, EARTH));
        assertEquals(expected, Astrology.calculate(ARIES, EARTH, MARS));
    }

    @Test
    void theGreatBerate(){
        WidgetSimulations.theGreatBerate();
        assertEquals(POOR_OMEN + 1, Astrology.calculate(URANUS, FIRE, ARIES));
        WidgetSimulations.theGreatBerateTwo();
        assertEquals(POOR_OMEN + 2, Astrology.calculate(WATER, URANUS, VIRGO));
        WidgetSimulations.partTwoTakeTwo();
        assertEquals(GOOD_OMEN + 6, Astrology.calculate(JUPITER, EARTH, GEMINI));
        WidgetSimulations.partTwoTakeThree();
    }
}