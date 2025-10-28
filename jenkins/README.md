# Jenkins Configuration for Ecommerce Microservices

## Descripción
Configuración completa de Jenkins para CI/CD de los microservicios ecommerce con pipelines para desarrollo, staging y producción.

## Arquitectura de Pipelines

### Ambientes
- **Development (Dev)**: Desarrollo local con Docker
- **Staging (Stage)**: Pruebas en Kubernetes
- **Production (Master)**: Despliegue en producción con Release Notes

### Microservicios Configurados
- user-service
- product-service
- order-service
- payment-service
- favourite-service
- proxy-client

## Instalación y Configuración

### 1. Levantar Jenkins con Docker

```bash
# Levantar Jenkins
docker-compose up -d

# Verificar que está corriendo
docker ps
```

### 2. Acceder a Jenkins
- URL: http://localhost:8080
- Usuario: admin
- Contraseña: admin123

### 3. Instalar Plugins Necesarios

```bash
# Ejecutar script de instalación de plugins
chmod +x install-plugins.sh
./install-plugins.sh
```

### 4. Configurar Credenciales

En Jenkins > Manage Jenkins > Manage Credentials:

1. **Docker Hub Credentials**
   - ID: docker-hub-credentials
   - Username: tu-usuario-dockerhub
   - Password: tu-contraseña-dockerhub

2. **Kubernetes Config**
   - ID: kubeconfig
   - Archivo: ~/.kube/config

### 5. Configurar Jobs

```bash
# Ejecutar script de configuración completa
chmod +x setup-jenkins.sh
./setup-jenkins.sh
```

## Estructura de Pipelines

### Pipeline de Desarrollo (Dev)
1. **Checkout** - Obtener código
2. **Build** - Compilar con Maven
3. **Unit Tests** - Ejecutar pruebas unitarias
4. **Docker Build** - Construir imagen Docker
5. **Deploy to Dev** - Desplegar en Docker local

### Pipeline de Staging (Stage)
1. **Checkout** - Obtener código
2. **Build** - Compilar con Maven
3. **Unit Tests** - Pruebas unitarias
4. **Integration Tests** - Pruebas de integración
5. **Docker Build** - Construir imagen
6. **Deploy to Kubernetes Stage** - Desplegar en K8s staging
7. **Health Check** - Verificar salud del servicio
8. **E2E Tests** - Pruebas end-to-end

### Pipeline de Producción (Master)
1. **Checkout** - Obtener código
2. **Build** - Compilar con Maven
3. **Unit Tests** - Pruebas unitarias
4. **Integration Tests** - Pruebas de integración
5. **Performance Tests** - Pruebas de rendimiento con Locust
6. **Docker Build** - Construir imagen
7. **Deploy to Kubernetes Production** - Desplegar en K8s prod
8. **Health Check** - Verificar salud del servicio
9. **Generate Release Notes** - Generar notas de release
10. **Create Git Tag** - Crear tag de versión

## Configuración de Kubernetes

### Namespaces
- `ecommerce-dev` - Desarrollo
- `ecommerce-stage` - Staging
- `ecommerce-prod` - Producción

### Recursos por Servicio
- **CPU**: 250m request, 500m limit
- **Memory**: 256Mi request, 512Mi limit
- **Replicas**: 3 en producción

### Health Checks
- **Liveness Probe**: `/actuator/health`
- **Readiness Probe**: `/actuator/health`
- **Initial Delay**: 60s (liveness), 30s (readiness)

## Monitoreo y Alertas

### Notificaciones por Email
- ✅ Despliegue exitoso
- ❌ Despliegue fallido

### Métricas de Pipeline
- Tiempo de ejecución
- Tasa de éxito/fallo
- Resultados de pruebas
- Artefactos generados

## Comandos Útiles

### Verificar Estado de Jenkins
```bash
docker logs jenkins
```

### Reiniciar Jenkins
```bash
docker-compose restart jenkins
```

### Ejecutar Pipeline Manualmente
```bash
# Desde Jenkins UI o CLI
java -jar jenkins-cli.jar -s http://localhost:8080 build user-service-dev --username admin --password admin123
```

### Ver Logs de Pipeline
```bash
java -jar jenkins-cli.jar -s http://localhost:8080 console user-service-dev --username admin --password admin123
```

## Troubleshooting

### Problemas Comunes

1. **Jenkins no inicia**
   - Verificar que el puerto 8080 esté libre
   - Revisar logs: `docker logs jenkins`

2. **Docker no funciona**
   - Verificar que Docker está corriendo
   - Verificar permisos del socket Docker

3. **Kubernetes no conecta**
   - Verificar configuración de kubeconfig
   - Verificar que kubectl funciona

4. **Tests fallan**
   - Verificar que los servicios están corriendo
   - Revisar logs de los contenedores

### Logs Importantes
- Jenkins: `docker logs jenkins`
- Pipeline: Jenkins UI > Job > Console Output
- Kubernetes: `kubectl logs -n namespace deployment/service`

## Próximos Pasos

1. Configurar webhooks de Git
2. Implementar notificaciones Slack/Discord
3. Configurar métricas con Prometheus
4. Implementar rollback automático
5. Configurar backup de Jenkins







