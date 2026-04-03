pipeline {
    agent any

    environment {
        SONAR_URL = "http://100.31.224.64:9000"
        DOCKER_IMAGE = "saqib/thesis-app:latest"
    }

    stages {
        stage('Checkout') {
            steps {
                // This pulls code from the GitHub URL you set in Jenkins
                checkout scm
            }
        }

        stage('Maven Build & SCA Scan') {
            steps {
                sh 'mvn clean verify'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQubeServer') { 
                    withCredentials([string(credentialsId: 'sonar-token-for-thesis-app', variable: 'SONAR_AUTH')]) {
                        sh "mvn sonar:sonar -Dsonar.login=${SONAR_AUTH} -Dsonar.host.url=${SONAR_URL} -Dsonar.projectKey=Final-Thesis-App"
                    }
                }
            }
        }
        
        stage('Quality Gate') {
            steps {
                timeout(time: 1, unit: 'HOURS') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Trivy FS Scan') {
            steps {
                sh 'trivy fs --severity HIGH,CRITICAL .'
            }
        }

        stage('Docker Build & Image Scan') {
            steps {
                script {
                    sh "docker build -t ${DOCKER_IMAGE} ."
                    sh "trivy image --severity HIGH,CRITICAL ${DOCKER_IMAGE}"
                }
            }
        }
    }

    post {
        always {
            echo 'Pipeline Execution Finished.'
        }
        success {
            echo 'Thesis Application built and scanned successfully!'
        }
        failure {
            echo 'Pipeline failed. Check SonarQube or Trivy logs.'
        }
    }
}
