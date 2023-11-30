#!/bin/bash
mvn clean install -Dmaven.test.skip=true
cp ./scan-server-business/target/scan-server-business-1.0-SNAPSHOT.jar ./docker/app.jar
docker build -t registry.baidubce.com/gitee-poc/gitee-scan-server-new:1.0.1-$1 ./docker
rm -f ./docker/app.jar
docker push registry.baidubce.com/gitee-poc/gitee-scan-server-new:1.0.1-$1
