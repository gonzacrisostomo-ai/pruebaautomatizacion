@Sodimac
Feature: Validación visual y funcional del catálogo Navidad

  Background:
    Given abrir el navegador en la url "https://www.sodimac.cl/sodimac-cl/lista/cat3640002/Navidad?facetSelected=true&f.range.derived.variant.discount=20%25+dcto+y+m%C3%A1s&sid=SO_HO__421300"

  @CP001
  Scenario: Validar elementos visibles del catálogo

    When finaliza la carga visual de la página

    Then debe visualizar el encabezado principal
    And debe visualizar el título de la categoría
    And debe visualizar el contenedor de productos
    And cada tarjeta de producto visible debe mostrar sus elementos obligatorios
    And las imágenes deben mostrarse correctamente
    And los nombres no deben estar vacíos
    And los precios deben tener formato monetario CLP
    And los enlaces de los productos deben ser válidos
    And deben cargarse más de 20 productos
    And ningún elemento debe superponerse
    And no deben existir tarjetas visualmente duplicadas
