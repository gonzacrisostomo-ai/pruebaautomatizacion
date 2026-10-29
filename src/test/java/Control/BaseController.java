package Control;

import Constant.Constant;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BaseController {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BaseController() {
        this.driver = DriverContext.getDriver();
        if (this.driver != null) {
            this.wait = new WebDriverWait(driver, Duration.ofSeconds(Constant.TIME_DISPLAY), Duration.ofSeconds(Constant.POLLING));
            PageFactory.initElements(new AjaxElementLocatorFactory(this.driver, Constant.TIME_RESPONSE), this);
        } else {
            System.out.println("WebDriver no está inicializado!");
        }
    }

    public static void esperarSegundos(int segundos) {
        try {
            Thread.sleep(segundos * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("La espera fue interrumpida", e);
        }
    }


    public boolean visualizarElemento(WebElement elementoWeb, int tiempoEspera) {
        try {
            WebDriverWait wait2 = new WebDriverWait(DriverContext.getDriver(), Duration.ofSeconds(tiempoEspera));
            wait2.until(ExpectedConditions.visibilityOf(elementoWeb));
            System.out.println("Es visible el elemento web " + elementoWeb.getText());
            return true;
        } catch (Exception e) {

            System.out.println(
                    "Error visualizando elemento: "
                            + e.getMessage());

            return false;
        }
    }

}
