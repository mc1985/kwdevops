folder('Pipeline')
folder('Deploy')
folder('Build')
folder('DevOps')
job('DevOps/Create Artifactory') {
    steps {
        description('Starts Artifactory docker container')
        shell('ssh -i "/home/vagrant/.ssh/id_rsa" -oStrictHostKeyChecking=no vagrant@192.168.135.111 "sudo /apps/start_artifactory.sh"')
    }
}


job('Deploy/Deploy Ruby App') {
    steps {
        description('Deploys Ruby App to Application Server 1')
        shell('')
    }
}


job('Deploy/Deploy Node.js App') {
    steps {
        description('Deploys Node.js App to Applications Server 2')
        shell('')
    }
}

pipelineJob('Pipeline/DSL_Pipeline') {

  def repo = 'https://github.com/path/to/your/repo.git'

  triggers {
    scm('H/5 * * * *')
  }
  description("Pipeline for $repo")

  definition {
    cpsScm {
      scm {
        git {
          remote { url(repo) }
          branches('master', '**/feature*')
          scriptPath('misc/Jenkinsfile')
          extensions { }  // required as otherwise it may try to tag the repo, which you may not want
        }

      }
    }
  }
}

job("DevOps/parameter job") {
	scm {
		git {
			remote {
			url 'https://github.com/path/to/your/repo.git'
		}
		branch '$branch'
		}
	}
 	publishers {
	  downstream '2-Demo build job' , 'SUCCESS'
	}
   parameters {
     choiceParam('Branch', ['master (default)', 'test', 'efix'], 'Select Branch to build from')
     }
}
