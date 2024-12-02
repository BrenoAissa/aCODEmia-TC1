
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



import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Log4j2
public class TestAulasScreen {
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
    @DisplayName("Should open Site and don't fill user infos, with error return")
    public void shouldOpenSiteAndDontFillUserInfosWithErrorReturn() {
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

    //@AfterEach
    //void tearDown() {
        //try{
            //driver.quit();
        //}catch (Exception e){
            //System.out.println("\nNavegador fechado!");
        //}
    //}
}
