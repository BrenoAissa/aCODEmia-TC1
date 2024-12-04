package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class RelatoriosScreenPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private final By cpfField = By.id("buscador-cpf");

    private final By modalidadeField = By.id("buscador-codigo-modalidade");

    private final By listAllButton = By.xpath("//button[@onclick='listarTodosRelatorios()']");
    private final By filterButton = By.xpath("//button[@onclick='filtrarRelatorios()']");


    public RelatoriosScreenPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Tempo de espera
    }

    public void fillCpf (String cpf) {
        WebElement campoCpfElement = wait.until(ExpectedConditions.elementToBeClickable(cpfField));
        campoCpfElement.clear();
        campoCpfElement.sendKeys(cpf);
    }

    public String getCpfValue() {
        WebElement campoCpfElement = wait.until(ExpectedConditions.visibilityOfElementLocated(cpfField));
        return campoCpfElement.getAttribute("value");
    }

    public void fillModalidade (String modalidade) {
        WebElement campoModalidadeElement = wait.until(ExpectedConditions.elementToBeClickable(modalidadeField));
        campoModalidadeElement.clear();
        campoModalidadeElement.sendKeys(modalidade);
    }

    public String getModalidadeValue() {
        WebElement campoModalidadeElement = wait.until(ExpectedConditions.visibilityOfElementLocated(modalidadeField));
        return campoModalidadeElement.getAttribute("value");
    }

    public void clickListAll () {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(listAllButton));
        button.click();
    }

    public void clickFilter () {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(filterButton));
        button.click();
    }

}
