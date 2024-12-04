
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.github.javafaker.Faker;
import pages.AlunosScreenPage;


import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@Log4j2
public class TestAlunosScreen {
    private WebDriver driver;
    private AlunosScreenPage alunosScreenPage ;
    public static final String LOCALDRIVER = "src/main/resources/drivers/chromedriver.exe";
    public static final String PROPERTY = "webdriver.chrome.driver";
    public static final String URL = "https://ciscodeto.github.io/AcodemiaGerenciamento/yalunos.html";
    Faker faker = new Faker();
    ChromeOptions chromeOptions = new ChromeOptions();
    String cpf = faker.numerify("###.###.###-##");
    Date dataNascimento = faker.date().future(90, TimeUnit.DAYS);
    String peso = faker.numerify("-##");
    String alturaEmMetros = faker.numerify("-##");
    String nome = faker.name().fullName();
    Date dataNascimentoFuture = faker.date().past(2600, TimeUnit.DAYS);
    String sexo = "Masculino";
    String email = faker.internet().emailAddress();
    String telefone = faker.numerify("(###)#########");




    @BeforeEach
    void setUp() {
        System.setProperty(PROPERTY, LOCALDRIVER);
        driver = new ChromeDriver(chromeOptions);
        chromeOptions.addArguments("--remote-allow-origins=*");
        alunosScreenPage = new AlunosScreenPage(driver);
        driver.get(URL);// Acessa o site Acodemia

        //Ajustar a tela para uma tela comum de desktop para evitar o erro com coleta de dados
        driver.manage().window().maximize();
        }


    @Test
    @DisplayName("Should open Site and click on Incluir with a insert error message ")
    public void shouldOpenSiteAndClickOnIncluirWithAInsertErrorMessage() {
        //Espera o elemento e clica em inserir
        alunosScreenPage.clickInsertButton();
        // Espera pelo container da mensagem e clicar em fechar no container de mensagem
        String returnedText = alunosScreenPage.clickContainerFecharButton();
        // Verifica a mensagem retornada
        assertEquals("Por favor digite o CPF do aluno que está tentando inserir!", returnedText);
    }
    @Test
    @DisplayName("Should open Site and don't fill user infos,only cpf, with error return")
    public void shouldOpenSiteAndDontFillUserInfosOnlyCpfWithErrorReturn() {
        //Inserir CPF com dados fakes.
        alunosScreenPage.fillCpf(cpf);
        //Aguarda o CPF ser preenchido
        alunosScreenPage.waitCpfFill(cpf);
        //Clica no botão incluir
        alunosScreenPage.clickInsertButton();
        //Clica em finalizar
        alunosScreenPage.clickButtonFinalizar();
        //clica em listar
        alunosScreenPage.clickButtonListar();
        fail("Não foi retornado o erro, segue os dados permitidos em cadastro: " + alunosScreenPage.getContentTableText());
    }

    @Test
    @DisplayName("Should open Site and don't fill CPF, with error return")
    public void shouldOpenSiteAndClickListarWithoutFillCPFWithErrorReturn() {
        alunosScreenPage.clickButtonListar();
        try {
            // Espera pelo container da mensagem
            alunosScreenPage.testContainerAviso();

        } catch (Exception e) {
            fail("A mensagem de aviso não foi gerada!");
        }
    }

    @Test
    @DisplayName("Should open Site and fill a future date for Data de nascimento")
    public void shouldOpenSiteAndFillAFutureDateForDataDeNascimento() {
        // Prenche o CPF
        alunosScreenPage.fillCpf(cpf);
        // Aguarda ser preenchido
        alunosScreenPage.waitCpfFill(cpf);
        // Clica no botão de inserir
        alunosScreenPage.clickInsertButton();
        // Insere a data de nascimento
        alunosScreenPage.fillDatanasc(dataNascimento);
        // Clica em finalizar
        alunosScreenPage.clickButtonFinalizar();
        // Clica em listar
        alunosScreenPage.clickButtonListar();
        // Coleta contentTable
        fail("Não foi retornado o erro, segue os dados permitidos em cadastro: " + alunosScreenPage.getContentTableText());
    }

    @Test
    @DisplayName("Should open Site and fill some fields with negative values return error")
    public void shouldOpenSiteAndFillSomeFieldsWithNegativeValuesReturn() {
        //Inserir CPF
        alunosScreenPage.fillCpf(cpf);
        // Aguarda o CPF ser preenchido
        alunosScreenPage.waitCpfFill(cpf);
        // Clica em incluir
        alunosScreenPage.clickInsertButton();
        // Adiciona o pesso
        alunosScreenPage.fillPeso(peso);
        // Adiciona a altura
        alunosScreenPage.fillAltura(alturaEmMetros);
        // Clica em finalizar
        alunosScreenPage.clickButtonFinalizar();
        // Clica em listar
        alunosScreenPage.clickButtonListar();
        fail("Não foi retornado o erro, segue os dados permitidos em cadastro: " + alunosScreenPage.getContentTableText());
    }

    @Test
    @DisplayName("should open site and click on Alterar without fill the fields with error")
    public void shouldOpenSiteAndClickAlterarWithoutFillTheFieldsWithErrorReturn() {
        //Inserir CPF
        alunosScreenPage.fillCpf(cpf);
        // Aguarda o CPF ser preenchido
        alunosScreenPage.waitCpfFill(cpf);
        // Clica em incluir
        alunosScreenPage.clickInsertButton();
        // Clica em finalizar
        alunosScreenPage.clickButtonFinalizar();
        // Clica em alterar
        alunosScreenPage.clickButtonAlterar();
        // Clica em finalizar
        alunosScreenPage.clickButtonFinalizar();

        try {
            alunosScreenPage.testContainerAviso();
        } catch (Exception e) {
            fail("A mensagem de aviso não foi gerada!");
        }
    }

    @Test
    @DisplayName("Should open site and show the Altura of Aluno")
    public void shouldOpenSiteAndShowTheAlturaOfAluno() {
        //Inserir CPF
        alunosScreenPage.fillCpf(cpf);
        // Aguarda o CPF ser preenchido
        alunosScreenPage.waitCpfFill(cpf);
        // Clica em incluir
        alunosScreenPage.clickInsertButton();
        // Adiciona o pesso
        alunosScreenPage.fillPeso(peso);
        // Adiciona a altura
        alunosScreenPage.fillAltura(alturaEmMetros);
        // Clica em finalizar
        alunosScreenPage.clickButtonFinalizar();
        // Clica em listar
        alunosScreenPage.clickButtonListar();
        assertNotEquals("undefined", alunosScreenPage.getHeightValue(),
                "O Valor da Altura está sendo representado como undefined");
    }

    @Test
    @DisplayName("Should be possible to view the 'Altura','E-mails' and 'Telefones' fields completely in different resolutions")
    public void shouldBePossibleToViewTheAlturaEmailsAndTelefonesFieldCompletelyInDifferentResolutions() {
        Dimension[] screenSizes = {
                new Dimension(1920, 1080),  // Desktop
                new Dimension(1024, 768),  // Tablet (landscape)
                new Dimension(768, 1024),  // Tablet (portrait)
                new Dimension(375, 812)   // Mobile (iPhone X)
        };
        //Inserir CPF
        alunosScreenPage.fillCpf(cpf);
        // Aguarda o CPF ser preenchido
        alunosScreenPage.waitCpfFill(cpf);
        // Clica em incluir
        alunosScreenPage.clickInsertButton();
        // Preencher Peso
        alunosScreenPage.fillPeso(peso);
        //Prencher Altura
        alunosScreenPage.fillAltura(alturaEmMetros);
        // Clica em finalizar
        alunosScreenPage.clickButtonFinalizar();
        // Clica em listar
        alunosScreenPage.clickButtonListar();

        // Verificar o campo Altura em diferentes resoluções
        for (Dimension dimension : screenSizes) {
            driver.manage().window().setSize(dimension);

            // Reencontrar o elemento altura para garantir sua localização atual
            try {
                final String height = alunosScreenPage.getHeightValue();
            } catch (Exception e) {
                fail("Não foi possível obter a altura na dimensão: " + dimension);
            }
        }
    }
    @Test
    @DisplayName("Should open Site and fill all fields with correct values return sucess and 'Listar' correct values")
    public void shouldOpenSiteAndFillAllFieldsWithCorrectValues() {
        //Ajustar a tela para uma tela comum de desktop para evitar o erro com coleta de dados
        driver.manage().window().maximize();

        //Inserir CPF
        alunosScreenPage.fillCpf(cpf);
        // Aguarda o CPF ser preenchido
        alunosScreenPage.waitCpfFill(cpf);
        // Clicar em "Incluir"
        alunosScreenPage.clickInsertButton();
        // Preencher o nome
        alunosScreenPage.fillName(nome);
        // Preencher a data de nascimento
        alunosScreenPage.fillDatanasc(dataNascimento);
        // Preencher o sexo
        alunosScreenPage.fillSexo(sexo);
        // Preencher Peso
        alunosScreenPage.fillPeso(peso);
        //Preencher Altura
        alunosScreenPage.fillAltura(alturaEmMetros);
        //Preencher Email
        alunosScreenPage.fillEmail(email);
        //Clica no botão de adicionar o e-mail
        alunosScreenPage.clickEmailButton();
        //Preencher Telefone
        alunosScreenPage.fillTelefone(telefone);
        //Clica no botão de confirme
        alunosScreenPage.clickTelefoneButton();
        //Clica em finalizar
        alunosScreenPage.clickButtonFinalizar();
        //clica em listar quando estiver disponível
        alunosScreenPage.clickButtonListar();
        //Coleta a tabela disponível na pagina de listar
        alunosScreenPage.getContentTableText();
        String comparacao =
                "CPF Nome Data de Nascimento Sexo Peso Altura E-mails Telefones\n" +
                        cpf + " " +
                        nome + " " +
                        new SimpleDateFormat("yyyy-MM-dd").format(dataNascimento) + " " +
                        "Masculino " +
                        peso + " " +
                        "undefined " +
                        email + "\n\n" +
                        telefone;
        assertEquals(alunosScreenPage.getContentTableText(),comparacao,"Dados incompativeis, por favor verifique a inserção de dados!");
    }
    @Test
    @DisplayName("Should open site and click on alterar and fill the fields")
    public void shouldOpenSiteAndClickOnAlterarAndFillTheFields() {
        //Ajustar a tela para uma tela comum de desktop para evitar o erro com coleta de dados
        driver.manage().window().maximize();
        //Inserir CPF
        alunosScreenPage.fillCpf(cpf);
        // Aguarda o CPF ser preenchido
        alunosScreenPage.waitCpfFill(cpf);
        // Clicar em "Incluir"
        alunosScreenPage.clickInsertButton();
        // Fechar tela de inscrição
        alunosScreenPage.clickButtonFinalizar();
        // Clicar em alterar
        alunosScreenPage.clickButtonAlterar();
        // Preencher o nome
        alunosScreenPage.fillName(nome);
        // Preencher a data de nascimento
        alunosScreenPage.fillDatanasc(dataNascimento);
        // Preencher o sexo
        alunosScreenPage.fillSexo(sexo);
        // Preencher Peso
        alunosScreenPage.fillPeso(peso);
        //Preencher Altura
        alunosScreenPage.fillAltura(alturaEmMetros);
        //Preencher Email
        alunosScreenPage.fillEmail(email);
        //Clica no botão de adicionar o e-mail
        alunosScreenPage.clickEmailButton();
        //Preencher Telefone
        alunosScreenPage.fillTelefone(telefone);
        //Clicar no botão do telefone
        alunosScreenPage.clickTelefoneButton();
        //Clica em finalizar
        alunosScreenPage.clickButtonFinalizar();
        //clica em listar quando estiver disponível
        alunosScreenPage.clickButtonListar();
        //Coleta a tabela disponível na pagina de listar

        String comparacao =
                "CPF Nome Data de Nascimento Sexo Peso Altura E-mails Telefones\n" +
                        cpf + " " +
                        nome + " " +
                        new SimpleDateFormat("yyyy-MM-dd").format(dataNascimento) + " " +
                        "Masculino " +
                        peso + " " +
                        "undefined " +
                        email + "\n\n" +
                        telefone;
        assertEquals(alunosScreenPage.getContentTableText(), comparacao,"Dados incompativeis, por favor verifique a inserção de dados!");

    }
    @Test
    @DisplayName("Should open site and delete one Aluno after insert someone")
    public void ShouldOpenSiteAndDeleteOneAlunoAfterInsertSomeone() {
        // Maximiza a tela
        driver.manage().window().maximize();
        // Preenche os camos
        shouldOpenSiteAndFillAllFieldsWithCorrectValues();
        alunosScreenPage.clickButtonExcluir();
        alunosScreenPage.clickContainerFecharButton();
        assertEquals("CPF Nome Data de Nascimento Sexo Peso Altura E-mails Telefones",alunosScreenPage.getContentTableText(),
                "O corpo retornado não está vazio, verifique se a exclusão foi feita corretamente");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }
}


