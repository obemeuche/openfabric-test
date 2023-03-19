FROM openjdk:8
ADD build/libs/*.jar openfabric-test.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "openfabric-test.jar"]
