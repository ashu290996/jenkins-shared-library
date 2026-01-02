def call() {
    stage('Deploy to Kubernetes') {
        withKubeConfig(
            credentialsId: 'k8-prod-token',
            namespace: 'prod',
            serverUrl: 'https://bankapp-dns-prn5mjxo.hcp.eastus.azmk8s.io:443'
        ) {
            sh """
                kubectl apply -f bankapp/bankapp-ds.yml -n prod
                kubectl apply -f database/database-ds.yml -n prod
                kubectl set image deployment bankapp-deployment \
                  bankapp=ashu290996/bankapp:${APP_VERSION} -n prod
            """
        }
    }
}
