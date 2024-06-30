FROM amd64/amazoncorretto:17
WORKDIR /app
COPY ./build/libs/seonyakServer-0.0.1-SNAPSHOT.jar /app/SEONYAK.jar
CMD ["java", "-Duser.timezone=Asia/Seoul", "-jar", "-Dspring.profiles.active=dev", "SEONYAK.jar"]