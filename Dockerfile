FROM alpine

ENV ASTIVE_VERSION 1.0.9
ENV ASTIVE_HOME=/opt/astive
ENV ASTIVE_APPS=/opt/astive/apps

WORKDIR /tmp

RUN wget https://github.com/fonoster/astive/archive/v$ASTIVE_VERSION.tar.gz \
    && apk update \
    && apk add openjdk8 \
    && apk add maven \
    && tar xvf v$ASTIVE_VERSION.tar.gz \
    && cd astive-$ASTIVE_VERSION \
    && ./assembly \
    && cd dist \
    && tar xvf astive-server-$ASTIVE_VERSION.tar.gz \
    && mkdir -p $ASTIVE_HOME \
    && mv astive-server-$ASTIVE_VERSION/* /opt/astive \
    && apk del maven \
    && apk del openjdk8 \
    && apk add openjdk8-jre-base \
    && rm -rf /var/cache/apk/* /tmp/astive*

WORKDIR $ASTIVE_HOME

EXPOSE 4573
EXPOSE 4200
EXPOSE 4202

CMD ["/bin/sh", "-c", "./bin/astived start"]