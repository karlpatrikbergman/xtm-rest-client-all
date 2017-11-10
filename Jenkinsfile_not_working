#!groovy

pipeline {
    agent {
        label 'docker'
    }
    stages {
        stage('Clone') {
            steps {
                git credentialsId: '64c67773-022f-4330-97bc-70201486dd8f', url: 'ssh://bitbucket.transmode.se:7999/nm/xtm-rest-client-all.git'
            }
        }
        stage('Build') {
            steps {
                sh './gradlew clean build -xtest'
            }
        }
        stage('Test') {
            steps {
                sh './gradlew test'
                junit allowEmptyResults: true, testResults: '**/build/test-results/TEST-*.xml'
            }
        }
        stage('Clean up docker environment') {
            steps {
                sh('chmod +x scripts/docker_clean_up.sh')
                sh('./scripts/docker_clean_up.sh')
            }
        }
        stage('Integration Test') {
            steps {
                sh './gradlew IntegrationTest'
                junit allowEmptyResults: true, testResults: '**/build/test-results/INTEGRATION_TEST-*.xml'
            }
        }
        stage('Publish') {
            steps {
                sh('git rev-parse HEAD > GIT_COMMIT')
                git_commit = readFile('GIT_COMMIT')
                short_commit = git_commit.take(7)
                sh "echo short git commit hash: ${short_commit}"
                echo 'Publishing to Artifactory...'
                sh "./gradlew artifactoryPublish -PpartOfLatestCommitHash=${short_commit}"
            }
        }
    }
}