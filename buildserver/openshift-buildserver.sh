#!/bin/bash
#--context-dir="2/test/s2i"
SCRIPT_DIR=$(dirname ${0})
JENKINS_S2I_ROOT=${SCRIPT_DIR}/jenkins-s2i

function clear() {
  oc login --username developer --password developer
  oc delete project ${PROJECT_ID}
  oc logout
}

function create() {
  # Login as admin and add templates
  oc login --username admin --password admin
  oc delete -f templates/image-streams-rhel7-jenkins.json -n openshift # Delete default image streams
  oc create -f templates/image-streams-rhel7-jenkins.json -n openshift # Import maipulated once which uses our jenkins image
  oc logout

  # Login as developer and create project with its apps
  oc login --username developer --password developer
  oc new-project ${PROJECT_ID}

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
    -p "MAVEN_MIRROR_URL=http://nexus:8081/repository/maven-public/"
  oc new-app -f ${SCRIPT_DIR}/templates/hogarama-jenkins-pipeline-git.yml \
    -p "APP_NAME=mqtt-java-lcient" \
    -p "GIT_REPO=https://github.com/Gepardec/Hogarama.git" \
    -p "GIT_REF=OPENSHIFT_JENKINS_PIPELINE" \
    -p "JENKINS_FILE_PATH=mqtt-client-java/build/Jenkinsfile" \
    -p "MAVEN_MIRROR_URL=http://nexus:8081/repository/maven-public/"
  oc new-app -f ${SCRIPT_DIR}/templates/hogarama-jenkins-pipeline-git.yml \
    -p "APP_NAME=fluentd" \
    -p "GIT_REPO=https://github.com/Gepardec/Hogarama.git" \
    -p "GIT_REF=OPENSHIFT_JENKINS_PIPELINE" \
    -p "JENKINS_FILE_PATH=Fluentd/build/Jenkinsfile" \
    -p "MAVEN_MIRROR_URL=http://nexus:8081/repository/maven-public/"
  oc new-app -f ${SCRIPT_DIR}/templates/hogarama-jenkins-pipeline-git.yml \
    -p "APP_NAME=all-parallel" \
    -p "GIT_REPO=https://github.com/Gepardec/Hogarama.git" \
    -p "GIT_REF=OPENSHIFT_JENKINS_PIPELINE" \
    -p "JENKINS_FILE_PATH=buildserver/Jenkinsfile" \
    -p "MAVEN_MIRROR_URL=http://nexus:8081/repository/maven-public/"

  oc logout
}

PROJECT_ID=${2:-"hogarama-buildserver"}
NAMESPACE='gepardec'
${1}
