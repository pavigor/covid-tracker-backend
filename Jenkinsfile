pipeline {
    agent {
        kubernetes {
            yamlFile 'cicd/agent.yaml'
        }
    }
    environment {
        TAG_NAME = sh(returnStdout: true, script: "git tag --contains").trim()
    }
    stages {

        stage('Build') {
            steps {
                container('maven') {
                    sh 'mvn clean package -Dmaven.test.failure.ignore=true -DskipTests=true'
                }
            }
        }

//        stage('Sonar analyse') {
//            steps {
//                container('maven') {
//                    withSonarQubeEnv('Sonar') {
//                        sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.7.0.1746:sonar -DskipTests=true'
//                    }
//                }
//            }
//        }
       stage('Unit Tests') {
          steps {
              container('docker') {
                  script {
                      docker.image('mysql:8').withRun('-e "MYSQL_ROOT_PASSWORD=pass_word" -e "MYSQL_DATABASE=covid_tracker" -p 3306:3306') { c ->
                          docker.image('mysql:8').inside("--link ${c.id}:db") {
                              sh 'while ! mysqladmin ping -hdb --silent; do sleep 1; done'
                          }
                          docker.image('maven:3-openjdk-11').inside("--link ${c.id}:db") {
                              sh 'mvn test -DskipTests=false -Dspring.jpa.hibernate.ddl-auto=create -Dspring.datasource.url=jdbc:mysql://db/covid_tracker -Dspring.datasource.username=root -Dspring.datasource.password=pass_word'
                          }
                      }
                  }
              }
          }
       }
        stage('Create image') {
            when {
                    anyOf {
                        buildingTag(); environment name: 'GIT_BRANCH', value: 'qa'
                    }
                }
            steps {
                script {
                    container('docker') {
                        sh 'printenv'
                        def shaSum = env.GIT_COMMIT
                        def branch = env.GIT_BRANCH
                        def shortSum = shaSum.substring(0,7)
                        if (branch.contains("/")) {
                            branch = branch.split("/")[1]
                        }
                        def imageTag = "${branch}-${shortSum}"

                        def repository = ""
                        if (branch == 'main') {
                            repository = "backend"
                            if (env.TAG_NAME) {
                                tag = env.TAG_NAME
                            }
                        } else {
                            repository = "backend-dev"
                        }
                        env.APP_IMAGE="${repository}:${imageTag}"
                        docker.withRegistry("https://${env.ECR}",'ecr:eu-central-1:JenkinsECR') {
                           def image = docker.build("${repository}:${imageTag}")
                           image.push()
                        }
                    }
                }
            }
        }
        stage('Test feature branch') {
            when {
                environment name: 'GIT_BRANCH', value: 'f-01'
            }
            steps {
                sh 'printenv'
            }
        }
        stage('Deploy to qa') {
            when {
                branch 'qa'
            }
            steps {
               script {
                   container('jnlp') {
                       sh 'sed -i "s/__NAMESPACE__/app-dev/g" cicd/deployment.yaml'
                       sh 'sed -i "s/__IMAGE__/${APP_IMAGE}/g" cicd/deployment.yaml'
                       sh 'sed -i "s/__ECR__/${ECR}/g" cicd/deployment.yaml'
                       sh 'cat cicd/deployment.yaml'
                       kubernetesDeploy(configs: "cicd/deployment.yaml", kubeconfigId: "k8s")
                   }
               }
           }
        }
        stage('Deploy to production') {
            when {
                buildingTag()
            }
            steps {
                echo "Deploy it to prod!"
                sh 'printenv'
            }
        }
//       stage('Deploy') {
//           steps {
//               script {
//                   container('jnlp') {
//                           def branch = env.GIT_BRANCH
//                           if (branch.contains("main")) {
//                               sh 'sed -i "s/__NAMESPACE__/app-prod/g" cicd/deployment.yaml'
//                               sh 'sed -i "s/__IMAGE__/backend/g" cicd/deployment.yaml'
//                           } else {
//                               sh 'sed -i "s/__NAMESPACE__/app-dev/g" cicd/deployment.yaml'
//                               sh 'sed -i "s/__IMAGE__/backend-dev/g" cicd/deployment.yaml'
//                           }
//                           sh 'sed -i "s/__ECR__/${ECR}/g" cicd/deployment.yaml'
//                           sh 'cat cicd/deployment.yaml'
//                           kubernetesDeploy(configs: "cicd/deployment.yaml", kubeconfigId: "k8s")
//                   }
//               }
//           }
//       }
    }
}