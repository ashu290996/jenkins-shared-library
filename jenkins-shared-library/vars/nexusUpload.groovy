def call() {
    stage('Nexus Upload') {
        withMaven(
            globalMavenSettingsConfig: 'Global-maven-setting',
            jdk: 'jdk21',
            maven: 'maven3',
            traceability: true
        ) {
            sh "mvn deploy -DskipTests=true"
        }
    }
}