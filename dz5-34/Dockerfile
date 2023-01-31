FROM amazoncorretto:11.0.8-alpine
VOLUME /app
ADD /target/2022-05-spring-Kozlovskii-dz5-34-1.0.jar 2022-05-spring-Kozlovskii-dz5-34-1.0.jar
EXPOSE 8580
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/2022-05-spring-Kozlovskii-dz5-34-1.0.jar"]