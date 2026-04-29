pipeline {
    agent any

    environment {
        SONAR_HOST_URL = 'http://host.docker.internal:9000'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build and Test') {
            steps {
                sh 'chmod +x mvnw'
                sh './mvnw clean package'
            }
        }

        stage('Static Analysis (SonarQube)') {
            steps {
                withCredentials([string(credentialsId: 'sonarqube-token', variable: 'SONAR_TOKEN')]) {
                    sh '''
                        ./mvnw sonar:sonar \
                          -Dsonar.projectKey=cicd-demo \
                          -Dsonar.host.url=$SONAR_HOST_URL \
                          -Dsonar.token=$SONAR_TOKEN
                    '''
                }
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t mi-app:latest .'
            }
        }

        stage('Container Security Scan (Trivy)') {
            steps {
                sh '''
                    docker run --rm \
                      -v /var/run/docker.sock:/var/run/docker.sock \
                      aquasec/trivy:latest image \
                      --scanners vuln \
                      --no-progress \
                      mi-app:latest
                '''
            }
        }

        stage('Deploy') {
            when {
                anyOf {
                    branch 'main'
                    expression { env.GIT_BRANCH?.contains('main') }
                }
            }
            steps {
                sh '''
                    docker rm -f cicd-demo-app 2>/dev/null || true
                    docker run -d --name cicd-demo-app -p 8081:8080 mi-app:latest
                '''
            }
        }
    }

    post {
        always {
            echo 'Limpiando entorno...'
            cleanWs()
        }
    }
}
