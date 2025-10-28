pipeline {
    agent any
    
    environment {
        SERVICE_NAME = 'user-service'
        DOCKER_IMAGE = 'selimhorri/user-service-ecommerce-boot'
        DOCKER_TAG = "${env.BUILD_NUMBER}"
    }
    
    stages {
        stage('Checkout') {
            steps {
                echo 'Using local workspace for ecommerce microservices'
                sh 'pwd && ls -la'
            }
        }
        
        stage('Build') {
            steps {
                echo "Building ${SERVICE_NAME}..."
                dir('user-service') {
                    sh 'echo "Building user-service..."'
                    sh 'ls -la'
                    // sh 'mvn clean package -DskipTests'
                }
            }
        }
        
        stage('Unit Tests') {
            steps {
                echo "Running unit tests for ${SERVICE_NAME}..."
                dir('user-service') {
                    sh 'echo "Unit tests would run here..."'
                    sh 'ls -la src/test/java/'
                }
            }
            post {
                always {
                    echo 'Test results would be published here'
                }
            }
        }
        
        stage('Docker Build') {
            steps {
                echo "Building Docker image for ${SERVICE_NAME}..."
                dir('user-service') {
                    sh 'echo "Docker build would happen here..."'
                    sh 'ls -la Dockerfile'
                }
            }
        }
        
        stage('Deploy to Dev') {
            steps {
                echo "Deploying ${SERVICE_NAME} to development environment..."
                sh "echo 'Service ${SERVICE_NAME} would be deployed to Docker/Kubernetes'"
            }
        }
    }
    
    post {
        always {
            echo 'Pipeline execution completed'
        }
        success {
            echo '✅ Pipeline completed successfully!'
        }
        failure {
            echo '❌ Pipeline failed!'
        }
    }
}



