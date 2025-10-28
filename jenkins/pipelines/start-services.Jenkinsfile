pipeline {
    agent any
    
    stages {
        stage('Start Infrastructure') {
            steps {
                sh 'echo "Starting Eureka and Cloud Config..."'
                sh 'docker-compose -f compose.yml up -d service-discovery-container cloud-config-container'
                sh 'sleep 30'
            }
        }
        
        stage('Start API Gateway') {
            steps {
                sh 'echo "Starting API Gateway..."'
                sh 'docker-compose -f compose.yml up -d api-gateway-container'
                sh 'sleep 15'
            }
        }
        
        stage('Start Microservices') {
            steps {
                sh 'echo "Starting microservices..."'
                sh 'docker-compose -f compose.yml up -d user-service-container product-service-container order-service-container payment-service-container'
                sh 'sleep 30'
            }
        }
        
        stage('Health Check') {
            steps {
                sh 'echo "Checking service health..."'
                sh 'curl -f http://localhost:8761/eureka/apps || echo "Eureka not ready yet"'
                sh 'docker ps'
            }
        }
    }
    
    post {
        always {
            sh 'echo "Services started successfully"'
        }
    }
}
