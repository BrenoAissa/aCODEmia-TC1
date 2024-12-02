
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.github.javafaker.Faker;


import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@Log4j2
public class TestAlunosScreen {
    public static final String XPATH_CONTAINERDEAVISO = "/html/body/div[1]/div";
    public static final String LOCALDRIVER = "src/main/resources/drivers/chromedriver.exe";
    public static final String PROPERTY = "webdriver.chrome.driver";
    public static final String URL = "https://ciscodeto.github.io/AcodemiaGerenciamento/yalunos.html";
    public static final int WAITTIME = 7;
    public static final String XPATH_MENSAGEM = "/html/body/div[1]/div/p";
    public static final String XPATH_BUTTON_INSERT = "/html/body/div[2]/main/div[1]/ul/li[3]/a";
    public static final String XPATH_BOTAO_FECHAR = "/html/body/div[1]/div/div/a";
    public static final String XPATH_INSERIR_CPF = "/html/body/div[2]/main/div[1]/ul/li[1]/input";
    public static final String XPATH_BUTTON_FINALIZAR = "/html/body/div[1]/div/form/div[3]/a[2]";
    public static final String XPATH_BUTTON_LISTAR = "/html/body/div[2]/main/div[1]/ul/li[2]/a";
    public static final String XPATH_CONTENTTABLE = "/html/body/div[2]/main/div[2]";
    public static final String XPATH_DATE_INPUT = "/html/body/div[1]/div/form/input[2]";
    public static final String XPATH_PESO_INPUT = "/html/body/div[1]/div/form/input[3]";
    public static final String XPATH_ALTURA_INPUT = "/html/body/div[1]/div/form/input[4]";
    public static final String XPATH_BUTTON_ALTERAR = "/html/body/div[2]/main/div[1]/ul/li[4]/a";
    public static final String XPATH_HEIGHT = "/html/body/div[2]/main/div[2]/table/tbody/tr/td[6]";
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
    @DisplayName("Should open Site and click on Incluir with a insert error message ")
    public void shouldOpenSiteAndClickOnIncluirWithAInsertErrorMessage() {

            // Espera pelo botão e clica
            final WebElement button = getWebElement(driver, XPATH_BUTTON_INSERT);
            button.click();

            // Espera pelo container da mensagem
            final WebElement container = getWebElement(driver, XPATH_CONTAINERDEAVISO);
            String returnedText = container.findElement(By.xpath(XPATH_MENSAGEM)).getText();
            container.findElement(By.xpath(XPATH_BOTAO_FECHAR)).click();
            // Verifica a mensagem retornada
            assertEquals("Por favor digite o CPF do aluno que está tentando inserir!",
                    returnedText);
    }

    @Test
    @DisplayName("Should open Site and don't fill user infos,only cpf, with error return")
    public void shouldOpenSiteAndDontFillUserInfosOnlyCpfWithErrorReturn() {
        final WebElement input = getWebElement(driver,XPATH_INSERIR_CPF);
        String cpf = faker.numerify("###.###.###-##");
        input.sendKeys(cpf);
        new WebDriverWait(driver, Duration.ofSeconds(WAITTIME))
                .until(in -> cpf.equals(input.getAttribute("value")));
        final WebElement buttonIncluir = getWebElement(driver,XPATH_BUTTON_INSERT);
        buttonIncluir.click();
        final WebElement buttonFinalizar = getWebElement(driver,XPATH_BUTTON_FINALIZAR);
        buttonFinalizar.click();
        final WebElement buttonListar = getWebElement(driver,XPATH_BUTTON_LISTAR);
        buttonListar.click();
        final WebElement contentTable = getWebElement(driver,XPATH_CONTENTTABLE);
        Assertions.fail("Não foi retornado o erro, segue os dados permitidos em cadastro: " + contentTable.getText());
    }
    @Test
    @DisplayName("Should open Site and don't fill CPF, with error return")
    public void shouldOpenSiteAndClickListarWithoutFillCPFWithErrorReturn() {
        final WebElement buttonListar = getWebElement(driver,XPATH_BUTTON_LISTAR);
        buttonListar.click();
        try{
            // Espera pelo container da mensagem
            final WebElement container = getWebElement(driver,XPATH_CONTAINERDEAVISO);
        }catch (Exception e){
            Assertions.fail("A mensagem de aviso não foi gerada!");
        }
    }

    @Test
    @DisplayName("Should open Site and fill a future date for Data de nascimento")
    public void shouldOpenSiteAndFillAFutureDateForDataDeNascimento() {
        final WebElement input = getWebElement(driver,XPATH_INSERIR_CPF);
        String cpf = faker.numerify("###.###.###-##");
        input.sendKeys(cpf);
        new WebDriverWait(driver, Duration.ofSeconds(WAITTIME))
                .until(in -> cpf.equals(input.getAttribute("value")));
        final WebElement buttonIncluir = getWebElement(driver,XPATH_BUTTON_INSERT);
        buttonIncluir.click();
        Date dataNascimento = faker.date().future(90,TimeUnit.DAYS);
        String dataNascimentoFormatada = new SimpleDateFormat("dd-MM-yyyy").format(dataNascimento);
        final WebElement dateInput = getWebElement(driver, XPATH_DATE_INPUT);
        dateInput.click();
        dateInput.sendKeys(dataNascimentoFormatada);
        final WebElement buttonFinalizar = getWebElement(driver,XPATH_BUTTON_FINALIZAR);
        buttonFinalizar.click();
        final WebElement buttonListar = getWebElement(driver,XPATH_BUTTON_LISTAR);
        buttonListar.click();
        final WebElement contentTable = getWebElement(driver,XPATH_CONTENTTABLE);
        Assertions.fail("Não foi retornado o erro, segue os dados permitidos em cadastro: " + contentTable.getText());
    }
    @Test
    @DisplayName("Should open Site and fill some fields with negative values return error")
    public void shouldOpenSiteAndFillSomeFieldsWithNegativeValuesReturn() {
        final WebElement input = getWebElement(driver,XPATH_INSERIR_CPF);
        String cpf = faker.numerify("###.###.###-##");
        input.sendKeys(cpf);
        new WebDriverWait(driver, Duration.ofSeconds(WAITTIME))
                .until(in -> cpf.equals(input.getAttribute("value")));
        final WebElement buttonIncluir = getWebElement(driver,XPATH_BUTTON_INSERT);
        buttonIncluir.click();
        final WebElement pesoInput = getWebElement(driver, XPATH_PESO_INPUT);
        String peso = faker.numerify("-##");
        pesoInput.sendKeys(peso);
        final WebElement alturaInput = getWebElement(driver, XPATH_ALTURA_INPUT);
        String alturaEmMetros = faker.numerify("-##");
        alturaInput.sendKeys(alturaEmMetros);
        final WebElement buttonFinalizar = getWebElement(driver,XPATH_BUTTON_FINALIZAR);
        buttonFinalizar.click();
        final WebElement buttonListar = getWebElement(driver,XPATH_BUTTON_LISTAR);
        buttonListar.click();
        final WebElement contentTable = getWebElement(driver,XPATH_CONTENTTABLE);
        Assertions.fail("Não foi retornado o erro, segue os dados permitidos em cadastro: " + contentTable.getText());
    }
    @Test
    @DisplayName("should open site and click on Alterar without fill the fields with error")
    public void shouldOpenSiteAndClickAlterarWithoutFillTheFieldsWithErrorReturn() {
        final WebElement input = getWebElement(driver,XPATH_INSERIR_CPF);
        String cpf = faker.numerify("###.###.###-##");
        input.sendKeys(cpf);
        new WebDriverWait(driver, Duration.ofSeconds(WAITTIME))
                .until(in -> cpf.equals(input.getAttribute("value")));
        final WebElement buttonIncluir = getWebElement(driver,XPATH_BUTTON_INSERT);
        buttonIncluir.click();
        WebElement buttonFinalizar = getWebElement(driver,XPATH_BUTTON_FINALIZAR);
        buttonFinalizar.click();
        final WebElement buttonAlterar = getWebElement(driver, XPATH_BUTTON_ALTERAR);
        buttonAlterar.click();
        buttonFinalizar = getWebElement(driver, XPATH_BUTTON_FINALIZAR);
        buttonFinalizar.click();

        try{
            final WebElement containerAviso = getWebElement(driver,XPATH_CONTAINERDEAVISO);
        }catch (Exception e){
            Assertions.fail("A mensagem de aviso não foi gerada!");
        }
    }
    @Test
    @DisplayName("Should open site and show the Altura of Aluno")
    public void shouldOpenSiteAndShowTheAlturaOfAluno() {
        final WebElement input = getWebElement(driver,XPATH_INSERIR_CPF);
        String cpf = faker.numerify("###.###.###-##");
        input.sendKeys(cpf);
        new WebDriverWait(driver, Duration.ofSeconds(WAITTIME))
                .until(in -> cpf.equals(input.getAttribute("value")));
        final WebElement buttonIncluir = getWebElement(driver,XPATH_BUTTON_INSERT);
        buttonIncluir.click();
        final WebElement pesoInput = getWebElement(driver, XPATH_PESO_INPUT);
        String peso = faker.numerify("-##");
        pesoInput.sendKeys(peso);
        final WebElement alturaInput = getWebElement(driver, XPATH_ALTURA_INPUT);
        String alturaEmMetros = faker.numerify("-#,#");
        alturaInput.sendKeys(alturaEmMetros);
        final WebElement buttonFinalizar = getWebElement(driver,XPATH_BUTTON_FINALIZAR);
        buttonFinalizar.click();
        final WebElement buttonListar = getWebElement(driver,XPATH_BUTTON_LISTAR);
        buttonListar.click();
        final WebElement height = getWebElement(driver, XPATH_HEIGHT);
        String heightValue = height.getText();
        assertNotEquals("undefined", heightValue,
                "O Valor da Altura está sendo representado como undefined");
    }
    //@AfterEach
    //void tearDown() {
        //try{
            //driver.quit();
        //}catch (Exception e){
            //System.out.println("\nNavegador fechado!");
        //}
    //}
}
