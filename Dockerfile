# Usando a imagem base do JDK 17
FROM maven:3.9.9-eclipse-temurin-17

# Configurando o diretório de trabalho
WORKDIR /app

# Copiando o arquivo JAR do projeto para dentro do contêiner
# COPY target/minimal-not-a-bot-0.0.1-SNAPSHOT.jar app.jar
COPY src /app/src
COPY pom.xml /app

RUN mvn clean package -DskipTests

# Comando para executar a aplicação
# ENTRYPOINT ["java", "-jar", "app.jar"]
CMD [ "java", "-jar", "target/minimal-not-a-bot-0.0.1-SNAPSHOT.jar" ]
