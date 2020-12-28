FROM openjdk:12.0.2


COPY /maven/*.jar /usr/src/me/
COPY /maven/bin/review-reactive-api /usr/bin/review-reactive-api

RUN chmod 0755 /usr/bin/review-reactive-api && \
    mkdir /etc/me/ && \
    useradd -M me -u 42

WORKDIR /etc/me/

USER 42 
CMD ["review-reactive-api"]

VOLUME [ "/etc/me/" ]
EXPOSE 7000
