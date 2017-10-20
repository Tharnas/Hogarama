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

  # Create apps
  # Build config creation for all Hogarama openshift builds
  oc new-app -f templates/hogarama-build-configs.yml \
    -p "JENKINS_GIT_REPO_URL=${GIT_URL}" \
    -p "JENKINS_GIT_REPO_REF=${GIT_REF}" \
    -p "FLUENTD_GIT_REPO_URL=${GIT_URL}" \
    -p "FLUENTD_GIT_REPO_REF=${GIT_REF}"
  # Nexus app which is the maven mirror
  oc new-app -f ${SCRIPT_DIR}/templates/hogarama-nexus.yml \
    -p "SERVICE_NAME=nexus"
  # Pipeline for the Hogajama build
  oc new-app -f ${SCRIPT_DIR}/templates/hogarama-jenkins-pipeline-git.yml \
    -p "APP_NAME=hogajama" \
    -p "GIT_REPO=${GIT_URL}" \
    -p "GIT_REF=${GIT_REF}" \
    -p "JENKINS_FILE_PATH=Hogajama/build/Jenkinsfile" \
    -p "MAVEN_MIRROR_URL=http://nexus:8081/repository/maven-public/"
  # Pipeline for the mqtt-java-client build
  oc new-app -f ${SCRIPT_DIR}/templates/hogarama-jenkins-pipeline-git.yml \
    -p "APP_NAME=mqtt-java-lcient" \
    -p "GIT_REPO=${GIT_URL}" \
    -p "GIT_REF=${GIT_REF}" \
    -p "JENKINS_FILE_PATH=mqtt-client-java/build/Jenkinsfile" \
    -p "MAVEN_MIRROR_URL=http://nexus:8081/repository/maven-public/"
  # Pipeline for the Fluentd build
  oc new-app -f ${SCRIPT_DIR}/templates/hogarama-jenkins-pipeline-git.yml \
    -p "APP_NAME=fluentd" \
    -p "GIT_REPO=${GIT_URL}" \
    -p "GIT_REF=${GIT_REF}" \
    -p "JENKINS_FILE_PATH=Fluentd/build/Jenkinsfile" \
    -p "MAVEN_MIRROR_URL=http://nexus:8081/repository/maven-public/"
  # Pipeline for executing all builds parallel
  oc new-app -f ${SCRIPT_DIR}/templates/hogarama-jenkins-pipeline-git.yml \
    -p "APP_NAME=all-parallel" \
    -p "GIT_REPO=${GIT_URL}" \
    -p "GIT_REF=${GIT_REF}" \
    -p "JENKINS_FILE_PATH=buildserver/Jenkinsfile" \
    -p "MAVEN_MIRROR_URL=http://nexus:8081/repository/maven-public/"
  # Pipeline for jenkins build
  oc new-app -f ${SCRIPT_DIR}/templates/hogarama-jenkins-pipeline-git.yml \
    -p "APP_NAME=jenkins" \
    -p "GIT_REPO=${GIT_URL}" \
    -p "GIT_REF=${GIT_REF}" \
    -p "JENKINS_FILE_PATH=Jenkins/build/Jenkinsfile" \
    -p "MAVEN_MIRROR_URL=http://nexus:8081/repository/maven-public/"

  oc logout
}

PROJECT_ID=${2:-"hogarama-buildserver"}
NAMESPACE='gepardec'
GIT_URL="https://github.com/Gepardec/Hogarama.git"
GIT_REF="OPENSHIFT_JENKINS_PIPELINE"

${1}
