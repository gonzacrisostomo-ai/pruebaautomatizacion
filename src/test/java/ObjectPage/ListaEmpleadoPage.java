package ObjectPage;

import Control.BaseController;
import Control.DriverContext;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ListaEmpleadoPage extends BaseController {

    // LOGIN

    @FindBy(xpath = "//*[@id='app']/div[1]/div/div[1]/div/div[2]/div[2]/form/div[1]/div/div[2]/input")
    private WebElement username;

    @FindBy(xpath = "//*[@id='app']/div[1]/div/div[1]/div/div[2]/div[2]/form/div[2]/div/div[2]/input")
    private WebElement password;

    @FindBy(xpath = "//*[@id='app']/div[1]/div/div[1]/div/div[2]/div[2]/form/div[3]/button")
    private WebElement loginbtn;

    // PIM

    @FindBy(xpath = "//*[@id='app']/div[1]/div[1]/aside/nav/div[2]/ul/li[2]/a")
    private WebElement menuPim;

    @FindBy(xpath = "//*[@id='app']/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[1]/div/div[2]/div/div/input")
    private WebElement nombreempleado;

    @FindBy(xpath = "//*[@id='app']/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[2]/button[2]")
    private WebElement buscarempladobtn;

    @FindBy(xpath = "//*[@role='cell']/div")
    private List<WebElement> listaNombresEmpleado;

    // TC-002 EMPLOYEE ID

    @FindBy(xpath = "//*[@id='app']/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[2]/div/div[2]/input")
    private WebElement employeeId;

    @FindBy(xpath = "//*[@id='app']/div[1]/div[2]/div[2]/div/div[2]/div[2]/div/span")
    private WebElement lblCantidadRegistros;

    // TC-003 Employment Status

    @FindBy(xpath = "//*[@id='app']/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[3]/div/div[2]/div/div/div[2]/i")
    private WebElement employeestatus;

    @FindBy(xpath = "//*[@id='app']/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[3]/div/div[2]/div/div/div[1]")
    private WebElement employeestatustype;

    @FindBy(xpath = "//*[@id='app']/div[1]/div[2]/div[2]/div/div[2]/div[3]/div/div[2]/*/div/div[6]/div")
    private List<WebElement> listaEmploymentStatus;

    // TC-004 include

    // flecha del combo Include
    @FindBy(xpath = "//*[@id='app']/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[4]/div/div[2]/div/div/div[2]/i")
    private WebElement includeFilter;

    // valor seleccionado
    @FindBy(xpath = "//*[@id='app']/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[4]/div/div[2]/div/div/div[1]")
    private WebElement includeFilterType;

    // TC-005 include
    // TC-005 JOB TITLE

    @FindBy(xpath = "//*[@id='app']/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[6]/div/div[2]/div/div/div[2]/i")
    private WebElement jobTitleSelector;

    @FindBy(xpath = "//*[@id='app']/div[1]/div[2]/div[2]/div/div[2]/div[3]/div/div[2]/div[1]/div/div[5]")
    private List<WebElement> listaJobTitle;

    //SUB UNIT
    @FindBy(xpath = "//*[@id='app']/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[7]/div/div[2]/div/div/div[2]/i")
    private WebElement subUnitSelector;

    @FindBy(xpath = "//*[@id='app']/div[1]/div[2]/div[2]/div/div[2]/div[3]/div/div[2]/div[1]/div/div[7]")
    private List<WebElement> listaSubUnit;

    //SUPERVISOR NAME
    @FindBy(xpath = "//*[@id='app']/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[5]/div/div[2]/div/div/input")
    private WebElement supervisorName;

    @FindBy(xpath = "//*[@id='app']/div[1]/div[2]/div[2]/div/div[2]/div[3]/div/div[2]/*/div/div[8]/div")
    private List<WebElement> listaSupervisorResultado;


    // LOGIN

    public void escribirUsersname(String usuario) {
        username.sendKeys(usuario);
    }

    public void escribirPassword(String clave) {
        password.sendKeys(clave);
    }

    public void clickLogin() {
        loginbtn.click();
    }

    // PIM

    public void clickPIM() {
        try {
            visualizarElemento(menuPim, 10);
            menuPim.click();
        } catch (Exception e) {
            Assert.fail("Error al ingresar al módulo PIM: " + e.getMessage());
        }
    }

    public void ingresarNombreEmpleado(String nombreEmpleado) {
        nombreempleado.clear();
        nombreempleado.sendKeys(nombreEmpleado);
    }

    public void clickBuscar() {
        buscarempladobtn.click();
    }

    public boolean validarNombreEmpleado(String nombreEsperado) {

        List<WebElement> nombres =
                DriverContext.getDriver().findElements(
                        By.xpath("//div[@role='cell']/div")
                );

        System.out.println(
                "Cantidad encontrados: " + nombres.size()
        );

        for (WebElement nombre : nombres) {

            String texto = nombre.getText().trim();

            System.out.println(
                    "Nombre encontrado: [" + texto + "]"
            );

            if (texto.equalsIgnoreCase(nombreEsperado)) {
                return true;
            }
        }

        return false;
    }

    public boolean existenResultadosBusqueda() {

        return !listaNombresEmpleado.isEmpty();

    }
    // TC-002

    public void ingresarEmployeeId(String id) {
        employeeId.clear();
        employeeId.sendKeys(id);
    }

    public String obtenerCantidadRegistros() {

        return lblCantidadRegistros.getText();

    }

    // TC-003
    public boolean seleccionarEmploymentStatus(String status)
            throws InterruptedException {

        employeestatus.click();

        Thread.sleep(2000);

        List<WebElement> opciones =
                DriverContext.getDriver().findElements(
                        By.xpath("//*[normalize-space()='" + status + "']")
                );

        if (opciones.isEmpty()) {

            System.out.println(
                    "Employment Status no existe: "
                            + status
            );

            return false;
        }

        opciones.get(0).click();

        return true;
    }

    public boolean validarEmploymentStatus(String estadoEsperado) {

        System.out.println(
                "Cantidad encontrados: "
                        + listaEmploymentStatus.size()
        );

        if (listaEmploymentStatus.isEmpty()) {
            return false;
        }

        for (WebElement estado : listaEmploymentStatus) {

            String texto = estado.getText().trim();

            System.out.println(
                    "Estado encontrado: [" + texto + "]"
            );

            if (!texto.equalsIgnoreCase(
                    estadoEsperado.trim())) {

                return false;
            }
        }

        return true;
    }

    // TC-004
    public boolean seleccionarInclude(String includeOption)
            throws InterruptedException {

        includeFilter.click();

        Thread.sleep(2000);

        List<WebElement> opciones =
                DriverContext.getDriver().findElements(
                        By.xpath(
                                "//*[normalize-space()='"
                                        + includeOption
                                        + "']"
                        )
                );

        if (opciones.isEmpty()) {

            System.out.println(
                    "Include no existe: "
                            + includeOption
            );

            return false;
        }

        opciones.get(0).click();

        return true;
    }

    // TC-005
    public boolean seleccionarJobTitle(String jobTitle)
            throws InterruptedException {

        jobTitleSelector.click();

        Thread.sleep(2000);

        List<WebElement> opciones =
                DriverContext.getDriver().findElements(
                        By.xpath("//*[normalize-space()='" + jobTitle + "']")
                );

        if (opciones.isEmpty()) {

            System.out.println(
                    "VALIDACIÓN: El Job Title no existe -> "
                            + jobTitle
            );

            return false;
        }

        opciones.get(0).click();

        return true;
    }

    public boolean seleccionarSubUnit(String subUnit) throws InterruptedException {

        subUnitSelector.click();
        Thread.sleep(2000);

        List<WebElement> opciones =
                DriverContext.getDriver().findElements(
                        By.xpath("//*[normalize-space()='" + subUnit + "']")
                );

        if (opciones.isEmpty()) {

            System.out.println(
                    "No se encontró la Sub Unit: " + subUnit
            );

            return false;
        }

        System.out.println(
                "Sub Unit encontrada: " + subUnit
        );

        opciones.get(0).click();

        return true;
    }

    public boolean ingresarSupervisor(String supervisor)
            throws InterruptedException {

        supervisorName.clear();
        supervisorName.sendKeys(supervisor);

        Thread.sleep(2000);

        List<WebElement> opciones =
                DriverContext.getDriver().findElements(
                        By.xpath("//*[normalize-space()='"
                                + supervisor + "']")
                );

        if (opciones.isEmpty()) {

            System.out.println(
                    "Supervisor no existe: "
                            + supervisor
            );

            return false;
        }

        opciones.get(0).click();

        return true;
    }

    public boolean validarSupervisorResultado(
            String supervisorEsperado) {

        if (listaSupervisorResultado.isEmpty()) {
            return false;
        }

        for (WebElement supervisor : listaSupervisorResultado) {

            String texto = supervisor.getText().trim();

            if (texto.isEmpty()) {
                continue;
            }

            if (texto.equalsIgnoreCase(
                    supervisorEsperado.trim())) {

                return true;
            }
        }

        return false;
    }
}