import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import com.github.javafaker.Faker;
import pages.RelatoriosScreenPage;

@Log4j2
public class TestRelatoriosScreen {

    private WebDriver driver;
    private RelatoriosScreenPage relatoriosScreenPage;
    Faker faker = new Faker();
    public static final String LOCALDRIVER = "src/main/resources/drivers/chromedriver.exe";
    public static final String PROPERTY = "webdriver.chrome.driver";
    public static final String URL = "https://ciscodeto.github.io/AcodemiaGerenciamento/yrelatorios.html";

    @BeforeEach
    void setUp() {
        System.setProperty(PROPERTY, LOCALDRIVER);
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(chromeOptions);
        relatoriosScreenPage = new RelatoriosScreenPage(driver);
        driver.get(URL);
    }

    @Test
    @DisplayName("Should not allow string input in CPF field")
    public void shouldNotAllowStringInCpfField() {
        String stringGenerate = faker.lorem().word();
        relatoriosScreenPage.fillCpf(stringGenerate);
        String cpfValue = relatoriosScreenPage.getCpfValue();
        Assertions.assertFalse(cpfValue.equals(stringGenerate), "Era esperado que o campo não fosse preenchido por string");
    }


//    @AfterEach
//    void tearDown() {
//        driver.quit(); // Encerra o driver e fecha o navegador
//    }
}
