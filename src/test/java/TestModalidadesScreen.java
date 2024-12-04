import com.github.javafaker.Faker;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.github.javafaker.shaded.snakeyaml.nodes.Tag.STR;
import static java.awt.SystemColor.text;
import static org.junit.jupiter.api.Assertions.*;

@Log4j2

@Nested

public class TestModalidadesScreen {
    public static final String LOCALDRIVER = "src/main/resources/drivers/chromedriver.exe";
    public static final String PROPERTY = "webdriver.chrome.driver";
    public static final String URL = "https://ciscodeto.github.io/AcodemiaGerenciamento/ymodalidades.html";
    public static final int WAITTIME = 7;
    public static final String XPATH_MENSAGEM = "/html/body/div[1]/div/p";
    public static final String XPATH_BUTTON_INSERT = "/html/body/div[2]/main/div[1]/ul/li[3]/a";
    public static final String XPATH_CONTAINERDEAVISO = "/html/body/div[1]/div";
    public static final String XPATH_INPUT_FIELD ="/html/body/div[2]/main/div[1]/ul/li[1]/input";
    public static final String XPATH_BUTTON_FINALIZAR = "/html/body/div[1]/div/form/div[4]/a[2]";
    public static final String XPATH_BUTTON_LISTAR = "/html/body/div[2]/main/div[1]/ul/li[2]/a";
    public static final String XPATH_CONTENTTABLE = "/html/body/div[2]/main/div[2]";
    public static final String XPATH_CONTAINER_INSERT = "/html/body/div[1]/div";
    public static final String XPATH_DATA_OFERECIMENTO = "/html/body/div[1]/div/form/div[1]/input";
    public static final String X_PATH_DESCRICAO = "/html/body/div[1]/div/form/input[1]";
    public static final String X_PATH_PROFESSORES = "/html/body/div[1]/div/form/div[3]/input";
    public static final String X_PATH_NOME_PROFESSOR = "/html/body/div[1]/div/form/div[3]/input";
    public static final String XPATH_DESCRIPTION = "/html/body/div[1]/div/form/input[1]";
    public static final String X_PATH_DURACAO = "/html/body/div[1]/div/form/input[2]";
    public static final String X_PATH_VALOR = "/html/body/div[1]/div/form/input[3]";
    public static final String X_PATH_DIAS = "/html/body/div[1]/div/form/div[1]/input";
    public static final String X_PATH_HORARIOS = "/html/body/div[1]/div/form/div[2]/input";

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
    @Tag("Modalidades Screend")
    @DisplayName("Should open page and click on Incluir return Modalidades not exist")
    public void shouldopenpageandclickonIncluirreturnModalidadesnotexist() throws InterruptedException {
        final WebElement button = getWebElement(driver, XPATH_BUTTON_INSERT);
        button.click();
        final WebElement container = getWebElement(driver, XPATH_CONTAINERDEAVISO);

        final String mensagemDevolvida = container.findElement(By.xpath(XPATH_MENSAGEM)).getText();
        fail("Precisava retornar a mensagem solicitando o código da modalidade para inserção, e a mensagem que retorna é: " + mensagemDevolvida);
    }

    @Test
    @Tag("Modalidades Screend")
    @DisplayName("Should show error message when inserting a negative number in the inclusion modalidade")
    public void shouldshowerrormessagewheninsertinganegativenumberintheinclusionmodalidade() throws InterruptedException {

        final WebElement inputField = getWebElement(driver, XPATH_INPUT_FIELD);
        String codigo = faker.numerify("-###");
        inputField.sendKeys(codigo);
        new WebDriverWait(driver, Duration.ofSeconds(WAITTIME))
                .until(in -> codigo.equals(inputField.getAttribute("value")));
        final WebElement button = getWebElement(driver, XPATH_BUTTON_INSERT);
        button.click();
        final WebElement buttonFinalizar = getWebElement(driver,XPATH_BUTTON_FINALIZAR);
        buttonFinalizar.click();
        final WebElement buttonListar = getWebElement(driver, XPATH_BUTTON_LISTAR);
        buttonListar.click();
        final WebElement contentTable = getWebElement(driver, XPATH_CONTENTTABLE);
        fail("Não foi retornado mensagem sobre inserção de número negativo, segue os dados permitidos em cadastro: " + contentTable.getText());
    }

    @Test
    @Tag("Modalidades Screend")
    @DisplayName("Should display error message if any field is empty when Finalizar is clicked")
    public void shouldShowErrorMessageIfFieldIsEmptyOnFinalizar() throws InterruptedException {

        final WebElement inputField = getWebElement(driver, XPATH_INPUT_FIELD);

        String codigo = faker.numerify("###");
        inputField.sendKeys(codigo);

        final WebElement button = getWebElement(driver, XPATH_BUTTON_INSERT);
        button.click();

        // Localiza o container principal
        final WebElement container = getWebElement(driver, XPATH_CONTAINER_INSERT);

        // Localiza todos os campos dentro do container
        List<WebElement> fields = container.findElements(By.xpath(".//input|.//textarea|.//select"));

        boolean isAnyFieldEmpty = false;

        // Verifica se algum campo está vazio
        for (WebElement field : fields) {
            String fieldType = field.getTagName();
            String value = "";

            switch (fieldType) {
                case "input":
                case "textarea":
                    // Obtém o valor do campo
                    value = field.getAttribute("value");
                    break;

                case "select":
                    // Obtém o texto da opção selecionada
                    WebElement selectedOption = field.findElement(By.xpath(".//option[@selected]"));
                    value = selectedOption.getText();
                    break;

                default:
                    log.warn("Field type {} is not being checked for value", fieldType);
                    continue;
            }

            log.info("Field value: {}", value);


            if (value == null || value.trim().isEmpty()) {
                isAnyFieldEmpty = true;
                break; // Não é necessário continuar verificando se já sabemos que há um campo vazio
            }
        }

        final WebElement finalizarButton = getWebElement(driver, XPATH_BUTTON_FINALIZAR);
        finalizarButton.click();

        final WebElement contentTable = getWebElement(driver, XPATH_CONTENTTABLE);
            fail("Não foi retornado mensagem sobre campos vazios" +
                    ", segue os dados permitidos em cadastro: " + contentTable.getText());
    }

    @Test
    @Tag("Modalidades Screend")
    @DisplayName("Should provide a date in the past for the offering days")
    public void Shouldprovideadateinthepastfortheofferingdays() {
        final WebElement inputField = getWebElement(driver, XPATH_INPUT_FIELD);
        String codigo = faker.numerify("###");
        inputField.sendKeys(codigo);
        new WebDriverWait(driver, Duration.ofSeconds(WAITTIME))
                .until(in -> codigo.equals(inputField.getAttribute("value")));
        final WebElement buttonIncluir = getWebElement(driver,XPATH_BUTTON_INSERT);
        buttonIncluir.click();
        Date dataOferecimento = faker.date().past(60, TimeUnit.DAYS);
        String dataOferecimentoFormatada = new SimpleDateFormat("dd-MM-yyyy").format(dataOferecimento);
        final WebElement dateInput = getWebElement(driver, XPATH_DATA_OFERECIMENTO);
        dateInput.click();
        dateInput.sendKeys(dataOferecimentoFormatada);
        final WebElement buttonFinalizar = getWebElement(driver,XPATH_BUTTON_FINALIZAR);
        buttonFinalizar.click();
        final WebElement buttonListar = getWebElement(driver,XPATH_BUTTON_LISTAR);
        buttonListar.click();
        final WebElement contentTable = getWebElement(driver,XPATH_CONTENTTABLE);
        fail("Não foi retornado o erro devido a data no passado, segue os dados permitidos em cadastro: " + contentTable.getText());
    }

    @Test
    @Tag("Modalidades Screend")
    @DisplayName("should insert number in teacher's name")
    public void shouldinsertnumberinteachersname() {
        final WebElement inputField = getWebElement(driver, XPATH_INPUT_FIELD);
        String codigo = faker.numerify("###");
        inputField.sendKeys(codigo);
        new WebDriverWait(driver, Duration.ofSeconds(WAITTIME))
                .until(in -> codigo.equals(inputField.getAttribute("value")));
        final WebElement buttonIncluir = getWebElement(driver, XPATH_BUTTON_INSERT);
        buttonIncluir.click();
        final WebElement nomeProfessor = getWebElement(driver, X_PATH_NOME_PROFESSOR);
        String nome = faker.numerify("####");
        nomeProfessor.sendKeys(nome);
        final WebElement buttonFinalizar = getWebElement(driver, XPATH_BUTTON_FINALIZAR);
        buttonFinalizar.click();
        final WebElement contentTable = getWebElement(driver, XPATH_CONTENTTABLE);
        fail("Não podemos inserir número no nome, segue o preenchimento do nome: " + contentTable.getText());
    }

    @Test
    @Tag("Modalidades Screend")
    @DisplayName("Should be possible to view the 'Descrição' field completely in different resolutions")
    public void shouldbepossibletoviewtheDescriçãofieldcompletelyindifferentresolutions() {
        Dimension[] screenSizes = {
                new Dimension(1920, 1080),  // Desktop
                new Dimension(1024, 768),  // Tablet (landscape)
                new Dimension(768, 1024),  // Tablet (portrait)
                new Dimension(375, 812)  // Mobile (iPhone X)
        };

        final WebElement inputField = getWebElement(driver, XPATH_INPUT_FIELD);
        String codigo = faker.numerify("###");
        inputField.sendKeys(codigo);

        new WebDriverWait(driver, Duration.ofSeconds(WAITTIME))
                .until(in -> codigo.equals(inputField.getAttribute("value")));

        // Clicar em "Incluir"
        final WebElement buttonIncluir = getWebElement(driver, XPATH_BUTTON_INSERT);
        buttonIncluir.click();

        // Preencher descrição
        final WebElement descricaoInput = getWebElement(driver, X_PATH_DESCRICAO);
        descricaoInput.sendKeys(faker.lorem().characters(1, 10));
        ;

        final WebElement buttonFinalizar = getWebElement(driver, XPATH_BUTTON_FINALIZAR);
        buttonFinalizar.click();
        final WebElement buttonListar = getWebElement(driver, XPATH_BUTTON_LISTAR);
        buttonListar.click();

        // Verificar o campo Altura em diferentes resoluções
        for (Dimension dimension : screenSizes) {
            driver.manage().window().setSize(dimension);

            // Reencontrar o elemento altura para garantir sua localização atual
            try {
                final WebElement height = getWebElement(driver, XPATH_DESCRIPTION);
            } catch (Exception e) {
                fail("Não foi possível obter a altura na dimensão: " + dimension);
            }
        }
    }

    //TESTES DE SUCESSO

    @Test
    @Tag("Modalidades Screend")
    @DisplayName("Should include modality")
    public void shouldincludemodality() {
        driver.manage().window().maximize();

        final WebElement inputField = getWebElement(driver, XPATH_INPUT_FIELD);
        String codigo = faker.numerify("###");
        inputField.sendKeys(codigo);
        new WebDriverWait(driver, Duration.ofSeconds(WAITTIME))
                .until(in -> codigo.equals(inputField.getAttribute("value")));
        final WebElement buttonIncluir = getWebElement(driver, XPATH_BUTTON_INSERT);
        buttonIncluir.click();


        //Preencher descrição
        final WebElement descricaoInput = getWebElement(driver, X_PATH_DESCRICAO);
        String descricao = faker.lorem().characters(1, 10);
        descricaoInput.sendKeys(descricao);

        //Preencher duração
        final WebElement duracaoInput = getWebElement(driver, X_PATH_DURACAO);
        int hora = faker.number().numberBetween(0, 24);
        int minuto = faker.number().numberBetween(0, 60);
        String horaFormatada = String.format("%02d:%02d", hora, minuto);
        duracaoInput.sendKeys(horaFormatada);

        //Preencher valor
        final WebElement valorInput = getWebElement(driver, X_PATH_VALOR);
        String valorAleatorio = String.format("%.2f", faker.number().randomDouble(2, 50, 500)); // Gera valor entre 50.00 e 500.00
        valorInput.sendKeys(valorAleatorio);

        //Preencher dias
        final WebElement diasInput = getWebElement(driver, X_PATH_DIAS);
        Date dataOferecimento = faker.date().future(30, TimeUnit.DAYS);
        String dataOferecimentoFormatada = new SimpleDateFormat("dd-MM-yyyy").format(dataOferecimento);
        final WebElement dateInput = getWebElement(driver, XPATH_DATA_OFERECIMENTO);
        dateInput.sendKeys(dataOferecimentoFormatada);

        //Preencher horários
        final WebElement horariosInput = getWebElement(driver, X_PATH_HORARIOS);
        String horarioAleatorio = String.format("%02d:%02d", faker.number().numberBetween(0, 24), faker.number().numberBetween(0, 60));
        horariosInput.sendKeys(horarioAleatorio);

        //Preencher Professores
        final WebElement professoresInput = getWebElement(driver, X_PATH_PROFESSORES);
        String professorAleatorio = faker.name().fullName(); // Nome completo aleatório
        professoresInput.sendKeys(professorAleatorio);


        final WebElement buttonFinalizar = getWebElement(driver, XPATH_BUTTON_FINALIZAR);
        buttonFinalizar.click();
        final WebElement buttonListar = getWebElement(driver, XPATH_BUTTON_LISTAR);
        buttonListar.click();
        // Localizar tabela e validar dados
        final WebElement contentTable = getWebElement(driver, XPATH_CONTENTTABLE);
        List<WebElement> linhasTabela = contentTable.findElements(By.tagName("tr"));
        boolean dadosEncontrados = false;

        for (WebElement linha : linhasTabela) {
            List<WebElement> colunas = linha.findElements(By.tagName("td"));
            if (colunas.size() > 0 && colunas.get(0).getText().equals(codigo)) {
                assertEquals(descricao, colunas.get(1).getText(), "Descrição incorreta!");
                assertEquals(horaFormatada, colunas.get(2).getText(), "Duração incorreta!");
                assertEquals(horarioAleatorio, colunas.get(4).getText(), "Horário incorreto!");
                assertEquals(professorAleatorio, colunas.get(5).getText(), "Professor incorreto!");
                assertEquals(valorAleatorio, colunas.get(6).getText(), "Valor incorreto!");
                dadosEncontrados = true;
                break;
            }
        }

        assertTrue(dadosEncontrados, "Dados não encontrados na tabela!");
        if (dadosEncontrados) {
            System.out.println("Os dados foram inseridos corretamente na tabela.");
        }
    }
}


        //@AfterEach
        //void tearDown() {
        //driver.quit(); // Encerra o driver e fecha o navegador
        //}

