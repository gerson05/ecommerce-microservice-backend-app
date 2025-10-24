# Jenkins Pipelines para Ecommerce Microservices

## Descripción
Este directorio contiene los pipelines de Jenkins para los 6 microservicios del proyecto ecommerce.

## Microservicios Incluidos
- **user-service** (Puerto 8700)
- **product-service** (Puerto 8500)
- **order-service** (Puerto 8300)
- **payment-service** (Puerto 8400)
- **favourite-service** (Puerto 8800)
- **proxy-client** (Puerto 8900)

## Archivos de Pipeline

### Pipelines Individuales
- `user-service-dev.Jenkinsfile` - Pipeline para user-service
- `product-service-dev.Jenkinsfile` - Pipeline para product-service
- `order-service-dev.Jenkinsfile` - Pipeline para order-service
- `payment-service-dev.Jenkinsfile` - Pipeline para payment-service
- `favourite-service-dev.Jenkinsfile` - Pipeline para favourite-service
- `proxy-client-dev.Jenkinsfile` - Pipeline para proxy-client

### Pipeline Completo
- `all-services-dev.Jenkinsfile` - Pipeline que ejecuta todos los servicios en paralelo

## Cómo Usar

### 1. Levantar Jenkins
```bash
# En Windows
jenkins\start-jenkins.cmd

# En Linux/Mac
chmod +x jenkins/start-jenkins.sh
./jenkins/start-jenkins.sh
```

### 2. Acceder a Jenkins
- URL: http://localhost:8080
- Usar la contraseña inicial mostrada en la consola

### 3. Crear Jobs de Pipeline
1. Ir a "New Item"
2. Seleccionar "Pipeline"
3. Nombrar el job (ej: "user-service-dev")
4. En "Pipeline Definition" seleccionar "Pipeline script from SCM"
5. Configurar Git repository
6. En "Script Path" especificar: `jenkins/pipelines/user-service-dev.Jenkinsfile`

### 4. Ejecutar Pipelines
- Los pipelines se pueden ejecutar manualmente o con webhooks
- Cada pipeline incluye: Build, Test, Docker Build, Deploy

## Estructura de los Pipelines

Cada pipeline incluye las siguientes etapas:

1. **Checkout** - Obtener código del repositorio
2. **Build** - Compilar el proyecto con Maven
3. **Test** - Ejecutar pruebas unitarias
4. **Docker Build** - Construir imagen Docker
5. **Deploy to Dev** - Desplegar en entorno de desarrollo

## Requisitos
- Jenkins con Docker plugin
- Docker instalado
- Maven instalado
- Java 11+
- Acceso a Docker Hub (para push de imágenes)

## Notas
- Los pipelines están configurados para ser simples y robustos
- Se pueden personalizar según necesidades específicas
- Los tests se ejecutan pero no fallan el pipeline si no hay tests implementados
