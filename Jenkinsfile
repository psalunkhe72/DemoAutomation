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
                    allowMissing: false,              // ❌ If report not found, fail the build
                    alwaysLinkToLastBuild: true,      // 🔗 Sidebar link always points to latest report
                    keepAll: true,                    // 📂 Keep reports from old builds too
                    reportDir: 'target/extent-report',// 📂 Folder where Extent report is generated
                    reportFiles: 'index.html',        // 📄 Main HTML file to publish
                    reportName: 'Extent Report'       // 🏷️ Sidebar link name in Jenkins UI
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
