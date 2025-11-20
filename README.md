# Naruto Character Manager API

API REST desarrollada con Spring Boot para la gestión de personajes del universo Naruto y sus comentarios.

## Descripción

Esta aplicación permite a los usuarios:
- Crear y listar personajes de Naruto
- Agregar comentarios sobre personajes
- Ver comentarios asociados a cada personaje

## Tecnologías

- **Spring Boot 3.5.4**
- **Java 17**
- **Spring Data JPA**
- **Spring Security** (autenticación y autorización)
- **JWT** (JSON Web Tokens)
- **H2 Database** (base de datos en memoria para desarrollo local)
- **MySQL** (base de datos para producción/VPS)
- **Swagger/OpenAPI 3** (documentación de API)
- **ModelMapper** (mapeo de objetos)
- **Jakarta Validation** (validación de datos)

## Estructura del Proyecto

El proyecto sigue una arquitectura en capas:

```
src/main/java/com/naruto/charactermanager/
├── domain/                    # Capa de dominio
│   ├── inheritance/         # Entidades base con auditoría
│   └── utils/                  # Utilidades de dominio
├── application/                # Capa de aplicación
│   ├── services/               # Interfaces y servicios de aplicación
│   └── dto/                    # DTOs de aplicación
├── infrastructure/             # Capa de infraestructura
│   ├── entities/               # Entidades JPA
│   ├── repositories/           # Repositorios JPA
│   ├── config/                 # Configuraciones (Swagger, CORS, etc.)
│   └── utils/                  # Utilidades de infraestructura
└── presentation/               # Capa de presentación
    ├── controllers/            # Controladores REST
    ├── dto/                    # DTOs de presentación
    └── shared/                 # Clases compartidas (WebResponse, WebException)
```

## Endpoints de la API

### Autenticación (Públicos)

- **POST** `/api/auth/login` - Iniciar sesión (requiere documento y contraseña)
- **POST** `/api/auth/refresh` - Refrescar token de acceso
- **POST** `/api/auth/logout` - Cerrar sesión

### Personajes (Requieren autenticación)

- **POST** `/api/personajes` - Crear un nuevo personaje
- **GET** `/api/personajes` - Listar personajes (con paginación y filtros)
- **GET** `/api/personajes/{id}` - Obtener un personaje por ID

### Comentarios (Requieren autenticación)

- **POST** `/api/comentario` - Agregar un comentario a un personaje
- **GET** `/api/comentario/{personaje_id}` - Ver comentarios de un personaje

## Ejecución

### Requisitos

- Java 17 o superior
- Maven 3.6 o superior

### Compilar y ejecutar

```bash
# Compilar el proyecto
mvn clean install

# Ejecutar la aplicación (perfil local por defecto)
mvn spring-boot:run

# Ejecutar con perfil remote (VPS)
mvn spring-boot:run -Dspring-boot.run.profiles=remote
# O usando variable de entorno
SPRING_PROFILES_ACTIVE=remote mvn spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080`

## Documentación de la API

Una vez que la aplicación esté ejecutándose, puedes acceder a:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs (JSON)**: http://localhost:8080/v3/api-docs

## Base de Datos

### Nombre de la Base de Datos
**`naruto_character_manager`**

### Perfil Default (Local - H2)
La aplicación utiliza H2 Database en memoria por defecto. Para acceder a la consola de H2:

- **URL**: http://localhost:8080/h2-console
- **JDBC URL**: `jdbc:h2:mem:naruto_character_manager`
- **Usuario**: `sa`
- **Contraseña**: (vacía)

### Perfil Remote (VPS - MySQL)
Para usar MySQL en tu VPS, configura las variables de entorno:

```bash
export SPRING_PROFILES_ACTIVE=remote
export DB_HOST=tu-vps.com
export DB_PORT=3306
export DB_USERNAME=tu_usuario
export DB_PASSWORD=tu_contraseña
```

O edita `application-remote.properties` con tus credenciales.

**Nota**: Asegúrate de crear la base de datos `naruto_character_manager` en tu servidor MySQL antes de ejecutar la aplicación.

## Ejemplos de Uso

### 1. Iniciar sesión (obtener token)

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "documento": "12345678",
    "contrasena": "Admin123!"
  }'
```

**Respuesta:**
```json
{
  "result": {
    "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "userInfo": {
      "id": 1,
      "documento": "12345678",
      "nombreUsuario": "ADMIN",
      "nombre": "Administrador del Sistema",
      "email": "admin@naruto.com"
    }
  }
}
```

### 2. Crear un personaje (requiere autenticación)

```bash
curl -X POST http://localhost:8080/api/personajes \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer TU_ACCESS_TOKEN_AQUI" \
  -d '{
    "nombre": "Naruto Uzumaki",
    "aldea": "Konoha",
    "rango": "Hokage",
    "descripcion": "El ninja más fuerte de Konoha",
    "tecnicas": "Rasengan, Modo Sabio, Modo Kurama"
  }'
```

### 3. Listar personajes (requiere autenticación)

```bash
curl http://localhost:8080/api/personajes \
  -H "Authorization: Bearer TU_ACCESS_TOKEN_AQUI"
```

### 4. Agregar un comentario (requiere autenticación)

```bash
curl -X POST http://localhost:8080/api/comentario \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer TU_ACCESS_TOKEN_AQUI" \
  -d '{
    "contenido": "Naruto es mi personaje favorito!",
    "autor": "Usuario123",
    "calificacion": 5,
    "personajeId": 1
  }'
```

### 5. Ver comentarios de un personaje (requiere autenticación)

```bash
curl http://localhost:8080/api/comentario/1 \
  -H "Authorization: Bearer TU_ACCESS_TOKEN_AQUI"
```

## Usuarios por Defecto (Seed)

Al iniciar la aplicación, se crean automáticamente los siguientes usuarios:

1. **Administrador**
   - DNI: `12345678`
   - Usuario: `admin`
   - Contraseña: `Admin123!`
   - Email: `admin@naruto.com`

2. **Usuario de Prueba**
   - DNI: `87654321`
   - Usuario: `usuario`
   - Contraseña: `User123!`
   - Email: `usuario@naruto.com`

## Características

- ✅ Arquitectura en capas DDD (Domain, Application, Infrastructure, Presentation)
- ✅ Autenticación JWT (Access Token + Refresh Token)
- ✅ Spring Security configurado
- ✅ Auditoría automática (creación, modificación, eliminación)
- ✅ Soft delete (eliminación lógica)
- ✅ Validación de datos con Jakarta Validation
- ✅ Manejo global de excepciones
- ✅ Documentación automática con Swagger
- ✅ CORS configurado
- ✅ Paginación y filtros dinámicos
- ✅ Seed automático de usuarios
- ✅ Perfiles de configuración (default/remote)
- ✅ Soporte para H2 (local) y MySQL (VPS)

## Seguridad

- Los endpoints de autenticación (`/api/auth/**`) son públicos
- Todos los demás endpoints requieren un token JWT válido en el header `Authorization: Bearer <token>`
- Los tokens de acceso expiran en 1 hora
- Los tokens de refresco expiran en 24 horas
- Bloqueo automático de cuenta después de 3 intentos fallidos de login
- Contraseñas encriptadas con BCrypt

## Notas

- **Perfil Default**: La base de datos H2 se reinicia cada vez que se ejecuta la aplicación (en memoria)
- **Perfil Remote**: Requiere configuración de MySQL en VPS
- El usuario por defecto para auditoría es "SYSTEM"
- Todos los endpoints permiten CORS desde cualquier origen (configuración de desarrollo)
- El seed de usuarios se ejecuta automáticamente al iniciar la aplicación
