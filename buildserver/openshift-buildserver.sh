#!/bin/bash
#--context-dir="2/test/s2i"
SCRIPT_DIR=$(dirname ${0})
JENKINS_S2I_ROOT=${SCRIPT_DIR}/jenkins-s2i

function clear() {
  oc login --username ${USERNAME} --password ${PASSWORD}
  oc delete project ${PROJECT_ID}
  oc logout
}

function create_jenkins() {
  oc login --username ${USERNAME} --password ${PASSWORD}
  oc new-project ${PROJECT_ID}

  oc new-app -f ${SCRIPT_DIR}/templates/hogarama-jenkins.yml \
    -p "JENKINS_SERVICE_HOST=jenkins-static" \
    -p "JNLP_SERVICE_NAME=jenkins-static-jnlp" \
    -p "GIT_REPO_URL=https://github.com/Gepardec/Hogarama.git" \
    -p "GIT_REPO_REF=OPENSHIFT_JENKINS_PIPELINE" \
    -p "JENKINS_FILE=buildserver/Jenkinsfile" \
    -p "MAVEN_MIRROR_URL=http://hogarama-nexus:8081/repository/maven-public/"

  oc logout
}

function create_pipelines() {
  oc login --username ${USERNAME} --password ${PASSWORD}
  oc new-project ${PROJECT_ID}

  # Setup openshift platform
  #oc create namespace ${NAMESPACE}
  #oc create -f ${SCRIPT_DIR}/templates/hogarama-jenkins.yml -n ${NAMESPACE}

  # Create build configs
  oc create -f templates/hogarama-image-streams.yml -n ${PROJECT_ID}
  oc create -f templates/hogarama-fluentd.yml -n ${PROJECT_ID}

  # Create apps
  oc new-app -f ${SCRIPT_DIR}/templates/hogarama-nexus.yml \
    -p "SERVICE_NAME=nexus"
  oc new-app -f ${SCRIPT_DIR}/templates/hogarama-jenkins-pipeline-git.yml \
    -p "APP_NAME=hogajama" \
    -p "GIT_REPO=https://github.com/Gepardec/Hogarama.git" \
    -p "GIT_REF=OPENSHIFT_JENKINS_PIPELINE" \
    -p "JENKINS_FILE_PATH=Hogajama/build/Jenkinsfile" \
    -p "MAVEN_MIRROR_URL=http://hogarama-nexus:8081/repository/maven-public/"
  oc new-app -f ${SCRIPT_DIR}/templates/hogarama-jenkins-pipeline-git.yml \
    -p "APP_NAME=mqtt-java-lcient" \
    -p "GIT_REPO=https://github.com/Gepardec/Hogarama.git" \
    -p "GIT_REF=OPENSHIFT_JENKINS_PIPELINE" \
    -p "JENKINS_FILE_PATH=mqtt-client-java/build/Jenkinsfile" \
    -p "MAVEN_MIRROR_URL=http://hogarama-nexus:8081/repository/maven-public/"
  oc new-app -f ${SCRIPT_DIR}/templates/hogarama-jenkins-pipeline-git.yml \
    -p "APP_NAME=fluentd" \
    -p "GIT_REPO=https://github.com/Gepardec/Hogarama.git" \
    -p "GIT_REF=OPENSHIFT_JENKINS_PIPELINE" \
    -p "JENKINS_FILE_PATH=Fluentd/build/Jenkinsfile" \
    -p "MAVEN_MIRROR_URL=http://hogarama-nexus:8081/repository/maven-public/"
  oc new-app -f ${SCRIPT_DIR}/templates/hogarama-jenkins-pipeline-git.yml \
    -p "APP_NAME=all-parallel" \
    -p "GIT_REPO=https://github.com/Gepardec/Hogarama.git" \
    -p "GIT_REF=OPENSHIFT_JENKINS_PIPELINE" \
    -p "JENKINS_FILE_PATH=buildserver/Jenkinsfile" \
    -p "MAVEN_MIRROR_URL=http://hogarama-nexus:8081/repository/maven-public/"

  oc logout

  echo \
  " \
  You need to update the following jenkins plugins: \
  OpenShift-*
  Kubernetes
  "
}

function restore() {
  clear && create
}

USERNAME=${2:-"developer"}
PASSWORD=${3:-"developer"}
PROJECT_ID=${4:-"hogarama-buildserver"}
NAMESPACE='gepardec'
${1}
