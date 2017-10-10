## -*- mode: ruby -*-
# vi: set ft=ruby :
#Maintained by: Matt Cole & Jeremy Carson
# README
#
# Requirements:
# 1. vagrant plugin install vagrant-hostmanager
# 2. vagrant up
# 3. vagrant ssh <node name>
#
Vagrant.configure(2) do |config|
  config.hostmanager.enabled = true
  config.vm.provider "virtualbox" do |v|
    v.memory = 2048
    v.cpus = 2
end
  # IF you want to sync folder from host to guest
  #config.vm.synced_folder ".", "/vagrant", type: "virtualbox"
  #config.vm.synced_folder "./", "/vagrant", id: "vagrant-root", type: "nfs"
  config.vm.box = "centos/7"
  config.vm.define "jenkins", primary: true do |h|
    h.vm.box_check_update = false
    h.vm.synced_folder "./", "/vagrant", id: "vagrant-root", type: "nfs"
    h.vm.network "private_network", ip: "192.168.135.10"
    h.vm.provision "file", source: "." , destination: "/tmp"
    h.vm.provision "shell" do |s|
      s.inline = "mkdir /apps; chmod 755 /apps; cp /tmp/jenkins/* /apps; chmod -R 755 /apps"
      s.privileged = true
    end
    h.vm.provision :shell, :inline => <<'EOF'
if [ ! -f "/home/vagrant/.ssh/id_rsa" ]; then
  ssh-keygen -t rsa -b 4096 -N "" -f /home/vagrant/.ssh/id_rsa
fi

cp /home/vagrant/.ssh/id_rsa.pub /vagrant/control.pub
chown vagrant:vagrant /vagrant/control.pub
chmod 777 /vagrant/control.pub
cat << 'SSHEOF' > /home/vagrant/.ssh/config
Host *
  StrictHostKeyChecking no
  UserKnownHostsFile=/dev/null
SSHEOF

chown -R vagrant:vagrant /home/vagrant/.ssh/

EOF
  end
  config.vm.define "artifactory" do |h|
    h.vm.box_check_update = false
    h.vm.synced_folder "./", "/vagrant", id: "vagrant-root", type: "nfs"
    h.vm.network "private_network", ip: "192.168.135.111"
    h.vm.provision "file", source: "./artifactory" , destination: "/tmp/artifactory"
    h.vm.provision "shell" do |s|
      s.inline = "mkdir /apps; chmod 755 /apps; cp /tmp/artifactory/* /apps; chmod -R 755 /apps"
      s.privileged = true
    end
    h.vm.provision :shell, inline: 'cat /vagrant/control.pub >> /home/vagrant/.ssh/authorized_keys'
    h.vm.provision "shell", inline: <<-SHELL
       yum -y install epel-release
  	 yum -y install ansible
     SHELL

     h.vm.provision "file", source: "./playbook.yml", destination: "~/playbook.yml"

    h.vm.provision "ansible_local" do |ansible|
  	ansible.provisioning_path = "/home/vagrant"
      ansible.playbook = "playbook.yml"
    end
  end
   config.vm.provision "file", source: "./playbook.yml", destination: "~/playbook.yml"

   config.vm.provision "ansible_local" do |ansible|
	ansible.provisioning_path = "/home/vagrant"
    ansible.playbook = "playbook.yml"
   end
end
