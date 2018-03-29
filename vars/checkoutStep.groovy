def call(String env, String module, version = '') {
	// if (env == 'test') {
 //            BRANCH = 'master'
 //        } 
 //        else if (env == 'validation') {
 //            BRANCH = 'validation'
 //        }
 //        else if (env == 'prod') {
 //            BRANCH = 'release'
 //        }
 	def env2branch = [test: 'master', validation: 'validation', prod: 'release']

 	def branch = env2branch[env] ?: 'master'

	git branch: "${branch}", credentialsId: 'xsio', url: "git@github.com:xsio/${module}.git"
	commitId = sh returnStdout: true, script: 'git rev-parse HEAD'
    commitId = commitId.trim()
    IMAGE = "${env.REGISTRY_SNAPSHOT}/${IMAGE_PATH}:${commitId}"


    // switch(env) {
    // 	case "test":
    // 	echo "env is ${env}."
    //     echo "module is ${module}"
    // 	git branch: "master", credentialsId: 'xsio', url: "git@github.com:xsio/${module}.git"
    //  //    commitId = sh returnStdout: true, script: 'git rev-parse HEAD'
    //  //    commitId = commitId.trim()
    //     // IMAGE = "${env.REGISTRY_SNAPSHOT}/${IMAGE_PATH}:${commitId}"
    //     break;
    //     case "validation":
    //     git branch: "validation", credentialsId: 'xsio', url: "git@github.com:xsio/${MODULE}.git"
    //     commitId = sh returnStdout: true, script: 'git rev-parse HEAD'
    //     commitId = commitId.trim()
    //     // IMAGE = "${env.REGISTRY_SNAPSHOT}/${IMAGE_PATH}:${commitId}"
    //     break;
    //     case "prod":
    //     git branch: "release", credentialsId: 'xsio', url: "git@github.com:xsio/${MODULE}.git"
    //     commitId = sh returnStdout: true, script: 'git rev-parse HEAD'
    //     commitId = commitId.trim()
    //     // IMAGE = "${env.REGISTRY_SNAPSHOT}/${IMAGE_PATH}:${commitId}"
    //     break;
    }
}