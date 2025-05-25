#!/bin/bash
docker build -f Dockerfile.base -t ms-comi-jdk11:latest .
docker build -f Dockerfile.base.run -t ms-comi-jre11:latest .

docker build -f Dockerfile.auth --build-arg ENVIRONMENT=dev -t ms-comi-auth:latest .
docker build -f Dockerfile.user --build-arg ENVIRONMENT=dev -t ms-comi-user:latest .
docker build -f Dockerfile.gateway --build-arg ENVIRONMENT=dev -t ms-comi-gateway:latest .
docker build -f Dockerfile.comi --build-arg ENVIRONMENT=dev -t ms-comi-comi:latest .


docker tag ms-comi-jdk11 harbor.qihoo.net/tyzcb-comiai-ms-comi/ms-comi-jdk11:v0.1
docker push harbor.qihoo.net/tyzcb-comiai-ms-comi/ms-comi-jdk11:v0.1


docker tag ms-comi-jre11 harbor.qihoo.net/tyzcb-comiai-ms-comi/ms-comi-jre11:v0.1
docker push harbor.qihoo.net/tyzcb-comiai-ms-comi/ms-comi-jre11:v0.1



#docker tag ms-comi-auth harbor.qihoo.net/tyzcb-comiai-ms-comi/ms-comi-auth:v0.0.1
#docker push harbor.qihoo.net/tyzcb-comiai-ms-comi/ms-comi-auth:v0.0.1
#docker tag ms-comi-user harbor.qihoo.net/tyzcb-comiai-ms-comi/ms-comi-user:v0.0.1
#docker push harbor.qihoo.net/tyzcb-comiai-ms-comi/ms-comi-user:v0.0.1
#docker tag ms-comi-gateway harbor.qihoo.net/tyzcb-comiai-ms-comi/ms-comi-gateway:v0.0.1
#docker push harbor.qihoo.net/tyzcb-comiai-ms-comi/ms-comi-gateway:v0.0.1
#docker tag ms-comi-comi harbor.qihoo.net/tyzcb-comiai-ms-comi/ms-comi-comi:v0.0.1
#docker push harbor.qihoo.net/tyzcb-comiai-ms-comi/ms-comi-comi:v0.0.1