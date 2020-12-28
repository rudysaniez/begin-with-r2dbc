FROM openjdk:12.0.2

EXPOSE 7000

ADD ./target/review-reactive-api-0.0.1-SNAPSHOT.jar review-reactive-api.jar

RUN mkdir /etc/me
WORKDIR /etc/me/
VOLUME [ "/etc/me/" ]

ENTRYPOINT ["java","-jar","/review-reactive-api.jar"]