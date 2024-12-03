import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@Log4j2
public class TestPraticasScreen {
    public static final String CONTAINERDEAVISO = "/html/body/div[1]/div";
    public static final String LOCALDRIVER = "src/main/resources/drivers/chromedriver.exe";
    public static final String PROPERTY = "webdriver.chrome.driver";
    public static final String URL = "https://ciscodeto.github.io/AcodemiaGerenciamento/ypraticas.html";
    public static final int WAITTIME = 7;
    public static final String XPATH_MENSAGEM = "/html/body/div[1]/div/p";
    public static final String XPATH_BUTTON_INSERT = "/html/body/div[2]/main/div[1]/ul/li[4]/a";

    private WebDriver driver;
    ChromeOptions chromeOptions = new ChromeOptions();

    @BeforeEach
    void setUp() {
        System.setProperty(PROPERTY, LOCALDRIVER);
        chromeOptions.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(chromeOptions);
    }

    @Test
    @DisplayName("Should open Site and click on Incluir return User not exist")
    public void shouldOpenSite() throws InterruptedException {
        log.info("Accessing site");
        driver.get(URL); // Acessa o site Acodemia
    }
    /*@AfterEach
    void tearDown() {
    driver.quit(); // Encerra o driver e fecha o navegador
    }*/
}
