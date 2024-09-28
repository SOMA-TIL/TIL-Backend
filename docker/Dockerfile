FROM openjdk:17

ARG PROFILE=${PROFILE:-local}
ARG PROJECT_NAME=${PROJECT_NAME}
ARG JAR_FILE=${PROJECT_NAME}/build/libs/*.jar

COPY ${JAR_FILE} ${PROJECT_NAME}.jar

ENTRYPOINT ["java", "-jar", "${PROJECT_NAME}.jar", "--spring.profiles.active=${PROFILE}"]
