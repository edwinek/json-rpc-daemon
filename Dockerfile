FROM openjdk:8
RUN apt update && \
    apt install openjdk-8-jre-headless \
                openjdk-8-jdk-headless -y