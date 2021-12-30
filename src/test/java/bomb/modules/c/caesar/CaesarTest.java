package bomb.modules.c.caesar;

import bomb.Widget;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class CaesarTest {
    @BeforeTest
    public void setUp() {
        Widget.resetProperties();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void validationTest() {
        Caesar.reshuffle("");
    }



    @AfterClass
    public void tearDown() {
        Widget.resetProperties();
    }
}
