#образ
FROM amazoncorretto:21-al2023-headful

#нужно скопировать в образ файлы с расширением jar назовем app jar
#COPY target/*.jar app.jar
ADD /target/*.jar app.jar
#COPY target/dependency ./lib
#EXPOSE 8080
ENTRYPOINT [ "java","-jar","/app.jar"]
#RUN adduser --system spring-boot && addgroup --system spring-boot && adduser spring-boot spring-boot
#
#USER spring-boot
#
#WORKDIR /app
#
#COPY target/dependency ./lib
#COPY target/RoCo-0.0.1-SNAPSHOT.jar ./application.jar
#
#ENTRYPOINT [ "java","-cp","lib/*:./application.jar", "com.RoCo.RoCoApplication"]
