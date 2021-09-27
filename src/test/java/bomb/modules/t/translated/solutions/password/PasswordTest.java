package bomb.modules.t.translated.solutions.password;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;

public class PasswordTest {
    private static String[] brazilianPasswords;

    @BeforeClass
    public void passwordSetUp() {
        String tempBuffer = "achar|aluno|baixo|breve|caixa|canto|carta|certo|cinco|coisa|" +
                "disco|falar|horas|letra|livro|lugar|menor|menos|mesmo|mundo|" +
                "nobre|nunca|ontem|outro|parou|pensa|pobre|ponto|praia|salas|" +
                "sobre|terra|troca|trono|verde";

        brazilianPasswords = tempBuffer.split("\\|");
    }

    @BeforeMethod
    public void setUp() {
        Password.setPossiblePasswords(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void exceptionTest() {
        Password.getPasswords(new String[]{});
    }

    @DataProvider
    public Object[][] validInputTestProvider() {
        return new Object[][]{
                {new String[]{"a", "", "", "", ""}, 2, "achar"},
                {new String[]{"z", "", "", "", ""}, 1, "No passwords found"},
                {new String[]{"", "", "", "", ""}, 0, null},
                {null, 0, null}
        };
    }

    @Test(dataProvider = "validInputTestProvider")
    public void validInputTest(String[] input, int expectedSize, String expectedFirstIndex) {
        Password.setPossiblePasswords(brazilianPasswords);

        List<String> results = Password.getPasswords(input);
        assertEquals(results.size(), expectedSize);
        if (expectedFirstIndex != null)
            assertEquals(results.get(0), expectedFirstIndex);
    }

    @AfterClass
    public void tearDown() {
        Password.setPossiblePasswords(null);
        brazilianPasswords = null;
    }
}
