
pipeline {
  agent any
  parameters {
        string(name: 'tag_input', defaultValue: '', description: '部署的服务的镜像标签')
        booleanParam(name: 'deploy_k8s', defaultValue: true, description: '是否在Kubernetes集群发布')
    }
  stages {
    stage('build') {
      steps {
        sh '''./gradlew clean
        ./gradlew bootRepackage'''
      }
    }
    stage ('def impage_path ') {
      steps {
          script {
          def gitURLcommand = 'git config --local remote.origin.url'
          tag = tag_input ?: GIT_COMMIT
          gitURL = sh(returnStdout: true, script: gitURLcommand).trim()
          repoName = gitURL.split('/')[-1].split('\\.')[0]
          def branch2env = [master: 'test', validation: 'validation', release: 'prod']
          IMAGE_PATH = "nexus-release.xsio.cn/${branch2env[env.BRANCH_NAME]}/${repoName}:${tag}"
          echo IMAGE_PATH
          IMAGE_PUB = "nexus-public.xsio.cn/${branch2env[env.BRANCH_NAME]}/${repoName}:${tag}"
          echo IMAGE_PUB
          }
      }
    }
    stage ('docker') {
      steps {
        sh "docker build -t ${IMAGE_PATH} ."
        sh "docker push ${IMAGE_PATH}"
        sh "docker rmi ${IMAGE_PATH} || echo"
      }
    }
  }
}
