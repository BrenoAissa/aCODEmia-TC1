import com.github.javafaker.Faker;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Log4j2
public class TestModalidadesScreen {
    public static final String LOCALDRIVER = "src/main/resources/drivers/chromedriver.exe";
    public static final String PROPERTY = "webdriver.chrome.driver";
    public static final String URL = "https://ciscodeto.github.io/AcodemiaGerenciamento/ymodalidades.html";
    public static final int WAITTIME = 7;
    public static final String XPATH_MENSAGEM = "/html/body/div[1]/div/p";
    public static final String XPATH_BUTTON_INSERT = "/html/body/div[2]/main/div[1]/ul/li[3]/a";
    public static final String CONTAINERDEAVISO = "/html/body/div[1]/div";
    public static final String XPATH_INPUT_FIELD ="/html/body/div[2]/main/div[1]/ul/li[1]/input";
    public static final String XPATH_BUTTON_FINALIZAR = "/html/body/div[1]/div/form/div[4]/a[2]";
    public static final String XPATH_BUTTON_LISTAR = "/html/body/div[2]/main/div[1]/ul/li[2]/a";
    public static final String XPATH_CONTENTTABLE = "/html/body/div[2]/main/div[2]";
    public static final String XPATH_CONTAINER_INSERT = "/html/body/div[1]/div";

    Faker faker = new Faker();

    private WebDriver driver;
    ChromeOptions chromeOptions = new ChromeOptions();

    public WebElement getWebElement( WebDriver driver,String xPath) {
        new WebDriverWait(driver, Duration.ofSeconds(WAITTIME))
                .until(ExpectedConditions.elementToBeClickable(By.xpath(xPath)));
        return driver.findElement(By.xpath(xPath));
    }

    @BeforeEach
    void setUp() {
        System.setProperty(PROPERTY, LOCALDRIVER);
        chromeOptions.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(chromeOptions);
    }

    @Test
    @DisplayName("Should open page and click on Incluir return Modalidades not exist")
    public void shouldOpenSite() throws InterruptedException {
        log.info("Accessing site");
        driver.get(URL); // Acessa o site Acodemia
        final WebElement button = new WebDriverWait(driver, Duration.ofSeconds(WAITTIME))
                .until(ExpectedConditions.elementToBeClickable(By.xpath(XPATH_BUTTON_INSERT)));
        button.click();
        final WebElement container = new WebDriverWait(driver, Duration.ofSeconds(WAITTIME))
                .until(ExpectedConditions.elementToBeClickable(By.xpath(CONTAINERDEAVISO)));
        final String mensagemDevolvida = container.findElement(By.xpath(XPATH_MENSAGEM)).getText();
        System.out.println(mensagemDevolvida);
    }

    @Test
    @DisplayName("Should show error message when inserting a negative number in the inclusion modalidade")
    public void shouldShowErrorForNegativeNumber() throws InterruptedException {
        log.info("Accessing site");
        driver.get(URL);
        final WebElement inputField = new WebDriverWait(driver, Duration.ofSeconds(WAITTIME))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath(XPATH_INPUT_FIELD)));
        String codigo = faker.numerify("-###");
        inputField.sendKeys(codigo);
        new WebDriverWait(driver, Duration.ofSeconds(WAITTIME))
                .until(in -> codigo.equals(inputField.getAttribute("value")));
        final WebElement button = new WebDriverWait(driver, Duration.ofSeconds(WAITTIME))
                .until(ExpectedConditions.elementToBeClickable(By.xpath(XPATH_BUTTON_INSERT)));
        button.click();
        final WebElement buttonFinalizar = getWebElement(driver,XPATH_BUTTON_FINALIZAR);
        buttonFinalizar.click();
        final WebElement buttonListar = getWebElement(driver, XPATH_BUTTON_LISTAR);
        buttonListar.click();
        final WebElement contentTable = getWebElement(driver, XPATH_CONTENTTABLE);
        Assertions.fail("Não foi retornado mensagem sobre inserção de número negativo, segue os dados permitidos em cadastro: " + contentTable.getText());
    }

    @Test
    @DisplayName("Should display error message if any field is empty when Finalizar is clicked")
    public void shouldShowErrorMessageIfFieldIsEmptyOnFinalizar() throws InterruptedException {
        log.info("Accessing site");

        // Acessa o site
        driver.get(URL);

        final WebElement inputField = new WebDriverWait(driver, Duration.ofSeconds(WAITTIME))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath(XPATH_INPUT_FIELD)));

        String codigo = faker.numerify("-###");
        inputField.sendKeys(codigo);

        final WebElement button = new WebDriverWait(driver, Duration.ofSeconds(WAITTIME))
                .until(ExpectedConditions.elementToBeClickable(By.xpath(XPATH_BUTTON_INSERT)));
        button.click();


        // Localiza o container principal
        final WebElement container = new WebDriverWait(driver, Duration.ofSeconds(WAITTIME))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath(XPATH_CONTAINER_INSERT)));

        // Localiza todos os campos dentro do container
        List<WebElement> fields = container.findElements(By.xpath(".//input|.//textarea|.//select"));

        boolean isAnyFieldEmpty = false;

        // Verifica se algum campo está vazio
        for (WebElement field : fields) {
            String fieldType = field.getTagName();
            String value = "";

            switch (fieldType) {
                case "input":
                case "textarea":
                    // Obtém o valor do campo
                    value = field.getAttribute("value");
                    break;

                case "select":
                    // Obtém o texto da opção selecionada
                    WebElement selectedOption = field.findElement(By.xpath(".//option[@selected]"));
                    value = selectedOption.getText();
                    break;

                default:
                    log.warn("Field type {} is not being checked for value", fieldType);
                    continue;
            }

            log.info("Field value: {}", value);


            if (value == null || value.trim().isEmpty()) {
                isAnyFieldEmpty = true;
                break; // Não é necessário continuar verificando se já sabemos que há um campo vazio
            }
        }

        final WebElement finalizarButton = new WebDriverWait(driver, Duration.ofSeconds(WAITTIME))
                .until(ExpectedConditions.elementToBeClickable(By.xpath(XPATH_BUTTON_FINALIZAR)));
        finalizarButton.click();

        final WebElement contentTable = getWebElement(driver, XPATH_CONTENTTABLE);
            Assertions.fail("Não foi retornado mensagem sobre campos vazios" +
                    ", segue os dados permitidos em cadastro: " + contentTable.getText());
    }



    //@AfterEach
    //void tearDown() {
    // driver.quit(); // Encerra o driver e fecha o navegador
    //}
}


