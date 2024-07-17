FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install -DskipTests

FROM openjdk:17.0-jdk-slim
WORKDIR /app
COPY --from=build /app/target/your_spring_app.jar your_spring_app.jar
ENTRYPOINT ["java", "-jar", "your_spring_app.jar"]