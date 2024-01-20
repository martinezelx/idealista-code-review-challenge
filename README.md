# Reto: Servicio para gestión de calidad de los anuncios

[![Build Status](https://travis-ci.org/idealista/coding-test-ranking.svg?branch=master)](https://travis-ci.org/idealista/coding-test-ranking)

Este repositorio contiene el API de un servicio que se encarga de medir la calidad de los anuncios. Tu objetivo será realizar un code review sobre el código contenido en el repositorio. Para ello, te proporcionamos la descripción de la tarea que habría recibido el equipo de desarrollo.

Los supuestos están basados en un hipotético *equipo de gestión de calidad de los anuncios*, que demanda una serie de verificaciones automáticas para clasificar los anuncios en base a una serie de características concretas.

## Historias de usuario

* Yo, como encargado del equipo de gestión de calidad de los anuncios quiero asignar una puntuación a un anuncio para que los usuarios de idealista puedan ordenar anuncios de más completos a menos completos. La puntuación del anuncio es un valor entre 0 y 100 que se calcula teniendo en cuenta las siguientes reglas:
  * Si el anuncio no tiene ninguna foto se restan 10 puntos. Cada foto que tenga el anuncio proporciona 20 puntos si es una foto de alta resolución (HD) o 10 si no lo es.
  * Que el anuncio tenga un texto descriptivo suma 5 puntos.
  * El tamaño de la descripción también proporciona puntos cuando el anuncio es sobre un piso o sobre un chalet. En el caso de los pisos, la descripción aporta 10 puntos si tiene entre 20 y 49 palabras o 30 puntos si tiene 50 o más palabras. En el caso de los chalets, si tiene más de 50 palabras, añade 20 puntos.
  * Que las siguientes palabras aparezcan en la descripción añaden 5 puntos cada una: Luminoso, Nuevo, Céntrico, Reformado, Ático.
  * Que el anuncio esté completo también aporta puntos. Para considerar un anuncio completo este tiene que tener descripción, al menos una foto y los datos particulares de cada tipología, esto es, en el caso de los pisos tiene que tener también tamaño de vivienda, en el de los chalets, tamaño de vivienda y de jardín. Además, excepcionalmente, en los garajes no es necesario que el anuncio tenga descripción. Si el anuncio tiene todos los datos anteriores, proporciona otros 40 puntos.

* Yo como encargado de calidad quiero que los usuarios no vean anuncios irrelevantes para que el usuario siempre vea contenido de calidad en idealista. Un anuncio se considera irrelevante si tiene una puntación inferior a 40 puntos.

* Yo como encargado de calidad quiero poder ver los anuncios irrelevantes y desde que fecha lo son para medir la calidad media del contenido del portal.

* Yo como usuario de idealista quiero poder ver los anuncios ordenados de mejor a peor para encontrar fácilmente mi vivienda.

## Consideraciones importantes

Para nosotros, lo importante de este code review es entender como piensas. Queremos saber que consideras importante y que no, que crees que podría mejorarse y que crees que está bien como está. 

Si tienes dudas entre comentar algo porque es algo que en un proyecto real no comentarías, hazlo. Sabemos que en este code review falta un montón de contexto sobre consensos que tendrías con tu equipo en una situación real, por lo que cualquier comentario nos va a servir mejor para entenderte.

No queremos que dediques tiempo a refactorizar el código, pero si hay algún comentario que quieres hacer y no sabes como explicarnos, nos puedes adjuntar código en cualquier lenguaje que conozcas (o pseudocódigo).

Para facilitar las cosas, cuando quieras referirte a alguna línea en concreto del código utiliza como nomenclatura algo parecido a NombreDeClase#lineaDeCódigo o NombreDeClase#NombreDeMétodo.

## Criterios de aceptación

Debes entregarnos un fichero de texto con todos los comentarios que harías sobre el código del repositorio.

---
# Code Review
* Aqui se explican los cambios realizados en el código para resolver los problemas planteados en el reto.
---

> [!TIP]
> Se utiliza el README.md a modo de documentación para los cambios realizados.

> [!NOTE]
> Se ha eliminado la clase InMemoryPersistence y se ha implementado una base de datos local H2 con un carga inicial para que el entorno del proyecto funcione correctamente.

# Actualizaciones de requisitos

## Cambio en el Cálculo de Completitud del Anuncio y Normalización de Puntuación

### Problema:
Originalmente, la puntuación de un anuncio se establecía en 40 puntos si cumplía con los criterios de completitud, sin tener en cuenta otras puntuaciones acumuladas. Esto subestimaba la puntuación total de anuncios con atributos adicionales positivos. Además, no había una verificación explícita para asegurar que la puntuación total no excediera el límite máximo de 100 puntos.

### Solución:
Se realizó una doble modificación en el método de cálculo:

1. **Puntuación por Completitud**:
  - En lugar de asignar directamente 40 puntos a los anuncios completos, se modificó el método para sumar 40 puntos a la puntuación acumulada. Este enfoque garantiza que los anuncios que son completos y tienen características adicionales de alta calidad reciban una puntuación que refleje adecuadamente su valor.
  - Cambio en el código:
    ```java
    private int calculateCompletenessScore(Ad ad) {
        return ad.isComplete() ? Constants.FORTY : 0;
    }
    ```

2. **Normalización de la Puntuación Total**:
  - Se añadió una lógica para asegurar que la puntuación total de un anuncio no exceda el límite máximo de 100 puntos. Esto se realiza mediante la aplicación de las funciones `Math.max` y `Math.min` para mantener la puntuación dentro del rango de 0 a 100.
  - Cambio en el código:
    ```java
    private void calculateScore(Ad ad) {
        int score = calculatePicturesScore(ad.getPictures());
        score += calculateDescriptionScore(ad);
        score += calculateCompletenessScore(ad);

        ad.setScore(Math.min(Math.max(score, Constants.ZERO), Constants.ONE_HUNDRED));
    }
    ```
> [!NOTE]
> Con estos ajustes, el sistema ahora no solo calcula la puntuación de los anuncios de manera más justa y representativa, sino que también asegura que dicha puntuación se mantenga dentro de los límites establecidos, mejorando la precisión y la fiabilidad del sistema de clasificación de anuncios en términos de completitud y calidad general.

# Actualizaciones del Proyecto

## Actualización a Java 17
### Descripción
Se ha actualizado el proyecto de Java 1.8 a Java 17 para aprovechar las últimas características, mejoras de rendimiento y seguridad que ofrece esta versión.

### Ventajas
- Acceso a nuevas características y APIs de Java.
- Mejoras significativas en rendimiento y seguridad.
- Mayor compatibilidad y soporte a largo plazo.

## Implementación de Lombok
### Descripción
Se ha integrado Lombok en el proyecto para reducir el código boilerplate, especialmente en la definición de modelos y constructores.

### Ventajas
- Reducción significativa del código repetitivo.
- Mejora en la legibilidad y mantenimiento del código.
- Generación automática de métodos comunes como getters, setters y toString.

## Mejora del Archivo .gitignore
### Descripción
Se ha actualizado el archivo `.gitignore` con reglas más completas para proyectos Java, asegurando que solo los archivos relevantes sean rastreados en el repositorio.

### Ventajas
- Evita la inclusión de archivos innecesarios en el control de versiones.
- Mejora la limpieza y organización del repositorio.
- Prevención de conflictos y problemas de merge relacionados con archivos no deseados.

## Incorporación de la Base de Datos H2
### Descripción
Se ha integrado la base de datos en memoria H2 en el proyecto. Esto permite simular un entorno de base de datos real sin la necesidad de configurar un sistema de base de datos externo, facilitando las pruebas y el desarrollo.

### Ventajas
- La incorporación de H2 es una mejora significativa para el proyecto, ya que aporta flexibilidad y eficiencia en el desarrollo y las pruebas, manteniendo al mismo tiempo una experiencia cercana a la realidad de un entorno de producción.

> [!TIP]
> Se puede acceder al dashboard de H2 en el siguiente endpoint:
> - http://localhost:8080/h2-console
> - JDBC URL: jdbc:h2:mem:testdb
> - User Name: sa
> - Password: root

## Incorporación de MapStruct
### Descripción
Se ha añadido MapStruct al proyecto para manejar de forma eficiente los mapeos entre distintos objetos (DTOs y entidades), reduciendo el boilerplate en esta área.

### Ventajas
- Simplificación de los mapeos de objetos.
- Mejora en la mantenibilidad y claridad del código.
- Integración fácil con Lombok y Java 17.

## Adopción de OpenAPI
### Descripción
Se ha integrado OpenAPI para generar automáticamente la documentación de la API, facilitando su visualización y uso a través de herramientas como Swagger.

### Ventajas
- Documentación automática y actualizada de la API.
- Facilita la prueba y exploración de la API a través de interfaces de usuario como Swagger.
- Mejora en la colaboración y comprensión de los endpoints de la API.

> [!TIP]
> Se puede acceder a Swagger en el siguiente endpoint:
> - http://localhost:8080/swagger-ui/index.html

## Uso de Spring Actuator
### Descripción
Se ha implementado Spring Actuator para proporcionar información detallada sobre el estado y rendimiento de la aplicación.

### Ventajas
- Monitoreo en tiempo real del estado de la aplicación.
- Posibilidad de medir el tiempo de ejecución de endpoints específicos.
- Herramienta esencial para el diagnóstico y el análisis de rendimiento.

> [!TIP]
> Los tiempos de ejecución pueden ser revisados en los siguientes endpoints:
> - http://localhost:8080/actuator/metrics/ads.quality
> - http://localhost:8080/actuator/metrics/ads.public
> - http://localhost:8080/actuator/metrics/ads.score

## Incorporación de GitHub Actions
### Descripción
Se ha implementado GitHub Actions en el proyecto para automatizar flujos de trabajo de integración continua (CI) y despliegue continuo (CD). Esto incluye la automatización de pruebas, compilación, y despliegue de la aplicación.

### Ventajas
- **Automatización de Pruebas y Compilación**: Cada vez que se realiza un push o pull request, GitHub Actions puede ejecutar automáticamente las pruebas y la compilación del proyecto, asegurando que todos los cambios cumplen con los estándares de calidad establecidos.
- **Despliegue Continuo**: Facilita la implementación automática de la aplicación en un entorno de producción o de prueba tras cada confirmación en la rama principal, lo que agiliza significativamente el proceso de despliegue.
- **Integración Sencilla con GitHub**: Al estar integrado directamente en GitHub, se simplifica la configuración y el mantenimiento de los flujos de trabajo de CI/CD.

> [!NOTE]
> Con estas actualizaciones, el proyecto no solo se mantiene al día con las últimas tecnologías y prácticas de desarrollo, sino que también mejora en términos de eficiencia, mantenimiento, y capacidad de monitoreo.

# Actualizaciones de código
## Mejora y Refactorización de Código
> [!NOTE]
> Se han realizado varias mejoras y refactorizaciones en el código para intentar seguir los principios SOLID y las buenas prácticas de programación, en esta sección se explican los cambios más importantes.

### Refactorización de la Clase `AdsServiceImpl`

#### Descripción:
La clase `AdsServiceImpl` ha sido refactorizada para mejorar su legibilidad y mantenibilidad. Los cambios realizados incluyen la extracción de bloques de código a métodos privados, la simplificación de la lógica compleja y la mejora de la nomenclatura de las variables y métodos.

#### Cambios Detallados:

1. **Extracción de Métodos**: Se han extraído bloques de código a métodos privados para mejorar la legibilidad y reutilización del código. Por ejemplo, el cálculo de la puntuación de las imágenes y la descripción se ha movido a los métodos `calculatePicturesScore` y `calculateDescriptionScore` respectivamente.

2. **Simplificación de la Lógica**: Se ha simplificado la lógica compleja para mejorar la legibilidad del código. Por ejemplo, la lógica para calcular la puntuación de las palabras clave se ha simplificado utilizando operaciones de stream y lambda.

3. **Mejora de la Nomenclatura**: Se han renombrado algunas variables y métodos para mejorar la claridad y coherencia del código. Por ejemplo, el método `calculateScores` se ha renombrado a `calculateAdScores` para reflejar más claramente su propósito.

## Incorporación de Global Exception Handler

### Descripción:
Se ha implementado un `GlobalExceptionHandler` en el proyecto. Este componente de Spring maneja las excepciones a nivel global, proporcionando respuestas uniformes y controladas en caso de errores.

### Ventajas:
- Mejora la consistencia y la legibilidad del manejo de errores.
- Reduce la duplicación de código en el manejo de excepciones.
- Proporciona un punto central para el manejo de errores y la generación de respuestas de error.

### Descripción de las Excepciones:

#### AdScoreCalculationException
Esta excepción se lanza cuando ocurre un error durante el cálculo de la puntuación de un anuncio. Esto puede suceder, por ejemplo, si los datos necesarios para el cálculo son nulos o no están en el formato esperado.

#### AdRepositoryAccessException
Esta excepción se lanza cuando ocurre un error al acceder al repositorio de anuncios. Esto puede suceder, por ejemplo, si la base de datos está inaccesible o si ocurre un error durante la consulta.

## Incorporación de Logs con Lombok @Slf4j

### Descripción:
Se ha integrado la anotación `@Slf4j` de Lombok en las clases de servicio y repositorio para facilitar la generación de logs. Esta anotación de Lombok genera automáticamente un objeto `log` de tipo `org.slf4j.Logger` en la clase anotada, permitiendo registrar mensajes de log de manera sencilla y eficiente.

### Ventajas:
- Simplificación del código de registro de logs.
- Mejora en la legibilidad y mantenibilidad del código.
- Facilita el seguimiento y depuración de la aplicación.

### Uso en el Código:

La anotación `@Slf4j` se utiliza en las clases de servicio y repositorio para generar logs. A continuación, se muestra un ejemplo de su uso en la clase `AdServiceImpl`:

```java
@Slf4j
@Service
public class AdServiceImpl implements AdService {

    // ...

    @Override
    public List<PublicAd> findPublicAds() {
        log.info("Finding public ads");
        // ...
        log.info("Found {} public ads", publicAds.size());
        return publicAds;
    }

    // ...
}
```
> [!NOTE]
> En este código, se utilizan los métodos `log.info` para registrar información sobre la ejecución de los métodos. Los mensajes de registro pueden incluir información adicional, como el número de anuncios encontrados.

## Cambios en el Controller

### Descripción:
Se han realizado varias mejoras y cambios en la clase `AdController` para mejorar su funcionalidad y adherirse a las mejores prácticas de desarrollo. Los cambios incluyen la adición de anotaciones para la documentación de la API, la incorporación de métricas de tiempo de ejecución y la modificación de los endpoints.

### Cambios Detallados:

1. **Cambio de Nombre de Clase y Servicio**: Se ha cambiado el nombre de la clase de `AdsController` a `AdController` y el servicio de `AdsService` a `AdService` para mantener la consistencia en la nomenclatura.

2. **Incorporación de Anotaciones de OpenAPI**: Se han añadido anotaciones de OpenAPI a los métodos del controlador para generar automáticamente la documentación de la API. Estas anotaciones proporcionan detalles sobre la operación de cada endpoint y las posibles respuestas.

3. **Incorporación de Métricas de Tiempo de Ejecución**: Se ha añadido la anotación `@Timed` a los métodos del controlador para medir el tiempo de ejecución de cada endpoint. Esto proporciona información valiosa para el monitoreo y la optimización del rendimiento.

4. **Modificación de Endpoints**: Se ha cambiado el endpoint para calcular la puntuación de los anuncios de un método GET a un método PUT para adherirse a las convenciones REST. Además, se ha cambiado la respuesta de este endpoint a `ResponseEntity.noContent().build()` para indicar que la operación fue exitosa pero no devuelve contenido.

> [!NOTE]
> Con estos cambios, el controlador ahora proporciona una documentación de API más detallada, permite el seguimiento del tiempo de ejecución de los endpoints y sigue las convenciones REST de manera más precisa.

## Cambio de Integer a UUID para los IDs de las Entidades

### Descripción:
Se ha realizado un cambio en las entidades `Ad` y `Picture`, donde los IDs, que antes eran de tipo `Integer`, ahora son de tipo `UUID`. Este cambio proporciona una mayor seguridad y reduce la posibilidad de colisiones, ya que los UUID son identificadores únicos a nivel mundial.

### Ventajas:
- **Seguridad Mejorada**: Los UUID son más seguros que los enteros para los IDs, ya que son prácticamente únicos. Esto reduce la posibilidad de colisiones de IDs.
- **Escalabilidad**: Los UUID son especialmente útiles en sistemas distribuidos donde no se puede garantizar la unicidad de los IDs a través de diferentes nodos.
- **Consistencia**: Los UUID son consistentes en tamaño (siempre 128 bits), lo que puede simplificar el diseño de la base de datos y la interfaz de programación.

> [!NOTE]
> Con este cambio, el sistema ahora utiliza UUID para los IDs de las entidades `Ad` y `Picture`, mejorando la seguridad y la escalabilidad del sistema.

## Actualizaciones de Testing

### Descripción:
Se han realizado varias adiciones y modificaciones a las pruebas unitarias e integración del proyecto. Estos cambios buscan mejorar la cobertura de las pruebas, asegurando que todas las funcionalidades del sistema estén adecuadamente probadas.

### Detalles:

1. **Adición de Nuevas Pruebas**: Se han añadido nuevas pruebas para cubrir funcionalidades que anteriormente no estaban probadas. Estas nuevas pruebas ayudan a garantizar que todas las partes del sistema funcionen como se espera.

2. **Modificación de Pruebas Existentes**: Se han modificado algunas pruebas existentes para reflejar los cambios realizados en el código del sistema. Estas modificaciones aseguran que las pruebas sigan siendo relevantes y útiles a pesar de los cambios en el código.

3. **Mejora de la Cobertura de las Pruebas**: Con la adición de nuevas pruebas y la modificación de las existentes, se ha mejorado la cobertura de las pruebas del sistema. Esto significa que una mayor proporción del código del sistema está siendo probada, lo que aumenta la confianza en la corrección y robustez del sistema.

> [!NOTE]
> Con estas actualizaciones, el sistema ahora tiene una cobertura de pruebas más completa y precisa, lo que mejora la calidad y la fiabilidad del código.