#!groovy

def projectProperties = [
        [$class: 'BuildDiscarderProperty',strategy: [$class: 'LogRotator', numToKeepStr: '10']],
]

node {
    stage('Clone') {
        echo 'Cloning from bitbucket'
        git credentialsId: '64c67773-022f-4330-97bc-70201486dd8f', url: 'ssh://bitbucket.transmode.se:7999/nm/xtm-rest-client-all.git'
    }
    stage('Build') {
        echo 'Building....'
        sh './gradlew clean build -xtest'
    }
    stage('Test') {
        echo 'Running unit tests....'
        sh './gradlew test'
        junit allowEmptyResults: true, testResults: '**/build/test-results/TEST-*.xml'
    }
    stage('Integration Test') {
        echo 'Running integration tests....'
        sh './gradlew IntegrationTest'
        junit allowEmptyResults: true, testResults: '**/build/test-results/TEST-*.xml'
    }
    stage('Publish') {
        sh('git rev-parse HEAD > GIT_COMMIT')
        git_commit=readFile('GIT_COMMIT')
        short_commit=git_commit.take(7)
        sh "echo short git commit hash: ${short_commit}"
        echo 'Publishing to Artifactory...'
        sh "./gradlew artifactoryPublish -PpartOfLatestCommitHash=${short_commit}"
    }
}