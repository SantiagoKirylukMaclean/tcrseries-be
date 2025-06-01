# Cambios

## 2023-07-10: Configuración del Proyecto e Implementación de Autenticación

### Añadido
- Configuración de la estructura básica del proyecto siguiendo la arquitectura hexagonal
- Añadidos modelos de dominio User, Role y Token
- Implementadas interfaces de repositorio y sus implementaciones por defecto
- Configuración de Spring Security con autenticación JWT
- Creados endpoints de autenticación (registro, inicio de sesión, actualización de token, cierre de sesión)
- Añadida validación para DTOs
- Configurado application.yml con ajustes de base de datos, seguridad y documentación de API

### Corregido
- Renombradas las clases de implementación de repositorio de *RepositoryImpl a Default* para seguir las convenciones de nomenclatura
- Eliminados comentarios innecesarios del código
- Eliminado application.properties en favor de application.yml

## 2023-07-11: Docker y Migraciones de Base de Datos

### Añadido
- Creados scripts de migración Flyway para el esquema de base de datos
  - V1__create_initial_schema.sql: Crea tablas para usuarios, roles, tokens y user_roles
  - V2__insert_default_roles.sql: Inserta roles predeterminados USER y ADMIN
- Añadido Dockerfile con construcción multi-etapa para una contenerización eficiente
- Creado docker-compose.yml para desarrollo local
  - Configurado servicio de aplicación con Spring Boot
  - Añadido servicio de base de datos PostgreSQL
  - Configuración de redes y volúmenes para persistencia

## 2023-07-12: Makefile para Flujo de Trabajo de Desarrollo

### Añadido
- Creado Makefile con comandos comunes para el flujo de trabajo de desarrollo:
  - Compilación y ejecución de la aplicación
  - Ejecución de pruebas (todas las pruebas y pruebas unitarias)
  - Gestión de contenedores Docker (construcción, inicio, parada, logs, etc.)
  - Operaciones de base de datos (iniciar base de datos, ejecutar migraciones)
  - Limpieza de artefactos de compilación
  - Comando de ayuda con documentación para todos los comandos disponibles

## 2023-07-13: Documentación del Proyecto

### Añadido
- Creado README.md con instrucciones completas para ejecutar la aplicación
- Traducido archivo changes.md al español

## 2025-05-21: Corrección de Problemas de Compilación y Pruebas

### Añadido
- Configurado plugin de Flyway en build.gradle.kts para permitir migraciones de base de datos desde la línea de comandos
- Añadida dependencia de TestContainers JUnit Jupiter para pruebas de integración con base de datos
- Añadida dependencia de Flyway PostgreSQL para soporte de PostgreSQL 15.13

### Corregido
- Configuración de pruebas para usar TestContainers en lugar de la base de datos local
- Deshabilitado Flyway durante las pruebas para evitar problemas de migración
- Configurado Hibernate para crear y eliminar tablas durante las pruebas (ddl-auto: create-drop)
- Solucionado problema de compilación relacionado con la conexión a la base de datos durante las pruebas

## 2025-05-22: Corrección de Problemas de Construcción de Docker

### Corregido
- Cambiada la imagen base de Docker en la etapa de ejecución de `eclipse-temurin:17-jre-alpine` a `amazoncorretto:17-alpine` para resolver problemas de compatibilidad de plataforma
- Solucionado error de construcción de Docker relacionado con la resolución de metadatos de la imagen base

## 2025-05-23: Implementación de OpenAPI con Swagger

### Añadido
- Creada clase de configuración OpenApiConfig para personalizar la documentación de la API
- Añadidas anotaciones de OpenAPI a los DTOs para mejorar la documentación
- Configurado servidor local en la documentación de OpenAPI con el contexto de la aplicación
- Mejorada la documentación de la API con información detallada sobre endpoints, parámetros y respuestas

## 2025-05-24: Corrección de Error 403 en Endpoint de Registro

### Corregido
- Solucionado error 403 Forbidden al acceder al endpoint de registro (/api/auth/register)
- Actualizada la configuración de seguridad para permitir correctamente el acceso a los endpoints de autenticación teniendo en cuenta el context-path de la aplicación
- Modificado SecurityConfig.kt para usar la ruta correcta en la configuración de permisos

## 2025-06-01: Reorganización de Tests

### Mejorado
- Reorganizados los tests en paquetes separados para tests unitarios e integración
- Creado paquete `unit` para tests unitarios
- Movidos los tests de servicio al paquete `unit/service`
- Movidos los tests de controlador al paquete `integration/controller`
- Mejorada la estructura del proyecto para una mejor organización y mantenibilidad

## 2025-06-10: Implementación de Sistema de Permisos por Características

### Añadido
- Creado modelo de dominio para permisos de características (Feature, Permission)
- Implementado repositorio de permisos con operaciones CRUD
- Añadida tabla de permisos en la base de datos mediante migración Flyway
- Modificado servicio de autenticación para incluir permisos en el token JWT
- Creada anotación `@RequirePermission` para proteger endpoints que requieren permisos específicos
- Implementado aspecto para validar permisos en endpoints anotados
- Añadidos tests para verificar la validación de permisos

### Características Implementadas
- Permisos para acceder a Clasificaciones (Standings)
- Permisos para Simular Fin de Semana (Simulate Weekend)
- Permisos para Tiempos por Microsector (Timing by Microsector)
- Permisos para Configuración de Pruebas (Test Configuration)
- Permisos para Lista de Piezas (Parts List)
- Permisos para Notas (Notes)

## 2025-06-11: Corrección de Error en Migraciones de Base de Datos

### Corregido
- Solucionado error "relation 'users' does not exist" al ejecutar la migración V3__create_permissions_table.sql
- Actualizada la configuración de Flyway en build.gradle.kts para usar las credenciales correctas de la base de datos
- Alineadas las credenciales de base de datos entre application.yml, docker-compose.yml y build.gradle.kts
- Corregido problema con la tabla flyway_schema_history que mostraba migraciones como aplicadas cuando no lo estaban realmente
- Reiniciado el historial de migraciones para asegurar que todas las tablas se creen correctamente

## 2025-06-15: Mejora en la Creación de Usuarios con Permisos Específicos

### Añadido
- Implementada funcionalidad para asignar permisos específicos durante la creación de usuarios
- Modificado el DTO RegisterRequest para aceptar una lista de accesos (opcional)
- Actualizado el servicio de autenticación para guardar los permisos especificados al crear un usuario
- Añadidos tests unitarios y de integración para validar la asignación de permisos durante el registro
