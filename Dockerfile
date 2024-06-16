FROM postgres:latest
ENV POSTGRES_DB mydb
ENV POSTGRES_USER myuser
ENV POSTGRES_PASSWORD mypassword
COPY ../Diploma_UINS-ui%203/init.sql /docker-entrypoint-initdb.d/init.sql