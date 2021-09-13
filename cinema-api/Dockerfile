FROM openjdk:11

LABEL source="https://github.com/coelhocaique/cinema-api"\
      maintainer="Caique Coelho"

ADD ./build/distributions/*.zip /cinema-api.zip

RUN unzip cinema-api.zip && \
   rm -rf *.zip && \
   mv cinema-api-* cinema-api

EXPOSE 80 443

ENV JAVA_OPTS="-Djdk.tls.client.protocols=TLSv1.2"

ENTRYPOINT ["/cinema-api/bin/cinema-api"]
