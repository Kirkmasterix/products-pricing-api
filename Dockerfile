FROM gradle:8.5.0-jdk21 AS build

WORKDIR /home/gradle/project

COPY build.gradle settings.gradle gradle.properties* ./
COPY gradle ./gradle
RUN gradle dependencies --no-daemon || true

COPY . .
RUN gradle clean build -x test --no-daemon

# 👇 CAMBIO CLAVE
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /home/gradle/project/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]