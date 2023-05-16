FROM tomcat:latest
RUN rm -rf /usr/local/tomcat/webapps/*
COPY target/reviews_service-1.0.war /usr/local/tomcat/webapps/
RUN sed -i 's/port="8080"/port="8081"/' ${CATALINA_HOME}/conf/server.xml
RUN sed -i 's/port="8005"/port="8006"/' ${CATALINA_HOME}/conf/server.xml
RUN sed -i '/<\/Host>/i \    <Context path="" docBase="reviews_service-1.0.war"/>' ${CATALINA_HOME}/conf/server.xml
EXPOSE 8081
CMD ["catalina.sh", "run"]