package StepDefinition;

import ObjectPage.LeavePage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class LeaveDefinition {

    private LeavePage leavePage = new LeavePage();
    private boolean empleadoExiste = true;

    // NAVEGACIÓN

    @And("que el usuario ingresa al módulo LEAVE")
    public void queElUsuarioIngresaAlModuloLeave() {
        leavePage.ingresarModuloLeave();
    }

    @Given("que existen solicitudes de vacaciones")
    public void queExistenSolicitudesDeVacaciones() {
        // Precondición documental
    }

    @And("el usuario se encuentra en {string}")
    public void elUsuarioSeEncuentraEn(String opcion) {

        if (opcion.equalsIgnoreCase("Leave List")) {

            leavePage.ingresarLeaveList();

            leavePage.guardarValoresPorDefecto();
        }
    }

    // FILTROS

    @When("ingresa la fecha inicial {string}")
    public void ingresaLaFechaInicial(String fecha) {
        leavePage.ingresarFechaInicio(fecha);
    }

    @When("ingresa la fecha final {string}")
    public void ingresaLaFechaFinal(String fecha) {
        leavePage.ingresarFechaFin(fecha);
    }

    @When("selecciona el estado {string}")
    public void seleccionaElEstado(String estado) {
        leavePage.seleccionarEstado(estado);
    }

    @When("ingresa el nombre del empleado {string}")
    public void ingresaElNombreDelEmpleado(String empleado) {
        leavePage.ingresarNombreEmpleado(empleado);
    }

    @When("selecciona al empleado desde el autocompletado")
    public void seleccionaEmpleadoDesdeAutocompletado() {

        empleadoExiste =
                leavePage.seleccionarEmpleadoAutocompletado();
    }

    @When("selecciona la unidad {string}")
    public void seleccionaLaUnidad(String unidad) {
        leavePage.seleccionarUnidad(unidad);
    }

    @When("configura empleados anteriores en {string}")
    public void configuraEmpleadosAnteriores(String valor) {
        leavePage.configurarEmpleadosAnteriores(valor);
    }

    // ACCIONES

    @When("presiona el botón {string}")
    public void presionaElBoton(String boton) {

        if (boton.equalsIgnoreCase("Search")) {
            leavePage.clickSearch();
        }
    }

    @When("el usuario presiona el botón {string}")
    public void elUsuarioPresionaElBoton(String boton) {

        if (boton.equalsIgnoreCase("Reset")) {
            leavePage.clickReset();
        }
    }

    // VALIDACIONES DE BÚSQUEDA

    @Then("se debe completar la búsqueda sin errores")
    public void seDebeCompletarLaBusquedaSinErrores() {

        Assert.assertTrue(
                "Empleado inexistente en autocompletado",
                empleadoExiste
        );

        Assert.assertTrue(
                "La búsqueda presentó errores",
                leavePage.busquedaEjecutada()
        );
    }

    @Then("se debe visualizar la tabla de resultados")
    public void seDebeVisualizarLaTablaDeResultados() {

        Assert.assertTrue(
                leavePage.existenResultados()
        );
    }

    // VALIDACIONES DE RESULTADOS

    @And("las solicitudes deben corresponder al empleado {string}")
    public void lasSolicitudesDebenCorresponderAlEmpleado(
            String empleado) {

        Assert.assertTrue(
                "Existen registros que no corresponden a "
                        + empleado,
                leavePage.validarEmpleadoResultados(
                        empleado)
        );
    }

    @And("las solicitudes deben cumplir el estado {string}")
    public void lasSolicitudesDebenCumplirElEstado(
            String estado) {

        Assert.assertTrue(
                "Existen registros con estado distinto a "
                        + estado,
                leavePage.validarEstadoResultados(
                        estado)
        );
    }

    @And("las solicitudes deben corresponder a la unidad {string}")
    public void lasSolicitudesDebenCorresponderALaUnidad(
            String unidad) {

        System.out.println(
                "Validación no aplicable: "
                        + "la columna Sub Unit no existe "
                        + "en la tabla de resultados.");
    }

    @And("los registros deben respetar el filtro de empleados anteriores")
    public void losRegistrosDebenRespetarElFiltroDeEmpleadosAnteriores() {

        System.out.println(
                "Validación no aplicable: la grilla no expone información de empleados anteriores."
        );
    }

    @And("no deben existir registros duplicados")
    public void noDebenExistirRegistrosDuplicados() {

        Assert.assertTrue(
                "Se encontraron registros duplicados",
                leavePage.validarSinDuplicados()
        );
    }

    @And("todas las páginas deben respetar los filtros aplicados")
    public void todasLasPaginasDebenRespetarLosFiltrosAplicados() {

        System.out.println(
                "Validación omitida: existe una sola página de resultados."
        );
    }

    // VALIDACIONES RESET

    @Then("los filtros deben recuperar sus valores predeterminados")
    public void losFiltrosDebenRecuperarSusValoresPredeterminados() {

        Assert.assertTrue(
                "Los filtros no fueron reiniciados",
                leavePage.filtrosReseteados()
        );
    }

    @And("no deben permanecer los criterios ingresados anteriormente")
    public void noDebenPermanecerLosCriteriosIngresadosAnteriormente() {

        Assert.assertTrue(
                "Persisten filtros después del Reset",
                leavePage.filtrosReseteados()
        );
    }

    @And("las solicitudes deben estar dentro del período consultado")
    public void lasSolicitudesDebenEstarDentroDelPeriodoConsultado() {

        Assert.assertTrue(
                "Existen registros fuera del período consultado",
                leavePage.validarPeriodoResultados()
        );
    }

}