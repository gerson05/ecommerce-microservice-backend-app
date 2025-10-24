pipeline {
    agent any
    
    environment {
        SERVICE_NAME = 'favourite-service'
        DOCKER_IMAGE = 'selimhorri/favourite-service-ecommerce-boot'
        DOCKER_TAG = "${env.BUILD_NUMBER}"
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Build') {
            steps {
                dir('favourite-service') {
                    sh 'mvn clean package -DskipTests'
                }
            }
        }
        
        stage('Test') {
            steps {
                dir('favourite-service') {
                    sh 'mvn test'
                }
            }
            post {
                always {
                    publishTestResults testResultsPattern: '**/target/surefire-reports/*.xml'
                }
            }
        }
        
        stage('Docker Build') {
            steps {
                dir('favourite-service') {
                    script {
                        def image = docker.build("${DOCKER_IMAGE}:${DOCKER_TAG}")
                        docker.withRegistry('', 'docker-hub-credentials') {
                            image.push()
                            image.push('latest')
                        }
                    }
                }
            }
        }
        
        stage('Deploy to Dev') {
            steps {
                sh "echo 'Deploying ${SERVICE_NAME} to development environment'"
                sh "docker run -d --name ${SERVICE_NAME}-dev -p 8800:8800 ${DOCKER_IMAGE}:${DOCKER_TAG}"
            }
        }
    }
    
    post {
        always {
            cleanWs()
        }
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}
