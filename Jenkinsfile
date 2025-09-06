pipeline {
    agent any

    environment {
        REPORT_DIR = "target/extent-report"
    }

    stages {
        stage('Checkout from GitHub') {
            steps {
                checkout([$class: 'GitSCM',
                          branches: [[name: '*/main']],
                          userRemoteConfigs: [[
                              url: 'git@github.com:psalunkhe72/DemoAutomation.git'
                          ]]
                ])
            }
        }

        stage('Build & Test') {
            steps {
                sh 'mvn clean test -Dsurefire.suiteXmlFiles=testng.xml -Denv=jenkins'
            }
        }

        stage('Publish Extent Report') {
            steps {
                publishHTML(target: [
                    allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: "${REPORT_DIR}",
                    reportFiles: 'index.html',
                    reportName: 'Extent Report'
                ])
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'target/screenshots/*.png', allowEmptyArchive: true
            junit 'target/surefire-reports/*.xml'
        }
    }
}
