@echo off
echo =====================================
echo Limpiando todos los deployments de Kubernetes
echo =====================================

cd jenkins
if not exist minikube.exe (
    echo ERROR: minikube.exe no encontrado en el directorio jenkins
    exit /b 1
)

echo.
echo Eliminando deployments...
call .\minikube.exe kubectl -- delete -f ..\k8s\user-service-deployment.yaml
call .\minikube.exe kubectl -- delete -f ..\k8s\product-service-deployment.yaml
call .\minikube.exe kubectl -- delete -f ..\k8s\order-service-deployment.yaml
call .\minikube.exe kubectl -- delete -f ..\k8s\payment-service-deployment.yaml
call .\minikube.exe kubectl -- delete -f ..\k8s\shipping-service-deployment.yaml
call .\minikube.exe kubectl -- delete -f ..\k8s\favourite-service-deployment.yaml
call .\minikube.exe kubectl -- delete -f ..\k8s\api-gateway-deployment.yaml
call .\minikube.exe kubectl -- delete -f ..\k8s\zipkin-deployment.yaml
call .\minikube.exe kubectl -- delete -f ..\k8s\cloud-config-deployment.yaml
call .\minikube.exe kubectl -- delete -f ..\k8s\service-discovery-deployment.yaml
call .\minikube.exe kubectl -- delete -f ..\k8s\mysql-deployment.yaml

echo.
echo =====================================
echo Limpieza completada!
echo =====================================

cd ..




