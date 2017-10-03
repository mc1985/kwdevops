#!/bin/bash
cp /home/vagrant/.ssh/id_rsa .
docker build -t jenkins:lab .
docker-compose up -d
