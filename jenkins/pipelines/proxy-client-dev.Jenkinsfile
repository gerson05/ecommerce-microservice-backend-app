pipeline {
	agent any
	
	environment {
		SERVICE_NAME = 'proxy-client'
		DOCKER_IMAGE = 'selimhorri/proxy-client-ecommerce-boot'
		DOCKER_TAG = "${env.BUILD_NUMBER}"
		KUBERNETES_NAMESPACE = 'ecommerce-dev'
	}
	
	stages {
		stage('Checkout') { steps { checkout scm } }
		stage('Build') { steps { dir('proxy-client') { sh 'mvn clean package -DskipTests' } } }
		stage('Unit Tests') { steps { dir('proxy-client') { sh 'mvn test' } } }
		stage('Docker Build') {
			steps {
				dir('proxy-client') {
					script {
						def image = docker.build("${DOCKER_IMAGE}:${DOCKER_TAG}")
						docker.withRegistry('', 'docker-hub-credentials') { image.push(); image.push('latest') }
					}
				}
			}
		}
		stage('Deploy to Dev') { steps { sh 'echo "Deploying proxy-client to development"' } }
	}
	post { always { cleanWs() } }
}



