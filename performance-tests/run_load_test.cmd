@echo off
REM Script para ejecutar pruebas de carga con Locust en Windows
echo ================================================
echo Starting Load Tests with Locust
echo ================================================

REM Verificar que los servicios estan corriendo
echo Checking services...
curl -s http://localhost:8080/app/api/products
if %errorlevel% neq 0 (
    echo ERROR: Services are not running. Please start all services first.
    exit /b 1
)

echo Services are running. Starting load tests...

REM Ejecutar Locust
locust -f locustfile.py --headless --users 50 --spawn-rate 5 --run-time 300 --host http://localhost:8080

echo ================================================
echo Load Tests Completed
echo ================================================



