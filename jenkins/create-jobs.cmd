@echo off
echo ================================================
echo Creating Jenkins Pipeline Jobs
echo ================================================

REM Crear jobs de pipeline usando Job DSL
echo Creating pipeline jobs...

REM User Service Jobs
java -jar jenkins-cli.jar -s http://localhost:8080 -auth admin:admin123 create-job "user-service-dev" < pipelines\user-service-dev.Jenkinsfile
java -jar jenkins-cli.jar -s http://localhost:8080 -auth admin:admin123 create-job "user-service-stage" < pipelines\user-service-stage.Jenkinsfile
java -jar jenkins-cli.jar -s http://localhost:8080 -auth admin:admin123 create-job "user-service-master" < pipelines\user-service-master.Jenkinsfile

REM Product Service Jobs
java -jar jenkins-cli.jar -s http://localhost:8080 -auth admin:admin123 create-job "product-service-dev" < pipelines\product-service-dev.Jenkinsfile
java -jar jenkins-cli.jar -s http://localhost:8080 -auth admin:admin123 create-job "product-service-stage" < pipelines\product-service-stage.Jenkinsfile
java -jar jenkins-cli.jar -s http://localhost:8080 -auth admin:admin123 create-job "product-service-master" < pipelines\product-service-master.Jenkinsfile

REM Order Service Jobs
java -jar jenkins-cli.jar -s http://localhost:8080 -auth admin:admin123 create-job "order-service-dev" < pipelines\order-service-dev.Jenkinsfile
java -jar jenkins-cli.jar -s http://localhost:8080 -auth admin:admin123 create-job "order-service-stage" < pipelines\order-service-stage.Jenkinsfile
java -jar jenkins-cli.jar -s http://localhost:8080 -auth admin:admin123 create-job "order-service-master" < pipelines\order-service-master.Jenkinsfile

REM Payment Service Jobs
java -jar jenkins-cli.jar -s http://localhost:8080 -auth admin:admin123 create-job "payment-service-dev" < pipelines\payment-service-dev.Jenkinsfile
java -jar jenkins-cli.jar -s http://localhost:8080 -auth admin:admin123 create-job "payment-service-stage" < pipelines\payment-service-stage.Jenkinsfile
java -jar jenkins-cli.jar -s http://localhost:8080 -auth admin:admin123 create-job "payment-service-master" < pipelines\payment-service-master.Jenkinsfile

REM Favourite Service Jobs
java -jar jenkins-cli.jar -s http://localhost:8080 -auth admin:admin123 create-job "favourite-service-dev" < pipelines\favourite-service-dev.Jenkinsfile
java -jar jenkins-cli.jar -s http://localhost:8080 -auth admin:admin123 create-job "favourite-service-stage" < pipelines\favourite-service-stage.Jenkinsfile
java -jar jenkins-cli.jar -s http://localhost:8080 -auth admin:admin123 create-job "favourite-service-master" < pipelines\favourite-service-master.Jenkinsfile

REM Proxy Client Jobs
java -jar jenkins-cli.jar -s http://localhost:8080 -auth admin:admin123 create-job "proxy-client-dev" < pipelines\proxy-client-dev.Jenkinsfile
java -jar jenkins-cli.jar -s http://localhost:8080 -auth admin:admin123 create-job "proxy-client-stage" < pipelines\proxy-client-stage.Jenkinsfile
java -jar jenkins-cli.jar -s http://localhost:8080 -auth admin:admin123 create-job "proxy-client-master" < pipelines\proxy-client-master.Jenkinsfile

REM All Services Job
java -jar jenkins-cli.jar -s http://localhost:8080 -auth admin:admin123 create-job "all-services-dev" < pipelines\all-services-dev.Jenkinsfile

echo ================================================
echo All Jenkins jobs created successfully!
echo ================================================
echo Access Jenkins at: http://localhost:8080
echo Username: admin
echo Password: admin123
echo ================================================
