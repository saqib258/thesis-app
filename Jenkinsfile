pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                sh 'cp -r /opt/thesis-app/* .'
            }
        }

        stage('Maven Build & SCA Scan') {
            steps {
                sh 'mvn clean verify'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                // Using the credential ID we discussed
                withCredentials([string(credentialsId: 'sonar-token-for-thesis-app', variable: 'SONAR_AUTH')]) {
                    sh "mvn sonar:sonar -Dsonar.login=${SONAR_AUTH} -Dsonar.host.url=http://100.31.224.64:9000 -Dsonar.projectKey=Final-Thesis-App"
                }
            }
        }

        /* stage('Trivy File Scan') {
            steps {
                sh 'trivy fs .'
            }
        } 
        */
        
        // You can also comment out Docker if you aren't ready for it yet
    }

    post {
        always {
            echo 'Pipeline Execution Finished.'
        }
    }
}
