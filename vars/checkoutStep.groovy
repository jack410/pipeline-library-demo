def call(String env, String module) {
	if (ENV == 'test') {
            BRANCH = 'master'
        } 
        else if (ENV == 'validation') {
            BRANCH = 'validation'
        }
        else if (ENV == 'prod') {
            BRANCH = 'release'
        }
    switch(env) {
    	case "test":
    	git branch: "${BRANCH}", credentialsId: 'xsio', url: "git@github.com:xsio/${MODULE}.git"
        commitId = sh returnStdout: true, script: 'git rev-parse HEAD'
        commitId = commitId.trim()
        IMAGE = "${env.REGISTRY_SNAPSHOT}/${IMAGE_PATH}:${commitId}"
        break;
        case "validation":
        git branch: "${BRANCH}", credentialsId: 'xsio', url: "git@github.com:xsio/${MODULE}.git"
        commitId = sh returnStdout: true, script: 'git rev-parse HEAD'
        commitId = commitId.trim()
        IMAGE = "${env.REGISTRY_SNAPSHOT}/${IMAGE_PATH}:${commitId}"
        break;
        case "prod":
        git branch: "${BRANCH}", credentialsId: 'xsio', url: "git@github.com:xsio/${MODULE}.git"
        commitId = sh returnStdout: true, script: 'git rev-parse HEAD'
        commitId = commitId.trim()
        IMAGE = "${env.REGISTRY_SNAPSHOT}/${IMAGE_PATH}:${commitId}"
        break;
    }
}