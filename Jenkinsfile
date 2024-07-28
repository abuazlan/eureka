pipeline {
    agent {
        docker {
            sh '''
                if ! [ -x "$(command -v mvn)" ]; then
                  echo "Maven is not installed. Installing Maven..."
                '''
            image 'maven:3.9.0'
            args '-v /root/.m2:/root/.m2'
        }
    }
    stages {
        stage('Install Maven') {
            steps {
                sh '''
                if ! [ -x "$(command -v mvn)" ]; then
                  echo "Maven is not installed. Installing Maven..."
                  apt-get update
                  apt-get install -y maven
                else
                  echo "Maven is already installed."
                fi
                '''
            }
        }
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
        stage('Deliver') {
            steps {
                sh './jenkins/scripts/deliver.sh'
            }
        }
    }
}
