package bomb.modules.ab;

import bomb.Widget;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bomb.enumerations.AstroSymbols.ARIES;
import static bomb.enumerations.AstroSymbols.EARTH;
import static bomb.enumerations.AstroSymbols.FIRE;
import static bomb.enumerations.AstroSymbols.MARS;
import static bomb.enumerations.AstroSymbols.MERCURY;
import static bomb.enumerations.AstroSymbols.SAGITTARIUS;
import static bomb.enumerations.AstroSymbols.TAURUS;
import static bomb.enumerations.AstroSymbols.WATER;
import static bomb.modules.ab.Astrology.GOOD_OMEN;
import static bomb.modules.ab.Astrology.POOR_OMEN;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AstrologyTest {
    @BeforeEach
    void resetProperties(){
        Widget.resetProperties();
    }

    @Test
    void videoTest(){
        Widget.setSerialCode("jt3gu5");
        assertEquals(POOR_OMEN + 4, Astrology.calculate(EARTH, MARS, ARIES));
        assertEquals(GOOD_OMEN + 2, Astrology.calculate(WATER, MERCURY, TAURUS));
        assertEquals(GOOD_OMEN + 3, Astrology.calculate(FIRE, MERCURY, SAGITTARIUS));
    }
}
