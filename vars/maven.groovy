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
            stage('Code Compile') {
                steps {
                    sh 'mvn compile'
                }

            }
            stage('Code Quality') {
                steps {
//                    sh 'ls -l'
//                    sh 'sonar-scanner -Dsonar.projectKey=${component} -Dsonar.host.url=http://172.31.91.128:9000 -Dsonar.login=admin -Dsonar.password=admin123 -Dsonar.qualitygate.wait=true -Dsonar.java.binaries=./target'
                    sh 'echo Code Quality'
                }

            }
            stage('Unit test cases') {
                steps {
                    sh 'echo Unit test'
                    // sh 'mvn test'
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
                    sh 'mvn package ; cp target/${component}-1.0.jar ${component}.jar'
                    sh 'echo $TAG_NAME >VERSION'
                    sh 'zip -r ${component}-${TAG_NAME}.zip ${component}.jar VERSION'
                    sh 'curl -f -v -u ${NEXUS_USR}:${NEXUS_PSW} --upload-file ${component}-${TAG_NAME}.zip http://172.31.90.94:8081/repository/${component}/${component}-${TAG_NAME}.zip'
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