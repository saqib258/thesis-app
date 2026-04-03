pipeline {
    agent any

    environment {
        // Defining variables for easier management
        SONAR_URL = "http://100.31.224.64:9000"
        DOCKER_IMAGE = "saqib/thesis-app:latest"
    }

    stage('Checkout') {
    steps {
        // This pulls the code automatically from GitHub
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
                // Using the specific Jenkins-SonarQube integration for the icon/badge
                withSonarQubeEnv('SonarQubeServer') { 
                    withCredentials([string(credentialsId: 'sonar-token-for-thesis-app', variable: 'SONAR_AUTH')]) {
                        sh "mvn sonar:sonar \
                            -Dsonar.login=${SONAR_AUTH} \
                            -Dsonar.host.url=${SONAR_URL} \
                            -Dsonar.projectKey=Final-Thesis-App"
                    }
                }
            }
        }
        
        stage('Quality Gate') {
            steps {
                // This ensures the pipeline waits for SonarQube to finish and show the icon
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
                    // Building the image using the Dockerfile in your repo
                    sh "docker build -t ${DOCKER_IMAGE} ."
                    
                    // Scanning the built image before finishing
                    sh "trivy image --severity HIGH,CRITICAL ${DOCKER_IMAGE}"
                }
            }
        }
    }

    post {
        always {
            echo 'Pipeline Execution Finished.'
            // Optional: Clean up workspace to save EC2 space
            // cleanWs()
        }
        success {
            echo 'Thesis Application built and scanned successfully!'
        }
        failure {
            echo 'Pipeline failed. Check SonarQube or Trivy logs.'
        }
    }
}
