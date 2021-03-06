FROM maven:3.5.2-jdk-8-alpine

MAINTAINER https://github.com/HoverEpic

# application placed into /opt/app
RUN mkdir -p /opt/app
WORKDIR /opt/app

COPY pom.xml /opt/app/
COPY src /opt/app/src
RUN mvn package -e

RUN cp /opt/app/target/*.jar /opt/app/src/app.jar

# local application port
EXPOSE 8080

CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=container", "-jar", "/opt/app/src/app.jar"]
