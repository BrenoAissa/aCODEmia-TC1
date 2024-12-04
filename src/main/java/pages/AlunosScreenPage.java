package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class AlunosScreenPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    public static final By BycontainerAviso =  By.xpath("/html/body/div[1]/div");

    public static final int WAITTIME =10;
    public static final By BymensagemAviso = By.xpath("/html/body/div[1]/div/p");
    public static final By BybuttonInserir = By.xpath("/html/body/div[2]/main/div[1]/ul/li[3]/a");
    public static final By BybuttonFechar = By.xpath("/html/body/div[1]/div/div/a");
    public static final By BycpfInput = By.xpath("/html/body/div[2]/main/div[1]/ul/li[1]/input");
    public static final By BybuttonFinalizar = By.xpath("/html/body/div[1]/div/form/div[3]/a[2]");
    public static final By BybuttonListar = By.xpath("/html/body/div[2]/main/div[1]/ul/li[2]/a");
    public static final By BycontentTable = By.xpath("/html/body/div[2]/main/div[2]");
    public static final By BydateInput = By.xpath("/html/body/div[1]/div/form/input[2]");
    public static final By BypesoInput = By.xpath("/html/body/div[1]/div/form/input[3]");
    public static final By ByalturaInput = By.xpath("/html/body/div[1]/div/form/input[4]");
    public static final By BybuttonAlterar = By.xpath("/html/body/div[2]/main/div[1]/ul/li[4]/a");
    public static final By Byheight = By.xpath("/html/body/div[2]/main/div[2]/table/tbody/tr/td[6]");
    public static final By ByemailInput = By.xpath("/html/body/div[1]/div/form/div[1]/input");
    public static final By BytelefoneInput = By.xpath("/html/body/div[1]/div/form/div[2]/input");
    public static final By ByemailConfirmButton = By.xpath("/html/body/div[1]/div/form/a[1]");
    public static final By BytelefoneConfirmButton = By.xpath("/html/body/div[1]/div/form/a[2]");
    public static final By BynomeInput= By.xpath("/html/body/div[1]/div/form/input[1]");
    public static final By BysexoDropdown = By.xpath("/html/body/div[1]/div/form/select");
    public static final By ByexcluirButton = By.xpath("/html/body/div[2]/main/div[1]/ul/li[5]/a");
    public static final By ByexcluirContainerButton = By.xpath("/html/body/div[1]/div/div/a[1]");

    public AlunosScreenPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(WAITTIME));
    }

    public WebElement getWebElement(WebDriver driver,By by) {
        return wait.until(ExpectedConditions.elementToBeClickable(by));
    }
    public void clickInsertButton(){
        // Espera pelo botão e clica em insert
        final WebElement button = getWebElement(driver, BybuttonInserir);
        button.click();
    }
    public String clickContainerFecharButton(){
        // Espera pelo container da mensagem e clicar em fechar no container de mensagem
        final WebElement container = getWebElement(driver, BycontainerAviso);
        String returnedText = container.findElement(BymensagemAviso).getText();
        container.findElement(BybuttonFechar).click();
        return returnedText;
    }
    public void fillCpf (String cpf){
        //Inserir CPF com dados.
        final WebElement input = getWebElement(driver,BycpfInput);
        input.sendKeys(cpf);

    }
    public void waitCpfFill(String cpf){
        final WebElement input = getWebElement(driver,BycpfInput);
        new WebDriverWait(driver, Duration.ofSeconds(WAITTIME))
                .until(in -> cpf.equals(input.getAttribute("value")));
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
    public String getContentTableText(){
        final WebElement contentTable = getWebElement(driver, BycontentTable);
        return contentTable.getText();
    }
    public void testContainerAviso(){
        final WebElement container = getWebElement(driver, BycontainerAviso);
    }
    public void fillDatanasc(Date data){
        String dataNascimentoFormatada = new SimpleDateFormat("dd-MM-yyyy").format(data);
        final WebElement dateInput = getWebElement(driver, BydateInput);
        dateInput.click();
        dateInput.sendKeys(dataNascimentoFormatada);
    }
    public void fillPeso(String peso){
        final WebElement pesoInput = getWebElement(driver, BypesoInput);
        pesoInput.sendKeys(peso);
    }
    public void fillAltura(String altura){
        // Adiciona a altura
        final WebElement alturaInput = getWebElement(driver, ByalturaInput);
        alturaInput.sendKeys(altura);
    }
    public void clickButtonAlterar(){
        final WebElement buttonAlterar = getWebElement(driver, BybuttonAlterar);
        buttonAlterar.click();
    }
    public String getHeightValue(){
        final WebElement height = getWebElement(driver,Byheight);
        return height.getText();
    }
    public void fillName(String name){
        // Preencher o nome
        final WebElement nomeInput = getWebElement(driver, BynomeInput);
        nomeInput.sendKeys(name);
    }
    public void fillSexo(String sexo){
        final WebElement sexoDropdown = getWebElement(driver, BysexoDropdown);
        Select optionSelected = new Select(sexoDropdown);
        optionSelected.selectByVisibleText(sexo);
    }
    public void fillEmail(String email){
        final WebElement emailInput = getWebElement(driver, ByemailInput);
        emailInput.sendKeys(email);
    }
    public void clickEmailButton(){
        final WebElement emailButton = getWebElement(driver, ByemailConfirmButton);
        emailButton.click();
    }
    public void fillTelefone(String telefone){
        final WebElement telefoneInput = getWebElement(driver, BytelefoneInput);
        telefoneInput.sendKeys(telefone);
    }
    public void clickTelefoneButton(){
        final WebElement telefoneButton = getWebElement(driver, BytelefoneConfirmButton);
    }
    public void clickButtonExcluir(){
        final WebElement buttonExcluir = getWebElement(driver, ByexcluirButton);
        buttonExcluir.click();
    }
    public void clickContainerExcluir(){
        final WebElement containerExcluir = getWebElement(driver, BycontainerAviso);
        containerExcluir.findElement(ByexcluirContainerButton).click();
    }

}
