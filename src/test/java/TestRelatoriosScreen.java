import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import com.github.javafaker.Faker;
import pages.RelatoriosScreenPage;

@Log4j2
public class TestRelatoriosScreen {

    private WebDriver driver;
    private RelatoriosScreenPage relatoriosScreenPage;
    private TestModalidadesScreen testModalidadesScreen = new TestModalidadesScreen();
//    private TestAlunosScreen testAlunosScreen = new TestAlunosScreen();
//    private TestPraticasScreen testPraticasScreen = new TestPraticasScreen();
    Faker faker = new Faker();
    public static final String LOCALDRIVER = "src/main/resources/drivers/chromedriver.exe";
    public static final String PROPERTY = "webdriver.chrome.driver";
    public static final String URL = "https://ciscodeto.github.io/AcodemiaGerenciamento/yrelatorios.html";
    public String stringGenerate = faker.lorem().word();
    public String numberGenerate = faker.number().toString();
    public String cpfGenerate = faker.numerify("###.###.###-##");

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
    @DisplayName("Should open site and not allow string input in CPF field")
    public void shouldOpenSiteNotAllowStringInCpfField() {
        relatoriosScreenPage.fillCpf(stringGenerate);
        String cpfValue = relatoriosScreenPage.getCpfValue();
        Assertions.assertFalse(cpfValue.equals(stringGenerate), "Era esperado que o campo não fosse preenchido por string");
    }

    @Test
    @DisplayName("Should open site and fill the field Modalidade without presenting an error")
    public void shouldOpenSiteAndFillTheFieldModalideWithoutPresentingAnError() {
        relatoriosScreenPage.fillModalidade(numberGenerate);
        String getModalidadeValue = relatoriosScreenPage.getModalidadeValue();
        Assertions.assertTrue(getModalidadeValue.equals(numberGenerate), "Era esperado que o campo fosse preenchido por string, caractere especial ou número");
    }

    @Test
    @DisplayName("Should open site and fill the field Modalide and click on the Filter Button")
    public void shouldOpenSiteAndFillTheFieldModalideAndClickOnTheFilterButton (){
        testModalidadesScreen.shouldincludemodality();
        relatoriosScreenPage.fillModalidade(numberGenerate);
        relatoriosScreenPage.clickFilter();
        //implementar atualização da tabela
        //Assertions.assertTrue(True se a tabela atualizou para qualquer coisa, "Era esperado que o botão filtrasse pelo campo de modalidade");
    }


    @AfterEach
    void tearDown() {
        driver.quit(); // Encerra o driver e fecha o navegador
    }
}
