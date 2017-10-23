FROM jenkins:latest

USER root
RUN apt-get update && apt-get install -y make vim python-dev jq maven ant python-setuptools build-essential libssl-dev libffi-dev figlet python-pip
RUN pip install awscli
ADD id_rsa /home/vagrant/.ssh/
RUN chmod 600 /home/vagrant/.ssh/id_rsa
