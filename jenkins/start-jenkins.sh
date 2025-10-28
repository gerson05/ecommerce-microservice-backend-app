#!/bin/bash

echo "================================================"
echo "Starting Jenkins for Ecommerce Microservices"
echo "================================================"

# Verificar que Docker está corriendo
if ! docker info > /dev/null 2>&1; then
    echo "❌ Docker is not running. Please start Docker first."
    exit 1
fi

# Crear directorio de Jenkins si no existe
mkdir -p jenkins-data

# Levantar Jenkins con Docker Compose
echo "🚀 Starting Jenkins..."
docker-compose up -d

# Esperar a que Jenkins esté listo
echo "⏳ Waiting for Jenkins to start..."
sleep 30

# Verificar que Jenkins está corriendo
if curl -s http://localhost:8080 > /dev/null; then
    echo "✅ Jenkins is running at http://localhost:8080"
    echo "👤 Username: admin"
    echo "🔑 Password: admin123"
    echo "================================================"
    echo "Next steps:"
    echo "1. Access Jenkins at http://localhost:8080"
    echo "2. Run: ./install-plugins.sh"
    echo "3. Run: ./setup-jenkins.sh"
    echo "================================================"
else
    echo "❌ Jenkins failed to start. Check logs with: docker logs jenkins"
    exit 1
fi



