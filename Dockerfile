FROM openjdk:12-jdk-alpine
COPY . .
COPY --from=build /libs/demo-0.0.1-SNAPSHOT.jar demo.jar
ENTRYPOINT ["java","-jar","demo.jar"]