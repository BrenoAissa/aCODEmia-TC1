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
    public String stringGenerate = faker.lorem().word();
    public String numberGenerate = faker.number().toString();

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
        relatoriosScreenPage.fillCpf(stringGenerate);
        String cpfValue = relatoriosScreenPage.getCpfValue();
        Assertions.assertFalse(cpfValue.equals(stringGenerate), "Era esperado que o campo não fosse preenchido por string");
    }

    @Test
    @DisplayName("Should Fill The Field Modalidade Without Presenting An Error")
    public void shouldFillTheFieldModalideWithoutPresentingAnError() {
        relatoriosScreenPage.fillModalidade(numberGenerate);
        String getModalidadeValue = relatoriosScreenPage.getModalidadeValue();
        Assertions.assertTrue(getModalidadeValue.equals(numberGenerate), "Era esperado que o campo fosse preenchido por string, caractere especial ou número");
    }

//    @AfterEach
//    void tearDown() {
//        driver.quit(); // Encerra o driver e fecha o navegador
//    }
}
