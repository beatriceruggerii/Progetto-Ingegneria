# Usa un'immagine di base con OpenJDK per eseguire applicazioni Java
FROM openjdk:17-jdk-slim

# Crea una directory per l'applicazione all'interno del container
WORKDIR /app

# Copia il file JAR generato dalla build Spring Boot nel container
COPY target/OmiStories-0.0.1.jar app.jar

# Esponi la porta 8080 per consentire l'accesso all'applicazione
EXPOSE 8080

# Comando per avviare l'applicazione Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
