FROM amazoncorretto:17-alpine
USER root
ARG VERSION
WORKDIR /app
COPY build/libs/central-service-$VERSION.jar central-service.jar
CMD ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "-jar", "central-service.jar"]
EXPOSE 8080
