FROM amazoncorretto:11.0.8-alpine
VOLUME /app
ADD /target/2022-05-spring-Kozlovskii-dz5-34-1.0.jar application.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","application.jar"]