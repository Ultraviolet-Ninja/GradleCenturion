package bomb;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class WidgetTest {
    @BeforeMethod
    public void setUp() {
        Widget.resetProperties();
    }

    @DataProvider
    public Object[][] twoFactorProvider(){
        return new Object[][]{
                {"o24ksa"}
        };
    }

    @Test(dataProvider = "twoFactorProvider")
    public void twoFactorTest(String val){
        Widget.setTwoFactor(val);

        assertEquals(val, Widget.twoFactor);
    }

    @DataProvider
    public Object[][] booleanProvider(){
        return new Object[][]{
                {false, false}, {true, true}
        };
    }

    @Test(dataProvider = "booleanProvider")
    public void forgetMeNotTest(boolean val, boolean expected){
        Widget.setForgetMeNot(val);

        assertEquals(Widget.getForgetMeNot(), expected);
    }

    @Test(dataProvider = "booleanProvider")
    public void souvenirTest(boolean val, boolean expected){
        Widget.setSouvenir(val);

        assertEquals(Widget.getSouvenir(), expected);
    }

    @DataProvider
    public Object[][] negativeValueProvider(){

        return new Object[][]{
                {-1, 0}, {5, 5}
        };
    }

    @Test(dataProvider = "negativeValueProvider")
    public void negativePortPlateTest(int val, int expected){
        Widget.setPlates(val);

        assertEquals(Widget.numPlates, expected);
    }

    @Test(dataProvider = "negativeValueProvider")
    public void negativeModNumberTest(int val, int expected){
        Widget.setNumModules(val);

        assertEquals(Widget.getNumModules(), expected);
    }

    @Test(dataProvider = "negativeValueProvider")
    public void negativeHolderNumberTest(int val, int expected){
        Widget.setNumHolders(val);

        assertEquals(Widget.getNumHolders(), expected);
    }

    @Test(dataProvider = "negativeValueProvider")
    public void negativeDBatteriesTest(int val, int expected){
        Widget.setDBatteries(val);

        assertEquals(Widget.getAllBatteries(), expected);
        assertEquals(Widget.numDBatteries, expected);
    }

    @Test(dataProvider = "negativeValueProvider")
    public void negativeDoubleATest(int val, int expected){
        Widget.setDoubleAs(val);

        assertEquals(Widget.getAllBatteries(), expected);
        assertEquals(Widget.numDoubleAs, expected);
    }

    @AfterClass
    public void tearDown(){
        Widget.resetProperties();
    }
}
