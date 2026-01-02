def call() {
    stage('Run Trivy') {
        sh "trivy image --format table -o image.html ashu290996/bankapp:${APP_VERSION}"
    }
}
