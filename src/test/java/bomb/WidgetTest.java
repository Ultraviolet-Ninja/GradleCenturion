package bomb;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class WidgetTest {
    @BeforeTest
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

    @AfterClass
    public void tearDown(){
        Widget.resetProperties();
    }
}
