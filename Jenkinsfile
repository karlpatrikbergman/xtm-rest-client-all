pipeline {
    agent none
    triggers {
        pollSCM('H/5 * * * *')
    }
    options {
        timestamps()
        buildDiscarder(logRotator(numToKeepStr: '10'))
        ansiColor('xterm')
        timeout(time: 1, unit: 'HOURS')
        skipDefaultCheckout()
    }
    stages {
        stage('Build') {
            agent {
                docker {
                    label 'docker && infinera'
                    image 'se-artif-prd.infinera.com/gradle:4.3.1'
                }
            }
            steps {
                checkout scm
                stash 'checkout'
                sh 'gradle assemble'
                stash 'assemble'

                script {
                    GIT_COMMIT = sh(
                            script: 'git rev-parse HEAD',
                            returnStdout: true
                    ).trim()
                    echo "git commit: ${GIT_COMMIT}"
                }
            }
        }
        post {
            always {
                cleanWs()
            }
        }
    }
    stage('Test') {
        // parallelize unit tests / api tests?
        agent {
            docker {
                label 'docker'
                image 'se-artif-prd.infinera.com/gradle:4.3.1'
            }
        }
        steps {
            unstash 'assemble'
            sh 'gradle test'
        }
        post {
            always {
                junit '**/build/test-results/test/TEST-*.xml'
                cleanWs()
            }
        }

    }
    stage('Integration test') {
        agent {
            label 'docker'
        }
        steps {
            unstash 'assemble'
            sh('./gradlew IntegrationTest')
        }
        post {
            always {
                junit '**/build/test-results/integrationTest/TEST-*.xml'
                cleanWs()
            }
        }
    }
    stage('Publish') {
        agent {
            docker {
                label 'docker'
                image 'se-artif-prd.infinera.com/gradle:4.3.1'
            }
        }
        steps {
            unstash 'assemble'
            sh("./gradlew artifactoryPublish -PpartOfLatestCommitHash=1")
        }
    }
}
}
