#!/bin/bash
# Source environment variables
source .env

function build(){
  # Build oracle image
  docker build \
    --build-arg VERSION_JAVA_MAJOR=${VERSION_JAVA_MAJOR} \
    --build-arg VERSION_JAVA_MINOR=${VERSION_JAVA_MINOR} \
    --build-arg VERSION_JAVA_BUILD=${VERSION_JAVA_BUILD} \
    --build-arg JAVA_ORACLE_HASH=${JAVA_ORACLE_HASH} \
    --tag ${ORACLE_TAG}:${TAG_VERSION_ORACLE} \
    --tag ${ORACLE_TAG_HUB}:${TAG_VERSION_ORACLE} \
    slave-oracle-jdk-8/

  # Build maven image
  docker build \
    --build-arg VERSION_MAVEN=${VERSION_MAVEN} \
    --tag ${MAVEN_TAG}:${TAG_VERSION_MAVEN} \
    --tag ${MAVEN_TAG_HUB}:${TAG_VERSION_MAVEN} \
    --tag ${MAVEN_TAG_HUB}:latest  \
    slave-maven/

    # Build s2i images
    s2i --loglevel 5 --ref master build https://github.com/Gepardec/Hogarama openshift/jenkins-2-centos7 ${JENKINS_TAG}
    docker tag  ${JENKINS_TAG} ${JENKINS_TAG_HUB}
}

function push(){
  # Login to docker hub
  docker login -u gdhet -p ${1} ${REGISTRY_HUB}

  # Push images to docker hub
  docker push ${ORACLE_TAG_HUB}:${TAG_VERSION_ORACLE}
  docker push ${MAVEN_TAG_HUB}:${TAG_VERSION_MAVEN}
  docker push ${MAVEN_TAG_HUB}:latest
  docker push ${JENKINS_TAG_HUB}:latest
  # Logout of docker hub
  docker logout ${REGISTRY_HUB}
}

if ! [ -z "${2}" ];
then
  ${2} ${1}
fi

if ! [ -z "${3}" ];
then
  ${3} ${1}
fi
