FROM maven:3.8.3-openjdk-17 AS maven
WORKDIR /app
COPY pom.xml .
RUN mvn clean package

FROM amazoncorretto:17 AS runtime
WORKDIR /app

COPY --from=maven /app/target/*.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

