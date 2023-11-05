      ___     ___   __   __   ___      ___    ___   
     |   \   | __|  \ \ / /  / _ \    | _ \  / __|  
     | |) |  | _|    \ V /  | (_) |   |  _/  \__ \  
     |___/   |___|   _\_/_   \___/   _|_|_   |___/  

# DevOps Project
This is the devops project and will contain all automation related to CI Architecture. 


Folder Structure
 - resources - Jenkins Library Resources (External libraries may load adjunct files from a resources/ directory using the libraryResource step)
 - vars - Jenkins Libarary Scripts (Only entire pipelines can be defined in shared libraries as of this time. This can only be done in vars/*.groovy, and only in a call method. Only one Declarative Pipeline can be executed in a single build, and if you attempt to execute a second one, your build will fail as a result.)
 - training - Include some groovy templates to implementing ci/cd flow with Groovy script.

# What you'll learn
- Understand the basics of the Jenkins architecture.
- Understand the concept of the Job DSL Plugin on Jenkins and its features.
- Understand with Shared Libraries and Plug-ins
- Implement CICD Pipelines With Jenkins Groovy script
- Understand the basic scenario CICD flow (Build -> Run Testing -> Build Images -> Sonar analysis -> Quality Gateway -> Push Images -> Deploy)

# Prerequisite
- Working knowledge of Jenkins
- Basic knowledge of automation and the CI-CD strategy
- Basic knowledge of Docker and K8S

# Requirement
- Jenkins Server has
	- installed some
		- Plugins:
			- Jenkins suggested
 			- Docker PipelineVersion
			- xUnit
			- Cobertura
			- Code Coverage API
			- Pipeline Utility Steps
			- Kubernetes
		- Tools:
			- kubectl cli
			- docker
	- added credentials
		- GitHub with Kind Username with password (ID name: github)
		- ACR with Kind Username with password (ID name: acr-demo-token)
		- SonarQube WebHook with Kind Secret text (ID name: sonarwh)
		- SonarQube Token with Kind Secret text (ID name: sonar-token)
		- kubeconig with Kind Secret file (ID name: config)
	- manage Jenkins -> System
   		- GitHub Enterprise Servers
			- API endpoint: https://api.github.com
			- Name: devops
		- Global Pipeline Libraries
			- Name: devops-jenkins-ci
			- Default version: jenkins
			- [x] Allow default version to be overridden
			- [x] Include @Library changes in job recent changes 
		- Retrieval method: Modern SCM
			- Source Code Management: Git
				- Project Repository: https://github.com/nashtech-garage/devops-ci-cd.git
				- Credentials: github
- SonarQube Server
  	- Generate Tokens
  	- Create Webhook: Administration -> Configuration -> Webhooks -> Create
  	  	- Name: Jenkins
  	  	- URL: 	http://<Jenkinserver>/sonarqube-webhook/
  	  	- [x] Secret
- ACR
  	- Admin user: Enable
- MSSQL
- AKS
	- Attach an ACR to an AKS cluster:
   		- az aks update -n myAKSCluster -g myResourceGroup --attach-acr <acr-name>
- Repositories:
	- https://github.com/nashtech-garage/dotnet-bookstore-api/tree/jenkins


# Refer
- https://www.jenkins.io/doc/book/pipeline/shared-libraries/
- https://learn.microsoft.com/en-us/azure/aks/cluster-container-registry-integration?tabs=azure-cli
- https://www.jenkins.io/doc/book/pipeline/syntax/
- https://www.eficode.com/blog/jenkins-groovy-tutorial
