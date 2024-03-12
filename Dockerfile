FROM openjdk:8

MAINTAINER be
# RUN mvn -f /app/pom.xml clean package

COPY ./target/be-0.3.0-SNAPSHOT.jar be-0.3.0-SNAPSHOT.jar
CMD ["java","-jar","be-0.3.0-SNAPSHOT.jar"]