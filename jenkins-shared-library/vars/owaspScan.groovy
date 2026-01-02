def call() {
    stage('OWASP Scan') {
        dependencyCheck additionalArguments: '--scan ./', odcInstallation: 'DP-check'
        dependencyCheckPublisher pattern: '**/dependency-check-report.xml'
    }
}
