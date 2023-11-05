#!/usr/bin/env groovy
void call() {
    String name = "tan-be"
    String registry = "018811972711.dkr.ecr.ap-southeast-1.amazonaws.com"
    // String sonarToken = "sonar-token"

//========================================================================
//========================================================================

//========================================================================
//========================================================================


    stage ("Build Image") {
        docker.build("${name}:${BUILD_NUMBER}", "-f src/backend/Dockerfile \
        ${WORKSPACE}/src/backend")
    }

    stage ("Trivy scan") {
        def formatOption = "--format template --template \"@/usr/local/share/trivy/templates/html.tpl\""

        // Scan all vuln levels
        sh 'mkdir -p reports'
        sh "trivy filesystem --ignore-unfixed --vuln-type os,library ${formatOption} -o reports/backend-scan.html ./src/backend"
        publishHTML(target: [
          allowMissing: true,
          alwaysLinkToLastBuild: false,
          keepAll: true,
          reportDir: "reports",
          reportFiles: "backend-scan.html",
          reportName: "Trivy Report - Backend",
        ])

        // Scan again and fail on CRITICAL vulns
        // sh 'trivy filesystem --ignore-unfixed --vuln-type os,library --exit-code 1 --severity CRITICAL ./src/backend'
    }

    stage ("Push Docker Images") {
        script {
            sh "aws configure list"
            sh "aws ecr get-login-password --region ap-southeast-1 | docker login --username AWS --password-stdin ${registry}"

            sh "docker tag ${name}:${BUILD_NUMBER} ${registry}/${name}"

            sh "docker push ${registry}/${name}"
        }   
    }
    // stage ("Push Docker Images") {
    //     docker.withRegistry("https://${registry}", ecrCredential) {
    //         docker.image("${registry}/${name}:${BUILD_NUMBER}").push()
    //     }
    // }
}
//========================================================================
// Demo CI
// Version: v1.0
// Updated:
//========================================================================
//========================================================================
// Notes:
//
//
//========================================================================