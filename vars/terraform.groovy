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
        parameters {
            choice(name: 'env', choices: ['dev', 'prod'], description: 'Pick environment')
            choice(name: 'action', choices: ['apply', 'destroy'], description: 'Pick action')
        }
        stages {
            stage('Terraform INIT') {
                steps {
                    sh 'terraform init -backend-config=env-${env}/state.tfvars'
                }
            }
            stage('Terraform APPLY') {
                steps {
                    sh 'terraform apply -var-file=env-${env}/main.tfvars -auto-approve'
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