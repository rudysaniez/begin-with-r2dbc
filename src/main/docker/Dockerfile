FROM adoptopenjdk/openjdk11:alpine-jre as builder
WORKDIR /application
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract \
    && addgroup -S spring-group && adduser -S spring-user -G spring-group
USER spring-user

# the second stage of our build will copy the extracted layers
FROM adoptopenjdk/openjdk11:alpine-jre
WORKDIR /application
COPY src/main/docker/entrypoint.sh /
COPY /src/main/docker/certificates/*.crt /
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./
RUN addgroup -S spring-group && adduser -S spring-user -G spring-group && \
    chmod +x /entrypoint.sh && \
    apk add vim

USER spring-user
EXPOSE 8080

ENTRYPOINT ["/entrypoint.sh"]