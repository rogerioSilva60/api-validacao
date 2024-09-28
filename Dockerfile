# Usar uma imagem base do OpenJDK
FROM openjdk
# Definir o diretório de trabalho
WORKDIR /app
COPY /target/validacao-1.0.0.jar /app/validacao-app.jar
# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "validacao-app.jar"]
