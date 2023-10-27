FROM adoptopenjdk/openjdk17:alpine-jre
COPY target/app.jar /app.jar
EXPOSE 7008
ENTRYPOINT ["java", "-jar", "/app.jar"]