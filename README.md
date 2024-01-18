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

> [!TIP]
> Se utiliza el README.md a modo de documentación para los cambios realizados.

> [!NOTE]
> Se ha eliminado la clase InMemoryPersistence y se ha implementado una base de datos local H2 con un carga inicial para que el entorno del proyecto funcione correctamente.

# Solución
* Aqui se explican los cambios realizados en el código para resolver los problemas planteados en el reto.

## Actualización del proyecto a Java 17
### Problema
El proyecto estaba utilizando una versión anterior de Java (1.8) y necesitaba actualizarlo a Java 17 para aprovechar las nuevas características, mejoras de rendimiento y seguridad de esta versión.

### Solución
Para resolver este problema, he realizado los siguientes cambios en el archivo `pom.xml`:
1. Cambiamos la versión de Java en la sección de propiedades a 17.
2. Actualizamos la versión de Spring Boot a una que sea compatible con Java 17. En este caso, utilizamos la versión 3.2.1.

## Lombok
### Problema
El proyecto no estaba utilizando Lombok, por lo que el código contenía mucho boilerplate que dificultaba la lectura y mantenimiento del mismo.

### Solución
Para resolver este problema, he agregado la dependencia de Lombok compatible y las anotaciones necesarias para su implementación.

## Fichero .gitignore
### Problema
El proyecto contenía un fichero `.gitignore` casi vacío, por lo que se podrían subir al repositorio archivos innecesarios.

### Solución
Para resolver este problema, he agregado un fichero `.gitignore` con reglas default para proyectos Java.

## MapStruct
### Problema
El proyecto no estaba utilizando ningun Mapper, por lo que el código contenía muchos mapeos que generabam boilerplate, dificultando la lectura y mantenimiento del mismo.

### Solución
Para resolver este problema, he agregado la dependencia de MapStruct compatible con Java 17/Lombok y creado los mappers necesarios.

## OpenAPI
### Problema
El proyecto no estaba utilizando OpenAPI, por lo que no se podía generar la documentación de la API.

### Solución
Para resolver este problema, he agregado la dependencia de OpenAPI compatible con Spring Boot 3.2.1 y creado varias anotaciones para documentar la API y su visualización en Swagger.

## Spring Actuator
### Problema
El proyecto no estaba utilizando Spring Actuator, por lo que no se podía obtener información sobre el estado de la aplicación.

### Solución
Para resolver este problema, he agregado la dependencia de Spring Actuator compatible, aparte de los endpoints por defecto tambien he agregado un @Timed para medir el tiempo de ejecución de los endpoints.