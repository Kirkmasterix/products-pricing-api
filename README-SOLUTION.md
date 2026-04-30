🧪 Sistema de Productos con Precios Históricos

📌 Descripción

Este proyecto implementa una API REST para la gestión de productos y sus precios históricos.
Cada producto puede tener múltiples precios a lo largo del tiempo, cumpliendo la restricción de que no puede haber solapamiento de fechas entre precios del mismo producto.

El sistema permite:

Crear productos

Añadir precios con rango temporal

Consultar el precio vigente en una fecha

Obtener el historial completo de precios

🏗️ Stack tecnológico

Java 21

Spring Boot 3.2.5

Spring Data JPA (Hibernate)

PostgreSQL

Docker + Docker Compose

Gradle

k6 (pruebas de rendimiento)

Mockito + JUnit + Testcontainer (pruebas de integración y unitarias)

▶️ Instrucciones para ejecutar el proyecto

🔹 Requisitos

Docker

Docker Compose

🔹 Ejecución del sistema 

docker compose up --build

Esto levanta automáticamente:

API → http://localhost:9090

PostgreSQL

Test de rendimiento con k6

⚠️ Problema común: Docker Desktop no arranca

Si Docker Compose no funciona correctamente:

🔹 Solución

Abrir Docker Desktop

Ir a:

Settings → Docker Engine

Añadir o modificar:

{
"min-api-version": "1.24"
}

Guardar y reiniciar Docker Desktop


📘 Endpoints implementados

🔹 Crear producto

POST /products
{
"name": "Zapatillas deportivas",
"description": "Modelo 2025 edición limitada"
}

🔹 Añadir precio a producto

POST /products/{id}/prices
{
"value": 99.99,
"initDate": "2024-01-01",
"endDate": "2024-06-30"
}

✔️ Validaciones:

No solapamiento de fechas
initDate < endDate
endDate puede ser null

🔹 Obtener precio en fecha
GET /products/{id}/prices?date=2024-04-15
{
"value": 99.99
}

🔹 Obtener historial de precios
GET /products/{id}/prices
{
"name": "...",
"description": "...",
"prices": [...]
}

🧠 Justificación de decisiones técnicas

🔹 Modelado

Relación 1:N entre Product y Price
Índices por product_id y fechas para optimizar consultas temporales

🔹 Persistencia

JPA + Hibernate por rapidez de desarrollo y mantenibilidad
Configuración pensada para entorno Docker

🔹 Validación de negocio

Implementada en la capa de servicio

Reglas:

No solapamiento de rangos de fechas
Consistencia temporal
Se evita depender únicamente de la base de datos para mantener control del dominio

🔹 Diseño REST

Se mantiene la estructura:

/products/{id}/prices

👉 Justificación:

Refuerza jerarquía del recurso
Mejora semántica REST
Explicita relación entre entidades
⚠️ Supuestos y decisiones adicionales
Un producto puede no tener precios
endDate = null implica precio vigente indefinidamente
No se implementan update/delete (no requeridos)
No se añade autenticación para centrarse en lógica de negocio y rendimiento

⚡ Prueba de rendimiento (k6)

🔹 Ejecución

Se ejecuta automáticamente al levantar el sistema:

docker compose up --build

🔹 Escenario

Hasta 50 usuarios concurrentes

Flujo:

Crear producto

Añadir precio

Consultar precio

🔹 Resultados

p95 latency: ~358 ms ✅

error rate: ~0.65% ✅

throughput: ~42 req/s

✔️ Cumple objetivos:

Latencia < 500 ms

Error rate < 1%

⚠️ Consideraciones

Posibles errores iniciales si la API aún no está lista

Se mitiga con setup() en k6 que espera disponibilidad del servicio

🧪 Tests

Validaciones de negocio

Lógica de fechas

Creación y consulta de entidades

🐳 Arquitectura Docker

app → Spring Boot API

db → PostgreSQL

k6 → pruebas de carga

Restricciones respetadas:

Sin ajuste de CPU/memoria

Servicios aislados

🚀 Mejoras posibles

Optimización de índices

Pool de conexiones (HikariCP)

Cache (Redis)

Separación lectura/escritura

Paginación en historial

Multimoneda

Circuit breaker / retries

📦 Entrega

ZIP del proyecto

Repositorio privado de GitHub compartido con el evaluador

📌 Conclusión

El sistema cumple con:

Requisitos funcionales

Validaciones de negocio

Diseño REST consistente

Buen rendimiento bajo carga

Se prioriza:

Simplicidad

Claridad

Eficiencia