pipeline {
    agent any

    environment {
        imagename = "lmslmsms0616/teamchat_back_chat"
        registryCredential = 'docker-hub'
        dockerImage = ''
    }

    stages {
        // git에서 repository clone
        stage('Prepare') {
          steps {
            echo 'Clonning Repository'
            git url: 'https://github.com/KA-ForCloud/backend-chatting.git',
              branch: 'master',
              credentialsId: 'github_cred'
            }
            post {
             success {
               echo 'Successfully Cloned Repository'
             }
           	 failure {
               error 'This pipeline stops here...'
             }
          }
        }

        // gradle build
        stage('Bulid Gradle') {
          agent any
          steps {
            echo 'Bulid Gradle'
            dir ('.'){
                sh """
                ./gradlew clean build --exclude-task test
                """
            }
          }
          post {
            failure {
              error 'This pipeline stops here...'
            }
          }
        }

        // docker build
        stage('Bulid Docker') {
          agent any
          steps {
            echo 'Bulid Docker'
            script {
                dockerImage = docker.build imagename
            }
          }
          post {
            failure {
              error 'This pipeline stops here...'
            }
          }
        }

        // docker push
        stage('Push Docker') {
          agent any
          steps {
            echo 'Push Docker'
            script {
                docker.withRegistry( '', registryCredential) {
                    dockerImage.push("${currentBuild.number}")  // ex) "1.0"
                }
            }
          }
          post {
            failure {
              error 'This pipeline stops here...'
            }
          }
        }
        stage('Remove image in Jenkins'){
           steps{
               sh "docker rmi lmslmsms0616/teamchat_back_chat:${currentBuild.number}"
           }
        }
        stage('Deploy to dev') {
                  steps {
                        sshagent(credentials: ['kic_key']) {
                            echo "sshagent start"
                            
                            sh "ssh -o StrictHostKeyChecking=no centos@210.109.61.15 uptime"
                            echo "uptime end"
                            // sh "ssh -o StrictHostKeyChecking=no centos@210.109.60.60 -p 10003 'sudo chmod 666 /var/run/docker.sock'"
                            sh "ssh -o StrictHostKeyChecking=no centos@210.109.61.15 'docker stop back_chat | true'"
                            sh "ssh -o StrictHostKeyChecking=no centos@210.109.61.15 'docker rm back_chat | true'"
                            echo "docker remove"
                            sh "ssh -o StrictHostKeyChecking=no centos@210.109.61.15 'docker rmi -f lmslmsms0616/teamchat_back_chat:latest | true'"
                            echo "docker remove image"
                            sh "ssh -o StrictHostKeyChecking=no centos@210.109.61.15 'docker run -d --name back_chat -p 80:8081 -p 443:8081 -p 8081:8081 lmslmsms0616/teamchat_back_chat:${currentBuild.number}'"
                            echo "docker run"

                            //sh "ssh -o StrictHostKeyChecking=no centos@210.109.60.60 -p 10003 uptime"
                            //echo "uptime end"
                            // sh "ssh -o StrictHostKeyChecking=no centos@210.109.60.60 -p 10003 'sudo chmod 666 /var/run/docker.sock'"
                            //sh "ssh -o StrictHostKeyChecking=no centos@210.109.60.60 -p 10003 'docker stop back_chat | true'"
                            //sh "ssh -o StrictHostKeyChecking=no centos@210.109.60.60 -p 10003 'docker rm back_chat | true'"
                            //echo "docker remove"
                            //sh "ssh -o StrictHostKeyChecking=no centos@210.109.60.60 -p 10003 'docker rmi -f lmslmsms0616/teamchat_back_chat:latest | true'"
                            //echo "docker remove image"
                            //sh "ssh -o StrictHostKeyChecking=no centos@210.109.60.60 -p 10003 'docker run -d --name back_chat -p 80:8081 -p 443:8081 -p 8081:8081 lmslmsms0616/teamchat_back_chat:${currentBuild.number}'"
                            //echo "docker run"

                            //slackSend (channel: '#jenkins-alert', color: '#FFFF00', message: "WAS-1-BACKEND-CHAT Deploy Complete: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                            //echo "WAS_1_Success"

                            //sh "ssh -o StrictHostKeyChecking=no centos@210.109.60.60 -p 10004 uptime"
                            //echo "uptime end"
                            // sh "ssh -o StrictHostKeyChecking=no centos@210.109.60.60 -p 10004 'sudo chmod 666 /var/run/docker.sock'"
                            //sh "ssh -o StrictHostKeyChecking=no centos@210.109.60.60 -p 10004 'docker stop back_chat | true'"
                            //sh "ssh -o StrictHostKeyChecking=no centos@210.109.60.60 -p 10004 'docker rm back_chat | true'"
                            //echo "docker remove"
                            //sh "ssh -o StrictHostKeyChecking=no centos@210.109.60.60 -p 10004 'docker rmi -f lmslmsms0616/teamchat_back_chat:latest | true'"
                            //echo "docker remove image"
                            //sh "ssh -o StrictHostKeyChecking=no centos@210.109.60.60 -p 10004 'docker run -d --name back_chat -p 80:8081 -p 443:8081 -p 8081:8081 lmslmsms0616/teamchat_back_chat:${currentBuild.number}'"
                            //echo "docker run"

                            //slackSend (channel: '#jenkins-alert', color: '#FFFF00', message: "WAS-2-BACKEND-CHAT Deploy Complete: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                            //echo "WAS_2_Success"
                        }
                    }

                   post {
                            failure {
                              echo 'Update 실패ㅠㅠ'
                            }
                            success {
                              echo 'Update 성공!!!! test'
                            }
                    }
                }
            }
    
}
