FROM tomcat:latest
RUN rm -rf /usr/local/tomcat/webapps/*
COPY target/tokens_service-1.0.war /usr/local/tomcat/webapps/
RUN sed -i 's/port="8080"/port="8084"/' ${CATALINA_HOME}/conf/server.xml
RUN sed -i 's/port="8005"/port="8009"/' ${CATALINA_HOME}/conf/server.xml
RUN sed -i '/<\/Host>/i \    <Context path="" docBase="tokens_service-1.0.war"/>' ${CATALINA_HOME}/conf/server.xml
EXPOSE 8084
CMD ["catalina.sh", "run"]