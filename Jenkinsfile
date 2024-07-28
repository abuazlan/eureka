pipeline {
    agent any
    environment {
        ARTIFACT_ID = sh(script: "mvn help:evaluate -Dexpression=project.artifactId -q -DforceStdout | sed 's/[^a-zA-Z0-9.-]//g'", returnStdout: true).trim()
        VERSION = sh(script: "mvn help:evaluate -Dexpression=project.version -q -DforceStdout | sed 's/[^a-zA-Z0-9.-]//g'", returnStdout: true).trim()
        TIMESTAMP = sh(script: "date +%m%d%Y%H%M%S", returnStdout: true).trim()
        CHART_NAME = "${ARTIFACT_ID}-${VERSION}-${TIMESTAMP}"
        FINAL_JAR = "${ARTIFACT_ID}-${VERSION}.jar"
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
        stage('Generate Helm Chart') {
            steps {
                sh '''
                echo "Generating Helm chart..."
                helm create ${CHART_NAME}
                helm package ${CHART_NAME}
                '''
            }
        }
        stage('Clean Up') {
            steps {
                sh '''
                echo "Cleaning up..."
                find . -mindepth 1 -maxdepth 1 ! -name "${ARTIFACT_ID}-*.tgz" ! -name "*.jar" -exec rm -rf {} +
                if [ -d "target" ]; then
                find target -mindepth 1 ! -name "*.jar" -exec rm -rf {} +
                fi
                '''
            }
        }
    }
}
