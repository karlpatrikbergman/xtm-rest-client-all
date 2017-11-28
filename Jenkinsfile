#!groovy

def projectProperties = [
        [$class: 'BuildDiscarderProperty', strategy: [$class: 'LogRotator', numToKeepStr: '10']],
]

node('docker') {
    stage('Clone') {
        label 'infinera'
        checkout scm
    }
    stage('Build') {
        sh('./gradlew clean build -x test -x IntegrationTest')
    }
    try {
        stage('Test') {
            sh('./gradlew test')
        }
    }
    finally {
        junit '**/build/test-results/test/TEST-*.xml'
    }
    try {
        stage('Integration Test') {
            sh('./gradlew IntegrationTest')
        }
    }
    finally {
        junit '**/build/test-results/integrationTest/TEST-*.xml'
    }
    stage('Publish') {
//        sh('git rev-parse HEAD > GIT_COMMIT')
//        git_commit = readFile('GIT_COMMIT')
//        short_commit = git_commit.take(7)
//        sh("echo short git commit hash: ${short_commit}")
//        echo 'Publishing to Artifactory...'
//        sh("./gradlew artifactoryPublish -PpartOfLatestCommitHash=${short_commit}")
        sh("./gradlew artifactoryPublish -PpartOfLatestCommitHash=999")
    }
}
