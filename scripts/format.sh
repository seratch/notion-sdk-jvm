#!/bin/bash

project=$1
# ------------------------------------------
if [[ "project" == "" ]]; then
  mvn spotless:apply
else
  mvn spotless:apply -pl "$project"
fi
