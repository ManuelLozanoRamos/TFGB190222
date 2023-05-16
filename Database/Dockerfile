FROM mysql:latest
ENV MYSQL_ROOT_PASSWORD=abcde12345abc
EXPOSE 3306
COPY DB.sql /docker-entrypoint-initdb.d/
