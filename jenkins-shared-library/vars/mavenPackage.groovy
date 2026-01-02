def call() {
    stage('MVN package') {
        sh "mvn clean package -DskipTests=true"
    }
}