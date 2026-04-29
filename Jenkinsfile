pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/NicolasC101/cicd-demo.git', branch: 'master'
            }
        }

        stage('Build') {
            steps {
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
}