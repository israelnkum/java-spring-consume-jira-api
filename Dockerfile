FROM openjdk:8-jdk-alpine
RUN apk add --no-cache curl tar bash procps

# Downloading and installing Maven
ARG MAVEN_VERSION=3.6.3
ARG USER_HOME_DIR="/root"

ARG BASE_URL=https://mirrors.estointernet.in/apache/maven/maven-3/${MAVEN_VERSION}/binaries
# ARG BASE_URL=https://apache.osuosl.org/maven/maven-3/${MAVEN_VERSION}/binaries


RUN mkdir -p /usr/share/maven /usr/share/maven/ref \
 && curl -fsSL -o /tmp/apache-maven.tar.gz ${BASE_URL}/apache-maven-${MAVEN_VERSION}-bin.tar.gz \
 && tar -xzf /tmp/apache-maven.tar.gz -C /usr/share/maven --strip-components=1 \
 && rm -f /tmp/apache-maven.tar.gz \
 && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"

WORKDIR /app

COPY . .
RUN mvn clean install
#COPY /target/docker-java-app-example.jar /app

EXPOSE 4200

CMD ["java", "-jar", "target/amalitech-project-dashboard-0.0.1-SNAPSHOT.jar"]