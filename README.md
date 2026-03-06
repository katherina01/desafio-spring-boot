# Task Manager API - NUEVO SPA

API REST desarrollada con Spring Boot 3.5.x y Java 21 para la gestión de tareas, incluyendo autenticación mediante JWT, manejo de estados de tareas y persistencia en base de datos H2.

## Características

- API REST para gestión de tareas
- Autenticación basada en JWT
- Endpoints protegidos con Spring Security
- Persistencia con Spring Data JPA
- Base de datos en memoria H2
- Documentación automática con OpenAPI / Swagger

## Tecnologías

- **Java 21** (records, lambdas, streams)
- **Spring Boot 3.5.10**
- **Spring Security** con JWT (jjwt 0.12.6)
- **Spring Data JPA** con H2 (base de datos en memoria)
- **SpringDoc OpenAPI** (Swagger UI)
- **Maven** como gestor de dependencias


## Estructura del Proyecto

```
src/main/java/com/nuevospa/taskmanager/
├── config/          # Configuración de seguridad, OpenAPI y carga de datos
├── controller/      # Controladores REST
├── dto/             # Records para request/response
├── entity/          # Entidades JPA
├── exception/       # Manejo global de excepciones
├── repository/      # Repositorios JPA
├── security/        # JWT filter, provider y UserDetailsService
└── service/         # Lógica de negocio

```

## Autenticación

El proyecto implementa autenticación basada en **JWT (JSON Web Token)** utilizando Spring Security.

El flujo de autenticación es el siguiente:

1. El cliente envía sus credenciales (email y password).
2. El servidor valida las credenciales.
3. Si son correctas, se genera un **JWT firmado**.
4. El cliente debe incluir el token en las siguientes peticiones usando el header:

Authorization: Bearer <token>.

El token es validado en cada request para permitir el acceso a endpoints protegidos.

---

## Requisitos Previos

- Java 21+
- Maven 3.9+



# Cómo ejecutar el proyecto

1. Clonar el repositorio

```
git clone <repo>
```
2. Compilar el proyecto

```
mvn clean install
```
3. Ejecutar la aplicación

```
mvn spring-boot:run
```

La aplicación se iniciará en:

```
http://localhost:8080
```

---

## Documentación API

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs
- **OpenAPI YAML**: http://localhost:8080/v3/api-docs.yaml
- **Especificación OpenAPI (fuente)**: `src/main/resources/openapi.yml`

---

## Consola H2

Accesible en http://localhost:8080/h2-console con:
- **JDBC URL**: `jdbc:h2:mem:taskmanagerdb`
- **User**: `sa`
- **Password**: *(vacío)*

---

## Configuración

Las configuraciones principales se encuentran en:

```
application.properties
```
Ejemplo:

jwt.secret=secretkey
jwt.expiration=3600000

spring.datasource.url=jdbc:h2:mem:taskmanagerdb
spring.datasource.username=sa
spring.datasource.password=
  
---

# Ejemplo de flujo de uso

### 1. Login

POST /api/auth/login

Body

{
  "email": "admin@nuevospa.com",
  "password": "admin123"
}

### 2. Respuesta

{
  "token": "jwt_token"
}

### 3. Consumir endpoint protegido

GET /tasks

Header

Authorization: Bearer jwt_token



# Uso del token

Para consumir endpoints protegidos:

```
Authorization: Bearer <token>
```

---

# Endpoints principales

### Autenticación

```
POST /api/auth/login
```

### Tareas

```
GET /tasks
GET /tasks/{id}
POST /tasks
PUT /tasks/{id}
DELETE /tasks/{id}
```

---

## Datos Pre-cargados

### Usuarios

| Email               | Password  | Nombre       |
|---------------------|-----------|--------------|
| admin@nuevospa.com  | admin123  | Admin        |
| user@nuevospa.com   | user123   | Usuario Test |

### Estados de Tarea

| ID | Nombre      |
|----|-------------|
| 1  | PENDIENTE   |
| 2  | EN_PROGRESO |
| 3  | COMPLETADA  |

## Ejemplos de Requests

### 1. Autenticación (obtener token JWT)

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@nuevospa.com",
    "password": "admin123"
  }'
```

**Respuesta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "tokenType": "Bearer",
  "email": "admin@nuevospa.com"
}
```

### 2. Crear Tarea

```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <TOKEN>" \
  -d '{
    "title": "Implementar módulo de reportes",
    "description": "Crear los reportes mensuales del sistema",
    "taskStatusId": 1
  }'
```

### 3. Listar Tareas

```bash
curl -X GET http://localhost:8080/api/tasks \
  -H "Authorization: Bearer <TOKEN>"
```

### 4. Obtener Tarea por ID

```bash
curl -X GET http://localhost:8080/api/tasks/1 \
  -H "Authorization: Bearer <TOKEN>"
```

### 5. Actualizar Tarea

```bash
curl -X PUT http://localhost:8080/api/tasks/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <TOKEN>" \
  -d '{
    "title": "Módulo de reportes actualizado",
    "description": "Reportes mensuales y semanales",
    "taskStatusId": 2
  }'
```

### 6. Eliminar Tarea

```bash
curl -X DELETE http://localhost:8080/api/tasks/1 \
  -H "Authorization: Bearer <TOKEN>"
```

## Tablas de la Base de Datos

### usuarios


| Email               | Password  | Nombre       |
|---------------------|-----------|--------------|
| admin@nuevospa.com  | admin123  | Admin        |
| user@nuevospa.com   | user123   | Usuario Test |



### estados_tarea
| Columna | Tipo        | Restricción       |
|---------|-------------|-------------------|
| id      | BIGINT (PK) | Auto-generado     |
| name    | VARCHAR(50) | NOT NULL, UNIQUE  |

### tareas
| Columna              | Tipo          | Restricción              |
|----------------------|---------------|--------------------------|
| id                   | BIGINT (PK)   | Auto-generado            |
| title                | VARCHAR(255)  | NOT NULL                 |
| description          | VARCHAR(1000) |                          |
| createDate           | TIMESTAMP     | NOT NULL                 |
| updateDate           | TIMESTAMP     |                          |
| taskStatusId (FK)    | BIGINT        | NOT NULL -> estados_tarea|
| userId (FK)          | BIGINT        | NOT NULL -> usuarios     |


# Autor

Desarrollado por **Katherina Solange Lobos Rivera** como parte de un desafío técnico de backend con Spring Boot.
