pipeline {
    agent any
    
    environment {
        DOCKER_REGISTRY = 'selimhorri'
        DOCKER_TAG = "${env.BUILD_NUMBER}"
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Build All Services') {
            parallel {
                stage('Build User Service') {
                    steps {
                        dir('user-service') {
                            sh 'mvn clean package -DskipTests'
                        }
                    }
                }
                stage('Build Product Service') {
                    steps {
                        dir('product-service') {
                            sh 'mvn clean package -DskipTests'
                        }
                    }
                }
                stage('Build Order Service') {
                    steps {
                        dir('order-service') {
                            sh 'mvn clean package -DskipTests'
                        }
                    }
                }
                stage('Build Payment Service') {
                    steps {
                        dir('payment-service') {
                            sh 'mvn clean package -DskipTests'
                        }
                    }
                }
                stage('Build Favourite Service') {
                    steps {
                        dir('favourite-service') {
                            sh 'mvn clean package -DskipTests'
                        }
                    }
                }
                stage('Build Proxy Client') {
                    steps {
                        dir('proxy-client') {
                            sh 'mvn clean package -DskipTests'
                        }
                    }
                }
            }
        }
        
        stage('Test All Services') {
            parallel {
                stage('Test User Service') {
                    steps {
                        dir('user-service') {
                            sh 'mvn test'
                        }
                    }
                    post {
                        always {
                            publishTestResults testResultsPattern: '**/target/surefire-reports/*.xml'
                        }
                    }
                }
                stage('Test Product Service') {
                    steps {
                        dir('product-service') {
                            sh 'mvn test'
                        }
                    }
                    post {
                        always {
                            publishTestResults testResultsPattern: '**/target/surefire-reports/*.xml'
                        }
                    }
                }
                stage('Test Order Service') {
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
                stage('Test Payment Service') {
                    steps {
                        dir('payment-service') {
                            sh 'mvn test'
                        }
                    }
                    post {
                        always {
                            publishTestResults testResultsPattern: '**/target/surefire-reports/*.xml'
                        }
                    }
                }
                stage('Test Favourite Service') {
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
                stage('Test Proxy Client') {
                    steps {
                        dir('proxy-client') {
                            sh 'mvn test'
                        }
                    }
                    post {
                        always {
                            publishTestResults testResultsPattern: '**/target/surefire-reports/*.xml'
                        }
                    }
                }
            }
        }
        
        stage('Docker Build All Services') {
            parallel {
                stage('Build User Service Image') {
                    steps {
                        dir('user-service') {
                            script {
                                def image = docker.build("${DOCKER_REGISTRY}/user-service-ecommerce-boot:${DOCKER_TAG}")
                                docker.withRegistry('', 'docker-hub-credentials') {
                                    image.push()
                                    image.push('latest')
                                }
                            }
                        }
                    }
                }
                stage('Build Product Service Image') {
                    steps {
                        dir('product-service') {
                            script {
                                def image = docker.build("${DOCKER_REGISTRY}/product-service-ecommerce-boot:${DOCKER_TAG}")
                                docker.withRegistry('', 'docker-hub-credentials') {
                                    image.push()
                                    image.push('latest')
                                }
                            }
                        }
                    }
                }
                stage('Build Order Service Image') {
                    steps {
                        dir('order-service') {
                            script {
                                def image = docker.build("${DOCKER_REGISTRY}/order-service-ecommerce-boot:${DOCKER_TAG}")
                                docker.withRegistry('', 'docker-hub-credentials') {
                                    image.push()
                                    image.push('latest')
                                }
                            }
                        }
                    }
                }
                stage('Build Payment Service Image') {
                    steps {
                        dir('payment-service') {
                            script {
                                def image = docker.build("${DOCKER_REGISTRY}/payment-service-ecommerce-boot:${DOCKER_TAG}")
                                docker.withRegistry('', 'docker-hub-credentials') {
                                    image.push()
                                    image.push('latest')
                                }
                            }
                        }
                    }
                }
                stage('Build Favourite Service Image') {
                    steps {
                        dir('favourite-service') {
                            script {
                                def image = docker.build("${DOCKER_REGISTRY}/favourite-service-ecommerce-boot:${DOCKER_TAG}")
                                docker.withRegistry('', 'docker-hub-credentials') {
                                    image.push()
                                    image.push('latest')
                                }
                            }
                        }
                    }
                }
                stage('Build Proxy Client Image') {
                    steps {
                        dir('proxy-client') {
                            script {
                                def image = docker.build("${DOCKER_REGISTRY}/proxy-client-ecommerce-boot:${DOCKER_TAG}")
                                docker.withRegistry('', 'docker-hub-credentials') {
                                    image.push()
                                    image.push('latest')
                                }
                            }
                        }
                    }
                }
            }
        }
        
        stage('Deploy to Dev Environment') {
            steps {
                sh 'echo "Deploying all services to development environment"'
                sh 'docker-compose -f compose.yml up -d'
            }
        }
    }
    
    post {
        always {
            cleanWs()
        }
        success {
            echo 'All services deployed successfully!'
        }
        failure {
            echo 'Deployment failed!'
        }
    }
}
