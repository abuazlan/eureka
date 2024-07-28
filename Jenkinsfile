pipeline {
    agent {
        any {
           
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
        stage('Build and Install') {
            steps {
                sh 'mvn -B -DskipTests clean install'
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
        stage('Build and Install') {
            steps {
                sh 'mvn -B -DskipTests clean install package'
            }
        }
    }
}
