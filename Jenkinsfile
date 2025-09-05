pipeline {
    agent any



    parameters {
        string(name: 'BROWSER', defaultValue: 'chrome', description: 'Browser to run tests on')
        string(name: 'BASE_URL', defaultValue: 'https://www.google.com', description: 'Base URL for tests')
        booleanParam(name: 'HEADLESS', defaultValue: true, description: 'Run tests in headless mode')
    }

    environment {
        PATH = "${tool 'Maven-3.9.0'}/bin:${env.PATH}"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: 'main']],
                          userRemoteConfigs: [[url: 'git@github.com:username/repo.git', credentialsId: 'ssh-key-id']]])
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                sh "mvn test -Dbrowser=${params.BROWSER} -DbaseUrl=${params.BASE_URL} -Dheadless=${params.HEADLESS}"
            }
        }

        stage('Publish Reports') {
            steps {
                publishHTML([allowMissing: false, alwaysLinkToLastBuild: true, keepAll: true,
                             reportDir: 'target/extent-reports', reportFiles: 'index.html',
                             reportName: 'Extent Report'])
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'target/surefire-reports/*.xml, target/screenshots/*.png', allowEmptyArchive: true
            junit 'target/surefire-reports/*.xml'
        }
    }
}
