FROM eclipse-temurin:17-jre

WORKDIR /app

COPY build/libs/*.jar app.jar

EXPOSE 80

ENTRYPOINT ["java", "-jar", "app.jar"]
