FROM openjdk:17

ARG PROFILE=${PROFILE:-local}
ARG JAR_FILE=til-api/build/libs/*.jar

COPY ${JAR_FILE} til-api.jar

ENTRYPOINT ["java", "-jar","til-api.jar", "--spring.profiles.active=${PROFILE}"]
