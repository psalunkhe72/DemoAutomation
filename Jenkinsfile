pipeline {
    agent any

    environment {
        REPORT_DIR = "target/extent-report"
    }

    parameters {
        choice(name: 'ENV', choices: ['local', 'grid', 'jenkins'], description: 'Select test environment')
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

        stage('Start Selenium Grid') {
            when { expression { params.ENV == 'grid' } }
            steps {
                echo "Starting Selenium Grid using docker-compose.yml..."
                sh 'docker-compose -f docker-compose.yml up -d'
                sh 'docker ps'
            }
        }

        stage('Build & Test') {
            steps {
                script {
                    def browsers = ['chrome', 'firefox']
                    def tests = [:]

                    for (b in browsers) {
                        def browserName = b
                        tests[browserName] = {
                            sh "mvn clean test -Dsurefire.suiteXmlFiles=testng.xml -Denv=${params.ENV} -Dbrowser=${browserName}"
                        }
                    }

                    parallel tests
                }
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
            echo 'Archiving screenshots and JUnit reports...'
            archiveArtifacts artifacts: 'target/screenshots/*.png', allowEmptyArchive: true
            junit 'target/surefire-reports/*.xml'

            script {
                if (params.ENV == 'grid') {
                    echo "Stopping Selenium Grid..."
                    sh 'docker-compose -f docker-compose.yml down'
                }
            }
        }
    }
}
