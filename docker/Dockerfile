FROM registry.baidubce.com/gitee-poc/tomcat:7-jdk8-openjdk-slim
VOLUME /scan/server/log
EXPOSE 8080

ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

COPY /OUTPUT/target/gitee-scan-server.jar /app.jar
COPY /CODE/enterprise__Gitee-Scan/gitee-scan-2.0-server/docker/less.deb /usr/local/less/less.deb

ENV JVM_OPT="-XX:MaxRAMPercentage=75.0 -XX:InitialRAMPercentage=75.0 -XX:MinRAMPercentage=75.0 -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=9001"
ENV PROFILE_ACTIVE="test"

RUN apt-get install -y /usr/local/less/less.deb
RUN rm -rf /usr/local/less/
CMD java -jar ${JVM_OPT} /app.jar --spring.profiles.active=${PROFILE_ACTIVE}
