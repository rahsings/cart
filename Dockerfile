FROM openjdk:24-slim-bookworm AS builder
WORKDIR /app

FROM postgres:15 AS postgres

ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=postgres
ENV POSTGRES_DB=cart

COPY create-db.sh /docker-entrypoint-initdb.d/create-db.sh
RUN chmod +x /docker-entrypoint-initdb.d/create-db.sh

FROM openjdk:24-slim-bookworm
WORKDIR /app

COPY /build/libs/cart-0.0.1-SNAPSHOT.jar app.jar

COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

EXPOSE 8089

ENTRYPOINT ["/wait-for-it.sh", "postgres:5432", "--", "java", "-jar", "app.jar"]