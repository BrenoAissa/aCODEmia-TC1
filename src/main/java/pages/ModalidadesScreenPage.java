package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ModalidadesScreenPage {
    private WebDriver driver;
    private final WebDriverWait wait;
    public static final String LOCALDRIVER = "src/main/resources/drivers/chromedriver.exe";
    public static final String PROPERTY = "webdriver.chrome.driver";
    public static final String URL = "https://ciscodeto.github.io/AcodemiaGerenciamento/ymodalidades.html";
    public static final int WAITTIME = 10;
    public static final  By BymensagemAviso = By.xpath("/html/body/div[1]/div/p");
    public static final  By BybuttonInserir = By.xpath("/html/body/div[2]/main/div[1]/ul/li[3]/a");
    public static final  By BycontainerAviso = By.xpath("/html/body/div[1]/div");
    public static final  By ByinputField = By.xpath("/html/body/div[2]/main/div[1]/ul/li[1]/input");
    public static final  By BybuttonListar = By.xpath("/html/body/div[2]/main/div[1]/ul/li[2]/a");
    public static final  By BycontentTable = By.xpath("/html/body/div[2]/main/div[2]") ;
    public static final  By BydataOferecimento = By.xpath("/html/body/div[1]/div/form/div[1]/input") ;
    public static final  By Bydescricao = By.xpath("/html/body/div[1]/div/form/input[1]");
    public static final  By Byprofessores = By.xpath("/html/body/div[1]/div/form/div[3]/input");
    public static final  By BynomeProfessor = By.xpath("/html/body/div[1]/div/form/div[3]/input");
    public static final  By Bydescription = By.xpath("/html/body/div[1]/div/form/input[1]");
    public static final  By Byduracao = By.xpath("/html/body/div[1]/div/form/input[2]") ;
    public static final  By Byvalor = By.xpath("/html/body/div[1]/div/form/input[3]");
    public static final  By Bydias = By.xpath("/html/body/div[1]/div/form/div[1]/input");
    public static final  By Byhorarios = By.xpath("/html/body/div[1]/div/form/div[2]/input");
    public static final  By BybuttonAlterar = By.xpath("/html/body/div[2]/main/div[1]/ul/li[4]/a");
    public static final  By ByexcluirButton = By.xpath("/html/body/div[2]/main/div[1]/ul/li[5]/a");
    public static final  By ByexcluirButtonContainer = By.xpath("/html/body/div[1]/div/div/a[1]") ;
    public static final By BybuttonFinalizar = By.xpath("/html/body/div[1]/div/form/div[4]/a[2]");

    public ModalidadesScreenPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(WAITTIME));
    }

    public WebElement getWebElement(WebDriver driver, By by) {
        return wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    public void clickInsertButton(){
        final WebElement button = getWebElement(driver, BybuttonInserir);
        button.click();
    }

    public String clickContainerMensagem(){
        // Espera pelo container da mensagem e clicar em fechar no container de mensagem
        final WebElement container = getWebElement(driver, BycontainerAviso);
        String returnedText = container.findElement(BymensagemAviso).getText();
        return returnedText;
    }

    public void fillCodigo (String codigo) {
        //Inserir codigo com dados.
        final WebElement inputField = getWebElement(driver, ByinputField);
        inputField.sendKeys(codigo);
    }

    public void fillDataOferecimento(String data){
        final WebElement dateInput = getWebElement(driver, BydataOferecimento);
        dateInput.click();
        dateInput.sendKeys(data);
    }

    public void fillDescription (String description) {
        //Inserir codigo com dados.
        final WebElement inputField = getWebElement(driver, Bydescricao);
        inputField.sendKeys(description);
    }

    public void fillDuration (String horaFormatada) {
        final WebElement inputField = getWebElement(driver, Byduracao);
        inputField.sendKeys(horaFormatada);
    }

    public void fillValue (String valorAleatorio) {
        final WebElement inputField = getWebElement(driver, Byvalor);
        inputField.sendKeys(valorAleatorio);
    }

    public void fillHour(String hour){
        final WebElement dateInput = getWebElement(driver, Byhorarios);
        dateInput.click();
        dateInput.sendKeys(hour);
    }

    public String getDescriptionValue() {
        WebElement campoCpfElement = wait.until(ExpectedConditions.visibilityOfElementLocated(Bydescricao));
        return campoCpfElement.getAttribute("value");
    }

    public void waitCodigoFill(String codigo){
        final WebElement input = getWebElement(driver,ByinputField);
        new WebDriverWait(driver, Duration.ofSeconds(WAITTIME))
                .until(in -> codigo.equals(input.getAttribute("value")));
    }

    public void clickButtonFinalizar(){
        //Clica no botão finalizar
        final WebElement buttonFinaliza = getWebElement(driver,BybuttonFinalizar);
        buttonFinaliza.click();
    }

    public void clickButtonListar(){
        //clica no botão listar
        final WebElement buttonLista = getWebElement(driver, BybuttonListar);
        buttonLista.click();
    }

    public void clickInsertTeacher(String name){
        //clica no botão listar
        final WebElement buttonTeacher= getWebElement(driver, BynomeProfessor);
        buttonTeacher.sendKeys(name);
    }

    public String getContentTableText(){
        final WebElement contentTable = getWebElement(driver, BycontentTable);
        return contentTable.getText();
    }

    public void testContainerAviso(){
        final WebElement container = getWebElement(driver, BycontainerAviso);
    }

    public void clickButtonAlterar(){
        final WebElement buttonAlterar = getWebElement(driver, BybuttonAlterar);
        buttonAlterar.click();
    }

    public void clickButtonExcluir(){
        final WebElement buttonExcluir = getWebElement(driver, ByexcluirButton);
        buttonExcluir.click();
    }

    public void clickContainerExcluir(){
        final WebElement containerExcluir = getWebElement(driver, BycontainerAviso);
        containerExcluir.findElement(ByexcluirButtonContainer).click();
    }

}


