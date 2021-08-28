package bomb.modules.ab.astrology;

import bomb.ConditionSetter;
import bomb.Widget;
import bomb.WidgetSimulations;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static bomb.modules.ab.astrology.AstroSymbol.ARIES;
import static bomb.modules.ab.astrology.AstroSymbol.EARTH;
import static bomb.modules.ab.astrology.AstroSymbol.FIRE;
import static bomb.modules.ab.astrology.AstroSymbol.GEMINI;
import static bomb.modules.ab.astrology.AstroSymbol.JUPITER;
import static bomb.modules.ab.astrology.AstroSymbol.MARS;
import static bomb.modules.ab.astrology.AstroSymbol.MERCURY;
import static bomb.modules.ab.astrology.AstroSymbol.SAGITTARIUS;
import static bomb.modules.ab.astrology.AstroSymbol.TAURUS;
import static bomb.modules.ab.astrology.AstroSymbol.URANUS;
import static bomb.modules.ab.astrology.AstroSymbol.VENUS;
import static bomb.modules.ab.astrology.AstroSymbol.VIRGO;
import static bomb.modules.ab.astrology.AstroSymbol.WATER;
import static bomb.modules.ab.astrology.Astrology.GOOD_OMEN;
import static bomb.modules.ab.astrology.Astrology.POOR_OMEN;
import static org.testng.Assert.assertEquals;

public class AstrologyTest {
    @BeforeMethod
    public void setUp() {
        Widget.resetProperties();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void serialCodeExceptionTest() {
        Astrology.calculate(EARTH, ARIES, MARS);
    }

    @DataProvider
    public Object[][] exceptionProvider() {
        return new Object[][]{
                {ARIES, ARIES, ARIES}, {VENUS, VENUS, VENUS}, {FIRE, FIRE, FIRE}, {ARIES, MARS, EARTH, EARTH}, {VENUS}
        };
    }

    @Test(dataProvider = "exceptionProvider", expectedExceptions = IllegalArgumentException.class)
    public void exceptionTest(AstroSymbol... set) {
        Astrology.calculate(set);
    }

    @DataProvider
    public Object[][] trainingVideoProvider() {
        return new Object[][]{
                {(POOR_OMEN + 4), EARTH, MARS, ARIES}, {(GOOD_OMEN + 2), WATER, MERCURY, TAURUS},
                {(GOOD_OMEN + 3), FIRE, MERCURY, SAGITTARIUS}
        };
    }

    @Test(dataProvider = "trainingVideoProvider")
    public void trainingVideoTest(String expected, AstroSymbol... set) {
        Widget.setSerialCode("jt3gu5");

        assertEquals(Astrology.calculate(set), expected);
    }

    @DataProvider
    public Object[][] interchangeabilityProvider() {
        String expected = POOR_OMEN + 4;
        return new Object[][]{
                {expected, EARTH, MARS, ARIES}, {expected, MARS, EARTH, ARIES}, {expected, MARS, ARIES, EARTH},
                {expected, ARIES, MARS, EARTH}, {expected, ARIES, EARTH, MARS}
        };
    }

    @Test(dataProvider = "interchangeabilityProvider")
    public void interchangeabilityTest(String expected, AstroSymbol... set) {
        Widget.setSerialCode("jt3gu5");

        assertEquals(Astrology.calculate(set), expected);
    }

    @DataProvider
    public Object[][] theGreatBerateProvider() {
        ConditionSetter first = WidgetSimulations::theGreatBerateVideoOne;
        ConditionSetter second = WidgetSimulations::theGreatBerateVideoTwo;
        ConditionSetter third = WidgetSimulations::videoTwoTakeTwo;
        return new Object[][]{
                {first, (POOR_OMEN + 1), URANUS, FIRE, ARIES}, {second, (POOR_OMEN + 2), WATER, URANUS, VIRGO},
                {third, (GOOD_OMEN + 6), JUPITER, EARTH, GEMINI}
        };
    }

    @Test(dataProvider = "theGreatBerateProvider")
    public void theGreatBerateVideoTest(ConditionSetter cond, String expected, AstroSymbol... set) {
        cond.setCondition();

        assertEquals(Astrology.calculate(set), expected);
    }

    @AfterClass
    public void tearDown() {
        Widget.resetProperties();
    }
}
