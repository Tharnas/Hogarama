#!/bin/bash
#--context-dir="2/test/s2i"
SCRIPT_DIR=$(dirname ${0})
JENKINS_S2I_ROOT=${SCRIPT_DIR}/jenkins-s2i

function clear() {
  oc login --username ${USERNAME} --password ${PASSWORD}
  oc delete project ${PROJECT_ID}
  oc logout
}

function create_pipeline_git() {
  oc login --username ${USERNAME} --password ${PASSWORD}
  oc new-project ${PROJECT_ID}
  #oc create -f ${SCRIPT_DIR}/templates/pvc.yaml
  #oc new-app -f ${SCRIPT_DIR}/templates/hogarama-jenkins.yml \
  #  -p "JENKINS_SERVICE_HOST=hogarama-jenkins" \
  #  -p "JNLP_SERVICE_NAME=hogarama-jenkins-jnlp"
  oc new-app -f ${SCRIPT_DIR}/templates/hogarama-jenkins-pipeline-git.yml \
    -p "APP_NAME=hogajama" \
    -p "GIT_REPO=https://github.com/Gepardec/Hogarama.git" \
    -p "GIT_REF=OPENSHIFT_JENKINS_PIPELINE" \
    -p "JENKINS_FILE_PATH=Hogajama/Jenkinsfile"

  oc logout
}

function restore() {
  clear && create
}

USERNAME=${2:-"developer"}
PASSWORD=${3:-"developer"}
PROJECT_ID=${4:-"hogarama-buildserver"}

${1}
