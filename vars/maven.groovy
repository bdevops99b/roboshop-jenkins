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
            stage('Code Compile') {
                steps {
                    sh 'echo Code Compile'
                }

            }
          stage('Code Quality') {
              steps {
                  sh 'echo Code Quality'
              }

          }
            stage('Unit test cases') {
                steps {
                    sh 'echo Unit test'
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

        }
        post {
            always {
                cleanWs()
            }
        }

    }
}