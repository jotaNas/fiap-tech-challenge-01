# ==== Build stage =====
FROM maven:3.9.8-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew clean bootJar -x test --no-daemon


# ==== Run stage =====
FROM eclipse-temurin:21-jre
WORKDIR /app
RUN groupadd -r appuser && useradd -r -g appuser appuser
COPY --from=build /app/build/libs/*.jar app.jar
RUN chown appuser:appuser app.jar
USER appuser
EXPOSE 8080
CMD ["java", "-Xmx400m", "-jar", "app.jar", "--spring.profiles.active=prod"]