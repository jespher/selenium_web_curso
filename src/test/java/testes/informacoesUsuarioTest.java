package testes;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import suporte.Generator;
import suporte.Screenshot;

import java.util.concurrent.TimeUnit;

public class informacoesUsuarioTest {

    private WebDriver navegador;

    @Rule
    public TestName test = new TestName();

    @Before
    public void setUp() throws InterruptedException {
        //Abrindo o navegador
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
        navegador = new ChromeDriver();
        navegador.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
        navegador.manage().window().maximize();

        //Navegando para a pagina escolhida!
        navegador.get("http://www.juliodelima.com.br/taskit");

        //Aguardar até 10s para que a página inical seja carregada
        //Thread.sleep(10000);

        //Clicar no link que possui o texto "Sign in"

        navegador.findElement(By.linkText("Sign in")).click();

        //Identificando o formulario de Login
        WebElement formularioSigninBox = navegador.findElement(By.id("signinbox"));

        //Digitar no campo name "login" que está dentro do formulario de ID "signinbox" o texto "Jefferson88"
        formularioSigninBox.findElement(By.name("login")).sendKeys("Jefferson88");

        //Digitar no campo name "password" que está dentro do formulario de ID "signinbox" o texto "jespher88"
        formularioSigninBox.findElement(By.name("password")).sendKeys("jespher88");

        //Clicar no link com o texto "SIGN IN" (UPERCASE)
        navegador.findElement(By.linkText("SIGN IN")).click();

        //Clicar em um link que possui a class "me"
        navegador.findElement(By.className("me")).click();

        //Clicar em um link que possui o texto "More data about you"
        navegador.findElement(By.linkText("MORE DATA ABOUT YOU")).click();
    }

    @Test
    public void testAdicionarUmaInformacaoAdicionalDoUsuario() {

        //Clicar no botão através do seu xPath: //div[@id='moredata']//button[@data-target='addmoredata']
        navegador.findElement(By.xpath("//div[@id='moredata']//button[@data-target=\"addmoredata\"]")).click();

        //Identificar a popup onde está o formulario de id addmoredata
        WebElement popupAddMoreData = navegador.findElement(By.id("addmoredata"));

        //Na combo de name "type" escolher a opção "Phone"
        WebElement campoType = popupAddMoreData.findElement(By.name("type"));
        new Select(campoType).selectByVisibleText("Phone");

        //Colocar no campo de name "contact" digitar "+5541995167725"
        popupAddMoreData.findElement(By.name("contact")).sendKeys("+5541995167725");

        //Clicar no link com o texto "SAVE"(UPERCASE) que está na popup
        popupAddMoreData.findElement(By.linkText("SAVE")).click();

        //Na mensagem de id "toast-container" validar que o texto é "Your contact has been added!"
        WebElement toastContainer = navegador.findElement(By.id("toast-container"));
        String textoNoElementoToastContainer = toastContainer.getText();
        assertEquals("Your contact has been added!", textoNoElementoToastContainer);

        String screenshotArquivo = "C:\\drivers\\test-report\\" + Generator.dataHoraParaArquivo() + test.getMethodName() + ".png";
        Screenshot.tirar(navegador,screenshotArquivo);
    }

    @Test
    public void removerUmContatoDeUmUsuario() {

        //5541995167725 => Clicar no elemento pelo seu xPath => //span[text()='+554133335787']/following-sibling::a
        navegador.findElement(By.xpath("//span[text()='+5541995167725']/following-sibling::a")).click();

        //Confirmar a janela de JavaScript
        navegador.switchTo().alert().accept();

        //Validar que a mensagem apresentada foi "Rest in peace, dear phone!"
        WebElement mensagem = navegador.findElement(By.id("toast-container"));
        String textoMensagem = mensagem.getText();
        assertEquals("Rest in peace, dear phone!", textoMensagem);

        String screenshotArquivo = "C:\\drivers\\test-report\\" + Generator.dataHoraParaArquivo() + test.getMethodName() + ".png";
        Screenshot.tirar(navegador,screenshotArquivo);

        //Aguardar até 10s para que a janela desaperça
        WebDriverWait aguardar = new WebDriverWait(navegador, 10);
        aguardar.until(ExpectedConditions.stalenessOf(mensagem));

        //Clicar no link com o texto  "logout"
        navegador.findElement(By.linkText("Logout")).click();

    }

    @After
    public void tearDown(){
        //Fechar o navegador
        navegador.close();
    }
}
