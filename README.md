# API de Gestión de Usuarios

Microservicio **RESTful** construido con **Spring Boot 3.x** para administrar usuarios de una aplicación e-commerce.  
Usa **MongoDB** como almacén de datos, **JWT** para autenticación, **Spring Cache** para acelerar lecturas y
documentación **Swagger/OpenAPI** para explorar y probar la API.

---

## 📌 Importante

**Nota de Arturo Cordero**

> - **Por iniciativa propia**, añadí **Spring Security + JWT** y **Spring Cache** —no estaban en los requisitos
    originales— con el fin de **robustecer el servicio** y **demostrar dominio** de estas tecnologías.

**Demo Beanstalk (ejemplo)**  
<http://user-management-api.us-east-1.elasticbeanstalk.com/swagger-ui/index.html>

---

## 🚀 Funcionalidades Principales

| ✔ | Función                                                                            |
|---|------------------------------------------------------------------------------------|
| ✅ | CRUD completo de usuarios (crear, consultar, actualizar, eliminar)                 |
| ✅ | Validación automática de dirección vía **SEPOMEX** al crear/actualizar             |
| ✅ | **Autenticación JWT** y roles mediante **Spring Security**                         |
| ✅ | Caché de usuarios individuales y de la lista completa con **Spring Cache**         |
| ✅ | Integración con **Swagger UI** (botón **Authorize** para añadir el *Bearer token*) |
| ✅ | Manejo centralizado de errores con respuestas estructuradas                        |

---

## 📦 Tecnologías Utilizadas

- **Spring Boot 3.x** + Spring Web
- **Spring Data MongoDB**
- **Spring Security** + JWT (jjwt) ← *añadido extra*
- **Spring Cache** (*simple*, migrable a Redis) ← *añadido extra*
- **Springdoc-openapi / Swagger UI**
- **ModelMapper** & **Lombok**

---

## 🛠️ Configuración Rápida

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/arturhc/user-management-api.git
   cd user-management-api
   ```

2. **Ejecutar la aplicación**
   ```bash
   ./mvnw spring-boot:run
   ```

3. **Abrir Swagger UI**  
   <http://localhost:8080/swagger-ui-custom.html>

---

## 🔐 Autenticación & Seguridad

| Endpoint        | Método | Descripción                                                     |
|-----------------|--------|-----------------------------------------------------------------|
| `/auth/login`   | POST   | Devuelve `accessToken`, `refreshToken` y fechas de expiración   |
| `/auth/refresh` | POST   | Intercambia un `refreshToken` válido por un nuevo `accessToken` |

### Credenciales de prueba

| Usuario | Contraseña | Roles        |
|---------|------------|--------------|
| `admin` | `admin123` | `ROLE_ADMIN` |

### Pasos para autenticarte en Swagger UI

1. **Inicia sesión**
   ```bash
   curl -X POST http://localhost:8080/auth/login \
        -H "Content-Type: application/json" \
        -d '{ "username": "admin", "password": "admin123" }'
   ```
   Copia el valor de `accessToken` que recibas.

2. **Pulsa `Authorize`** (arriba a la derecha en Swagger UI) e introduce:
   ```
   Bearer <accessToken>
   ```
   Con esto, los endpoints protegidos se ejecutarán con tu sesión.

---

## 🧑‍💻 Endpoints de Usuario

| Método | Endpoint             | Descripción                    | Roles (sugeridos) |
|--------|----------------------|--------------------------------|-------------------|
| POST   | `/api/v1/users`      | **Crear** usuario              | `ADMIN`, `USER`   |
| GET    | `/api/v1/users/{id}` | Obtener usuario por *ObjectId* | `ADMIN`, `USER`   |
| GET    | `/api/v1/users`      | Listar todos los usuarios      | `ADMIN`           |
| PUT    | `/api/v1/users/{id}` | Actualizar usuario             | `ADMIN`, `USER`   |
| DELETE | `/api/v1/users/{id}` | Eliminar usuario               | `ADMIN`           |

### Ejemplo `SaveUserRequest`

```jsonc
POST /api/v1/users
Content-Type: application/json
Authorization: Bearer <accessToken>

{
  "nombre":          "Juan",
  "apellidoPaterno": "Pérez",
  "apellidoMaterno": "García",
  "correo":          "juan.perez@example.com",
  "cp":              "29000",
  "calle":           "Av. Revolución",
  "numeroExterior":  "123",
  "colonia":         "Centro"
}
```

**Respuesta (200)**

```jsonc
{
  "id": "6632d7f9e843021bd4730d90",
  "nombre": "Juan",
  "apellidoPaterno": "Pérez",
  "apellidoMaterno": "García",
  "correo": "juan.perez@example.com",
  "direccion": {
    "calle": "Av. Revolución",
    "numeroExterior": "123",
    "colonia": "Centro",
    "cp": "29000",
    "ciudad": "Tuxtla Gutiérrez",
    "estado": "Chiapas"
  }
}
```

> La ciudad y el estado se completan automáticamente consultando **SEPOMEX**.

---

## ⚠️ Notas de Implementación

- Los métodos **create/update/delete** invalidan la caché (`all_users` y/o `users/{id}`) de forma automática.
- Para producción se recomienda sustituir la caché *simple* por **Redis** (solo descomenta las líneas
  en `application.yml`).
- Los filtros de seguridad basados en JWT se pueden desactivar temporalmente durante el desarrollo si lo requieres.

---

## 👨‍💻 Autor

**Arturo Cordero** — [arturh.sw@gmail.com](mailto:arturh.sw@gmail.com)

