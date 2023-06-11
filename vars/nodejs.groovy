def call() {
    pipeline {

        agent {
            node {
                label 'workstation'
            }
        }

        options {
           ansiColor('xterm')
       }

        stages {
          stage('Code Quality') {
              steps {
//                  sh 'ls -l'
//                  sh 'sonar-scanner -Dsonar.projectKey=${component} -Dsonar.host.url=http://172.31.91.128:9000 -Dsonar.login=admin -Dsonar.password=admin123 -Dsonar.qualitygate.wait=true'
                  sh 'echo Code Quality'
              }

          }
            stage('Unit test cases') {
                steps {
                  sh 'echo Unit tests'
                   // sh 'npm test'
                }

            }
            stage('CheckMarx SAST Scan') {
                steps {
                    sh 'echo Checkmarx Scan'
                }

            }
            stage('CheckMarx SCA Scan') {
                steps {
                    sh 'echo Checkmarx SCA Scan'
                }

            }
            stage('Release application') {
                steps {
                    sh 'env'
                    sh 'echo Release application'
                }

            }

        }
        post {
            always {
                cleanWs()
            }
        }

    }
}