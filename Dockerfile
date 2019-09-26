FROM adaptris/interlok:latest-zulu-alpine

EXPOSE 8080
EXPOSE 5555

RUN rm -rf /opt/interlok/lib/adp-*.jar

COPY lib /opt/interlok/lib
COPY webapps /opt/interlok/webapps
COPY config /opt/interlok/config
