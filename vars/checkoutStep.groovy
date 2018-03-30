def call(String env, String module, version = '') {

		def env.REGISTRY_SNAPSHOT = "nexus-snapshot.xsio.cn"
		def IMAGE_PATH = "${env}/${module}-${module}"

        def env2branch = [test: 'master', validation: 'validation', prod: 'release']

        def branch = env2branch[env] ?: 'master'
        git branch: "${branch}", credentialsId: 'xsio', url: "git@github.com:xsio/${module}.git"
        commitId = sh returnStdout: true, script: 'git rev-parse HEAD'
        commitId = commitId.trim()
        echo commitId
        if (version) {
        	commitId = version
        	env.REGISTRY_SNAPSHOT = "nexus-release.xsio.cn"
        }
        IMAGE = "${env.REGISTRY_SNAPSHOT}/${IMAGE_PATH}:${commitId}"
}