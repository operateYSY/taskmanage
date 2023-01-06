FROM openjdk:8-jre

# service info
ENV APP_HOME /app
ENV SERVICE_NAME task-manage
ENV SERVICE_PORT 8081
ENV JAR_NAME task-0.0.1-SNAPSHOT
ENV LOG_PATH ${APP_HOME}/logs
ENV CONFIG_PATH ${APP_HOME}/config
ENV JAVA_OPTS ""
ENV LANG C.UTF-8
ENV LANGUAGE en_US:en
ENV LC_ALL C.UTF-8

WORKDIR ${APP_HOME}

RUN mkdir -p $LOG_PATH
RUN mkdir -p $CONFIG_PATH

VOLUME $LOG_PATH
VOLUME $CONFIG_PATH

ADD /data/task_manager/${JAR_NAME}.jar ${APP_HOME}/${JAR_NAME}.jar

EXPOSE ${SERVICE_PORT}
ENTRYPOINT ["/bin/sh","-c","java $JAVA_OPTS -jar /app/${JAR_NAME}.jar","--spring.config.location=${CONFIG_PATH}/application.yml"]
