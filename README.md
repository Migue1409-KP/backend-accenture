# Prueba Técnica Backend – Accenture

Este repositorio contiene la implementación de un microservicio **reactivo** para la gestión de franquicias, sucursales y productos, siguiendo principios de **Clean Architecture**.

---

## 📐 Arquitectura

- **Domain**: entidades (`Franchise`, `Branch`, `Product`) y casos de uso (Create, UpdateName, UpdateAmount, GetTopProductsByFranchise).  
- **Application**: servicios que orquestan mappers, validaciones y casos de uso; DTOs de entrada/salida; respuestas uniformes.  
- **Infrastructure**:  
  - **Web**: controladores REST con Spring WebFlux (`@RestController`) y validación.  
  - **Persistence**: repositorios implementados con `R2dbcEntityTemplate`, mappers MapStruct y Flyway para migraciones.  
- **Build**: Gradle + Dockerfile para empaquetado optimizado.

---

## ⚙️ Flujo de petición

1. El cliente envía un JSON a un endpoint (`POST /v1/rest/franchises`, etc.).  
2. El **Controller** deserializa, valida (`@Valid @NotBlank…`) y delega al **Service**.  
3. El **Service** traduce a caso de uso, aplica lógica de negocio y mapea dominio ↔ DTO.  
4. El **UseCase** invoca al repositorio (`IFranchiseRepository`, `IBranchRepository`, `IProductRepository`) que usa R2DBC para acceder a la base de datos reactiva.  
5. La respuesta se envuelve en un objeto `Response<T>` con `status`, `data` y `message`, y se devuelve al cliente.

---

## 🛠 Tecnologías

- **Java 21**, **Spring Boot 3.5**, **WebFlux**, **R2DBC** (reactive Postgres), **Flyway** (migraciones), **MapStruct** (mapeo).  
- **Gradle** como sistema de compilación.  
- **Docker** build para generar imagen ligera.

---

## 🚀 Ejecución con Docker

```bash
# 1. Construir la imagen (desde la carpeta raíz del proyecto):
docker build -t accenture/pruebatecnica-backend:latest .

# 2. Ejecutar el contenedor, inyectando las credenciales de la base de datos en la nube:
docker run -d \
  -p 8080:8080 \
  -e SPRING_R2DBC_URL="r2dbc:postgresql://ep-calm-haze-a5nob7p4-pooler.us-east-2.aws.neon.tech/neondb?sslMode=require" \
  -e SPRING_R2DBC_USERNAME="neondb_owner" \
  -e SPRING_R2DBC_PASSWORD="npg_f3gDKA8YeLOx" \
  accenture/pruebatecnica-backend:latest
