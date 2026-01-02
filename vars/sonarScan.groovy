def call() {
    stage('Sonar Analysis') {
        withSonarQubeEnv('sonar-scanner') {
            sh """
                ${SCANNER_HOME}/bin/sonar-scanner \
                  -Dsonar.java.binaries=. \
                  -Dsonar.projectName=Multi-Tier-BankApp-CI \
                  -Dsonar.projectKey=Multi-Tier-BankApp-CI
            """
        }
    }
}
