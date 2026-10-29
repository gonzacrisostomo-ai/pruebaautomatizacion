package ObjectPage;

import Control.BaseController;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class SodimacPage extends BaseController {

    // ELEMENTOS PRINCIPALES

    @FindBy(id = "testId-categoryBanner")
    private WebElement encabezadoPrincipal;

    @FindBy(xpath = "//a//h4")
    private List<WebElement> categoriasNavidad;

    @FindBy(xpath = "//div[@data-testid='ssr-pod']")
    private List<WebElement> tarjetasProductos;

    private static final String XPATH_NOMBRE =
            ".//*[contains(@class,'pod-subTitle')]";

    private static final String XPATH_PRECIO =
            ".//li[@data-cmr-price]";

    private static final String XPATH_IMAGEN =
            ".//img";

    private static final String XPATH_PRECIO_VISIBLE =
            ".//span[contains(text(),'$')]";

    // VALIDACIONES GENERALES

    public void esperarCargaPagina() {

        visualizarElemento(encabezadoPrincipal, 15);

        visualizarElemento(
                tarjetasProductos.get(0),
                15
        );
    }

    public boolean visualizarEncabezadoPrincipal() {
        return encabezadoPrincipal.isDisplayed();
    }

    public boolean visualizarTituloCategoria() {

        return categoriasNavidad != null
                && !categoriasNavidad.isEmpty();
    }

    public boolean visualizarContenedorProductos() {
        return !tarjetasProductos.isEmpty();
    }

    public int obtenerCantidadProductos() {
        return tarjetasProductos.size();
    }

    // VALIDAR ELEMENTOS TARJETA

    private boolean esProductoValido(WebElement producto) {

        List<WebElement> nombres =
                producto.findElements(
                        By.xpath(XPATH_NOMBRE));

        List<WebElement> precios =
                producto.findElements(
                        By.xpath(XPATH_PRECIO));

        return !nombres.isEmpty()
                && !precios.isEmpty();
    }

    public boolean validarTarjetasCompletas() {

        System.out.println(
                "Cantidad productos: "
                        + tarjetasProductos.size());

        for (WebElement producto : tarjetasProductos) {

            try {

                if (!esProductoValido(producto)) {

                    System.out.println(
                            "Elemento descartado: no es producto");

                    continue;
                }

                String nombre =
                        producto.findElement(
                                        By.xpath(XPATH_NOMBRE))
                                .getText()
                                .trim();


                List<WebElement> imagenes =
                        producto.findElements(
                                By.xpath(XPATH_IMAGEN));

                if (imagenes.isEmpty()) {

                    System.out.println(
                            "Elemento omitido (sin imagen)");

                    continue;
                }

                if (nombre.isEmpty()) {

                    System.out.println(
                            "Producto con nombre vacío");

                    return false;
                }

            } catch (Exception e) {

                System.out.println(
                        "Error producto: "
                                + e.getMessage());
            }
        }

        return true;
    }

    // VALIDAR IMÁGENES

    public boolean validarImagenesCargadas() {

        JavascriptExecutor js =
                (JavascriptExecutor) driver;

        for (WebElement producto : tarjetasProductos) {

            if (!esProductoValido(producto)) {
                continue;
            }

            List<WebElement> imagenes =
                    producto.findElements(
                            By.xpath(XPATH_IMAGEN));

            if (imagenes.isEmpty()) {

                System.out.println(
                        "Producto sin imagen detectado");

                continue;
            }

            WebElement imagen =
                    imagenes.get(0);

            Boolean cargada =
                    (Boolean) js.executeScript(
                            "return arguments[0].complete && arguments[0].naturalWidth > 0;",
                            imagen);

            if (!Boolean.TRUE.equals(cargada)) {

                System.out.println(
                        "Imagen no cargada correctamente");

                return false;
            }
        }

        return true;
    }

    // VALIDAR NOMBRES

    public boolean validarNombresNoVacios() {

        for (WebElement producto : tarjetasProductos) {

            if (!esProductoValido(producto)) {
                continue;
            }

            String nombre =
                    producto.findElement(
                                    By.xpath(XPATH_NOMBRE))
                            .getText()
                            .trim();

            if (nombre.isEmpty()) {

                System.out.println(
                        "Producto con nombre vacío");

                return false;
            }
        }

        return true;
    }

    // VALIDAR PRECIOS CLP

    public boolean validarFormatoMonedaCLP() {

        Pattern formatoCLP =
                Pattern.compile("\\$\\s?\\d{1,3}(\\.\\d{3})*");

        for (WebElement producto : tarjetasProductos) {

            if (!esProductoValido(producto)) {
                continue;
            }

            List<WebElement> precios =
                    producto.findElements(
                            By.xpath(XPATH_PRECIO_VISIBLE));

            boolean precioValido = false;

            for (WebElement precio : precios) {

                String texto =
                        precio.getText()
                                .replace("\n", "")
                                .trim();

                if (formatoCLP.matcher(texto).find()) {

                    precioValido = true;
                    break;
                }
            }

            if (!precioValido) {

                System.out.println(
                        "Precio con formato inválido");

                return false;
            }
        }

        return true;
    }

    // VALIDAR DUPLICADOS

    public boolean validarSinDuplicados() {

        Set<String> productosUnicos =
                new HashSet<>();

        for (WebElement producto : tarjetasProductos) {

            try {

                String contenido =
                        producto.getText();

                if (contenido.contains("Patrocinado")) {
                    continue;
                }

                if (!esProductoValido(producto)) {
                    continue;
                }

                List<WebElement> nombres =
                        producto.findElements(
                                By.xpath(XPATH_NOMBRE));

                List<WebElement> precios =
                        producto.findElements(
                                By.xpath(XPATH_PRECIO));

                String nombre =
                        nombres.get(0)
                                .getText()
                                .trim();

                String precio =
                        precios.get(0)
                                .getText()
                                .trim();

                String llave =
                        nombre + "|" + precio;

                if (!productosUnicos.add(llave)) {

                    System.out.println(
                            "Duplicado encontrado: "
                                    + llave);

                    return false;
                }

            } catch (Exception e) {

                System.out.println(
                        "Error validando duplicados: "
                                + e.getMessage());
            }
        }

        return true;
    }

    // VALIDAR SUPERPOSICIÓN

    public boolean validarTarjetasVisibles() {

        for (WebElement producto : tarjetasProductos) {

            Rectangle rect =
                    producto.getRect();

            if (rect.getWidth() <= 0) {
                return false;
            }

            if (rect.getHeight() <= 0) {
                return false;
            }

            if (!producto.isDisplayed()) {
                return false;
            }
        }

        return true;
    }
}