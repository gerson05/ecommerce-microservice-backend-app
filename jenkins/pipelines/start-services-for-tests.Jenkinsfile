pipeline {
    agent any
    
    stages {
        stage('Start Core Services') {
            steps {
                sh 'echo "Starting Eureka (Service Discovery)..."'
                sh 'docker compose -f compose.yml up -d service-discovery-container'
                sh 'sleep 30' // Wait for Eureka to start
                
                sh 'echo "Starting Cloud Config..."'
                sh 'docker compose -f compose.yml up -d cloud-config-container'
                sh 'sleep 30' // Wait for Cloud Config to start
                
                sh 'echo "Starting API Gateway..."'
                sh 'docker compose -f compose.yml up -d api-gateway-container'
                sh 'sleep 30' // Wait for API Gateway to start
            }
        }
        
        stage('Start Business Services') {
            steps {
                sh 'echo "Starting business microservices..."'
                sh 'docker compose -f compose.yml up -d user-service-container product-service-container order-service-container payment-service-container shipping-service-container favourite-service-container'
                sh 'sleep 60' // Wait for services to register with Eureka
            }
        }
        
        stage('Verify Services') {
            steps {
                sh 'echo "Verifying services are running..."'
                sh 'docker ps'
                sh 'curl http://localhost:8761/eureka/apps || echo "Eureka check failed"'
            }
        }
    }
    
    post {
        always {
            echo 'Services startup pipeline completed.'
        }
    }
}
