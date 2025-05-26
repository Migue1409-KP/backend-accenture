FROM gradle:8.6-jdk21 AS build
WORKDIR /app

COPY build.gradle settings.gradle gradle.* ./
COPY gradle ./gradle
RUN gradle --no-daemon dependencies

COPY src ./src
RUN gradle --no-daemon bootJar

FROM eclipse-temurin:21-jre-alpine
WORKDIR /opt/app

COPY --from=build /app/build/libs/*.jar app.jar

# Exponemos el puerto de la aplicación
EXPOSE 8080

# Comando por defecto
ENTRYPOINT ["java","-jar","app.jar"]
