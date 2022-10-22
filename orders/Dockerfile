FROM bitnami/java:latest
COPY target/order.jar order.jar
EXPOSE 8080/tcp
ENTRYPOINT ["java", "-jar", "order.jar"]
