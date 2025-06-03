FROM tomcat:10.1-jdk17-temurin

# 작업 디렉토리 설정
WORKDIR /app

# WAR 파일 복사 (Spring Boot 3.x에서는 -plain.war가 아닌 일반 .war 사용)
COPY build/libs/playground-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

# 8080 포트 노출
EXPOSE 8080

# Tomcat 실행
CMD ["catalina.sh", "run"] 