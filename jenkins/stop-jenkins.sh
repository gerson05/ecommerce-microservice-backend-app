#!/bin/bash

echo "================================================"
echo "Stopping Jenkins for Ecommerce Microservices"
echo "================================================"

# Parar Jenkins
echo "🛑 Stopping Jenkins..."
docker-compose down

# Limpiar contenedores huérfanos
echo "🧹 Cleaning up orphaned containers..."
docker-compose down --remove-orphans

# Verificar que Jenkins se detuvo
if ! curl -s http://localhost:8080 > /dev/null; then
    echo "✅ Jenkins has been stopped successfully"
else
    echo "❌ Jenkins is still running. Check with: docker ps"
fi

echo "================================================"



