import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

@Log4j2
public class TestChromeDriver {
    private WebDriver driver;
    ChromeOptions chromeOptions = new ChromeOptions();


    @BeforeEach
    void setUp() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver.exe");
        chromeOptions.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(chromeOptions);
    }

    @Test
    @DisplayName("Should open and close chrome browser")
    public void shouldOpenAndCloseChromeBrowser() throws InterruptedException {
        log.info("Accessing site");
        driver.get("https://www.google.com"); // Acessa o Google
        Thread.sleep(1000); // Espera por 1 segundo
    }

    @AfterEach
    void tearDown() {
        driver.quit(); // Encerra o driver e fecha o navegador
    }
}
