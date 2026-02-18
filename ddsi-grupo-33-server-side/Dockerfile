# Etapa 1: Construcción (Build)
FROM maven:3.9.6-amazoncorretto-17 AS build
COPY . .
# Saltamos tests para evitar errores de compilación por dependencias externas
RUN mvn clean package -Dmaven.test.skip=true

# Etapa 2: Ejecución
FROM amazoncorretto:17-al2-jdk
COPY --from=build /target/*.jar app.jar

# El front suele usar el 8081 para no chocar con el 8080 del back
EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/app.jar"]