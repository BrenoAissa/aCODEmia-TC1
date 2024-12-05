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
import pages.ModalidadesScreenPage;


import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@Log4j2

@Nested

public class TestModalidadesScreen {
    private WebDriver driver;
    private ModalidadesScreenPage modalidadesScreenPage;
    public static final String LOCALDRIVER = "src/main/resources/drivers/chromedriver.exe";
    public static final String PROPERTY = "webdriver.chrome.driver";
    public static final String URL = "https://ciscodeto.github.io/AcodemiaGerenciamento/ymodalidades.html";
    Faker faker = new Faker();
    ChromeOptions chromeOptions = new ChromeOptions();
    String codigo = faker.numerify("###");
    String codigoNegativo = faker.numerify("-###");
    int hora = faker.number().numberBetween(0, 2);
    int minuto = faker.number().numberBetween(0, 59);
    String horaFormatada = String.format("%02d:%02d", hora, minuto);
    Date dataOferecimento = faker.date().past(30, TimeUnit.DAYS);
    Date dataOferecimentoFuture = faker.date().future(30, TimeUnit.DAYS);
    String dataOferecimentoFormatadaFuture = new SimpleDateFormat("dd-MM-yyyy").format(dataOferecimentoFuture);
    String dataOferecimentoFormatada = new SimpleDateFormat("dd-MM-yyyy").format(dataOferecimento);
    String horarioAleatorio = String.format("%02d:%02d", faker.number().numberBetween(0, 24), faker.number().numberBetween(0, 60));
    String descricao = faker.lorem().characters(1, 10);
    String valorAleatorio = String.format("%.2f", faker.number().randomDouble(2, 50, 200));
    String professorAleatorio = faker.name().fullName();
    String teacherNumber = faker.numerify("###");
    public static final String XPATH_CONTAINER_INSERT = "/html/body/div[1]/div";


    @BeforeEach
    void setUp() {
        System.setProperty(PROPERTY, LOCALDRIVER);
        driver = new ChromeDriver(chromeOptions);
        chromeOptions.addArguments("--remote-allow-origins=*");
        modalidadesScreenPage = new ModalidadesScreenPage(driver);
        driver.get(URL);// Acessa o site Acodemia

        //Ajustar a tela para uma tela comum de desktop para evitar o erro com coleta de dados
        driver.manage().window().maximize();
    }

    @Test
    @Tag("Modalidades Screend")
    @DisplayName("Should open page and click on Incluir return Modalidades not exist")
    public void shouldopenpageandclickonIncluirreturnModalidadesnotexist() {
        modalidadesScreenPage.clickInsertButton();
        String returnedText = modalidadesScreenPage.clickContainerMensagem();
        assertEquals("Você não digitou o código da modalidade que está tentando inserir.", returnedText);
    }

    @Test
    @Tag("Modalidades Screend")
    @DisplayName("Should show error message when inserting a negative number in the inclusion modalidade")
    public void shouldshowerrormessagewheninsertinganegativenumberintheinclusionmodalidade() {

        modalidadesScreenPage.fillCodigo(codigoNegativo);

        modalidadesScreenPage.waitCodigoFill(codigoNegativo);

        modalidadesScreenPage.clickInsertButton();

        modalidadesScreenPage.clickButtonFinalizar();

        modalidadesScreenPage.clickButtonListar();
        fail("Não foi retornado o erro, segue os dados permitidos em cadastro: " + modalidadesScreenPage.getContentTableText());
    }

    //INSERIR PARA VERIFICAR CAMPOS NULOS

    @Test
    @DisplayName("Should open Site and don't fill modalidade infos,only codigo, with error return")
    public void shouldOpenSiteAndDontFillModalidadeInfosOnlyCodigoWithErrorReturn() {

        modalidadesScreenPage.fillCodigo(codigo);

        modalidadesScreenPage.waitCodigoFill(codigo);

        modalidadesScreenPage.clickInsertButton();

        modalidadesScreenPage.clickButtonFinalizar();

        modalidadesScreenPage.clickButtonListar();
        fail("Não foi retornado o erro, segue os dados permitidos em cadastro: " + modalidadesScreenPage.getContentTableText());
    }

    @Test
    @Tag("Modalidades Screend")
    @DisplayName("Should provide a date in the past for the offering days")
    public void Shouldprovideadateinthepastfortheofferingdays() {
        modalidadesScreenPage.fillCodigo(codigo);

        modalidadesScreenPage.waitCodigoFill(codigo);

        modalidadesScreenPage.clickInsertButton();

        modalidadesScreenPage.fillDataOferecimento(dataOferecimentoFormatada);

        modalidadesScreenPage.clickButtonFinalizar();

        modalidadesScreenPage.clickButtonListar();

        fail("Não foi retornado o erro a respeito da data ser ofertada no passado " + modalidadesScreenPage.getContentTableText());

    }

    @Test
    @Tag("Modalidades Screend")
    @DisplayName("should insert number in teacher's name")
    public void shouldinsertnumberinteachersname() {
        modalidadesScreenPage.fillCodigo(codigo);

        modalidadesScreenPage.waitCodigoFill(codigo);

        modalidadesScreenPage.clickInsertButton();

        modalidadesScreenPage.clickInsertTeacher(teacherNumber);

        modalidadesScreenPage.clickButtonFinalizar();

        modalidadesScreenPage.clickButtonListar();
        fail("Não podemos inserir número no nome, segue o preenchimento do nome: " + modalidadesScreenPage.getContentTableText());
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

        modalidadesScreenPage.fillCodigo(codigo);

        modalidadesScreenPage.waitCodigoFill(codigo);

        modalidadesScreenPage.clickInsertButton();

        // Preencher descrição

        modalidadesScreenPage.fillDescription(descricao);

        modalidadesScreenPage.clickButtonFinalizar();

        modalidadesScreenPage.clickButtonListar();

        // Verificar o campo descrição em diferentes resoluções
        for (Dimension dimension : screenSizes) {
            driver.manage().window().setSize(dimension);
            try {
                final String descriptionValue = modalidadesScreenPage.getDescriptionValue();
            } catch (Exception e) {
                fail("Não foi possível obter a descrição na dimensão: " + dimension);
            }
        }
    }

    @Test
    @Tag("Modalidades Screend")
    @DisplayName("Should include modality")
    public void shouldincludemodality() {
        driver.manage().window().maximize();

        modalidadesScreenPage.fillCodigo(codigo);

        modalidadesScreenPage.waitCodigoFill(codigo);

        modalidadesScreenPage.clickInsertButton();


        //Preencher descrição
        modalidadesScreenPage.fillDescription(descricao);

        //Preencher duração
        modalidadesScreenPage.fillDuration(horaFormatada);

        //Preencher valor
        modalidadesScreenPage.fillValue(valorAleatorio);

        modalidadesScreenPage.fillDataOferecimento(dataOferecimentoFormatadaFuture);

        //Preencher horários
        modalidadesScreenPage.fillHour(horarioAleatorio);

        //Preencher Professores
        modalidadesScreenPage.clickInsertTeacher(professorAleatorio);

        modalidadesScreenPage.clickButtonFinalizar();

        modalidadesScreenPage.clickButtonListar();
        // Localizar tabela e validar dados
        modalidadesScreenPage.getContentTableText();
        String comparacao =
                "CÓDIGO Descrição Duração Dias de oferecimento Horários Professores responsáveis Valor\n" +
                        codigo + " " +
                        descricao + " " +
                        horaFormatada + " " +
                        new SimpleDateFormat("yyyy-MM-dd").format(dataOferecimentoFuture)  + "\n" +
                        horarioAleatorio + "\n" +
                        professorAleatorio + "\n" +
                        valorAleatorio;


        assertEquals(modalidadesScreenPage.getContentTableText(), comparacao, "Dados incompativeis, por favor verifique a inserção de dados!");
    }

    @Test
    @Tag("Modalidades Screend")
    @DisplayName("Should change modality")
    public void shouldshouldchangemodality() {
        driver.manage().window().maximize();

        modalidadesScreenPage.fillCodigo(codigo);

        modalidadesScreenPage.waitCodigoFill(codigo);

        modalidadesScreenPage.clickInsertButton();

        modalidadesScreenPage.clickButtonFinalizar();

        modalidadesScreenPage.clickButtonAlterar();

        //Preencher descrição
        modalidadesScreenPage.fillDescription(descricao);

        //Preencher duração
        modalidadesScreenPage.fillDuration(horaFormatada);

        //Preencher valor
        modalidadesScreenPage.fillValue(valorAleatorio);

        modalidadesScreenPage.fillDataOferecimento(dataOferecimentoFormatadaFuture);

        //Preencher horários
        modalidadesScreenPage.fillHour(horarioAleatorio);

        //Preencher Professores
        modalidadesScreenPage.clickInsertTeacher(professorAleatorio);

        modalidadesScreenPage.clickButtonFinalizar();

        modalidadesScreenPage.clickButtonListar();
        // Localizar tabela e validar dados
        modalidadesScreenPage.getContentTableText();
        String comparacao =
                "CÓDIGO Descrição Duração Dias de oferecimento Horários Professores responsáveis Valor\n" +
                        codigo + " " +
                        descricao + " " +
                        horaFormatada + " " +
                        new SimpleDateFormat("yyyy-MM-dd").format(dataOferecimentoFuture)  + "\n" +
                        horarioAleatorio + "\n" +
                        professorAleatorio + "\n" +
                        valorAleatorio;


        assertEquals(modalidadesScreenPage.getContentTableText(),comparacao,"Dados incompativeis, por favor verifique a inserção de dados!");
    }


    @Test
        @Tag("Modalidades Screend")
        @DisplayName("Should Delete Modality")
        public void ShouldDeleteModality(){
            driver.manage().window().maximize();
            shouldincludemodality();

            modalidadesScreenPage.clickButtonExcluir();

            modalidadesScreenPage.clickContainerExcluir();

            modalidadesScreenPage.getContentTableText();
            assertEquals("CÓDIGO Descrição Duração Dias de oferecimento Horários Professores responsáveis Valor",
                    modalidadesScreenPage.getContentTableText()
                    ,"O corpo retornado não está vazio, verifique se a exclusão foi feita corretamente");
            System.out.println("Os dados foram excluídos corretamente na tabela.");

        }

        @AfterEach
        void tearDown() {
            driver.quit();
        }

    }













