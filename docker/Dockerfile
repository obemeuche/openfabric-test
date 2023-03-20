FROM openjdk:8
ARG JAR_FILE
COPY ${JAR_FILE} openfabric-test.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/openfabric-test.jar"]
