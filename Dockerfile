FROM openjdk:12-jdk-alpine
VOLUME /tmp
COPY --from=build /libs/assistant_vaadin-0.0.1-SNAPSHOT.jar demo.jar
ENTRYPOINT ["java","-jar","demo.jar"]