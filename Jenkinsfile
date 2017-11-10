#!groovy

def projectProperties = [
        [$class: 'BuildDiscarderProperty', strategy: [$class: 'LogRotator', numToKeepStr: '10']],
]

node('docker') {
    stage('Clone') {
        git credentialsId: '64c67773-022f-4330-97bc-70201486dd8f', url: 'ssh://bitbucket.transmode.se:7999/nm/xtm-rest-client-all.git'
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
        junit 'build/reports/**/*.xml'
    }
    try {
        stage('Integration Test') {
            sh('./gradlew IntegrationTest')
            junit 'build/reports/**/*.xml'
        }
    }
    finally {
        junit 'build/reports/**/*.xml'
//        junit allowEmptyResults: true, testResults: '**/build/test-results/INTEGRATION_TEST-*.xml'

    }
    stage('Publish') {
        sh('git rev-parse HEAD > GIT_COMMIT')
        git_commit = readFile('GIT_COMMIT')
        short_commit = git_commit.take(7)
        sh("echo short git commit hash: ${short_commit}")
        echo 'Publishing to Artifactory...'
        sh("./gradlew artifactoryPublish -PpartOfLatestCommitHash=${short_commit}")
    }
}
