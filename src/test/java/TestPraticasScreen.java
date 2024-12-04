import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.github.javafaker.Faker;

import java.time.Duration;

@Log4j2
public class TestPraticasScreen {
    Faker faker = new Faker();
    public static final String LOCALDRIVER = "src/main/resources/drivers/chromedriver.exe";
    public static final String PROPERTY = "webdriver.chrome.driver";
    public static final String URL = "https://ciscodeto.github.io/AcodemiaGerenciamento/ypraticas.html";
    public static final int WAITTIME = 7;
    public String cpfGenerate = faker.numerify("###.###.###-##");
    public String modalidadeGenerate = faker.lorem().word();
    private WebDriver driver;
    ChromeOptions chromeOptions = new ChromeOptions();
    public WebElement getWebElement( WebDriver driver,String xPath) {
        return new WebDriverWait(driver, Duration.ofSeconds(WAITTIME))
                .until(ExpectedConditions.elementToBeClickable(By.xpath(xPath)));

    }

    @BeforeEach
    void setUp() {
        System.setProperty(PROPERTY, LOCALDRIVER);
        chromeOptions.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(chromeOptions);
        log.info("Accessing site");
        driver.get(URL); // Acessa o site Acodemia
    }

    @Test
    @DisplayName("Should open Site and don't fill CPF, with error return")
    public void shouldOpenSiteAndClickListarWithoutFillCPFWithErrorReturn() {
        final WebElement buttonListar = driver.findElement(By.id("listar-pratica"));
        buttonListar.click();
        try{
            // Espera pelo container da mensagem
            isElementPresent(driver, By.id("modal-conteudo"));
        }catch (Exception e){
            Assertions.fail("A mensagem de aviso não foi gerada!");
        }
    }

    @Test
    @DisplayName("Should open Site and fill CPF and check error message")
    public void shouldOpenSiteAndFillCPF() {
        // Gera um CPF válido usando o Faker numerify
        final WebElement fieldCPF = driver.findElement(By.id("buscador-cpf"));

        // Preenche o campo com o CPF
        fieldCPF.sendKeys(cpfGenerate);

        // Localiza o botão Insert
        final WebElement buttonInsert = driver.findElement(By.id("incluir-pratica"));
        buttonInsert.click();

        // Verifica se o container está visível
        Assertions.assertTrue(isElementPresent(driver, By.id("modal-conteudo")), "A mensagem de aviso apareceu!");
        //driver.quit();
    }

    @Test
    @DisplayName("Should open Site and fill Modalidade and check error message")
    public void shouldOpenSiteAndFillModalidade() {
        // Gera uma string aleatória para o campo Modalidade com faker lorem word

        // Localiza o campo de Modalidade
        final WebElement fieldModalidade = driver.findElement(By.id("buscador-codigo"));

        // Preenche o campo de Modalidade com a string gerada
        fieldModalidade.sendKeys(modalidadeGenerate);

        // Localiza o botão Insert
        final WebElement buttonInsert = driver.findElement(By.id("incluir-pratica"));
        buttonInsert.click();

        // Verifica se o container está visível (ou presente)
        Assertions.assertTrue(isElementPresent(driver, By.id("modal-conteudo")), "A mensagem de aviso apareceu!");
        //driver.quit();
    }

    @Test
    @DisplayName("Should open Site and fill CPF and Modalidade")
    public void shouldOpenSiteAndFillCPFAndModalidade() {
        // Gera um CPF válido usando o Faker numerify

        // Localiza o campo de CPF utilizando o XPATH
        final WebElement fieldCPF = driver.findElement(By.id("buscador-cpf"));

        // Preenche o campo com o CPF gerado
        fieldCPF.sendKeys(cpfGenerate);

        // Localiza o campo de Modalidade
        final WebElement fieldModalidade = driver.findElement(By.id("buscador-codigo"));

        // Preenche o campo de Modalidade com a string gerada
        fieldModalidade.sendKeys(modalidadeGenerate);

        // Localiza o botão Insert
        final WebElement buttonInsert = driver.findElement(By.id("incluir-pratica"));
        buttonInsert.click();

        // Verifica se o container está visível
        Assertions.assertTrue(isElementPresent(driver, By.id("modal-conteudo")), "A mensagem de aluno ou modalidade");
        //driver.quit();
    }

    private boolean isElementPresent(WebDriver driver, By locator) {
        try {
            driver.findElement(locator);
            return true; // O elemento está presente
        } catch (NoSuchElementException e) {
            return false; // O elemento não está presente
        }

    /*@AfterEach
    void tearDown() {
    driver.quit(); // Encerra o driver e fecha o navegador
    }*/


    }
}
