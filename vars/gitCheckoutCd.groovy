def call() {
    stage('Git checkout CD') {
        git branch: 'master',
            credentialsId: 'github-login',
            url: 'https://github.com/ashu290996/Multi-Tier-BankApp-CD.git'
    }
}
