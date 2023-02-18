FROM amazoncorretto:17-alpine
VOLUME /tmp
COPY build/libs/Java-RESTful-API-0.0.1.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
