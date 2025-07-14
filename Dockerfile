# 1. 빌더 스테이지
FROM gradle:8.13-jdk17 AS builder
WORKDIR /home/gradle/src
COPY . .
RUN gradle clean build --no-daemon -x test

# 2. 런타임 스테이지
FROM tomcat:10.1-jdk17-temurin

ENV TZ=Asia/Seoul

# tomcat 유저 생성 (uid=1001)
RUN useradd -m -u 1001 tomcat

# WAR 파일 복사
COPY --from=builder /home/gradle/src/build/libs/*.war /usr/local/tomcat/webapps/ROOT.war
COPY readflag /readflag
RUN chmod 0111 /readflag
RUN chown -R tomcat:tomcat /usr/local/tomcat
USER tomcat
EXPOSE 8080
CMD ["catalina.sh", "run"]