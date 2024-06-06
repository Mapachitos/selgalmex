FROM openjdk:8-alpine

COPY target/uberjar/selgalmex.jar /selgalmex/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/selgalmex/app.jar"]
