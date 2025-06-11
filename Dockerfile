FROM tomcat:10.1-jdk17-temurin

ENV TZ=Asia/Seoul

# 1. tomcat 사용자 생성 (uid=1001으로 명시)
RUN useradd -m -u 1001 tomcat

# 2. WAR 파일과 readflag 복사
COPY build/libs/Robotics-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war
COPY readflag /readflag

# 3. readflag를 실행 가능하도록 설정
RUN chmod 0111 /readflag

# 4. Tomcat 디렉토리 소유권 변경
RUN chown -R tomcat:tomcat /usr/local/tomcat

# 5. tomcat 유저로 전환
USER tomcat

# 6. 포트 노출 및 실행
EXPOSE 8080
CMD ["catalina.sh", "run"]