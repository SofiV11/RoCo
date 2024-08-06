# build
FROM maven:sapmachine as builder
WORKDIR /opt/app
COPY mvnw pom.xml ./
COPY ./src ./src
RUN mvn clean install
#COPY target/dependency ./lib

#COPY target/*.jar app.jar

#COPY target/dependency ./lib

#FROM amazoncorretto:21    eclipse-temurin:17-jre-jammy
FROM amazoncorretto:17
WORKDIR /opt/app
EXPOSE 8080
#ADD /target/*.jar app.jar
COPY --from=builder /opt/app/target/*.jar /opt/app/*.jar
#VOLUME /tmp
ENTRYPOINT [ "java","-jar","*.jar"]
#RUN adduser --system spring-boot && addgroup --system spring-boot && adduser spring-boot spring-boot

#COPY target/RoCo-0.0.1-SNAPSHOT.jar ./application.jar
#
#ENTRYPOINT [ "java","-cp","lib/*:./application.jar", "com.RoCo.RoCoApplication"]