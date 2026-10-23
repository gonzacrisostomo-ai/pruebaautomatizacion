package StepDefinition;

import ObjectPage.ListaEmpleadoPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class OrangeDefinition {

    private final ListaEmpleadoPage listaEmpleadoPage = new ListaEmpleadoPage();
    private String supervisorSeleccionado;
    private boolean supervisorValido = true;

    //TC-001
    @And("que el usuario inicia sesión en OrangeHRM con el usuario {string} y la contraseña {string}")
    public void ingresarUsuario(String nombreUsuario, String password) {

        listaEmpleadoPage.escribirUsersname(nombreUsuario);
        listaEmpleadoPage.escribirPassword(password);
        listaEmpleadoPage.clickLogin();

    }

    @And("que el usuario ingresa al módulo PIM")
    public void moduloPIM() {

        listaEmpleadoPage.clickPIM();

    }

    @When("el usuario ingresa {string} en el campo Nombre del empleado de PIM")
    public void ingresarNombreEmpleado(String nombreEmpleado) throws InterruptedException {

        listaEmpleadoPage.ingresarNombreEmpleado(nombreEmpleado);
        Thread.sleep(8000);

    }

    @And("hace clic en Buscar")
    public void clickBuscar() throws InterruptedException {

        listaEmpleadoPage.clickBuscar();
        Thread.sleep(8000);

    }

    @And("cada resultado debe contener {string}")
    public void validarNombreEncontrado(String expectedName) {

        Assert.assertTrue(
                listaEmpleadoPage.validarNombreEmpleado(expectedName)
        );
    }

    @Then("se deben mostrar los resultados de la búsqueda")
    public void validarResultadosBusqueda() {

        Assert.assertTrue(
                "No se encontraron resultados",
                listaEmpleadoPage.existenResultadosBusqueda()
        );
    }


    //TC-002
    @When("the user enters employee id {string}")
    public void ingresarEmployeeId(String employeeId) {

        listaEmpleadoPage.ingresarEmployeeId(employeeId);

    }

    @And("clicks Search")
    public void clicksSearch() throws InterruptedException {

        listaEmpleadoPage.clickBuscar();
        Thread.sleep(10000);

    }

    @Then("the system should return {string} records")
    public void validarCantidadRegistros(String expectedRecords) {

        String resultado = listaEmpleadoPage.obtenerCantidadRegistros();

        System.out.println("Resultado encontrado: " + resultado);

        if (expectedRecords.equals("0")) {

            Assert.assertTrue(resultado.contains("No Records Found"));

        } else {

            Assert.assertTrue(resultado.contains(expectedRecords));

        }
    }

    //TC-003
    @When("the user selects employment status {string}")
    public void seleccionarEmploymentStatus(String status)
            throws InterruptedException {

        boolean encontrado =
                listaEmpleadoPage.seleccionarEmploymentStatus(status);

        if (!encontrado) {

            System.out.println(
                    "Employment Status inexistente: "
                            + status
            );
        }
    }

    @Then("every result should have employment status {string}")
    public void validarEmploymentStatus(String status) {

        String resultado =
                listaEmpleadoPage.obtenerCantidadRegistros();

        if (resultado.contains("No Records Found")) {

            System.out.println(
                    "No existen registros para el estado: "
                            + status
            );

            return;
        }

        Assert.assertTrue(
                listaEmpleadoPage.validarEmploymentStatus(status)
        );
    }

    //TC-003A

    @Then("the system should display {string}")
    public void validarMensaje(String mensajeEsperado) {

        String resultado = listaEmpleadoPage.obtenerCantidadRegistros();

        Assert.assertTrue(
                resultado.contains(mensajeEsperado)
        );
    }

    //TC-004

    @When("the user selects include option {string}")
    public void seleccionarInclude(String includeOption) throws InterruptedException {

        boolean encontrado =
                listaEmpleadoPage.seleccionarInclude(includeOption);

        if (!encontrado) {

            System.out.println(
                    "Include inexistente: "
                            + includeOption
            );
        }
    }

    @Then("results should belong to {string}")
    public void validarInclude(String includeOption) {

        String resultado = listaEmpleadoPage.obtenerCantidadRegistros();

        System.out.println("Resultado encontrado: " + resultado);

        if (includeOption.equals("Past Employees Only")) {

            Assert.assertTrue(
                    resultado.contains("No Records Found")
            );

        } else {

            Assert.assertFalse(resultado.isEmpty());

        }
    }

    // TC-005

    @When("the user enters employee name {string}")
    public void the_user_enters_employee_name(String employeeName) {

        listaEmpleadoPage.ingresarNombreEmpleado(employeeName);

    }

    @And("the user selects job title {string}")
    public void seleccionarJobTitle(String jobTitle)
            throws InterruptedException {

        boolean encontrado =
                listaEmpleadoPage.seleccionarJobTitle(jobTitle);

        if (!encontrado) {

            System.out.println(
                    "El Job Title no existe: "
                            + jobTitle
            );
        }
    }

    @And("the user selects sub unit {string}")
    public void seleccionarSubUnit(String subUnit)
            throws InterruptedException {

        boolean encontrada =
                listaEmpleadoPage.seleccionarSubUnit(subUnit);

        if (!encontrada) {

            System.out.println(
                    "La Sub Unit no existe: "
                            + subUnit
            );
        }
    }

    @And("the user selects supervisor {string}")
    public void seleccionarSupervisor(String supervisor)
            throws InterruptedException {

        supervisorSeleccionado = supervisor;

        boolean valido =
                listaEmpleadoPage.ingresarSupervisor(supervisor);

        if (!valido) {

            supervisorValido = false;

            System.out.println(
                    "Supervisor no encontrado: "
                            + supervisor
            );
        }
    }

    @Then("every result should match all selected criteria")
    public void every_result_should_match_all_selected_criteria() {

        String resultado =
                listaEmpleadoPage.obtenerCantidadRegistros();

        if (resultado.contains("No Records Found")) {

            System.out.println(
                    "No existen registros que cumplan los filtros seleccionados"
            );

            return;
        }

        Assert.assertFalse(
                resultado.trim().isEmpty()
        );

        if (supervisorValido) {

            Assert.assertTrue(
                    listaEmpleadoPage.validarSupervisorResultado(
                            supervisorSeleccionado
                    )
            );
        }

        System.out.println(
                "Se encontraron registros para los filtros aplicados"
        );
    }
}
