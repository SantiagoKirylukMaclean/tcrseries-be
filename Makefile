# Makefile for TCR Series Backend

# Variables
APP_NAME = tcrseries
GRADLE = ./gradlew
DOCKER_COMPOSE = docker-compose

# Help command
.PHONY: help
help:
	@echo "TCR Series Backend Makefile"
	@echo ""
	@echo "Usage:"
	@echo "  make <command>"
	@echo ""
	@echo "Commands:"
	@echo "  help                 Show this help message"
	@echo "  build                Build the application"
	@echo "  run                  Run the application locally"
	@echo "  test                 Run all tests"
	@echo "  test-unit            Run unit tests only"
	@echo "  clean                Clean build artifacts"
	@echo "  docker-build         Build Docker image"
	@echo "  docker-up            Start all Docker containers"
	@echo "  docker-down          Stop all Docker containers"
	@echo "  docker-logs          Show logs from Docker containers"
	@echo "  docker-ps            Show running Docker containers"
	@echo "  docker-restart       Restart Docker containers"
	@echo "  db-up                Start only the database container"
	@echo "  db-migrate           Run database migrations"
	@echo "  all                  Build and start the application with Docker"

# Build the application
.PHONY: build
build:
	$(GRADLE) build

# Run the application locally
.PHONY: run
run:
	$(GRADLE) bootRun

# Run all tests
.PHONY: test
test:
	$(GRADLE) test

# Run unit tests only
.PHONY: test-unit
test-unit:
	$(GRADLE) test --tests "com.puetsnao.tcrseries.*"

# Clean build artifacts
.PHONY: clean
clean:
	$(GRADLE) clean

# Build Docker image
.PHONY: docker-build
docker-build:
	$(DOCKER_COMPOSE) build

# Start all Docker containers
.PHONY: docker-up
docker-up:
	$(DOCKER_COMPOSE) up -d

# Stop all Docker containers
.PHONY: docker-down
docker-down:
	$(DOCKER_COMPOSE) down

# Show logs from Docker containers
.PHONY: docker-logs
docker-logs:
	$(DOCKER_COMPOSE) logs -f

# Show running Docker containers
.PHONY: docker-ps
docker-ps:
	$(DOCKER_COMPOSE) ps

# Restart Docker containers
.PHONY: docker-restart
docker-restart:
	$(DOCKER_COMPOSE) restart

# Start only the database container
.PHONY: db-up
db-up:
	$(DOCKER_COMPOSE) up -d db

# Run database migrations
.PHONY: db-migrate
db-migrate:
	$(GRADLE) flywayMigrate

# Build and start the application with Docker
.PHONY: all
all: build docker-build docker-up

# Default target
.DEFAULT_GOAL := help