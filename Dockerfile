FROM eclipse-temurin:17-jre-alpine 
WORKDIR /repositorio_1
COPY target/*.jar Repositorio_1.jar 
ENTRYPOINT ["java","-jar","app.jar"]