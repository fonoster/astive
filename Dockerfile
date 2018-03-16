FROM openjdk:8-jdk

ENV ASTIVE_VERSION 1.0.7
ENV ASTIVE_HOME=/opt/astive
ENV ASTIVE_APPS=/opt/astive/apps

WORKDIR /etc

RUN wget https://github.com/fonoster/astivetoolkit/archive/v$ASTIVE_VERSION.tar.gz \
    && apt-get update \
    && apt-get install maven -y \
    && tar xvf v$ASTIVE_VERSION.tar.gz \
    && cd astivetoolkit-$ASTIVE_VERSION \
    && ./assembly \
    && cd dist \
    && tar xvf astivetoolkit-server-$ASTIVE_VERSION.tar.gz \
    && mv astivetoolkit-server-$ASTIVE_VERSION /opt/astive \
    && rm -rf astivetoolkit-server-$ASTIVE_VERSION.tar.gz /etc/astivetoolkit* \
    && apt-get remove maven -y

WORKDIR $ASTIVE_HOME

EXPOSE 4573
EXPOSE 4200
EXPOSE 4202

CMD ["/bin/sh", "-c", "./bin/astived start"]
