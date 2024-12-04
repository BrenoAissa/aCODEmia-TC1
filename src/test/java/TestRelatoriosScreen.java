import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import com.github.javafaker.Faker;

@Log4j2
public class TestRelatoriosScreen {

    private WebDriver driver;
    private TestPraticasScreen testPraticasScreen;
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
        driver.get(URL);
    }

    @Test
    @DisplayName("Should open and close chrome browser")
    public void shouldOpenAndCloseChromeBrowser() throws InterruptedException {
        log.info("Accessing site");
        driver.get(URL); // Acessa o aCODEmia
        Thread.sleep(1000); // Espera por 1 segundo
    }

    @AfterEach
    void tearDown() {
        driver.quit(); // Encerra o driver e fecha o navegador
    }
}
