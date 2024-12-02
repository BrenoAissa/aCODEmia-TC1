import com.github.javafaker.Faker;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Log4j2

@Nested

public class TestModalidadesScreen {
    public static final String LOCALDRIVER = "src/main/resources/drivers/chromedriver.exe";
    public static final String PROPERTY = "webdriver.chrome.driver";
    public static final String URL = "https://ciscodeto.github.io/AcodemiaGerenciamento/ymodalidades.html";
    public static final int WAITTIME = 7;
    public static final String XPATH_MENSAGEM = "/html/body/div[1]/div/p";
    public static final String XPATH_BUTTON_INSERT = "/html/body/div[2]/main/div[1]/ul/li[3]/a";
    public static final String XPATH_CONTAINERDEAVISO = "/html/body/div[1]/div";
    public static final String XPATH_INPUT_FIELD ="/html/body/div[2]/main/div[1]/ul/li[1]/input";
    public static final String XPATH_BUTTON_FINALIZAR = "/html/body/div[1]/div/form/div[4]/a[2]";
    public static final String XPATH_BUTTON_LISTAR = "/html/body/div[2]/main/div[1]/ul/li[2]/a";
    public static final String XPATH_CONTENTTABLE = "/html/body/div[2]/main/div[2]";
    public static final String XPATH_CONTAINER_INSERT = "/html/body/div[1]/div";
    public static final String XPATH_DATA_OFERECIMENTO = "/html/body/div[1]/div/form/div[1]/input";
    public static final String X_PATH_DESCRICAO = "/html/body/div[1]/div/form/input[1]";
    public static final String X_PATH_NOME_PROFESSOR = "/html/body/div[1]/div/form/div[3]/input";

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
        if (driver == null){
            driver = new ChromeDriver(chromeOptions);
            driver.get(URL);// Acessa o site Acodemia
        }
    }

    @Test
    @Tag("Modalidades Screend")
    @DisplayName("Should open page and click on Incluir return Modalidades not exist")
    public void shouldopenpageandclickonIncluirreturnModalidadesnotexist() throws InterruptedException {
        final WebElement button = getWebElement(driver, XPATH_BUTTON_INSERT);
        button.click();
        final WebElement container = getWebElement(driver, XPATH_CONTAINERDEAVISO);

        final String mensagemDevolvida = container.findElement(By.xpath(XPATH_MENSAGEM)).getText();
        Assertions.fail("Precisava retornar a mensagem solicitando o código da modalidade para inserção, e a mensagem que retorna é: " + mensagemDevolvida);
    }

    @Test
    @Tag("Modalidades Screend")
    @DisplayName("Should show error message when inserting a negative number in the inclusion modalidade")
    public void shouldshowerrormessagewheninsertinganegativenumberintheinclusionmodalidade() throws InterruptedException {

        final WebElement inputField = getWebElement(driver, XPATH_INPUT_FIELD);
        String codigo = faker.numerify("-###");
        inputField.sendKeys(codigo);
        new WebDriverWait(driver, Duration.ofSeconds(WAITTIME))
                .until(in -> codigo.equals(inputField.getAttribute("value")));
        final WebElement button = getWebElement(driver, XPATH_BUTTON_INSERT);
        button.click();
        final WebElement buttonFinalizar = getWebElement(driver,XPATH_BUTTON_FINALIZAR);
        buttonFinalizar.click();
        final WebElement buttonListar = getWebElement(driver, XPATH_BUTTON_LISTAR);
        buttonListar.click();
        final WebElement contentTable = getWebElement(driver, XPATH_CONTENTTABLE);
        Assertions.fail("Não foi retornado mensagem sobre inserção de número negativo, segue os dados permitidos em cadastro: " + contentTable.getText());
    }

    @Test
    @Tag("Modalidades Screend")
    @DisplayName("Should display error message if any field is empty when Finalizar is clicked")
    public void shouldShowErrorMessageIfFieldIsEmptyOnFinalizar() throws InterruptedException {

        final WebElement inputField = getWebElement(driver, XPATH_INPUT_FIELD);

        String codigo = faker.numerify("-###");
        inputField.sendKeys(codigo);

        final WebElement button = getWebElement(driver, XPATH_BUTTON_INSERT);
        button.click();

        // Localiza o container principal
        final WebElement container = getWebElement(driver, XPATH_CONTAINER_INSERT);

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

        final WebElement finalizarButton = getWebElement(driver, XPATH_BUTTON_FINALIZAR);
        finalizarButton.click();

        final WebElement contentTable = getWebElement(driver, XPATH_CONTENTTABLE);
            Assertions.fail("Não foi retornado mensagem sobre campos vazios" +
                    ", segue os dados permitidos em cadastro: " + contentTable.getText());
    }

    @Test
    @Tag("Modalidades Screend")
    @DisplayName("Should provide a date in the past for the offering days")
    public void Shouldprovideadateinthepastfortheofferingdays() {
        final WebElement inputField = getWebElement(driver, XPATH_INPUT_FIELD);
        String codigo = faker.numerify("-###");
        inputField.sendKeys(codigo);
        new WebDriverWait(driver, Duration.ofSeconds(WAITTIME))
                .until(in -> codigo.equals(inputField.getAttribute("value")));
        final WebElement buttonIncluir = getWebElement(driver,XPATH_BUTTON_INSERT);
        buttonIncluir.click();
        Date dataOferecimento = faker.date().past(60, TimeUnit.DAYS);
        String dataOferecimentoFormatada = new SimpleDateFormat("dd-MM-yyyy").format(dataOferecimento);
        final WebElement dateInput = getWebElement(driver, XPATH_DATA_OFERECIMENTO);
        dateInput.click();
        dateInput.sendKeys(dataOferecimentoFormatada);
        final WebElement buttonFinalizar = getWebElement(driver,XPATH_BUTTON_FINALIZAR);
        buttonFinalizar.click();
        final WebElement buttonListar = getWebElement(driver,XPATH_BUTTON_LISTAR);
        buttonListar.click();
        final WebElement contentTable = getWebElement(driver,XPATH_CONTENTTABLE);
        Assertions.fail("Não foi retornado o erro devido a data no passado, segue os dados permitidos em cadastro: " + contentTable.getText());
    }

    @Test
    @Tag("Modalidades Screend")
    @DisplayName("should insert number in teacher's name")
    public void shouldinsertnumberinteachersname() {
        final WebElement inputField = getWebElement(driver, XPATH_INPUT_FIELD);
        String codigo = faker.numerify("###");
        inputField.sendKeys(codigo);
        new WebDriverWait(driver, Duration.ofSeconds(WAITTIME))
                .until(in -> codigo.equals(inputField.getAttribute("value")));
        final WebElement buttonIncluir = getWebElement(driver, XPATH_BUTTON_INSERT);
        buttonIncluir.click();
        final WebElement nomeProfessor = getWebElement(driver, X_PATH_NOME_PROFESSOR);
        String nome = faker.numerify("####");
        nomeProfessor.sendKeys(nome);
        final WebElement buttonFinalizar = getWebElement(driver, XPATH_BUTTON_FINALIZAR);
        buttonFinalizar.click();
        final WebElement contentTable = getWebElement(driver, XPATH_CONTENTTABLE);
        Assertions.fail("Não podemos inserir número no nome, segue o preenchimento do nome: " + contentTable.getText());
    }

    //@AfterEach
    //void tearDown() {
    // driver.quit(); // Encerra o driver e fecha o navegador
    //}
}


