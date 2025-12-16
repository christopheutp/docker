
# Étape 1 : image de build avec Maven + JDK 17
FROM maven:3.9.9-eclipse-temurin-17 AS builder

# Dossier de travail dans le conteneur
WORKDIR /app

# Copie uniquement du pom.xml
COPY pom.xml .

RUN mvn -q -DskipTests dependency:go-offline

COPY src ./src

RUN mvn -q -DskipTests package

# Étape 2 : image finale plus légère (runtime uniquement)
FROM eclipse-temurin:17-jre

WORKDIR /app

#COPY --from=builder /app/target/blog-0.0.1-SNAPSHOT.jar app.jar
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java","-jar","app.jar"]


# https://hub.docker.com/r/christopheutp/exo5