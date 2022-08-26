FROM amazoncorretto:8-al2-jdk
WORKDIR /app
COPY ./target/curso-kubernetes.jar .
EXPOSE 8001
ENTRYPOINT ["java","-jar","curso-kubernetes.jar"]