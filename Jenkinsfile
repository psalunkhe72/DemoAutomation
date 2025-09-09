pipeline {
    agent any

    environment {
        REPORT_DIR = "target/extent-report"
    }

    parameters {
        choice(name: 'ENV', choices: ['local', 'grid', 'jenkins'], description: 'Select test environment')
        string(name: 'BASE_URL', defaultValue: 'http://localhost:8080', description: 'Application base URL')
        choice(name: 'HEADLESS', choices: ['true', 'false'], description: 'Run browser in headless mode')
        choice(name: 'BROWSER', choices: ['chrome', 'firefox', 'both'], description: 'Select browser to run')
        string(name: 'TEST_SUITE', defaultValue: 'testng.xml', description: 'TestNG suite file to run')
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
                    // Determine browsers to run
                    def browsers = []
                    if (params.BROWSER == 'both') {
                        browsers = ['chrome', 'firefox']
                    } else {
                        browsers = [params.BROWSER]
                    }

                    // Prepare parallel test executions
                    def tests = [:]
                    for (b in browsers) {
                        def browserName = b
                        tests[browserName] = {
                            sh """
                                mvn clean test \
                                -Dsurefire.suiteXmlFiles=${params.TEST_SUITE} \
                                -Denv=${params.ENV} \
                                -Dbrowser=${browserName} \
                                -DbaseUrl=${params.BASE_URL} \
                                -Dheadless=${params.HEADLESS}
                            """
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

           // Optional: stop Selenium Grid if started by pipeline
           script {
               if (params.ENV == 'grid') {
                   echo "Stopping Selenium Grid..."
                   sh 'docker-compose -f docker-compose.yml down'
               }
           }
       }
   }

