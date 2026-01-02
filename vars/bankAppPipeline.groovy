def call() {

    pipeline {
        agent any

        tools {
            maven 'maven3'
            jdk 'jdk21'
        }

        environment {
            SCANNER_HOME = tool 'sonar-scanner'
            APP_VERSION  = "1.0.${BUILD_NUMBER}"
        }

        parameters {
            booleanParam(name: 'RUN_GIT_CHECKOUT', defaultValue: true)
            booleanParam(name: 'RUN_MVN_COMPILE', defaultValue: true)
            booleanParam(name: 'RUN_MVN_TEST', defaultValue: true)
            booleanParam(name: 'RUN_MVN_PACKAGE', defaultValue: true)
            booleanParam(name: 'RUN_NEXUS_UPLOAD', defaultValue: true)
            booleanParam(name: 'RUN_SONAR', defaultValue: true)
            booleanParam(name: 'RUN_OWASP', defaultValue: true)
            booleanParam(name: 'RUN_DOCKER_BUILD', defaultValue: true)
            booleanParam(name: 'RUN_TRIVY', defaultValue: true)
            booleanParam(name: 'RUN_GIT_CHECKOUT1', defaultValue: true)
            booleanParam(name: 'RUN_DEPLOY_CONTAINER', defaultValue: true)
        }

        stages {

            stage('Build & Artifact Flow') {
                stages {
                    stage('Git Checkout')    { when { expression { params.RUN_GIT_CHECKOUT } } steps { gitCheckout() } }
                    stage('Set Version')     { steps { setBuildVersion() } }
                    stage('Compile')         { when { expression { params.RUN_MVN_COMPILE } } steps { mavenCompile() } }
                    stage('Test')            { when { expression { params.RUN_MVN_TEST } } steps { mavenTest() } }
                    stage('Package')         { when { expression { params.RUN_MVN_PACKAGE } } steps { mavenPackage() } }
                    stage('Nexus Upload')    { when { expression { params.RUN_NEXUS_UPLOAD } } steps { nexusUpload() } }
                }
            }

            stage('Quality & Security') {
                parallel {
                    stage('Sonar')  { when { expression { params.RUN_SONAR } } steps { sonarScan() } }
                    stage('OWASP')  { when { expression { params.RUN_OWASP } } steps { owaspScan() } }
                    stage('Docker') { when { expression { params.RUN_DOCKER_BUILD } } steps { dockerBuildPush() } }
                    stage('Trivy')  { when { expression { params.RUN_TRIVY } } steps { trivyScan() } }
                }
            }

            stage('Deploy Flow') {
                stages {
                    stage('Checkout CD Repo') { when { expression { params.RUN_GIT_CHECKOUT1 } } steps { gitCheckoutCd() } }
                    stage('Deploy to K8s')    { when { expression { params.RUN_DEPLOY_CONTAINER } } steps { deployK8s() } }
                }
            }
        }
    }
}
