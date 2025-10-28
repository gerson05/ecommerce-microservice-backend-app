@echo off
echo ================================================
echo Setting up Jenkins for Ecommerce Microservices
echo ================================================

REM Crear directorio de trabajo
if not exist jenkins-setup mkdir jenkins-setup
cd jenkins-setup

REM Descargar Jenkins CLI
echo Downloading Jenkins CLI...
curl -o jenkins-cli.jar http://localhost:8080/jnlpJars/jenkins-cli.jar

REM Crear jobs de pipeline
echo Creating Jenkins jobs...

REM User Service Jobs
java -jar jenkins-cli.jar -s http://localhost:8080 create-job "user-service-dev" < ..\pipelines\user-service-dev.Jenkinsfile --username admin --password admin123
java -jar jenkins-cli.jar -s http://localhost:8080 create-job "user-service-stage" < ..\pipelines\user-service-stage.Jenkinsfile --username admin --password admin123
java -jar jenkins-cli.jar -s http://localhost:8080 create-job "user-service-master" < ..\pipelines\user-service-master.Jenkinsfile --username admin --password admin123

REM Product Service Jobs
java -jar jenkins-cli.jar -s http://localhost:8080 create-job "product-service-dev" < ..\pipelines\product-service-dev.Jenkinsfile --username admin --password admin123
java -jar jenkins-cli.jar -s http://localhost:8080 create-job "product-service-stage" < ..\pipelines\product-service-stage.Jenkinsfile --username admin --password admin123
java -jar jenkins-cli.jar -s http://localhost:8080 create-job "product-service-master" < ..\pipelines\product-service-master.Jenkinsfile --username admin --password admin123

REM Order Service Jobs
java -jar jenkins-cli.jar -s http://localhost:8080 create-job "order-service-dev" < ..\pipelines\order-service-dev.Jenkinsfile --username admin --password admin123
java -jar jenkins-cli.jar -s http://localhost:8080 create-job "order-service-stage" < ..\pipelines\order-service-stage.Jenkinsfile --username admin --password admin123
java -jar jenkins-cli.jar -s http://localhost:8080 create-job "order-service-master" < ..\pipelines\order-service-master.Jenkinsfile --username admin --password admin123

REM Payment Service Jobs
java -jar jenkins-cli.jar -s http://localhost:8080 create-job "payment-service-dev" < ..\pipelines\payment-service-dev.Jenkinsfile --username admin --password admin123
java -jar jenkins-cli.jar -s http://localhost:8080 create-job "payment-service-stage" < ..\pipelines\payment-service-stage.Jenkinsfile --username admin --password admin123
java -jar jenkins-cli.jar -s http://localhost:8080 create-job "payment-service-master" < ..\pipelines\payment-service-master.Jenkinsfile --username admin --password admin123

REM Favourite Service Jobs
java -jar jenkins-cli.jar -s http://localhost:8080 create-job "favourite-service-dev" < ..\pipelines\favourite-service-dev.Jenkinsfile --username admin --password admin123
java -jar jenkins-cli.jar -s http://localhost:8080 create-job "favourite-service-stage" < ..\pipelines\favourite-service-stage.Jenkinsfile --username admin --password admin123
java -jar jenkins-cli.jar -s http://localhost:8080 create-job "favourite-service-master" < ..\pipelines\favourite-service-master.Jenkinsfile --username admin --password admin123

REM Proxy Client Jobs
java -jar jenkins-cli.jar -s http://localhost:8080 create-job "proxy-client-dev" < ..\pipelines\proxy-client-dev.Jenkinsfile --username admin --password admin123
java -jar jenkins-cli.jar -s http://localhost:8080 create-job "proxy-client-stage" < ..\pipelines\proxy-client-stage.Jenkinsfile --username admin --password admin123
java -jar jenkins-cli.jar -s http://localhost:8080 create-job "proxy-client-master" < ..\pipelines\proxy-client-master.Jenkinsfile --username admin --password admin123

REM All Services Job
java -jar jenkins-cli.jar -s http://localhost:8080 create-job "all-services-dev" < ..\pipelines\all-services-dev.Jenkinsfile --username admin --password admin123

echo ================================================
echo Jenkins setup completed!
echo ================================================
echo Access Jenkins at: http://localhost:8080
echo Username: admin
echo Password: admin123
echo ================================================



