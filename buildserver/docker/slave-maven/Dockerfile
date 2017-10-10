FROM gdhet/hogarama-jenkins-slave-oracle-jdk-8-rhel7:jdk-8u144b01

# Labels consumed by Red Hat build service
LABEL com.redhat.component="hogarama-jenkins-slave-maven-rhel7-docker" \
     name="gdhet/jenkins-slave-maven-oracle-java-rhel7" \
     version="3.6" \
     architecture="x86_64" \
     release="1" \
     io.k8s.display-name="Jenkins Slave Maven and Oracle Java" \
     io.k8s.description="The jenkins slave maven image has the maven tools on top of the jenkins slave base image." \
     io.openshift.tags="openshift,jenkins,slave,maven"

     # Build arguments
 ARG VERSION_MAVEN
 ARG HOME_MAVEN=/maven-${VERSION_MAVEN}

 # Environment variables
 ENV MAVEN_VERSION=${VERSION_MAVEN} \
     MAVEN_HOME=${HOME_MAVEN} \
     MVN_HOME=${HOME_MAVEN} \
     M2_HOME=/.m2

 # Make directories
 RUN mkdir -p ${MAVEN_HOME} \
     mkdir -p ${M2_HOME}/repository

 # Download and install maven3
 RUN curl -L http://mirrors.ibiblio.org/apache/maven/maven-3/${VERSION_MAVEN}/binaries/apache-maven-${VERSION_MAVEN}-bin.tar.gz \
     | tar xz --strip-components=1 -C ${MAVEN_HOME}

 # Create links
 RUN ln -s ${MAVEN_HOME}/bin/mvn /usr/bin/mvn \
     && ln -s ${MAVEN_HOME}/bin/mvnDebug /usr/bin/mvnDebug

 # Add default settigns.xml
 COPY maven/settings.xml ${M2_HOME}/settings.xml

 # Change permissions for maven directory
 RUN chmod -R 777 ${M2_HOME}
