#!groovy
node {
    git credentialsId: '64c67773-022f-4330-97bc-70201486dd8f', url: 'ssh://bitbucket.transmode.se:7999/nm/xtm-rest-client-all.git'
    sh './gradlew clean build'
    sh './gradlew artifactoryPublish'
}