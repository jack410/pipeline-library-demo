def call(String name, String module, String merge, String cid) {
    switch(name) {
                case "test": 
                git branch: "master", credentialsId: 'xsio', url: "git@github.com:xsio/${module}.git"
                    break; 
                case "validation": 
                echo "env is ${name}."
                echo "module is ${module}"
                echo "merge is ${merge}"
                echo "cid is ${cid}"    
                git branch: "master", credentialsId: 'xsio', url: "git@github.com:xsio/${module}.git"
                git branch: "validation", credentialsId: 'xsio', url: "git@github.com:xsio/${module}.git"
                sh 'git branch'
                if (cid) {
                    sh '''
                    #!/bin/bash
                    echo $cid
                    arr=$(echo $cid|tr "," "\n")
                    for x in $arr; do
                    git cherry-pick --strategy=recursive -X theirs $x 
                    done
                    git push
                    '''
                    } else if (merge == "yes") {
                        sh '''
                        git merge -X theirs master
                        git push
                        '''
                    } else if (merge == 'no') {
                        println("No commit message.");
                        break;
                    }
                    break; 
                case "prod": 
                git branch: "master", credentialsId: 'xsio', url: "git@github.com:xsio/${MODULE}.git"
                git branch: "validation", credentialsId: 'xsio', url: "git@github.com:xsio/${MODULE}.git"
                git branch: "release", credentialsId: 'xsio', url: "git@github.com:xsio/${MODULE}.git"
                if (cid) {
                    sh '''
                    #!/bin/bash
                    echo $cid
                    arr=$(echo $cid|tr "," "\n")
                    for x in $arr; do
                    git cherry-pick --strategy=recursive -X theirs $x 
                    done
                    git push
                    '''
                    } else if (merge == "yes") {
                        sh '''
                        git merge -X theirs validation
                        git push
                        '''
                    } else (merge == 'no') {
                        println("No commit message.");
                    }
                    break; 
                default: 
                    println("The value is unknown"); 
                    break; 
                }
}