def call(String env, String module, version = '') {

    stage ('checkout'){
        def env2branch = [test: 'master', validation: 'validation', prod: 'release']

        def branch = env2branch[ENV] ?: 'master'
        git branch: "${branch}", credentialsId: 'xsio', url: "git@github.com:xsio/${module}.git"
        commitId = sh returnStdout: true, script: 'git rev-parse HEAD'
        commitId = commitId.trim()
        echo commitId
        // IMAGE = "${env.REGISTRY_SNAPSHOT}/${IMAGE_PATH}:${commitId}"
    }
    stage ('build'){

       sh '''
            #bypass jenkins $HOME bug
            export HOME=/opt/hudson
            . ${HOME}/.bashrc
            ./gradlew clean
            ./gradlew bootRepackage -Dgrails.env=${ENV}
        '''
    }
}