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
        environment {
            NEXUS = credentials('NEXUS')
        }
        stages {

            stage('Code Quality') {
                steps {
//                    sh 'ls -l'
//                    sh 'sonar-scanner -Dsonar.projectKey=${component} -Dsonar.host.url=http://172.31.93.98:9000 -Dsonar.login=admin -Dsonar.password=admin123 -Dsonar.qualitygate.wait=true -Dsonar.java.binaries=./target'
                    sh 'echo Code Quality'
                }

            }
            stage('Unit test cases') {
                steps {
                    sh 'echo Unit test'
//                    sh 'python3.6 -m unittest '
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
                when {
                    expression {
                        env.TAG_NAME ==~ ".*"
                    }
                }
                steps {
                    sh 'echo $TAG_NAME >VERSION'
                    sh 'zip -r ${component}-${TAG_NAME}.zip *.ini *.py *.txt  VERSION ${schema_dir}'
                    sh 'curl -f -v -u ${NEXUS_USR}:${NEXUS_PSW} --upload-file ${component}-${TAG_NAME}.zip http://172.31.92.175:8081/repository/${component}/${component}-${TAG_NAME}.zip'
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