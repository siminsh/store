@echo off
echo Checking if PostgreSQL is running...
docker ps | findstr shopping-postgres

if %errorlevel% neq 0 (
    echo PostgreSQL container is not running. Starting it...
    docker run -d --name shopping-postgres -e POSTGRES_DB=shopping_cart -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=password -p 5432:5432 postgres:15-alpine
    echo Waiting for PostgreSQL to start...
    timeout /t 15 /nobreak > nul
)

echo Testing database connection...
docker exec -it shopping-postgres psql -U postgres -d shopping_cart -c "SELECT version();"

echo Database is ready!
