FROM bitnami/java:latest
COPY target/client.jar client.jar
EXPOSE 8080/tcp
ENTRYPOINT ["java", "-jar", "client.jar"]
