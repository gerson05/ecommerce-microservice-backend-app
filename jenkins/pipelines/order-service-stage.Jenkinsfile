pipeline {
    agent any
    
    environment {
        SERVICE_NAME = 'order-service'
        DOCKER_IMAGE = 'selimhorri/order-service-ecommerce-boot'
        DOCKER_TAG = "${env.BUILD_NUMBER}"
        KUBERNETES_NAMESPACE = 'ecommerce-stage'
        KUBECONFIG = '/var/jenkins_home/.kube/config'
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Build') {
            steps {
                dir('order-service') {
                    sh 'mvn clean package -DskipTests'
                }
            }
        }
        
        stage('Unit Tests') {
            steps {
                dir('order-service') {
                    sh 'mvn test'
                }
            }
            post {
                always {
                    publishTestResults testResultsPattern: '**/target/surefire-reports/*.xml'
                }
            }
        }
        
        stage('Integration Tests') {
            steps {
                sh 'echo "Running integration tests..."'
                sh 'docker-compose -f compose.yml up -d'
                sh 'sleep 60'
                sh 'mvn test -Dtest=*IntegrationTest'
            }
            post {
                always {
                    sh 'docker-compose -f compose.yml down'
                }
            }
        }
        
        stage('Docker Build') {
            steps {
                dir('order-service') {
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
        
        stage('Deploy to Kubernetes Stage') {
            steps {
                script {
                    sh """
                        kubectl create namespace ${KUBERNETES_NAMESPACE} --dry-run=client -o yaml | kubectl apply -f -
                        kubectl set image deployment/order-service order-service=${DOCKER_IMAGE}:${DOCKER_TAG} -n ${KUBERNETES_NAMESPACE}
                        kubectl rollout status deployment/order-service -n ${KUBERNETES_NAMESPACE}
                    """
                }
            }
        }
        
        stage('Health Check') {
            steps {
                sh 'echo "Performing health check..."'
                sh 'kubectl get pods -n ${KUBERNETES_NAMESPACE}'
                sh 'kubectl get services -n ${KUBERNETES_NAMESPACE}'
            }
        }
        
        stage('E2E Tests') {
            steps {
                sh 'echo "Running E2E tests against stage environment..."'
                sh 'mvn test -Dtest=E2ETestSuite -Dspring.profiles.active=stage'
            }
        }
    }
    
    post {
        always {
            cleanWs()
        }
        success {
            echo 'Stage deployment completed successfully!'
        }
        failure {
            echo 'Stage deployment failed!'
        }
    }
}







