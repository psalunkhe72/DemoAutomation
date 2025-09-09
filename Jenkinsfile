pipeline {
    agent any

    environment {
        GRID_URL = "http://localhost:4444"
    }

    parameters {
        choice(name: 'ENV', choices: ['local', 'grid', 'jenkins'], description: 'Select test environment')
        choice(name: 'BROWSER', choices: ['chrome', 'firefox'], description: 'Select browser')
    }

    stages {



          stage('Build & Test') {
            parallel {
                stage('Chrome Tests') {
                    when {
                        expression { params.BROWSER == 'chrome' || params.BROWSER == 'all' }
                    }
                    steps {
                        echo "Running Chrome Tests..."
                        sh "mvn clean test -Dsurefire.suiteXmlFiles=testng.xml -Denv=grid -Dbrowser=chrome -DbaseUrl=http://localhost:8080 -Dheadless=true"
                    }
                }

                stage('Firefox Tests') {
                    when {
                        expression { params.BROWSER == 'firefox' || params.BROWSER == 'all' }
                    }
                    steps {
                        echo "Running Firefox Tests..."
                        sh "mvn clean test -Dsurefire.suiteXmlFiles=testng.xml -Denv=grid -Dbrowser=firefox -DbaseUrl=http://localhost:8080 -Dheadless=true"
                    }
                }
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
            echo 'Stopping Selenium Grid if running...'
            sh '''
            if [ "$ENV" = "grid" ]; then
                docker-compose -f docker-compose.yml down
            fi
            '''
        }
    }
}
