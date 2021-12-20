package bomb.modules.m.murder;

import bomb.ConditionSetter;
import bomb.Widget;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.EnumSet;

import static bomb.enumerations.Indicator.FRK;
import static bomb.enumerations.Port.SERIAL;
import static bomb.enumerations.TrinarySwitch.OFF;
import static bomb.modules.m.murder.Location.BILLIARD_ROOM;
import static bomb.modules.m.murder.Location.DINING_ROOM;
import static bomb.modules.m.murder.Location.STUDY;
import static bomb.modules.m.murder.Suspect.COLONEL_MUSTARD;
import static bomb.modules.m.murder.Suspect.MISS_SCARLETT;
import static bomb.modules.m.murder.Suspect.MRS_WHITE;
import static bomb.modules.m.murder.Suspect.PROFESSOR_PLUM;
import static bomb.modules.m.murder.Suspect.REVEREND_GREEN;
import static bomb.modules.m.murder.Weapon.CANDLE_STICK;
import static bomb.modules.m.murder.Weapon.DAGGER;
import static bomb.modules.m.murder.Weapon.LEAD_PIPE;
import static bomb.modules.m.murder.Weapon.REVOLVER;
import static bomb.modules.m.murder.Weapon.ROPE;
import static bomb.modules.m.murder.Weapon.SPANNER;
import static org.testng.Assert.assertEquals;

public class MurderTest {
    @BeforeMethod
    public void setUp() {
        Widget.resetProperties();
    }

    @DataProvider
    public Object[][] exceptionTestProvider() {
        EnumSet<Suspect> twoSuspects = EnumSet.range(MISS_SCARLETT, PROFESSOR_PLUM),
                fourSuspects = EnumSet.range(MISS_SCARLETT, REVEREND_GREEN);
        EnumSet<Weapon> twoWeapons = EnumSet.range(CANDLE_STICK, DAGGER),
                fourWeapons = EnumSet.range(CANDLE_STICK, REVOLVER);
        return new Object[][] {
                {twoSuspects, twoWeapons, null},
                {twoSuspects, fourWeapons, null},
                {fourSuspects, twoWeapons, null},
                {fourSuspects, fourWeapons, null}
        };
    }

    @Test(dataProvider = "exceptionTestProvider", expectedExceptions = IllegalArgumentException.class)
    public void exceptionTest(EnumSet<Suspect> suspects, EnumSet<Weapon> weapons, Location bodyFoundRoom) {
        Murder.solve(bodyFoundRoom, weapons, suspects);
    }

    @DataProvider
    public Object[][] videoTestProvider() {
        ConditionSetter setWidgetConditions = () -> {
            Widget.setDBatteries(2);
            Widget.setDoubleAs(2);
            Widget.setNumHolders(3);
            Widget.setIndicator(OFF, FRK);
            Widget.setPortValue(SERIAL, 1);
            Widget.setSerialCode("ZJ8FG7");
        };

        return new Object[][] {
                {
                    setWidgetConditions,
                        EnumSet.of(REVEREND_GREEN, COLONEL_MUSTARD, MISS_SCARLETT, PROFESSOR_PLUM),
                        EnumSet.of(DAGGER, CANDLE_STICK, SPANNER, REVOLVER),
                        DINING_ROOM, "Colonel Mustard - Candle Stick - Library"
                },
                {
                        setWidgetConditions,
                        EnumSet.of(REVEREND_GREEN, COLONEL_MUSTARD, MISS_SCARLETT, MRS_WHITE),
                        EnumSet.of(LEAD_PIPE, CANDLE_STICK, SPANNER, ROPE),
                        BILLIARD_ROOM, "Mrs White - Rope - Ballroom"
                },
                {
                        setWidgetConditions,
                        EnumSet.of(REVEREND_GREEN, PROFESSOR_PLUM, MISS_SCARLETT, MRS_WHITE),
                        EnumSet.of(LEAD_PIPE, CANDLE_STICK, SPANNER, REVOLVER),
                        STUDY, "Miss Scarlett - Spanner - Lounge"
                }
        };
    }

    @Test(dataProvider = "videoTestProvider")
    public void videoTest(ConditionSetter bombConditions, EnumSet<Suspect> possibleSuspects,
                          EnumSet<Weapon> possibleWeapons, Location bodyFoundRoom, String expectedValue) {
        bombConditions.setCondition();

        assertEquals(Murder.solve(bodyFoundRoom, possibleWeapons, possibleSuspects), expectedValue);
    }
}
