@echo off
echo ================================================
echo Stopping Jenkins for Ecommerce Microservices
echo ================================================

REM Parar Jenkins
echo 🛑 Stopping Jenkins...
docker-compose down

REM Limpiar contenedores huérfanos
echo 🧹 Cleaning up orphaned containers...
docker-compose down --remove-orphans

REM Verificar que Jenkins se detuvo
curl -s http://localhost:8080 >nul 2>&1
if %errorlevel% neq 0 (
    echo ✅ Jenkins has been stopped successfully
) else (
    echo ❌ Jenkins is still running. Check with: docker ps
)

echo ================================================







