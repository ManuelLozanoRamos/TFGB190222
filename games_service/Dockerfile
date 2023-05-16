FROM tomcat:latest
RUN rm -rf /usr/local/tomcat/webapps/*
COPY target/games_service-1.0.war /usr/local/tomcat/webapps/
RUN sed -i 's/port="8080"/port="8083"/' ${CATALINA_HOME}/conf/server.xml
RUN sed -i 's/port="8005"/port="8008"/' ${CATALINA_HOME}/conf/server.xml
RUN sed -i '/<\/Host>/i \    <Context path="" docBase="games_service-1.0.war"/>' ${CATALINA_HOME}/conf/server.xml
EXPOSE 8083
CMD ["catalina.sh", "run"]