FROM amd64/amazoncorretto:17

# 작업 디렉터리 설정
WORKDIR /app

# 애플리케이션 JAR 파일 복사
COPY ./build/libs/seonyakServer-0.0.1-SNAPSHOT.jar /app/SEONYAK.jar

# 컨테이너 실행 시 Java 애플리케이션 실행
CMD ["java", "-Duser.timezone=Asia/Seoul", "-jar", "-Dspring.profiles.active=dev", "SEONYAK.jar"]