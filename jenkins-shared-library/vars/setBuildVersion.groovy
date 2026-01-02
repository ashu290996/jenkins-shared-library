def call() {
    stage('Set Build Version') {
        sh """
            mvn versions:set \
              -DnewVersion=${APP_VERSION} \
              -DgenerateBackupPoms=false
        """
    }
}