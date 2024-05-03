FROM maven:latest

RUN mkdir /SonicServer
WORKDIR /SonicServer
COPY . .
EXPOSE 8080
CMD [ "mvn","spring-boot:run" ]