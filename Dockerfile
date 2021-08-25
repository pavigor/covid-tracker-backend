FROM alpine:latest
WORKDIR /app
RUN apk --no-cache add openjdk11
RUN adduser -D java_user && chown -R java_user /app
COPY ./target/*.jar /app/backend.jar
USER java_user
ENTRYPOINT ["java", "-jar", "/app/backend.jar"]