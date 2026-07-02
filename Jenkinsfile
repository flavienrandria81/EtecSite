pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                git branch: 'master', url: 'https://github.com/flavienrandria81/EtecSite.git'
            }
        }

        stage('Build Microservices') {
            steps {
                dir('etec-parent') {
                    bat 'mvn clean install -DskipTests'
                }
            }
        }

        stage('Test') {
            steps {
                dir('etec-parent') {
                    bat 'mvn test'
                }
            }
        }

        stage('Package') {
            steps {
                dir('etec-parent') {
                    bat 'mvn package -DskipTests'
                }
            }
        }
    }
}