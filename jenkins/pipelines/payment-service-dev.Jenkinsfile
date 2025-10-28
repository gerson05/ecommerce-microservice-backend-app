pipeline {
	agent any
	
	environment {
		SERVICE_NAME = 'payment-service'
		DOCKER_IMAGE = 'selimhorri/payment-service-ecommerce-boot'
		DOCKER_TAG = "${env.BUILD_NUMBER}"
		KUBERNETES_NAMESPACE = 'ecommerce-dev'
	}
	
	stages {
		stage('Checkout') {
			steps {
				checkout scm
			}
		}
		
		stage('Build') {
			steps {
				dir('payment-service') {
					sh 'mvn clean package -DskipTests'
				}
			}
		}
		
		stage('Unit Tests') {
			steps {
				dir('payment-service') {
					sh 'mvn test'
				}
			}
			post {
				always {
					publishTestResults testResultsPattern: '**/target/surefire-reports/*.xml'
					publishHTML([
						allowMissing: false,
						alwaysLinkToLastBuild: true,
						keepAll: true,
						reportDir: 'target/site/surefire-reports',
						reportFiles: 'index.html',
						reportName: 'Unit Test Report'
					])
				}
			}
		}
		
		stage('Docker Build') {
			steps {
				dir('payment-service') {
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
				sh "docker run -d --name ${SERVICE_NAME}-dev -p 8600:8600 ${DOCKER_IMAGE}:${DOCKER_TAG}"
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



