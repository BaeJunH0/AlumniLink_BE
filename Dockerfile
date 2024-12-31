FROM amazoncorretto:17
# FROM openjdk:17-jdk
ARG JAR_FILE=build/libs/*.jar
ARG KEY
ARG TOKEN_EXPIRE_LENGTH

# ENV로 설정하여 이후에 사용 가능
ENV KEY=$KEY
ENV TOKEN_EXPIRE_LENGTH=$TOKEN_EXPIRE_LENGTH

COPY ${JAR_FILE} my-project.jar
# COPY build/libs/*.jar my-project.jar
ENTRYPOINT ["java","-jar","/my-project.jar"]

RUN ln -snf /usr/share/zoneinfo/Asia/Seoul /etc/localtime