FROM eclipse-temurin:17-jdk-focal

WORKDIR /app

MAINTAINER be
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

COPY . .
RUN ./mvnw package -DskipTests

#COPY --from=build /build/target/*.jar be-0.0.2-SNAPSHOT.jar
COPY ./target/be-0.0.2-SNAPSHOT.jar /app
ENTRYPOINT ["java", "-jar", "/app/be-0.0.2-SNAPSHOT.jar"]
