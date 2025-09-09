pipeline {
    agent any

    environment {
        REPORT_DIR = "target/extent-report"
    }

    parameters {
        choice(name: 'ENV', choices: ['local', 'grid', 'jenkins'], description: 'Select test environment')
        choice(name: 'BROWSER', choices: ['chrome', 'firefox'], description: 'Select browser for local run')
    }

    stages {

        stage('Checkout from GitHub') {
            steps {
                checkout([$class: 'GitSCM',
                    branches: [[name: '*/main']],
                    userRemoteConfigs: [[url: 'git@github.com:psalunkhe72/DemoAutomation.git']]])
            }
        }

        stage('Start Selenium Grid') {
            when {
                expression { params.ENV == 'grid' }
            }
            steps {
                echo 'Starting Selenium Grid using docker-compose.yml...'
                sh 'docker-compose -f docker-compose.yml up -d'
            }
        }

        stage('Build & Test') {
            parallel {
                stage('Chrome Tests') {
                    when {
                        expression { params.ENV != 'local' || params.BROWSER == 'chrome' }
                    }
                    steps {
                        echo 'Running Chrome Tests...'
                        sh "mvn clean test -Dsurefire.suiteXmlFiles=testng.xml -Denv=${params.ENV} -Dbrowser=chrome -DbaseUrl=http://localhost:8080 -Dheadless=true"
                    }
                }
                stage('Firefox Tests') {
                    when {
                        expression { params.ENV != 'local' || params.BROWSER == 'firefox' }
                    }
                    steps {
                        echo 'Running Firefox Tests...'
                        sh "mvn clean test -Dsurefire.suiteXmlFiles=testng.xml -Denv=${params.ENV} -Dbrowser=firefox -DbaseUrl=http://localhost:8080 -Dheadless=true"
                    }
                }
            }
        }

        stage('Publish Extent Report') {
            steps {
                echo 'Publishing Extent Reports...'
                publishHTML(target: [
                    allowMissing: true,
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
            archiveArtifacts artifacts: 'target/screenshots/**/*.png', allowEmptyArchive: true
            junit 'target/surefire-reports/*.xml'
        }
        cleanup {
            when {
                expression { params.ENV == 'grid' }
            }
            echo 'Stopping Selenium Grid...'
            sh 'docker-compose -f docker-compose.yml down'
        }
    }
}
