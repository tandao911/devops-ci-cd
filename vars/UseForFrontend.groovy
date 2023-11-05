#!/usr/bin/env groovy
void call() {
    String name = "tan-fe"
    String registry = "018811972711.dkr.ecr.ap-southeast-1.amazonaws.com"
    // String sonarToken = "sonar-token"
    // String ecrCredential = 'ecr:ap-southeast-1:aws-ecr'
    

//========================================================================
//========================================================================

//========================================================================
//========================================================================


    stage ("Build Image") {
        docker.build("${name}:${BUILD_NUMBER}", "-f src/frontend/Dockerfile \
        ${WORKSPACE}/src/frontend")
    }

    stage ("Trivy scan") {
        def formatOption = "--format template --template \"@/usr/local/share/trivy/templates/html.tpl\""

        // Scan all vuln levels
        sh 'mkdir -p reports'
        sh "trivy filesystem --ignore-unfixed --vuln-type os,library ${formatOption} -o reports/frontend-scan.html ./src/frontend"
        publishHTML(target: [
          allowMissing: true,
          alwaysLinkToLastBuild: false,
          keepAll: true,
          reportDir: "reports",
          reportFiles: "frontend-scan.html",
          reportName: "Trivy Report - Frontend",
        ])

        // Scan again and fail on CRITICAL vulns
        // sh 'trivy filesystem --ignore-unfixed --vuln-type os,library --exit-code 1 --severity CRITICAL ./src/frontend'
    }

    stage ("Push Docker Images") {
        script {
            sh "aws configure list"
            sh "aws ecr get-login-password --region ap-southeast-1 | docker login --username AWS --password-stdin ${registry}"

            sh "docker tag ${name}:${BUILD_NUMBER} ${registry}/${name}"

            sh "docker push ${registry}/${name}"
        }   
    }
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