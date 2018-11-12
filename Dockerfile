FROM java:8-jre-alpine
# FROM openjdk:8-jre

COPY target/scala-2.12/soi-assembly-0.1.jar soi-assembly-0.1.jar

EXPOSE 9000

CMD ["java", "-jar", "soi-assembly-0.1.jar"]
