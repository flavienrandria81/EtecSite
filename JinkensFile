pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                git 'https://github.com/flavienrandria81/EtecSite.git'
            }
        }

        stage('Build Microservices') {
            steps {
                dir('etec-parent') {
                    sh 'mvn clean install -DskipTests'
                }
            }
        }

        stage('Test') {
            steps {
                dir('etec-parent') {
                    sh 'mvn test'
                }
            }
        }

        stage('Package') {
            steps {
                dir('etec-parent') {
                    sh 'mvn package -DskipTests'
                }
            }
        }
    }
}