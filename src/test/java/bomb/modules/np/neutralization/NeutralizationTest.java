package bomb.modules.np.neutralization;

import bomb.Widget;
import bomb.WidgetSimulations;
import bomb.enumerations.Indicators;
import bomb.enumerations.Ports;
import bomb.enumerations.TriState;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bomb.modules.np.neutralization.Chemical.Base.Ammonia;
import static bomb.modules.np.neutralization.Chemical.Base.Lithium_Hydroxide;
import static bomb.modules.np.neutralization.Chemical.Base.Potassium_Hydroxide;
import static bomb.modules.np.neutralization.Neutralization.FILTER;
import static bomb.modules.np.neutralization.Neutralization.NO_FILTER;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NeutralizationTest {
    @BeforeEach
    void resetProperties(){
        Widget.resetProperties();
    }

    @Test
    void exceptionTest(){
        assertThrows(IllegalArgumentException.class, () -> Neutralization.titrate(0, Color.RED));
        setupOne();
        assertDoesNotThrow(() -> Neutralization.titrate(20, Color.RED));
        assertThrows(IllegalArgumentException.class, () -> Neutralization.titrate(0, Color.CYAN));
    }

    @Test
    void videoTestOne(){ //Order: Base Name, Base Formula, Drop Count, Filter or No Filter
        setupOne();
        assertEqual(10, Color.YELLOW, new String[]{"Ammonia", Ammonia.getFormula(), "8", FILTER});
    }

    private void setupOne(){
        Widget.setPlates(1);Widget.setIndicator(TriState.ON, Indicators.NSA);
        Widget.addPort(Ports.PARALLEL);
        Widget.addPort(Ports.PARALLEL);
        Widget.addPort(Ports.SERIAL);
        Widget.addPort(Ports.PS2);
        Widget.addPort(Ports.RJ45);
        Widget.setDBatteries(1);
        Widget.setNumHolders(1);
        Widget.setSerialCode("2u3mr1");
    }

    @Test
    void videoTestTwo(){
        setupTwo();
        assertEqual(20, Color.BLUE, new String[]{"Lithium Hydroxide", Lithium_Hydroxide.getFormula(),
                "48", NO_FILTER});
    }

    private void setupTwo(){
        Widget.setPlates(3);
        Widget.addPort(Ports.PARALLEL);
        Widget.addPort(Ports.PARALLEL);
        Widget.addPort(Ports.SERIAL);
        Widget.setSerialCode("ew7qw5");
        Widget.setIndicator(TriState.ON, Indicators.MSA);
        Widget.setIndicator(TriState.ON, Indicators.NSA);
    }

    @Test
    void theGreatBerate(){
        WidgetSimulations.theGreatBerate();
        assertEqual(5, Color.BLUE, new String[]{"Lithium Hydroxide",
                Lithium_Hydroxide.getFormula(), "12", NO_FILTER});
        WidgetSimulations.theGreatBerateTwo();
        assertEqual(5, Color.RED, new String[]{"Lithium Hydroxide", Lithium_Hydroxide.getFormula(),
                "4", FILTER});
        WidgetSimulations.partTwoTakeTwo();
        assertEqual(10, Color.YELLOW, new String[]{"Potassium Hydroxide", Potassium_Hydroxide.getFormula(),
                "4", NO_FILTER});
        WidgetSimulations.partTwoTakeThree();

    }

    private void assertEqual(int volume, Color color, String[] expected){
        String[] actual = Neutralization.titrate(volume, color).split("-");
        for (int i = 0; i < expected.length; i++)
            assertEquals(expected[i], actual[i]);
    }
}
