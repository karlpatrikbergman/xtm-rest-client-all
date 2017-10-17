#!groovy

pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh './gradlew clean build -xtest'
            }
        }
        stage('Test') {
            steps {
                sh './gradlew test'
            }
        }
        stage('Integration Test') {
            steps {
                sh './gradlew IntegrationTest'
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