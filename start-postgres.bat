@echo off
echo Starting PostgreSQL container...
docker run -d --name shopping-postgres -e POSTGRES_DB=shopping_cart -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=password -p 5432:5432 postgres:15-alpine

echo Waiting for PostgreSQL to be ready...
timeout /t 10 /nobreak > nul

echo Checking if PostgreSQL is running...
docker ps

echo PostgreSQL container is ready!
echo You can now run your Spring Boot application.
