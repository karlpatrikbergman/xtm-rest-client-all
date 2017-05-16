#!groovy
node {
    echo 'Hello World 2'
    git credentialsId: '3e1b996b-9fdf-4592-85e9-bea4125473c0', url: 'https://pabe@atlas.transmode.se/bitbucket/scm/nm/xtm-rest-client-all.git'
    sh './gradlew clean build'
    sh './gradlew artifactoryPublish'
}