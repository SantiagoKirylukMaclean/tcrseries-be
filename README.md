# TCR Series Backend

## Descripción
TCR Series Backend es una aplicación desarrollada con Spring Boot que proporciona una API RESTful para la gestión de series. La aplicación está construida siguiendo los principios de arquitectura hexagonal y utiliza autenticación JWT para proteger los endpoints.

## Requisitos previos
- Java 17 o superior
- Gradle 8.5 o superior
- Docker y Docker Compose (opcional, para ejecución con contenedores)
- PostgreSQL (si se ejecuta sin Docker)

## Estructura del proyecto
El proyecto sigue una arquitectura hexagonal con tres capas principales:
- **Dominio**: Contiene los modelos de dominio y puertos (interfaces)
- **Aplicación**: Contiene los servicios de aplicación
- **Infraestructura**: Contiene adaptadores para bases de datos, seguridad y web

## Configuración

### Base de datos
La aplicación utiliza PostgreSQL como base de datos. La configuración se encuentra en el archivo `src/main/resources/application.yml`. Las migraciones de la base de datos se gestionan con Flyway.

### Seguridad
La aplicación utiliza Spring Security con autenticación JWT. La configuración de seguridad se encuentra en el paquete `com.puetsnao.tcrseries.infrastructure.security`.

## Ejecución de la aplicación

### Usando Docker Compose (recomendado)
La forma más sencilla de ejecutar la aplicación es utilizando Docker Compose:

```bash
# Construir y ejecutar todos los contenedores
make all

# O alternativamente
make docker-build
make docker-up
```

La aplicación estará disponible en `http://localhost:8080/api`.

### Ejecución local
Para ejecutar la aplicación localmente:

1. Asegúrate de tener PostgreSQL en ejecución con una base de datos llamada `tcrseries`
2. Ejecuta la aplicación:
```bash
make run
```

### Comandos útiles
El proyecto incluye un Makefile con varios comandos útiles:

- `make help`: Muestra la ayuda con todos los comandos disponibles
- `make build`: Compila la aplicación
- `make run`: Ejecuta la aplicación localmente
- `make test`: Ejecuta todas las pruebas
- `make test-unit`: Ejecuta solo las pruebas unitarias
- `make clean`: Limpia los artefactos de compilación
- `make docker-build`: Construye la imagen Docker
- `make docker-up`: Inicia todos los contenedores Docker
- `make docker-down`: Detiene todos los contenedores Docker
- `make docker-logs`: Muestra los logs de los contenedores Docker
- `make docker-ps`: Muestra los contenedores Docker en ejecución
- `make docker-restart`: Reinicia los contenedores Docker
- `make db-up`: Inicia solo el contenedor de la base de datos
- `make db-migrate`: Ejecuta las migraciones de la base de datos
- `make all`: Compila la aplicación, construye la imagen Docker e inicia los contenedores

## Documentación de la API
La aplicación utiliza OpenAPI con Swagger para documentar la API REST. Para acceder a la documentación:

1. Asegúrate de que la aplicación esté en ejecución (usando Docker o localmente)
2. Abre un navegador web y navega a:
   - Swagger UI: `http://localhost:8080/api/swagger-ui.html`
   - Especificación OpenAPI (JSON): `http://localhost:8080/api/api-docs`

### Uso de Swagger UI
Swagger UI proporciona una interfaz interactiva para explorar y probar la API:

- Puedes ver todos los endpoints disponibles organizados por controladores
- Cada endpoint muestra los métodos HTTP soportados, parámetros requeridos y respuestas posibles
- Puedes expandir cada endpoint para ver detalles y ejemplos de solicitud/respuesta
- Para probar un endpoint:
  1. Haz clic en el endpoint que deseas probar
  2. Haz clic en el botón "Try it out"
  3. Completa los parámetros requeridos
  4. Haz clic en "Execute" para enviar la solicitud
  5. Verás la respuesta del servidor, incluyendo el código de estado y el cuerpo de la respuesta

Para endpoints protegidos, necesitarás autenticarte primero:
1. Usa el endpoint `/auth/login` para obtener un token JWT
2. Copia el token de acceso de la respuesta
3. Haz clic en el botón "Authorize" en la parte superior de la página
4. Ingresa el token en el formato: `Bearer {tu-token-jwt}`
5. Haz clic en "Authorize" y luego en "Close"

## Endpoints principales

### Autenticación
- `POST /api/auth/register`: Registra un nuevo usuario
- `POST /api/auth/login`: Inicia sesión y obtiene un token JWT
- `POST /api/auth/refresh`: Actualiza un token JWT expirado
- `POST /api/auth/logout`: Cierra sesión y revoca el token JWT

## Desarrollo
Para contribuir al proyecto, sigue estos pasos:

1. Clona el repositorio
2. Crea una rama para tu funcionalidad (`git checkout -b feature/nueva-funcionalidad`)
3. Realiza tus cambios y haz commit (`git commit -am 'Añadir nueva funcionalidad'`)
4. Sube los cambios a tu rama (`git push origin feature/nueva-funcionalidad`)
5. Crea un Pull Request
