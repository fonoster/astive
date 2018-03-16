FROM java:openjdk-8

WORKDIR /etc
ENV ASTIVE_VERSION 1.0.6

ADD https://github.com/fonoster/astivetoolkit/archive/v$ASTIVE_VERSION.tar.gz /etc
RUN apt-get update \
    && apt-get install maven -y

RUN tar xvf v$ASTIVE_VERSION.tar.gz \
    && cd astivetoolkit-$ASTIVE_VERSION \
    && ./assembly \
    && cd dist \
    && tar xvf astivetoolkit-server-$ASTIVE_VERSION.tar.gz \
    && mv astivetoolkit-server-$ASTIVE_VERSION /opt/astive \
    && rm astivetoolkit-server-$ASTIVE_VERSION.tar.gz \
    && rm -rf /etc/astivetoolkit* \
    && apt-get remove maven -y

WORKDIR /opt/astive

EXPOSE 4573
EXPOSE 4200
EXPOSE 4202

CMD ["/bin/sh", "-c", "./bin/astived start"]



