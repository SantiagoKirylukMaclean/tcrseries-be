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
