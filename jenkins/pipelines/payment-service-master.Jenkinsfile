pipeline {
	agent any
	
	environment {
		SERVICE_NAME = 'payment-service'
		DOCKER_IMAGE = 'selimhorri/payment-service-ecommerce-boot'
		DOCKER_TAG = "${env.BUILD_NUMBER}"
		KUBERNETES_NAMESPACE = 'ecommerce-prod'
		KUBECONFIG = '/var/jenkins_home/.kube/config'
		RELEASE_VERSION = "${env.BUILD_NUMBER}"
	}
	
	stages {
		stage('Checkout') { steps { checkout scm } }
		stage('Build') { steps { dir('payment-service') { sh 'mvn clean package -DskipTests' } } }
		stage('Unit Tests') {
			steps { dir('payment-service') { sh 'mvn test' } }
			post { always { publishTestResults testResultsPattern: '**/target/surefire-reports/*.xml' } }
		}
		// stage('Integration Tests') {
		// 	steps {
		// 		sh 'echo "Running integration tests..."'
		// 		sh 'docker-compose -f compose.yml up -d'
		// 		sh 'sleep 60'
		// 		sh 'mvn test -Dtest=*IntegrationTest'
		// 	}
		// 	post { always { sh 'docker-compose -f compose.yml down' } }
		// }
		stage('Performance Tests') {
			steps { dir('performance-tests') { sh 'locust -f locustfile.py --headless --users 50 --spawn-rate 5 --run-time 60 --host http://localhost:8080' } }
		}
		stage('Docker Build') {
			steps {
				dir('payment-service') {
					script {
						def image = docker.build("${DOCKER_IMAGE}:${DOCKER_TAG}")
						docker.withRegistry('', 'docker-hub-credentials') { image.push(); image.push('latest') }
					}
				}
			}
		}
		stage('Deploy to Kubernetes Production') {
			steps {
				script {
					sh """
						kubectl create namespace ${KUBERNETES_NAMESPACE} --dry-run=client -o yaml | kubectl apply -f -
						kubectl set image deployment/payment-service payment-service=${DOCKER_IMAGE}:${DOCKER_TAG} -n ${KUBERNETES_NAMESPACE}
						kubectl rollout status deployment/payment-service -n ${KUBERNETES_NAMESPACE}
					"""
				}
			}
		}
		stage('Health Check') { steps { sh 'kubectl get pods -n ${KUBERNETES_NAMESPACE}'; sh 'kubectl get services -n ${KUBERNETES_NAMESPACE}' } }
		stage('Generate Release Notes') {
			steps {
				script {
					def releaseNotes = """
# Release Notes - Payment Service v${RELEASE_VERSION}

## Build
- Number: ${env.BUILD_NUMBER}
- Branch: ${env.BRANCH_NAME}
- Commit: ${env.GIT_COMMIT}

## Deployment
- Namespace: ${KUBERNETES_NAMESPACE}
- Image: ${DOCKER_IMAGE}:${DOCKER_TAG}
"""
					writeFile file: 'RELEASE_NOTES.md', text: releaseNotes
					archiveArtifacts artifacts: 'RELEASE_NOTES.md', fingerprint: true
				}
			}
		}
		stage('Create Git Tag') {
			steps {
				script {
					sh """
						git config user.email "jenkins@example.com"
						git config user.name "Jenkins"
						git tag -a "v${RELEASE_VERSION}" -m "Release version ${RELEASE_VERSION}"
						git push origin "v${RELEASE_VERSION}"
					"""
				}
			}
		}
	}
	post { always { cleanWs() } }
}



