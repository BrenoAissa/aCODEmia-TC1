import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import com.github.javafaker.Faker;
import pages.PraticasScreenPage;

@Log4j2
public class TestPraticasScreen {

    private WebDriver driver;
    private PraticasScreenPage praticasScreenPage;
    private TestModalidadesScreen testModalidadesScreen;
    //private TestAlunosScreen testAlunosScreen;
    Faker faker = new Faker();
    public static final String LOCALDRIVER = "src/main/resources/drivers/chromedriver.exe";
    public static final String PROPERTY = "webdriver.chrome.driver";
    public static final String URL = "https://ciscodeto.github.io/AcodemiaGerenciamento/ypraticas.html";

    @BeforeEach
    void setUp() {
        System.setProperty(PROPERTY, LOCALDRIVER);
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(chromeOptions);
        praticasScreenPage = new PraticasScreenPage(driver);  // Inicializa a PageObject
        driver.get(URL);
    }

    @Test
    @DisplayName("Should open Site and don't fill CPF, with error return")
    public void shouldOpenSiteAndClickListarWithoutFillCPFWithErrorReturn() {
        praticasScreenPage.clickList();
        Assertions.assertTrue(praticasScreenPage.isModalContentVisible(), "A mensagem de erro não foi gerada!");
    }

    @Test
    @DisplayName("Should open Site and fill CPF and check error message")
    public void shouldOpenSiteAndFillCPF() {
        String cpfGenerate = faker.numerify("###.###.###-##");
        praticasScreenPage.fillCpf(cpfGenerate);
        praticasScreenPage.clickInclude();
        Assertions.assertTrue(praticasScreenPage.isModalContentVisible(), "A mensagem de erro foi gerada!");
    }

    @Test
    @DisplayName("Should open Site and fill Modalidade and check error message")
    public void shouldOpenSiteAndFillModalidade() {
        String modalidadeGenerate = faker.lorem().word();
        praticasScreenPage.fillModalidade(modalidadeGenerate);
        praticasScreenPage.clickInclude();
        Assertions.assertTrue(praticasScreenPage.isModalContentVisible(), "A mensagem de erro foi gerada!");
    }

    @Test
    @DisplayName("Should open Site and click Delete without CPF and Modalidade, showing error message")
    public void shouldOpenSiteAndClickDeleteWithoutFillCPFAndModalidade() {
        praticasScreenPage.clickDelete();
        Assertions.assertTrue(praticasScreenPage.isModalContentVisible(), "A mensagem de erro foi gerada após tentar excluir sem preencher CPF ou Modalidade.");
    }

    @Test
    @DisplayName("Should open site and click delete by filling valid CPF")
    public void shouldOpenSiteAndClickDeleteFillingValidCPF() {
        //testAlunosScreen.shouldOpenSiteAndClickOnAlterarAndFillTheFields();
        //praticasScreenPage.fillCpf(); Aqui ele deve pegar o cpf inserido no testAlunosScreen.shouldOpenSiteAndClickOnAlterarAndFillTheFields.
        praticasScreenPage.clickDelete();
        praticasScreenPage.clickDeletePraticaPopUp();
        //Assertions.assertTrue(praticasScreenPage.isModalContentVisible(), "É esperado que o CPF tenha sumido do histórico");
    }

    @AfterEach
    void tearDown() {
        driver.quit(); // Encerra o driver e fecha o navegador
    }
}
