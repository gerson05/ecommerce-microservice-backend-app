@echo off
echo =====================================
echo Desplegando todos los servicios en Kubernetes
echo =====================================

cd jenkins
if not exist minikube.exe (
    echo ERROR: minikube.exe no encontrado en el directorio jenkins
    exit /b 1
)

echo.
echo 1. Aplicando namespaces...
call .\minikube.exe kubectl -- apply -f ..\k8s\namespaces.yaml

echo.
echo 2. Aplicando configmaps y secrets...
call .\minikube.exe kubectl -- apply -f ..\k8s\configmap.yaml
call .\minikube.exe kubectl -- apply -f ..\k8s\secrets.yaml

echo.
echo 3. Aplicando infraestructura base...
call .\minikube.exe kubectl -- apply -f ..\k8s\mysql-deployment.yaml
call .\minikube.exe kubectl -- apply -f ..\k8s\service-discovery-deployment.yaml
call .\minikube.exe kubectl -- apply -f ..\k8s\cloud-config-deployment.yaml
call .\minikube.exe kubectl -- apply -f ..\k8s\zipkin-deployment.yaml
call .\minikube.exe kubectl -- apply -f ..\k8s\api-gateway-deployment.yaml

echo.
echo 4. Aplicando servicios de negocio...
call .\minikube.exe kubectl -- apply -f ..\k8s\user-service-deployment.yaml
call .\minikube.exe kubectl -- apply -f ..\k8s\product-service-deployment.yaml
call .\minikube.exe kubectl -- apply -f ..\k8s\order-service-deployment.yaml
call .\minikube.exe kubectl -- apply -f ..\k8s\payment-service-deployment.yaml
call .\minikube.exe kubectl -- apply -f ..\k8s\shipping-service-deployment.yaml
call .\minikube.exe kubectl -- apply -f ..\k8s\favourite-service-deployment.yaml

echo.
echo 5. Aplicando HPA (Horizontal Pod Autoscaler)...
call .\minikube.exe kubectl -- apply -f ..\k8s\hpa.yaml

echo.
echo 6. Aplicando Ingress...
call .\minikube.exe kubectl -- apply -f ..\k8s\ingress.yaml

echo.
echo =====================================
echo Despliegue completado!
echo =====================================
echo.
echo Verificando pods...
call .\minikube.exe kubectl -- get pods -n ecommerce-prod
echo.
echo Verificando servicios...
call .\minikube.exe kubectl -- get services -n ecommerce-prod

cd ..








