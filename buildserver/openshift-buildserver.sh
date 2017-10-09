#!/bin/bash
#--context-dir="2/test/s2i"
SCRIPT_DIR=$(dirname ${0})
JENKINS_S2I_ROOT=${SCRIPT_DIR}/jenkins-s2i

function clear() {
  oc delete project ${PROJECT_ID}
}

function create() {
  oc login --username ${USERNAME} --password ${PASSWORD}
  oc new-project ${PROJECT_ID}
  oc new-app -f jenkins-pipeline-example
}

function restore() {
  clear && create ${0} ${1}
}

function build_s2i_images(){
  # Build our own jenkins image, because we want to configure jenkins as we need it
  s2i build \
    --context-dir="${JENKINS_S2I_ROOT}" \
    --loglevel=3 \
    --pull-policy=never \
    . \
    openshift3/jenkins-2-rhel7 \
    gepardec/jenkins-2-rhel7-hogarama

  
}

PROJECT_ID=${PROJECT_ID:-"hogarama-buildserver"}
USERNAME=${2:-"developer"}
PASSWORD=${3:-"developer"}

${1}
