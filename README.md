# test encoder/decoder/errcoder on the Feign call

```
void setBuildStatus(String message, String state, String context) {  step([
      $class: "GitHubCommitStatusSetter",
      reposSource: [$class: "ManuallyEnteredRepositorySource", url: "https://github.com/qingjie/qingjie-dev-core"],
      contextSource: [$class: "ManuallyEnteredCommitContextSource", context: context],
      errorHandlers: [[$class: "ChangingBuildStatusErrorHandler", result: "UNSTABLE"]],
      statusResultSource: [ $class: "ConditionalStatusResultSource", results: [[$class: "AnyBuildResult", message: message, state: state]] ]
  ]);
}

pipeline{
  agent any
  tools{
    maven 'default'
  }
  stages{
        stage('Set Pending status in Github'){
            steps{
                setBuildStatus("Build Started", "PENDING","Jenkins - Build package");
                setBuildStatus("Build Started", "PENDING","Junit tests validation");
                setBuildStatus("Build Started", "PENDING","Jenkins - Quality Check");
            }
        }
        stage('Maven Package') {
          steps {
            sh 'mvn -B clean package'
            setBuildStatus("Maven compile", "SUCCESS", "Jenkins - Build package");
          }
        }
        stage('Maven Tests') {
            steps {
                sh 'mvn -B test'
                setBuildStatus("Maven compile", "SUCCESS", "Junit tests validation");
            }
        }
        stage('Sonar Analysis') {
          steps {
            withSonarQubeEnv('sonar.dhruv.dev') {
                sh 'mvn $SONAR_MAVEN_GOAL -Dsonar.login=$SONAR_AUTH_TOKEN -Dsonar.host.url=$SONAR_HOST_URL -Dsonar.profile="Sonar way"'
            }
          }
        }
        stage('Sonar Quality Gate'){
            steps{
                script{
                  timeout(time: 1, unit: 'HOURS') { // Just in case something goes wrong, pipeline will be killed after a timeout
                    def qg = waitForQualityGate() // Reuse taskId previously collected by withSonarQubeEnv
                    if (qg.status != 'OK') {
                      setBuildStatus("Build failed", "FAILURE", "Jenkins - Quality Check");
                      error "Pipeline aborted due to quality gate failure: ${qg.status}"
                    } else {
                        setBuildStatus("Build succeeded", "SUCCESS", "Jenkins - Quality Check");
                    }
                  }
                }
            }
        }
    }

}

```
