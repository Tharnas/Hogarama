#!/bin/bash
#--context-dir="2/test/s2i"
SCRIPT_DIR=$(dirname ${0})
JENKINS_S2I_ROOT=${SCRIPT_DIR}/jenkins-s2i

function clear() {
  oc login --username ${USERNAME} --password ${PASSWORD}
  oc delete project ${PROJECT_ID}
  oc logout
}

function create() {
  oc login --username ${USERNAME} --password ${PASSWORD}
  oc new-project ${PROJECT_ID}
  oc new-app -f ${SCRIPT_DIR}/templates/hogarama-jenkins.yml \
    -p "JENKINS_SERVICE_NAME=hogarama-jenkins" \
    -p "JNLP_SERVICE_NAME=hogarama-jenkins-jnlp"
  oc logout
}

function restore() {
  clear && create
}

USERNAME=${2:-"developer"}
PASSWORD=${3:-"developer"}
PROJECT_ID=${4:-"hogarama-buildserver"}

${1}
