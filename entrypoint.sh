#!/bin/sh
# Tomcat을 백그라운드로 실행
catalina.sh run &

# readflag 파일이 생길 때까지 대기 후 실행 권한 부여
while [ ! -f /usr/local/tomcat/webapps/ROOT/readflag ]; do
 sleep 1
done
chmod +x /usr/local/tomcat/webapps/ROOT/readflag

# Tomcat 프로세스가 끝날 때까지 대기
wait