pipeline {
	agent any
	
	environment {
		SERVICE_NAME = 'favourite-service'
		DOCKER_IMAGE = 'selimhorri/favourite-service-ecommerce-boot'
		DOCKER_TAG = "${env.BUILD_NUMBER}"
		KUBERNETES_NAMESPACE = 'ecommerce-stage'
		KUBECONFIG = '/var/jenkins_home/.kube/config'
	}
	
	stages {
		stage('Checkout') { steps { checkout scm } }
		stage('Build') { steps { dir('favourite-service') { sh 'mvn clean package -DskipTests' } } }
		stage('Unit Tests') { steps { dir('favourite-service') { sh 'mvn test' } } }
		stage('Docker Build') {
			steps {
				dir('favourite-service') {
					script {
						def image = docker.build("${DOCKER_IMAGE}:${DOCKER_TAG}")
						docker.withRegistry('', 'docker-hub-credentials') { image.push(); image.push('latest') }
					}
				}
			}
		}
		stage('Deploy to Kubernetes Stage') {
			steps {
				script {
					sh """
						kubectl create namespace ${KUBERNETES_NAMESPACE} --dry-run=client -o yaml | kubectl apply -f -
						kubectl set image deployment/favourite-service favourite-service=${DOCKER_IMAGE}:${DOCKER_TAG} -n ${KUBERNETES_NAMESPACE}
						kubectl rollout status deployment/favourite-service -n ${KUBERNETES_NAMESPACE}
					"""
				}
			}
		}
	}
	post { always { cleanWs() } }
}



