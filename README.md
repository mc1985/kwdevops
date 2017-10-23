# kwdevops
# README
#
# Requirements:
 1. vagrant plugin install vagrant-hostmanager
 2. vagrant up
 3. vagrant ssh <node name> example:jenkins

# Bring up jenkins
 1. vagrant ssh jenkins
 2. sudo /apps/start_jenkins.sh
 3. docker logs <container id> find the admin password

# Setup jenkins
 1. Jenkins URL http://192.168.135.10:8080/login?from=%2F
 2. Install recommended plugins
 3. Mange jenkins plugin - Install Jenkins DSL
 4. Restart Jenkins
 5. Create Freestyle seed job https://github.com/mc1985/kwdevops

# Start Artifactory
 1. Jenkins Job -> DevOps -> create Artifactory
 2. http://192.168.135.111:8081/artifactory/
