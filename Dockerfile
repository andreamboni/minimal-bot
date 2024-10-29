# Usando a imagem base do JDK 17
FROM openjdk:17

# Configurando o diretório de trabalho
WORKDIR /app

# Copiando o arquivo JAR do projeto para dentro do contêiner
COPY target/minimal-not-a-bot-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta em que o Spring Boot está configurado para rodar (por padrão é 8080)
EXPOSE 8080

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
