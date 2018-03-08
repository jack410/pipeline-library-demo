def call() {
	switch(ENV) {
                case "test": 
                git branch: "master", credentialsId: 'xsio', url: "git@github.com:xsio/${MODULE}.git"
                    break; 
                case "validation": 
                git branch: "master", credentialsId: 'xsio', url: "git@github.com:xsio/${MODULE}.git"
                git branch: "validation", credentialsId: 'xsio', url: "git@github.com:xsio/${MODULE}.git"
                sh 'git branch'
                if (CID) {
                    sh '''
                    #!/bin/bash
                    echo $CID
                    arr=$(echo $CID|tr "," "\n")
                    for x in $arr; do
                    git cherry-pick --strategy=recursive -X theirs $x 
                    done
                    git push
                    '''
                    } else if (MERGE == "yes") {
                        sh '''
                        git merge -X theirs master
                        git push
                        '''
                    } else if (MERGE == 'no') {
                        println("No commit message.");
                        break;
                    }
                    break; 
                case "prod": 
                git branch: "master", credentialsId: 'xsio', url: "git@github.com:xsio/${MODULE}.git"
                git branch: "validation", credentialsId: 'xsio', url: "git@github.com:xsio/${MODULE}.git"
                git branch: "release", credentialsId: 'xsio', url: "git@github.com:xsio/${MODULE}.git"
                if (CID) {
                    sh '''
                    #!/bin/bash
                    echo $CID
                    arr=$(echo $CID|tr "," "\n")
                    for x in $arr; do
                    git cherry-pick --strategy=recursive -X theirs $x 
                    done
                    git push
                    '''
                    } else if (MERGE == "yes") {
                        sh '''
                        git merge -X theirs validation
                        git push
                        '''
                    } else (MERGE == 'no') {
                        println("No commit message.");
                    }
                    break; 
                default: 
                    println("The value is unknown"); 
                    break; 
                }
}