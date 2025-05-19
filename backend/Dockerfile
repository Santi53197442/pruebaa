# Etapa de build
FROM maven:3.8.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa de producción
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/buses-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
