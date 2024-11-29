# 베이스 이미지
FROM java-17-amazon-corretto-jdk

# 작업 디렉토리 설정
WORKDIR /AlumniLink_BE

# 애플리케이션 Jar 파일 복사
COPY build/libs/webBoard-0.0.1-SNAPSHOT.jar app.jar

# 컨테이너 실행 시 실행될 명령어
CMD ["java", "-jar", "app.jar"]