FROM mcr.microsoft.com/java/jre:8-zulu-alpine

COPY /target/migrator-1.0-SNAPSHOT-jar-with-dependencies.jar /app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]