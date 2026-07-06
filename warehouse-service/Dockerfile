FROM amazoncorretto:17-alpine
USER root
ARG VERSION
WORKDIR /app
COPY build/libs/warehouse-service-$VERSION.jar warehouse-service.jar
CMD ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "-jar", "warehouse-service.jar"]
EXPOSE 8081
