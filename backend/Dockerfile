FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY target/buses-0.0.1-SNAPSHOT.jar /app/buses.jar

ENV JAVA_OPTS="-Xms400m -Xmx400m"

CMD ["sh", "-c", "java $JAVA_OPTS -jar /app/buses.jar"]
