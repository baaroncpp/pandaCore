FROM maven:3.6.0-jdk-8-alpine AS MAVEN_TOOL_CHAIN

LABEL maintainer="starnapho@gmail.com"
LABEL version="1.0"
LABEL description="USSD UI"

COPY pom.xml /tmp/
COPY src /tmp/src/
WORKDIR /tmp/
RUN mvn package

FROM java:8
COPY --from=MAVEN_TOOL_CHAIN /tmp/target/*.jar app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]