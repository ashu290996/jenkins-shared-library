def call() {
    stage('MVN test') {
        sh "mvn test"
    }
}