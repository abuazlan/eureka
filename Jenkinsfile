pipeline {
    agent any
    environment {
        ARTIFACT_ID = sh(script: "mvn help:evaluate -Dexpression=project.artifactId -q -DforceStdout | sed 's/[^a-zA-Z0-9.-]//g'", returnStdout: true).trim()
        VERSION = sh(script: "mvn help:evaluate -Dexpression=project.version -q -DforceStdout | sed 's/[^a-zA-Z0-9.-]//g'", returnStdout: true).trim()
        TIMESTAMP = sh(script: "date +%m%d%Y%H%M%S", returnStdout: true).trim()
        CHART_NAME = "${ARTIFACT_ID}-${VERSION}-${TIMESTAMP}"
    }
    stages {
        stage('Check Tools Availability') {
            steps {
                script {
                    def mavenAvailable = sh(script: 'command -v mvn', returnStatus: true) == 0
                    def helmAvailable = sh(script: 'command -v helm', returnStatus: true) == 0
                    if (!mavenAvailable || !helmAvailable) {
                        def message = ''
                        if (!mavenAvailable) {
                            message += "Maven is not installed. Install Maven using below command:\n"
                            message += "docker exec -it -u root container_name /bin/bash\n"
                            message += "apt-get install maven\n\n"
                        }
                        if (!helmAvailable) {
                            message += "Helm is not installed. Install Helm using below command:\n"
                            message += "curl https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3 | bash\n"
                        }
                        error(message)
                    } else {
                        echo "Both Maven and Helm are installed. Good."
                    }
                }
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
                echo "Copying all jar files to the root level..."
                cp target/*.jar .

                echo "Cleaning up..."
                find
