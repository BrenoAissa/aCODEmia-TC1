import lombok.extern.log4j.Log4j2;
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

@Log4j2
public class TestModalidadesScreen {
    public static final String LOCALDRIVER = "src/main/resources/drivers/chromedriver.exe";
    public static final String PROPERTY = "webdriver.chrome.driver";
    public static final String URL = "https://ciscodeto.github.io/AcodemiaGerenciamento/yalunos.html";
    public static final int WAITTIME = 7;
    public static final String XPATH_MENSAGEM = "/html/body/div[1]/div/p";
    public static final String XPATH_BUTTON_INSERT = "/html/body/div[2]/main/div[1]/ul/li[3]/a";
    public static final String CONTAINERDEAVISO = "/html/body/div[1]/div";

    private WebDriver driver;
    ChromeOptions chromeOptions = new ChromeOptions();

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

    //@AfterEach
    //void tearDown() {
    // driver.quit(); // Encerra o driver e fecha o navegador
    //}
}


