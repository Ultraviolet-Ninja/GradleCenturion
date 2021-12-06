package bomb.modules.ab.astrology;

import bomb.BombSimulations;
import bomb.ConditionSetter;
import bomb.Widget;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static bomb.modules.ab.astrology.Astrology.GOOD_OMEN;
import static bomb.modules.ab.astrology.Astrology.POOR_OMEN;
import static bomb.modules.ab.astrology.AstrologySymbol.ARIES;
import static bomb.modules.ab.astrology.AstrologySymbol.EARTH;
import static bomb.modules.ab.astrology.AstrologySymbol.FIRE;
import static bomb.modules.ab.astrology.AstrologySymbol.GEMINI;
import static bomb.modules.ab.astrology.AstrologySymbol.JUPITER;
import static bomb.modules.ab.astrology.AstrologySymbol.MARS;
import static bomb.modules.ab.astrology.AstrologySymbol.MERCURY;
import static bomb.modules.ab.astrology.AstrologySymbol.SAGITTARIUS;
import static bomb.modules.ab.astrology.AstrologySymbol.TAURUS;
import static bomb.modules.ab.astrology.AstrologySymbol.URANUS;
import static bomb.modules.ab.astrology.AstrologySymbol.VENUS;
import static bomb.modules.ab.astrology.AstrologySymbol.VIRGO;
import static bomb.modules.ab.astrology.AstrologySymbol.WATER;
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
    public Object[][] exceptionTestProvider() {
        return new Object[][]{
                {ARIES, ARIES, ARIES}, {VENUS, VENUS, VENUS}, {FIRE, FIRE, FIRE},
                {ARIES, MARS, EARTH, EARTH}, {VENUS}
        };
    }

    @Test(dataProvider = "exceptionTestProvider", expectedExceptions = IllegalArgumentException.class)
    public void exceptionTest(AstrologySymbol... set) {
        Astrology.calculate(set);
    }

    @DataProvider
    public Object[][] trainingVideoTestProvider() {
        return new Object[][]{
                {(POOR_OMEN + 4), EARTH, MARS, ARIES}, {(GOOD_OMEN + 2), WATER, MERCURY, TAURUS},
                {(GOOD_OMEN + 3), FIRE, MERCURY, SAGITTARIUS}
        };
    }

    @Test(dataProvider = "trainingVideoTestProvider")
    public void trainingVideoTest(String expected, AstrologySymbol... set) {
        Widget.setSerialCode("jt3gu5");

        assertEquals(Astrology.calculate(set), expected);
    }

    @DataProvider
    public Object[][] interchangeabilityTestProvider() {
        String expected = POOR_OMEN + 4;
        return new Object[][]{
                {expected, EARTH, MARS, ARIES}, {expected, MARS, EARTH, ARIES},
                {expected, MARS, ARIES, EARTH}, {expected, ARIES, MARS, EARTH},
                {expected, ARIES, EARTH, MARS}
        };
    }

    @Test(dataProvider = "interchangeabilityTestProvider")
    public void interchangeabilityTest(String expected, AstrologySymbol... set) {
        Widget.setSerialCode("jt3gu5");

        assertEquals(Astrology.calculate(set), expected);
    }

    @DataProvider
    public Object[][] theGreatBerateSimulationProvider() {
        ConditionSetter first = BombSimulations::theGreatBerateVideoOne;
        ConditionSetter second = BombSimulations::theGreatBerateVideoTwo;
        ConditionSetter third = BombSimulations::videoTwoTakeTwo;
        return new Object[][]{
                {first, (POOR_OMEN + 1), URANUS, FIRE, ARIES},
                {second, (POOR_OMEN + 2), WATER, URANUS, VIRGO},
                {third, (GOOD_OMEN + 6), JUPITER, EARTH, GEMINI}
        };
    }

    @Test(dataProvider = "theGreatBerateSimulationProvider")
    public void theGreatBerateVideoTest(ConditionSetter cond, String expected,
                                        AstrologySymbol... set) {
        cond.setCondition();

        assertEquals(Astrology.calculate(set), expected);
    }

    @AfterClass
    public void tearDown() {
        Widget.resetProperties();
    }
}
