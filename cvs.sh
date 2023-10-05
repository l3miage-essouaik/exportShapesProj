#!/bin/bash

mvn clean verify sonar:sonar \
  -Dsonar.projectKey=fr.uga.miage.m1:myproject_g2_5 \
  -Dsonar.host.url=http://im2ag-sonar.u-ga.fr:9000 \
  -Dsonar.login=003360e39aaeced863c8c6e4bec6bdbab15acee8
