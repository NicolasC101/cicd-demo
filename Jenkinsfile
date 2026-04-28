pipeline {
    agent any

    options {
        skipDefaultCheckout(true)
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh "docker run --rm -v ${WORKSPACE}:/app -w /app maven:3.9.9-eclipse-temurin-17 mvn clean package -DskipTests"
            }
        }

        stage('Test') {
            steps {
                sh "docker run --rm -v ${WORKSPACE}:/app -w /app maven:3.9.9-eclipse-temurin-17 mvn test"
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t mi-app:latest .'
            }
        }

    }
}