def call() {
    stage('Create Docker Image') {
        withDockerRegistry(credentialsId: 'dockerhub', url: 'https://index.docker.io/v1/') {
            sh """
                docker build -t ashu290996/bankapp:${APP_VERSION} .
                docker tag ashu290996/bankapp:${APP_VERSION} ashu290996/bankapp:latest
                docker push ashu290996/bankapp:${APP_VERSION}
                docker push ashu290996/bankapp:latest
            """
        }
    }
}
