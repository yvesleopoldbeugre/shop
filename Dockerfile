FROM openjdk:17-jdk-slim-buster

EXPOSE 9000

WORKDIR /usr/app
COPY ./target/gestion-de-produits.jar /usr/app

CMD ["java", "-Dserver.address=0.0.0.0", "-jar", "gestion-de-produits.jar"]
