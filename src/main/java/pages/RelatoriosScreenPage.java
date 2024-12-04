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

    private final By typeRelatorioSelect = By.id("tipo-relatorio");

    private final By alunosLink = By.linkText("Alunos");

    private final By modalidadeLink = By.linkText("Modalidades");


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

    public void clickPageAluno () {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(alunosLink));
        button.click();
    }

    public void clickPageModalidade () {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(modalidadeLink));
        button.click();
    }

    public void clickListAll () {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(listAllButton));
        button.click();
    }

    public void clickFilter () {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(filterButton));
        button.click();
    }

    public void selectTipoRelatorio(String tipoRelatorio) {
        WebElement tipoRelatorioElement = wait.until(ExpectedConditions.elementToBeClickable(typeRelatorioSelect));
        tipoRelatorioElement.click();

        WebElement selectedOption = tipoRelatorioElement.findElement(By.xpath("//option[@value='" + tipoRelatorio + "']"));
        selectedOption.click();
    }

    public boolean isTableContentVisible() {
        try {
            WebElement modalElement = wait.until(ExpectedConditions.visibilityOfElementLocated(typeRelatorioSelect));
            return modalElement.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

}
