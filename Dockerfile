FROM openjdk:11-jdk

COPY build/libs/com.app.booking-1.0-SNAPSHOT.jar booking.jar
COPY src/main/resources/prod.yml application.yml

EXPOSE 8080

ENTRYPOINT ["/bin/sh", "-c" , "echo 127.0.0.1 $HOSTNAME >> /etc/hosts && java -jar booking.jar application.yml"]