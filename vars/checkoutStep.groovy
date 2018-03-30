def call(String env, String module, version = '') {

		def repo = "nexus-snapshot.xsio.cn"
		def imagePath = "${env}/${module}-${module}"

        def env2branch = [test: 'master', validation: 'validation', prod: 'release']

        def branch = env2branch[env] ?: 'master'
        git branch: "${branch}", credentialsId: 'xsio', url: "git@github.com:xsio/${module}.git"
        commitId = sh returnStdout: true, script: 'git rev-parse HEAD'
        commitId = commitId.trim()
        echo commitId
        if (version) {
        	commitId = version
        	repo = "nexus-release.xsio.cn"
        }
        IMAGE = "${repo}/${imagePath}:${commitId}"
}