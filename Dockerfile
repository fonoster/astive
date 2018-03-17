FROM alpine

ENV ASTIVE_VERSION 1.0.7
ENV ASTIVE_HOME=/opt/astive
ENV ASTIVE_APPS=/opt/astive/apps

WORKDIR /tmp

RUN wget https://github.com/fonoster/astivetoolkit/archive/v$ASTIVE_VERSION.tar.gz \
    && apk update \
    && apk add openjdk8 \
    && apk add maven \
    && tar xvf v$ASTIVE_VERSION.tar.gz \
    && cd astivetoolkit-$ASTIVE_VERSION \
    && ./assembly \
    && cd dist \
    && tar xvf astivetoolkit-server-$ASTIVE_VERSION.tar.gz \
    && mkdir -p $ASTIVE_HOME \
    && mv astivetoolkit-server-$ASTIVE_VERSION/* /opt/astive \
    && apk del maven \
    && apk del openjdk8 \
    && apk add openjdk8-jre-base \
    && rm -rf /var/cache/apk/* /tmp/astivetoolkit*

WORKDIR $ASTIVE_HOME

EXPOSE 4573
EXPOSE 4200
EXPOSE 4202

CMD ["/bin/sh", "-c", "./bin/astived start"]