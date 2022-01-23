# syntax=docker/dockerfile:1

FROM gradle:7.3.3-jdk17-alpine

EXPOSE 8080

WORKDIR /app

COPY . ./

RUN gradle build

CMD ["gradle", "run"]