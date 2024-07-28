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
                  echo "Maven is not installed. Install Maven using below command..."
                  echo "docker exec -it -u root container_name /bin/bash"
                  echo "apt-get install maven"
                  exit 1
                else
                  echo "Maven is already installed. Good"
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
        stage('Package') {
            steps {
                sh 'mvn -B -DskipTests clean install package'
            }
        }
    }
}
