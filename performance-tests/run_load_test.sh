#!/bin/bash

# Script para ejecutar pruebas de carga con Locust
echo "================================================"
echo "Starting Load Tests with Locust"
echo "================================================"

# Verificar que los servicios están corriendo
echo "Checking services..."
curl -s http://localhost:8080/app/api/products > /dev/null
if [ $? -ne 0 ]; then
    echo "ERROR: Services are not running. Please start all services first."
    exit 1
fi

echo "Services are running. Starting load tests..."

# Ejecutar Locust
# -f: archivo de configuración
# --headless: modo sin interfaz web
# --users: número de usuarios concurrentes
# --spawn-rate: tasa de spawn
# --run-time: duración de la prueba
# --host: host base
locust -f locustfile.py \
       --headless \
       --users 50 \
       --spawn-rate 5 \
       --run-time 300 \
       --host http://localhost:8080

echo "================================================"
echo "Load Tests Completed"
echo "================================================"



