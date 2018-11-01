#! /usr/bin/env bash
APP_NAME=json-rpc-daemon
CONTAINER_NAME=json_rpc_container
docker stop $CONTAINER_NAME
docker rm $CONTAINER_NAME
docker run --rm --name $CONTAINER_NAME -tid  -p 8080:8080 -v "$(pwd)":/src edwinek/openjdk-gradle-springboot:1.0.0 bash
docker exec -ti $CONTAINER_NAME bash -c "/src/gradlew clean bootJar -p /src \
                                            && ln -s /opt/$APP_NAME/$APP_NAME.jar /etc/init.d/$APP_NAME \
                                            && chmod 755 /etc/init.d/$APP_NAME"
docker exec -ti $CONTAINER_NAME bash