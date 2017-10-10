FROM openshift/base-centos7:latest

MAINTAINER Thomas Herzog <herzog.thomas81@gmail.com>

# Arguments for java version
ARG VERSION_JAVA_MAJOR
ARG VERSION_JAVA_MINOR
ARG VERSION_JAVA_BUILD
ARG JAVA_ORACLE_HASH

# Enviroment variables
ENV JAVA_VERSION=${VERSION_JAVA_MAJOR}u${VERSION_JAVA_MINOR}-b${VERSION_JAVA_BUILD} \
    JAVA_HOME=/opt/oracle-jdk-${VERSION_JAVA_MAJOR}

# Make directories
RUN mkdir -p ${JAVA_HOME}

# Download and install oracle java
RUN  curl -LH "Cookie: oraclelicense=accept-securebackup-cookie" \
  http://download.oracle.com/otn-pub/java/jdk/${JAVA_VERSION}/${JAVA_ORACLE_HASH}/jdk-${VERSION_JAVA_MAJOR}u${VERSION_JAVA_MINOR}-linux-x64.tar.gz \
  | tar xz --strip-components=1 -C ${JAVA_HOME}

# Change permissions for java directory
RUN chmod -R 0777 ${JAVA_HOME}

# Create symbolic links to usr/bin
RUN ln -s ${JAVA_HOME}/bin/jar /usr/bin/jar \
    && ln -s ${JAVA_HOME}/bin/jarsigner /usr/bin/jarsigner \
    && ln -s ${JAVA_HOME}/bin/java /usr/bin/java \
    && ln -s ${JAVA_HOME}/bin/javac /usr/bin/javac \
    && ln -s ${JAVA_HOME}/bin/javadoc /usr/bin/javadoc \
    && ln -s ${JAVA_HOME}/bin/javafxpackager /usr/bin/javafxpackager \
    && ln -s ${JAVA_HOME}/bin/javah /usr/bin/javah \
    && ln -s ${JAVA_HOME}/bin/javap /usr/bin/javap \
    && ln -s ${JAVA_HOME}/bin/javapackager /usr/bin/javapackager \
    && ln -s ${JAVA_HOME}/bin/java-rmi.cgi /usr/bin/java-rmi.cgi \
    && ln -s ${JAVA_HOME}/bin/javaws /usr/bin/javaws
