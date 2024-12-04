package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class PraticasScreenPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Elementos da página
    private final By cpfField = By.id("buscador-cpf");

    private final By modalidadeField = By.id("buscador-cpf");

    private final By listButton = By.id("listar-pratica");

    private final By includeButton = By.id("incluir-pratica");

    private final By deleteButton = By.id("excluir-pratica");

    private final By modalContent = By.id("modal-conteudo");

    private final By deletePraticaButton =  By.xpath("//a[@onclick='excluirPratica([0,1]);fecharPopUp();']");


    public PraticasScreenPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Tempo de espera
    }

    public void fillCpf (String cpf) {
        WebElement campoCpfElement = wait.until(ExpectedConditions.elementToBeClickable(cpfField));
        campoCpfElement.clear();
        campoCpfElement.sendKeys(cpf);
    }

    public void fillModalidade (String modalidade) {
        WebElement campoModalidadeElement = wait.until(ExpectedConditions.elementToBeClickable(modalidadeField));
        campoModalidadeElement.clear();
        campoModalidadeElement.sendKeys(modalidade);
    }

    public String getCpfValue() {
        WebElement campoCpfElement = wait.until(ExpectedConditions.visibilityOfElementLocated(cpfField));
        return campoCpfElement.getAttribute("value");
    }

    public void clickList () {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(listButton));
        button.click();
    }

    public void clickInclude () {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(includeButton));
        button.click();
    }

    public void clickDelete () {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(deleteButton));
        button.click();
    }

    public void clickDeletePraticaPopUp () {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(deletePraticaButton));
        button.click();
    }

    public boolean isModalContentVisible() {
        try {
            WebElement modalElement = wait.until(ExpectedConditions.visibilityOfElementLocated(modalContent));
            return modalElement.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}