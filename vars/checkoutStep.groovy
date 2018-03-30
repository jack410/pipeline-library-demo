def call(String env, String module, version = '') {

		def repo = "nexus-snapshot.xsio.cn"
		def imagePath = "${env}/${module}-${module}"
		def env2branch = [test: 'master', validation: 'validation', prod: 'release']
        def branch = env2branch[env] ?: 'master'
        def JAR = "${module}-latest.jar"
        
    node {
    	stage ('checkout') {

	        git branch: "${branch}", credentialsId: 'xsio', url: "git@github.com:xsio/${module}.git"
	        commitId = sh returnStdout: true, script: 'git rev-parse HEAD'
	        commitId = commitId.trim()
	        if (version) {
	        	commitId = version
	        	repo = "nexus-release.xsio.cn"
	        }
	        IMAGE = "${repo}/${imagePath}:${commitId}"
	        echo IMAGE
	    }
	    stage ('build'){
           sh """
                #bypass jenkins $HOME bug
                export HOME=/opt/hudson
                . ${HOME}/.bashrc
                ./gradlew clean
                ./gradlew bootRepackage -Dgrails.env=$env
            """
        }
        stage ('package'){
            sh """
                export HOME=/opt/hudson
                cp -r ../jacoco .
                docker build --build-arg jar=${JAR} -t ${IMAGE} .
                docker push ${IMAGE}
                docker rmi ${IMAGE} || echo 
            """
        }
        stage ('deploy'){
            build job: "deploy_${module}_${env}", parameters: [string(name: 'TAG', value: commitId), string(name: 'IMAGE_PATH', value: imagePath)]
        }

    }    

}