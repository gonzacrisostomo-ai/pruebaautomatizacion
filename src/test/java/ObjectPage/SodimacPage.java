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

    private final By productosLocator =
            By.xpath("//*[@data-pod]");

    private List<WebElement> obtenerProductos() {
        return driver.findElements(productosLocator);
    }

    private static final String XPATH_NOMBRE =
            ".//*[contains(@class,'pod-subTitle')]";

    private static final String XPATH_PRECIO =
            ".//span[contains(text(),'$')]";


    private static final String XPATH_IMAGEN =
            ".//img";

    private static final String XPATH_PRECIO_VISIBLE =
            ".//span[contains(text(),'$')]";

    private static final int MINIMO_PRODUCTOS = 20;

    // VALIDACIONES GENERALES

    public void esperarCargaPagina() {

        visualizarElemento(encabezadoPrincipal, 15);

        List<WebElement> productos = obtenerProductos();

        if (productos.isEmpty()) {
            throw new RuntimeException(
                    "No se encontraron productos en la página");
        }

        visualizarElemento(
                productos.get(0),
                15
        );

        cargarTodosLosProductos();
    }

    public boolean visualizarEncabezadoPrincipal() {
        return encabezadoPrincipal.isDisplayed();
    }

    public boolean visualizarTituloCategoria() {

        return categoriasNavidad != null
                && !categoriasNavidad.isEmpty();
    }

    public boolean visualizarContenedorProductos() {
        return !obtenerProductos().isEmpty();
    }

    public int obtenerCantidadProductos() {
        return obtenerProductos().size();
    }

    // VALIDAR ELEMENTOS TARJETA

    private boolean esProductoValido(WebElement producto) {

        List<WebElement> nombres =
                producto.findElements(
                        By.xpath(XPATH_NOMBRE));

        List<WebElement> precios =
                producto.findElements(
                        By.xpath(XPATH_PRECIO));

        List<WebElement> imagenes =
                producto.findElements(
                        By.xpath(XPATH_IMAGEN));

        return !nombres.isEmpty()
                && !precios.isEmpty()
                && !imagenes.isEmpty();
    }

    public boolean validarTarjetasCompletas() {

        System.out.println(
                "Cantidad productos: "
                        + obtenerProductos().size());

        int productosValidos = 0;

        for (WebElement producto : obtenerProductos()) {

            try {

                if (!esProductoValido(producto)) {

                    System.out.println(
                            "Elemento descartado: no es producto");

                    continue;
                }

                productosValidos++;

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

            } catch (Exception e) {

                System.out.println(
                        "Error producto: "
                                + e.getMessage());

                return false;
            }
        }

        System.out.println(
                "Productos válidos: "
                        + productosValidos);

        if (productosValidos == 0) {

            System.out.println(
                    "No existen productos válidos");

            return false;
        }

        return true;
    }

    public void cargarTodosLosProductos() {

        JavascriptExecutor js =
                (JavascriptExecutor) driver;

        long alturaAnterior = 0;
        int intentosSinCambios = 0;
        int maxIntentosSinCambios = 5;

        while (intentosSinCambios < maxIntentosSinCambios) {

            int productosAntes =
                    obtenerProductos().size();

            // Baja de forma gradual
            js.executeScript(
                    "window.scrollBy(0, 800);");

            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            int productosDespues =
                    obtenerProductos().size();

            Number altura =
                    (Number) js.executeScript(
                            "return document.body.scrollHeight");

            long alturaActual = 0;

            if (altura != null) {
                alturaActual = altura.longValue();
            }

            System.out.println(
                    "Productos detectados: "
                            + productosDespues);

            if (productosDespues == productosAntes
                    && alturaActual == alturaAnterior) {

                intentosSinCambios++;

            } else {

                intentosSinCambios = 0;
            }

            alturaAnterior = alturaActual;
        }

        System.out.println(
                "Productos encontrados: "
                        + obtenerProductos().size());

        // Regresa arriba para que las capturas queden ordenadas
        js.executeScript(
                "window.scrollTo(0,0);");
    }

    public boolean validarCantidadMinimaProductos() {

        int productosValidos = 0;

        for (WebElement producto : obtenerProductos()) {

            if (esProductoValido(producto)) {
                productosValidos++;
            }
        }

        System.out.println(
                "Productos válidos encontrados: "
                        + productosValidos);

        return productosValidos >= MINIMO_PRODUCTOS;
    }

    public boolean validarLinksProductos() {

        for (WebElement producto : obtenerProductos()) {

            if (!esProductoValido(producto)) {
                continue;
            }

            String href =
                    producto.getDomAttribute("href");

            if (href == null || href.trim().isEmpty()) {

                System.out.println(
                        "Producto sin enlace");

                return false;
            }
        }

        return true;
    }

    // VALIDAR IMÁGENES

    public boolean validarImagenesCargadas() {

        for (WebElement producto : obtenerProductos()) {

            if (!esProductoValido(producto)) {
                continue;
            }

            List<WebElement> imagenes =
                    producto.findElements(
                            By.xpath(XPATH_IMAGEN));

            if (imagenes.isEmpty()) {
                return false;
            }

            String src =
                    imagenes.get(0)
                            .getAttribute("src");

            if (src == null || src.trim().isEmpty()) {

                System.out.println(
                        "Imagen sin src");

                return false;
            }
        }

        return true;
    }

    // VALIDAR NOMBRES

    public boolean validarNombresNoVacios() {

        for (WebElement producto : obtenerProductos()) {

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
                Pattern.compile("^\\$\\s?\\d{1,3}(\\.\\d{3})*$");

        for (WebElement producto : obtenerProductos()) {

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

                if (formatoCLP.matcher(texto).matches()) {
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

        for (WebElement producto : obtenerProductos()) {

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

                String url =
                        producto.getDomAttribute("href");

                if (url == null) {
                    url = "";
                }
                String llave =
                        nombre + "|" + precio + "|" + url;

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
                return false;
            }
        }

        return true;
    }

    // validar productos con precio

    public boolean validarProductosConPrecio() {

        for (WebElement producto : obtenerProductos()) {

            if (!esProductoValido(producto)) {
                continue;
            }

            List<WebElement> precios =
                    producto.findElements(
                            By.xpath(XPATH_PRECIO_VISIBLE));

            if (precios.isEmpty()) {

                System.out.println(
                        "Producto sin precio visible");

                return false;
            }
        }

        return true;
    }

    // VALIDAR SUPERPOSICIÓN

    public boolean validarTarjetasVisibles() {

        for (WebElement producto : obtenerProductos()) {

            if (!esProductoValido(producto)) {
                continue;
            }

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