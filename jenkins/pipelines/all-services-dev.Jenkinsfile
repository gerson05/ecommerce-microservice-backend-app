pipeline {
    agent any
    
    environment {
        DOCKER_TAG = "${env.BUILD_NUMBER}"
        KUBERNETES_NAMESPACE = 'ecommerce-dev'
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
        
        stage('Unit Tests All Services') {
            parallel {
                stage('User Service Tests') {
                    steps {
                        dir('user-service') {
                            sh 'mvn test'
                        }
                    }
                }
                stage('Product Service Tests') {
                    steps {
                        dir('product-service') {
                            sh 'mvn test'
                        }
                    }
                }
                stage('Order Service Tests') {
                    steps {
                        dir('order-service') {
                            sh 'mvn test'
                        }
                    }
                }
                stage('Payment Service Tests') {
                    steps {
                        dir('payment-service') {
                            sh 'mvn test'
                        }
                    }
                }
                stage('Favourite Service Tests') {
                    steps {
                        dir('favourite-service') {
                            sh 'mvn test'
                        }
                    }
                }
                stage('Proxy Client Tests') {
                    steps {
                        dir('proxy-client') {
                            sh 'mvn test'
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
                                def image = docker.build("selimhorri/user-service-ecommerce-boot:${DOCKER_TAG}")
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
                                def image = docker.build("selimhorri/product-service-ecommerce-boot:${DOCKER_TAG}")
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
                                def image = docker.build("selimhorri/order-service-ecommerce-boot:${DOCKER_TAG}")
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
                                def image = docker.build("selimhorri/payment-service-ecommerce-boot:${DOCKER_TAG}")
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
                                def image = docker.build("selimhorri/favourite-service-ecommerce-boot:${DOCKER_TAG}")
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
                                def image = docker.build("selimhorri/proxy-client-ecommerce-boot:${DOCKER_TAG}")
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
        
        stage('Deploy All Services to Dev') {
            steps {
                sh 'echo "Deploying all services to development environment..."'
                sh 'docker-compose -f compose.yml up -d'
                sh 'sleep 30'
                sh 'docker ps'
            }
        }
        
        stage('Integration Tests') {
            steps {
                sh 'echo "Running integration tests..."'
                sh 'mvn test -Dtest=*IntegrationTest'
            }
        }
        
        stage('E2E Tests') {
            steps {
                sh 'echo "Running E2E tests..."'
                sh 'mvn test -Dtest=E2ETestSuite'
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



