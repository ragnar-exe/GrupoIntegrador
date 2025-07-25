# Usa una imagen base de Java
FROM eclipse-temurin:17-jdk

# Crea un directorio para la app
WORKDIR /app

# Copia el archivo JAR
COPY target/mi-proyecto.jar app.jar

# Expone el puerto
EXPOSE 8080

# Ejecuta la app
CMD ["java", "-jar", "app.jar"]
