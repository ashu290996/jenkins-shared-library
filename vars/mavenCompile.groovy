def call() {
    stage('MVN compile') {
        sh "mvn compile"
    }
}