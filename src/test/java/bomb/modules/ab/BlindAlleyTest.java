package bomb.modules.ab;

import bomb.Widget;
import bomb.WidgetSimulations;
import bomb.enumerations.Indicators;
import bomb.enumerations.Ports;
import bomb.enumerations.TriState;
import bomb.modules.ab.blind_alley.BlindAlley;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BlindAlleyTest {
    @BeforeEach
    void resetWidget(){
        Widget.resetProperties();
        BlindAlley.reset();
    }

    @Test
    void nullTest(){
        assertArrayEquals(new int[][]{{0,0,0}, {0,0,0}, {0,0,0}});
    }

    @Test
    void videoTestOne(){
        widgetSetupOne();
        assertArrayEquals(new int[][] {{1,1,0}, {1,0,2}, {1,2,0}});
    }

    private void widgetSetupOne(){
        Widget.setSerialCode("bu7we6");
        Widget.setDBatteries(1);
        Widget.setNumHolders(1);
        Widget.setIndicator(TriState.ON, Indicators.CAR);
        Widget.addPort(Ports.DVI);
        Widget.addPort(Ports.RJ45);
        Widget.addPort(Ports.PS2);
        Widget.addPort(Ports.PARALLEL);
        Widget.addPort(Ports.SERIAL);
        Widget.setPlates(2);
    }

    @Test
    void videoTestTwo(){
        widgetSetupTwo();
        assertArrayEquals(new int[][]{{0,1,0}, {2,1,1}, {1,1,2}});
    }

    private void widgetSetupTwo(){
        Widget.setNumHolders(1);
        Widget.setDoubleAs(2);
        Widget.setPlates(2);
        Widget.addPort(Ports.DVI);
        Widget.addPort(Ports.RJ45);
        Widget.addPort(Ports.PS2);
        Widget.addPort(Ports.PARALLEL);
        Widget.addPort(Ports.RCA);
        Widget.setSerialCode("718pz5");
        Widget.setIndicator(TriState.ON, Indicators.SND);
        Widget.setIndicator(TriState.OFF, Indicators.FRQ);
    }

    @Test
    void theGreatBerate(){
        WidgetSimulations.theGreatBerate();
        assertArrayEquals(new int[][]{{0,0,0}, {0,0,0}, {1,2,0}});
        WidgetSimulations.theGreatBerateTwo();
        assertArrayEquals(new int[][]{{1,1,0}, {1,0,2}, {1,2,1}});
        WidgetSimulations.partTwoTakeTwo();
        assertArrayEquals(new int[][]{{1,0,0}, {0,1,0}, {0,2,2}});
        WidgetSimulations.partTwoTakeThree();

    }

    private void assertArrayEquals(int[][] numbers){
        int[][] actual = BlindAlley.getAlleyCat();
        for (int i = 0; i < numbers.length; i++){
            for (int j = 0; j < numbers[i].length; j++)
                assertEquals(numbers[i][j], actual[i][j]);
        }
    }
}
