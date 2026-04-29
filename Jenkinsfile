pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                // Asegurar LF en mvnw (por si Git no aplicó .gitattributes)
                sh '''if [ -f mvnw ]; then
                        sed -i "s/\\r//" mvnw
                        chmod +x mvnw
                      fi
                '''
                sh 'sh ./mvnw clean compile -DskipTests'
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t mi-app:latest .'
            }
        }

        stage('Test') {
            steps {
                sh 'sh ./mvnw test'
            }
        }
    }

    post {
        always {
            echo 'Pipeline finalizado.'
        }
    }
}