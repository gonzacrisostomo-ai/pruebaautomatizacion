package StepDefinition;

import ObjectPage.SodimacPage;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;


public class SodimacDefinition {

    private SodimacPage sodimacPage = new SodimacPage();

    @When("finaliza la carga visual de la página")
    public void finalizaLaCargaVisualDeLaPagina() {

        sodimacPage.esperarCargaPagina();
    }

    @Then("debe visualizar el encabezado principal")
    public void debeVisualizarElEncabezadoPrincipal() {
        Assert.assertTrue(sodimacPage.visualizarEncabezadoPrincipal());
    }

    @Then("debe visualizar el título de la categoría")
    public void debeVisualizarElTituloCategoria() {
        Assert.assertTrue(sodimacPage.visualizarTituloCategoria());
    }

    @Then("debe visualizar el contenedor de productos")
    public void debeVisualizarElContenedorProductos() {
        Assert.assertTrue(sodimacPage.visualizarContenedorProductos());
    }

    @Then("cada tarjeta de producto visible debe mostrar")
    public void cadaTarjetaDeProductoVisibleDebeMostrar(
            DataTable dataTable) {

        Assert.assertTrue(
                "Las tarjetas no contienen todos los elementos requeridos",
                sodimacPage.validarTarjetasCompletas()
        );
    }

    @Then("las imágenes deben mostrarse correctamente")
    public void lasImagenesDebenMostrarseCorrectamente() {
        Assert.assertTrue(sodimacPage.validarImagenesCargadas());
    }

    @Then("los nombres no deben estar vacíos")
    public void losNombresNoDebenEstarVacios() {
        Assert.assertTrue(sodimacPage.validarNombresNoVacios());
    }

    @Then("los precios deben tener formato monetario CLP")
    public void losPreciosDebenTenerFormatoMonetarioCLP() {
        Assert.assertTrue(sodimacPage.validarFormatoMonedaCLP());
    }

    @Then("ningún elemento debe superponerse")
    public void ningunElementoDebeSuperponerse() {
        Assert.assertTrue(sodimacPage.validarTarjetasVisibles());
    }

    @Then("no deben existir tarjetas visualmente duplicadas")
    public void noDebenExistirTarjetasDuplicadas() {
        Assert.assertTrue(sodimacPage.validarSinDuplicados());
    }

    @Then("validar locators principales")
    public void validarLocatorsPrincipales() {

        System.out.println(
                "Header: " +
                        sodimacPage.visualizarEncabezadoPrincipal());

        System.out.println(
                "Categoria: " +
                        sodimacPage.visualizarTituloCategoria());

        System.out.println(
                "Cantidad Productos: " +
                        sodimacPage.obtenerCantidadProductos());

    }
}
