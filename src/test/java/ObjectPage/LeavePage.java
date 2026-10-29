package ObjectPage;

import Control.BaseController;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LeavePage extends BaseController {

    private String fechaInicioDefault;
    private String fechaFinDefault;

    private String fechaDesdeBusqueda;
    private String fechaHastaBusqueda;

    // NAVEGACIÓN

    @FindBy(xpath = "//*[@id='app']/div[1]/div[1]/aside/nav/div[2]/ul/li[3]/a")
    private WebElement menuLeave;

    @FindBy(xpath = "//a[normalize-space()='Leave List']")
    private WebElement tabLeaveList;

    // FILTROS

    @FindBy(xpath = "//*[@id='app']/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[1]/div/div[2]/div/div/input")
    private WebElement txtFromDate;

    @FindBy(xpath = "//*[@id='app']/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[2]/div/div[2]/div/div/input")
    private WebElement txtToDate;

    @FindBy(xpath = "//label[text()='Show Leave with Status']/ancestor::div[contains(@class,'oxd-input-group')]//div[contains(@class,'oxd-select-text')]")
    private WebElement cboStatus;

    @FindBy(xpath = "//input[@placeholder='Type for hints...']")
    private WebElement txtEmployeeName;

    @FindBy(xpath = "//div[@role='option']//span")
    private WebElement opcionEmpleado;

    @FindBy(xpath = "//label[text()='Sub Unit']/ancestor::div[contains(@class,'oxd-input-group')]//div[contains(@class,'oxd-select-text')]")
    private WebElement cboSubUnit;

    @FindBy(xpath = "//*[@id='app']/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[2]/div/div[3]/div/label/span")
    private WebElement switchPastEmployees;

    // BOTONES

    @FindBy(xpath = "//button[normalize-space()='Search']")
    private WebElement btnSearch;

    @FindBy(xpath = "//button[normalize-space()='Reset']")
    private WebElement btnReset;

    // RESULTADOS

    @FindBy(xpath = "//div[contains(@class,'oxd-table-body')]")
    private WebElement tablaResultados;

    @FindBy(xpath = "//div[text()='No Records Found']")
    private WebElement lblNoRecords;

    @FindBy(xpath =
            "//div[contains(@class,'oxd-table-body')]//div[@role='row']/div[3]/div")
    private List<WebElement> lstEmployeeNames;

    @FindBy(xpath =
            "//div[contains(@class,'oxd-table-body')]//div[contains(@class,'oxd-table-card')]//div[7]/div")
    private List<WebElement> lstStatus;

    @FindBy(xpath = "//div[contains(@class,'oxd-table-card')]")
    private List<WebElement> lstFilasResultados;

    @FindBy(xpath =
            "//div[contains(@class,'oxd-table-card')]//div[2]/div")
    private List<WebElement> lstFechas;

    @FindBy(xpath =
            "//div[contains(@class,'oxd-table-card')]//div[3]/div")
    private List<WebElement> lstEmpleados;

    @FindBy(xpath =
            "//div[contains(@class,'oxd-table-card')]//div[7]/div")
    private List<WebElement> lstEstados;


    // NAVEGACIÓN

    public void ingresarModuloLeave() {

        try {

            visualizarElemento(menuLeave, 10);
            menuLeave.click();

        } catch (Exception e) {

            Assert.fail(
                    "Error al ingresar al módulo Leave: "
                            + e.getMessage()
            );
        }
    }

    public void ingresarLeaveList() {
        tabLeaveList.click();
    }

    // FILTROS

    public void ingresarFechaInicio(String fecha) {

        fechaDesdeBusqueda = fecha;

        txtFromDate.click();
        txtFromDate.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        txtFromDate.sendKeys(Keys.DELETE);
        txtFromDate.sendKeys(fecha);
    }

    public void ingresarFechaFin(String fecha) {

        fechaHastaBusqueda = fecha;

        txtToDate.click();
        txtToDate.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        txtToDate.sendKeys(Keys.DELETE);
        txtToDate.sendKeys(fecha);
    }

    public void seleccionarEstado(String estado) {

        cboStatus.click();

        driver.findElement(
                By.xpath("//span[normalize-space()='" + estado + "']")
        ).click();
    }

    public void ingresarNombreEmpleado(String nombre) {

        txtEmployeeName.clear();
        txtEmployeeName.sendKeys(nombre);
    }

    public boolean seleccionarEmpleadoAutocompletado() {

        try {

            visualizarElemento(opcionEmpleado, 5);

            opcionEmpleado.click();

            return true;

        } catch (Exception e) {

            System.out.println(
                    "Empleado no encontrado en autocompletado"
            );

            return false;
        }
    }

    public void seleccionarUnidad(String unidad) {

        cboSubUnit.click();

        driver.findElement(
                By.xpath("//span[normalize-space()='" + unidad + "']")
        ).click();
    }

    public void configurarEmpleadosAnteriores(String valor) {

        try {

            visualizarElemento(switchPastEmployees, 5);

            String clases =
                    switchPastEmployees.getDomAttribute("class");

            boolean activo =
                    clases != null
                            && clases.contains("active");

            if (valor.equalsIgnoreCase("Yes") && !activo) {
                switchPastEmployees.click();
            }

            if (valor.equalsIgnoreCase("No") && activo) {
                switchPastEmployees.click();
            }

        } catch (Exception e) {

            System.out.println(
                    "VALIDACIÓN: Include Past Employees no encontrado"
            );
        }
    }

    // ACCIONES

    public void clickSearch() {
        btnSearch.click();
    }

    public void clickReset() {
        btnReset.click();
    }

    // VALIDACIONES

    public void guardarValoresPorDefecto() {

        visualizarElemento(txtFromDate, 10);
        visualizarElemento(txtToDate, 10);

        esperarTextoEnInput(txtFromDate);
        esperarTextoEnInput(txtToDate);

        fechaInicioDefault =
                txtFromDate.getDomProperty("value");

        fechaFinDefault =
                txtToDate.getDomProperty("value");

        System.out.println(
                "DEFAULT DESDE: " + fechaInicioDefault);

        System.out.println(
                "DEFAULT HASTA: " + fechaFinDefault);
    }

    private void esperarTextoEnInput(WebElement elemento) {

        for (int i = 0; i < 10; i++) {

            String valor =
                    elemento.getDomProperty("value");

            if (valor != null && !valor.trim().isEmpty()) {
                return;
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }


    public boolean existenResultados() {

        try {

            return tablaResultados.isDisplayed();

        } catch (Exception e) {

            return false;
        }
    }

    public boolean busquedaEjecutada() {

        try {

            return tablaResultados.isDisplayed()
                    || lblNoRecords.isDisplayed();

        } catch (Exception e) {

            return false;
        }
    }

    public boolean filtrosReseteados() {

        String empleado =
                txtEmployeeName.getDomAttribute("value");

        String fechaInicioActual =
                txtFromDate.getDomProperty("value");

        String fechaFinActual =
                txtToDate.getDomProperty("value");

        System.out.println(
                "DEFAULT DESDE VALIDACION: "
                        + fechaInicioDefault);

        System.out.println(
                "DEFAULT HASTA VALIDACION: "
                        + fechaFinDefault);
        System.out.println(
                "FECHA INICIO ACTUAL: "
                        + fechaInicioActual);

        System.out.println(
                "FECHA FIN ACTUAL: "
                        + fechaFinActual);

        if (fechaInicioDefault == null
                || fechaFinDefault == null) {

            System.out.println(
                    "Valores por defecto no inicializados"
            );

            return false;
        }

        return (empleado == null
                || empleado.trim().isEmpty())
                &&
                fechaInicioActual != null
                &&
                fechaFinActual != null
                &&
                fechaInicioDefault.equals(fechaInicioActual)
                &&
                fechaFinDefault.equals(fechaFinActual);
    }

    public boolean validarEmpleadoResultados(
            String empleadoEsperado) {

        try {

            System.out.println(
                    "=== VALIDANDO EMPLEADOS ===");

            System.out.println(
                    "URL ACTUAL: "
                            + driver.getCurrentUrl());

            System.out.println(
                    "Cantidad empleados encontrados: "
                            + lstEmployeeNames.size());

            if (lstEmployeeNames.isEmpty()) {

                System.out.println(
                        "No se encontraron empleados en la tabla");

                return false;
            }

            for (WebElement empleado : lstEmployeeNames) {

                String nombre =
                        empleado.getText().trim();

                System.out.println(
                        "Empleado tabla: "
                                + nombre);

                if (!nombre.equalsIgnoreCase(
                        empleadoEsperado)) {

                    System.out.println(
                            "Empleado incorrecto encontrado: "
                                    + nombre);

                    return false;
                }
            }

            return true;

        } catch (Exception e) {

            System.out.println(
                    "Error validando empleados: "
                            + e.getClass().getSimpleName());

            System.out.println(
                    "Detalle: "
                            + e.getMessage());

            return false;
        }
    }

    public boolean validarEstadoResultados(
            String estadoEsperado) {

        try {

            for (WebElement estado : lstStatus) {

                String valor =
                        estado.getText().trim();

                System.out.println(
                        "Estado tabla: " + valor);

                if (!valor.toLowerCase()
                        .contains(
                                estadoEsperado.toLowerCase())) {

                    System.out.println(
                            "Estado incorrecto encontrado: "
                                    + valor);

                    return false;
                }
            }

            return true;

        } catch (Exception e) {

            System.out.println(
                    "Error validando estado: "
                            + e.getMessage());

            return false;
        }
    }

    public boolean validarSinDuplicados() {

        try {

            if (lstFechas.isEmpty()) {

                System.out.println(
                        "Validación omitida: no existen registros."
                );

                return true;
            }

            Set<String> registros =
                    new HashSet<>();

            for (int i = 0; i < lstEmpleados.size(); i++) {

                String fecha =
                        lstFechas.get(i)
                                .getText()
                                .trim();

                String empleado =
                        lstEmpleados.get(i)
                                .getText()
                                .trim();

                String estado =
                        lstEstados.get(i)
                                .getText()
                                .trim();

                String llave =
                        fecha + "|" +
                                empleado + "|" +
                                estado;

                System.out.println(
                        "Registro: " + llave);

                if (!registros.add(llave)) {

                    System.out.println(
                            "Duplicado encontrado: "
                                    + llave);

                    return false;
                }
            }

            return true;

        } catch (Exception e) {

            System.out.println(
                    "Error validando duplicados: "
                            + e.getMessage());

            return false;
        }
    }

    public boolean validarPeriodoResultados() {

        try {

            if (lstFechas.isEmpty()) {

                System.out.println(
                        "Validación omitida: no existen registros."
                );

                return true;
            }

            DateTimeFormatter formato =
                    DateTimeFormatter.ofPattern("yyyy-dd-MM");

            LocalDate fechaDesde =
                    LocalDate.parse(
                            fechaDesdeBusqueda,
                            DateTimeFormatter.ISO_LOCAL_DATE);

            LocalDate fechaHasta =
                    LocalDate.parse(
                            fechaHastaBusqueda,
                            DateTimeFormatter.ISO_LOCAL_DATE);

            for (WebElement elemento : lstFechas) {

                String texto =
                        elemento.getText().trim();

                System.out.println(
                        "Fecha tabla: " + texto);

                if (texto.contains("to")) {

                    String[] partes =
                            texto.split("to");

                    LocalDate fechaInicioRegistro =
                            LocalDate.parse(
                                    partes[0].trim(),
                                    formato);

                    LocalDate fechaFinRegistro =
                            LocalDate.parse(
                                    partes[1].trim(),
                                    formato);

                    if (fechaInicioRegistro.isBefore(fechaDesde)
                            || fechaFinRegistro.isAfter(fechaHasta)) {

                        System.out.println(
                                "Fecha fuera de rango: "
                                        + texto);

                        return false;
                    }

                } else {

                    LocalDate fechaRegistro =
                            LocalDate.parse(
                                    texto,
                                    formato);

                    if (fechaRegistro.isBefore(fechaDesde)
                            || fechaRegistro.isAfter(fechaHasta)) {

                        System.out.println(
                                "Fecha fuera de rango: "
                                        + texto);

                        return false;
                    }
                }
            }

            return true;

        } catch (Exception e) {

            System.out.println(
                    "Error validando período: "
                            + e.getMessage());

            return false;
        }
    }
}